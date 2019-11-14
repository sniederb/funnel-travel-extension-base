package ch.want.funnel.extension.util;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ch.want.funnel.extension.model.Booking;
import ch.want.funnel.extension.model.Trip;

public class ExtensionDataAccess {

    private final Booking booking;

    public ExtensionDataAccess(final Booking booking) {
        this.booking = booking;
    }

    /**
     * Search in all extension data for a 'remarks' array, and therein an entry with the provided {@code remarkPrefix}.
     * If found, the entry value string is returned.
     */
    public static Optional<String> searchAllExtensionDataForRemark(final Trip trip, final String remarkPrefix) {
        final List<JsonNode> allExtensionData = trip.getBookings().stream()
            .filter(bk -> bk.getExtensionData() != null)
            .map(Booking::getExtensionData)
            .collect(Collectors.toList());
        for (final JsonNode extensionData : allExtensionData) {
            for (final Iterator<String> iterator = extensionData.fieldNames(); iterator.hasNext();) {
                final String extensionClassname = iterator.next();
                final Optional<String> result = searchExtensionSubnode(extensionData.get(extensionClassname), remarkPrefix);
                if (result.isPresent()) {
                    return result;
                }
            }
        }
        return Optional.empty();
    }

    private static Optional<String> searchExtensionSubnode(final JsonNode extensionSubnode, final String remarkPrefix) {
        final JsonNode remarks = extensionSubnode.get("remarks");
        if ((remarks != null) && remarks.isArray()) {
            final ArrayNode remarksArray = (ArrayNode) remarks;
            for (final JsonNode value : remarksArray) {
                if (!value.isNull() && value.asText().startsWith(remarkPrefix)) {
                    return Optional.of(value.asText());
                }
            }
        }
        return Optional.empty();
    }

    public Optional<JsonNode> getNode(final String extensionClassname, final String key) {
        return Optional.ofNullable(booking.getExtensionData())
            .map(json -> json.get(extensionClassname))
            .map(node -> node.get(key));
    }

    /**
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
        ObjectNode extensionData = (ObjectNode) booking.getExtensionData();
        if (extensionData == null) {
            extensionData = JsonNodeFactory.instance.objectNode();
            booking.setExtensionData(extensionData);
        }
        ObjectNode internalData = (ObjectNode) extensionData.get(extensionClassname);
        if ((internalData == null) || (internalData.isNull())) {
            internalData = JsonNodeFactory.instance.objectNode();
            extensionData.set(extensionClassname, internalData);
        }
        return internalData;
    }
}
