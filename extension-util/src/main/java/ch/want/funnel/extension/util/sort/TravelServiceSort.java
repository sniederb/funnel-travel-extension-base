package ch.want.funnel.extension.util.sort;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import ch.want.funnel.extension.model.TravelServiceType;

public class TravelServiceSort<T> {

    private final TravelServiceSortKeyTranslator<T> sortKeyTranslator;
    private final Map<TravelServiceSortKey, T> serviceMap = new HashMap<>();

    public TravelServiceSort(final TravelServiceSortKeyTranslator<T> sortKeyTranslator) {
        this.sortKeyTranslator = sortKeyTranslator;
    }

    /**
     * Sort a list of T using an internal {@link TravelServiceSortKey}.
     */
    public List<T> sort(final List<T> services) {
        serviceMap.clear();
        for (final T service : services) {
            serviceMap.put(sortKeyTranslator.getSortKey(service), service);
        }
        final List<TravelServiceSortKey> sortKeys = new ArrayList<>(serviceMap.keySet());
        sortKeys.stream()
            .filter(TravelServiceSortKey::isNeedsTime)
            .forEach(key -> setSortKeyTime(key));
        final Comparator<TravelServiceSortKey> byDepartureAndThenLastResort = Comparator
            .comparing(TravelServiceSortKey::getDeparture, Comparator.nullsLast(Comparator.naturalOrder()))
            .thenComparing(Comparator.comparing(TravelServiceSortKey::getLastResortSortProperty, Comparator.nullsLast(Comparator.naturalOrder())));
        return sortKeys.stream()
            .sorted(byDepartureAndThenLastResort)
            .map(serviceMap::get)
            .toList();
    }

    private void setSortKeyTime(final TravelServiceSortKey travelServiceSortKey) {
        // note that sortkeys with needsTime = true +always+ have a departure date set
        if (travelServiceSortKey.getServiceType() == TravelServiceType.HOTEL) {
            setSortKeyTimeForHotel(travelServiceSortKey);
        } else if (travelServiceSortKey.getServiceType() == TravelServiceType.CARRENTAL) {
            setSortKeyTimeForCarRental(travelServiceSortKey);
        } else if (travelServiceSortKey.getServiceType() == TravelServiceType.MISC) {
            if (travelServiceSortKey.getMiscServiceType() == MiscServiceType.TRANSFER) {
                setSortKeyTimeForTransfer(travelServiceSortKey, sortKeyTranslator.getSameDayItems(travelServiceSortKey.getDeparture(), serviceMap.values()));
            } else if (travelServiceSortKey.getMiscServiceType() == MiscServiceType.EVENT) {
                setSortKeyTimeForEvent(travelServiceSortKey);
            } else if (travelServiceSortKey.getMiscServiceType() == MiscServiceType.INSURANCE) {
                setSortKeyTimeForInsurance(travelServiceSortKey);
            } else {
                setSortKeyTimeForMisc(travelServiceSortKey, sortKeyTranslator.getSameDayItems(travelServiceSortKey.getDeparture(), serviceMap.values()));
            }
        }
        travelServiceSortKey.setNeedsTime(false);
    }

    /**
     * Hotel is always the last entry of the day, accepting the mistake for exceptions of hotel checkin > activity.
     */
    private void setSortKeyTimeForHotel(final TravelServiceSortKey travelServiceSortKey) {
        travelServiceSortKey.setDeparture(travelServiceSortKey.getDeparture().toLocalDate().atTime(LocalTime.MAX));
    }

    /**
     * Rental cars go before MISC events and hotels, but after transfers.
     */
    private void setSortKeyTimeForCarRental(final TravelServiceSortKey travelServiceSortKey) {
        travelServiceSortKey.setDeparture(travelServiceSortKey.getDeparture().toLocalDate().atTime(LocalTime.MAX.minusMinutes(3)));
    }

    /**
     * Transfers need to be between hotel / ship and flight.
     */
    private void setSortKeyTimeForTransfer(final TravelServiceSortKey travelServiceSortKey, final Collection<T> sameDayServices) {
        final boolean hasSameDayHotelCheckin = sameDayServices.stream()
            .anyMatch(srv -> sortKeyTranslator.getTravelServiceType(srv) == TravelServiceType.HOTEL ||
                sortKeyTranslator.getTravelServiceType(srv) == TravelServiceType.SHIP);
        final Optional<LocalDateTime> transportServiceDeparture = sameDayServices.stream()
            .filter(srv -> sortKeyTranslator.getTravelServiceType(srv) == TravelServiceType.FLIGHT ||
                sortKeyTranslator.getTravelServiceType(srv) == TravelServiceType.TRAIN)
            .findFirst()
            .map(srv -> sortKeyTranslator.getFirstSegmentDeparture(srv).orElse(null));
        if (hasSameDayHotelCheckin) {
            // sort just after flight, ideally before further items like car rental of events
            transportServiceDeparture.ifPresent(dtm -> travelServiceSortKey.setDeparture(dtm.plusMinutes(1)));
        } else {
            // looks like the return trip, sort just before flight
            transportServiceDeparture.ifPresent(dtm -> travelServiceSortKey.setDeparture(dtm.minusMinutes(1)));
        }
        // note that if there is no flight or train on the transfer day, the transfer will be the first item of the day, which is fitting
        // for over-night transports
    }

    /**
     * Events without time should be before general MISC and before hotel.
     */
    private void setSortKeyTimeForEvent(final TravelServiceSortKey travelServiceSortKey) {
        travelServiceSortKey.setDeparture(travelServiceSortKey.getDeparture().toLocalDate().atTime(LocalTime.MAX.minusMinutes(2)));
    }

    /**
     * Insurances are always sorted to the bottom.
     */
    private void setSortKeyTimeForInsurance(final TravelServiceSortKey travelServiceSortKey) {
        travelServiceSortKey.setDeparture(null);
    }

    /**
     * General-purpose MISC services without any time information are listed before hotels. The one exception is MISC services with a
     * reference number matching a same-day flight reference number, e.g. ZD8L73_1 and ZD8L73. Such a service will be sorted directly after
     * the flight.
     */
    private void setSortKeyTimeForMisc(final TravelServiceSortKey travelServiceSortKey, final Collection<T> sameDayServices) {
        serviceMap.entrySet().stream()
            .filter(entry -> sameDayServices.contains(entry.getValue()))
            .filter(entry -> entry.getKey().getServiceType() == TravelServiceType.FLIGHT)
            .filter(entry -> StringUtils.startsWith(travelServiceSortKey.getReferenceNumber(), entry.getKey().getReferenceNumber()))
            .findFirst()
            .ifPresentOrElse(entry -> {
                travelServiceSortKey.setDeparture(entry.getKey().getDeparture().plusMinutes(1));
                travelServiceSortKey.setLastResortSortProperty(travelServiceSortKey.getReferenceNumber());
            }, () -> {
                travelServiceSortKey.setDeparture(travelServiceSortKey.getDeparture().toLocalDate().atTime(LocalTime.MAX.minusMinutes(1)));
            });
    }
}
