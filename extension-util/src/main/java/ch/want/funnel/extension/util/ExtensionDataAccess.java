package ch.want.funnel.extension.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ch.want.funnel.extension.model.Booking;
import ch.want.funnel.extension.model.TravelService;
import ch.want.funnel.extension.model.Trip;

public class ExtensionDataAccess {

    public static final String REMARKS_NODE_NAME = "remarks";
    private final Booking booking;
    private final TravelService service;

    /**
     * Create instance to access booking-level extension data
     *
     * @param booking
     */
    public ExtensionDataAccess(final Booking booking) {
        this.booking = booking;
        this.service = null;
    }

    /**
     * Create instance to access booking-level extension data
     *
     * @param booking
     */
    public ExtensionDataAccess(final TravelService service) {
        this.booking = null;
        this.service = service;
    }

    /**
     * Search in all extension data for a 'remarks' array, and therein an entry with the provided {@code remarkPrefix}.
     * If found, the entry value string is returned.
     */
    @Deprecated
    public static Optional<String> searchAllExtensionDataForRemark(final Trip trip, final String remarkPrefix) {
        return searchAllExtensionDataForRemark(trip, remarkPrefix, null);
    }

    /**
     * Same as {@link #searchAllExtensionDataForRemark(Trip, String)} without the pax tattoo, but if a {@code paxTattoo}
     * is provided, this method searches for entries with a {@code ,Pn} suffix. For valid "P"-associations see
     * {@link DataUtils#mapAssociationToNumbers(String)}
     */
    @Deprecated
    public static Optional<String> searchAllExtensionDataForRemark(final Trip trip, final String remarkPrefix, final String paxTattoo) {
        final List<JsonNode> allExtensionData = trip.getBookings().stream()
            .filter(bk -> bk.getExtensionData() != null)
            .map(Booking::getExtensionData)
            .toList();
        for (final JsonNode extensionData : allExtensionData) {
            for (final Iterator<String> iterator = extensionData.fieldNames(); iterator.hasNext();) {
                final String extensionClassname = iterator.next();
                final Optional<String> result = searchExtensionSubnode(extensionData.get(extensionClassname), remarkPrefix, paxTattoo);
                if (result.isPresent()) {
                    return result;
                }
            }
        }
        return Optional.empty();
    }

    /**
     * If {@code replaceLinePrefix} is not empty, this method will look for an existing remark line
     * {@code startsWith(replaceLinePrefix)}, and if found remove that line.
     */
    public void addRemark(final String extensionName, final String remark, final String replaceLinePrefix, final String paxTattoo) {
        final ArrayNode remarksArrayNode = (ArrayNode) getOrCreateNode(extensionName, REMARKS_NODE_NAME, true);
        if (StringUtils.isNotBlank(replaceLinePrefix)) {
            for (final Iterator<JsonNode> iterator = remarksArrayNode.elements(); iterator.hasNext();) {
                final JsonNode remarkNode = iterator.next();
                if (getMatchingRemarkContent(remarkNode, replaceLinePrefix, paxTattoo).isPresent()) {
                    iterator.remove();
                }
            }
        }
        remarksArrayNode.add(remark);
    }

    /**
     * Return all remarks of the booking as well as all contained service remarks.
     *
     * @deprecated Use the more specific {@link #getAllBookingRemarks()} or {@link #getAllServiceRemarks()}
     * @return
     */
    @Deprecated
    public List<String> getAllRemarks() {
        final List<String> result = new ArrayList<>();
        if (booking != null) {
            result.addAll(getAllBookingRemarks());
            booking.getTravelservices().stream()
                .map(TravelService::getExtensionData)
                .map(this::getRemarks)
                .forEach(result::addAll);
        } else {
            return getAllServiceRemarks();
        }
        return result;
    }

    public List<String> getAllBookingRemarks() {
        return getRemarks(booking.getExtensionData());
    }

    public List<String> getAllServiceRemarks() {
        return getRemarks(service.getExtensionData());
    }

    private List<String> getRemarks(final JsonNode extensionData) {
        if (extensionData == null) {
            return Collections.emptyList();
        }
        final List<String> remarks = new ArrayList<>();
        for (final Iterator<Map.Entry<String, JsonNode>> iterator = extensionData.fields(); iterator.hasNext();) {
            final Map.Entry<String, JsonNode> entry = iterator.next();
            final JsonNode remarksNode = entry.getValue().get(REMARKS_NODE_NAME);
            if (remarksNode != null && remarksNode.isArray()) {
                for (final JsonNode value : remarksNode) {
                    remarks.add(value.asText());
                }
            }
        }
        return remarks;
    }

    private static Optional<String> searchExtensionSubnode(final JsonNode extensionSubnode, final String remarkPrefix, final String paxTattoo) {
        final JsonNode remarks = extensionSubnode.get(REMARKS_NODE_NAME);
        if ((remarks != null) && remarks.isArray()) {
            final ArrayNode remarksArray = (ArrayNode) remarks;
            for (final JsonNode value : remarksArray) {
                final Optional<String> remarkContent = getMatchingRemarkContent(value, remarkPrefix, paxTattoo);
                if (remarkContent.isPresent()) {
                    return remarkContent;
                }
            }
        }
        return Optional.empty();
    }

    private static Optional<String> getMatchingRemarkContent(final JsonNode remarkNode, final String remarkPrefix, final String paxTattoo) {
        if (remarkNode == null || remarkNode.isNull() || !remarkNode.isTextual()) {
            return Optional.empty();
        }
        final String[] remarkLineParts = remarkNode.asText().split("[,;][pP]");
        if (!remarkLineParts[0].startsWith(remarkPrefix)) {
            return Optional.empty();
        }
        if (remarkLineParts.length > 1) {
            final boolean hasTattooMatch = DataUtils.mapAssociationToNumbers(remarkLineParts[1]).stream()//
                .map(i -> i.toString())// NOSONAR
                .anyMatch(s -> Objects.equals(s, paxTattoo));
            if (!hasTattooMatch) {
                return Optional.empty();
            }
        }
        return Optional.of(remarkLineParts[0]);
    }

    public Optional<JsonNode> getNode(final String extensionClassname, final String key) {
        final JsonNode extensionData = service == null ? booking.getExtensionData() : service.getExtensionData();
        return Optional.ofNullable(extensionData)
            .map(json -> json.get(extensionClassname))
            .map(node -> node.get(key));
    }

    /**
     * Retrieve the current {@link JsonNode} for a given {@code key}. If no node exists, a new one is created.
     * Beware that this method might return a {@link NullNode}.
     *
     * @param extensionClassname
     * @param key
     * @param array
     *            Only relevant if there is no current node. If true, an ArrayNode is created, otherwise an ObjectNode
     * @return
     */
    public JsonNode getOrCreateNode(final String extensionClassname, final String key, final boolean array) {
        return getNode(extensionClassname, key)
            .orElseGet(() -> {
                final JsonNode newNode = array ? JsonNodeFactory.instance.arrayNode() : JsonNodeFactory.instance.objectNode();
                setNode(extensionClassname, key, newNode);
                return newNode;
            });
    }

    public ExtensionDataAccess setNode(final String extensionClassname, final String key, final JsonNode node) {
        final ObjectNode rootNode = getOrCreate(extensionClassname);
        rootNode.set(key, node);
        return this;
    }

    public ExtensionDataAccess setValue(final String extensionClassname, final String key, final String value) {
        final ObjectNode rootNode = getOrCreate(extensionClassname);
        rootNode.put(key, value);
        return this;
    }

    private ObjectNode getOrCreate(final String extensionClassname) {
        JsonNode extensionData = service == null ? booking.getExtensionData() : service.getExtensionData();
        if ((extensionData == null) || !extensionData.isObject()) {
            extensionData = JsonNodeFactory.instance.objectNode();
            if (service == null) {
                booking.setExtensionData(extensionData);
            } else {
                service.setExtensionData(extensionData);
            }
        }
        ObjectNode internalData = (ObjectNode) extensionData.get(extensionClassname);
        if ((internalData == null) || !internalData.isObject()) {
            internalData = JsonNodeFactory.instance.objectNode();
            ((ObjectNode) extensionData).set(extensionClassname, internalData);
        }
        return internalData;
    }
}
