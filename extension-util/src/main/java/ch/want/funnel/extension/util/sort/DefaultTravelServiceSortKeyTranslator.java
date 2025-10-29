package ch.want.funnel.extension.util.sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.want.funnel.extension.model.SingleSegment;
import ch.want.funnel.extension.model.TransportSegment;
import ch.want.funnel.extension.model.TravelService;
import ch.want.funnel.extension.model.TravelServiceType;

public class DefaultTravelServiceSortKeyTranslator implements TravelServiceSortKeyTranslator<TravelService> {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultTravelServiceSortKeyTranslator.class);

    @Override
    public TravelServiceSortKey getSortKey(final TravelService obj) {
        TravelServiceSortKey result;
        if (TravelServiceType.hasSegments(obj.getTravelServiceType())) {
            result = new TravelServiceSortKey(obj.getTravelServiceType(), MiscServiceType.DEFAULT, obj.getReferenceNumber(), obj.getUuid().toString());
            initFromSegment(obj, result);
        } else {
            result = new TravelServiceSortKey(obj.getTravelServiceType(), getMiscServiceType(obj), obj.getReferenceNumber(), obj.getUuid().toString());
            initFromUnsegmented(obj, result);
        }
        return result;
    }

    @Override
    public Collection<TravelService> getSameDayItems(final LocalDateTime departure, final Collection<TravelService> allItems) {
        return allItems.stream()
            .filter(srv -> Objects.equals(srv.getDeparturedate(), departure.toLocalDate()))
            .toList();
    }

    @Override
    public TravelServiceType getTravelServiceType(final TravelService obj) {
        return obj.getTravelServiceType();
    }

    @Override
    public Optional<LocalDateTime> getFirstSegmentDeparture(final TravelService obj) {
        return obj.getSegmentedLegs().stream()
            .flatMap(l -> l.getSegments().stream())
            .map(TransportSegment::getDeparturetime)
            .findFirst();
    }

    private MiscServiceType getMiscServiceType(final TravelService travelService) {
        final String serviceCode = Optional.ofNullable(travelService.getSingleSegment())
            .map(SingleSegment::getServiceTypeCode)
            .orElse("");
        if (SingleSegment.SERVICE_TYPECODE_TRANSFER.equalsIgnoreCase(serviceCode)) {
            return MiscServiceType.TRANSFER;
        }
        if (SingleSegment.SERVICE_TYPECODE_EVENT.equalsIgnoreCase(serviceCode)) {
            return MiscServiceType.EVENT;
        }
        if (SingleSegment.SERVICE_TYPECODE_INSURANCE.equalsIgnoreCase(serviceCode)) {
            return MiscServiceType.INSURANCE;
        }
        return MiscServiceType.DEFAULT;
    }

    private void initFromUnsegmented(final TravelService travelService, final TravelServiceSortKey sortKey) {
        if (travelService.getSingleSegment() != null) {
            final LocalDate departureDate = travelService.getDeparturedate();
            if ((departureDate != null)) {
                sortKey.setDeparture(departureDate.atStartOfDay());
                sortKey.setNeedsTime(true);
                final String timeExpression = travelService.getSingleSegment().getStartTime();
                if ((timeExpression != null) && (unsegmentedTimeIsBinding(travelService))) {
                    try {
                        final DateTimeFormatter timeFormatter = getMostLikeyDateTimeFormatter(timeExpression);
                        final LocalTime tm = LocalTime.parse(timeExpression.toUpperCase(), timeFormatter);
                        sortKey.setDeparture(departureDate.atTime(tm));
                        sortKey.setNeedsTime(false);
                    } catch (final DateTimeParseException ex) {
                        LOG.debug("Failed to parse time expression: {}, error was: {}", timeExpression, ex.getMessage());
                    }
                }
            }
        } else {
            sortKey.setDeparture(null);
            sortKey.setNeedsTime(false);
        }
    }

    private DateTimeFormatter getMostLikeyDateTimeFormatter(final String timeExpression) {
        if (timeExpression.indexOf(' ') >= 0) {
            return DateTimeFormatter.ofPattern("h:mm[:ss] a", Locale.US);
        }
        return DateTimeFormatter.ofPattern("H:mm[:ss]", Locale.US);
    }

    private void initFromSegment(final TravelService travelService, final TravelServiceSortKey sortKey) {
        travelService.getSegmentedLegs().stream()
            .flatMap(l -> l.getSegments().stream())
            .findFirst()
            .ifPresent(seg -> {
                sortKey.setDeparture(seg.getDeparturetime());
                sortKey.setNeedsTime(false);
            });
    }

    /**
     * Returns false for service types where a "start time" is an indicative "not before", such as a hotel check-in or a rental car pick up.
     */
    private boolean unsegmentedTimeIsBinding(final TravelService travelService) {
        return travelService.getTravelServiceType() != TravelServiceType.HOTEL &&
            travelService.getTravelServiceType() != TravelServiceType.CARRENTAL;
    }
}
