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

import ch.want.funnel.extension.model.TravelServiceType;

public class TravelServiceSort<T> {

    private final TravelServiceSortKeyTranslator<T> sortKeyTranslator;

    public TravelServiceSort(final TravelServiceSortKeyTranslator<T> sortKeyTranslator) {
        this.sortKeyTranslator = sortKeyTranslator;
    }

    /**
     * Sort a list of T using an internal {@link TravelServiceSortKey}.
     */
    public List<T> sort(final List<T> services) {
        final Map<TravelServiceSortKey, T> serviceMap = new HashMap<>();
        for (final T service : services) {
            serviceMap.put(sortKeyTranslator.getSortKey(service), service);
        }
        final List<TravelServiceSortKey> sortKeys = new ArrayList<>(serviceMap.keySet());
        Optional<TravelServiceSortKey> keyNeedingTime = sortKeys.stream()
            .filter(key -> key.isNeedsTime())
            .findFirst();
        while (keyNeedingTime.isPresent()) {
            setSortKeyTime(keyNeedingTime.get(), serviceMap.values());
            keyNeedingTime = sortKeys.stream()
                .filter(key -> key.isNeedsTime())
                .findFirst();
        }
        return sortKeys.stream()
            .sorted(
                Comparator.nullsLast(Comparator.comparing(TravelServiceSortKey::getDeparture).thenComparing(TravelServiceSortKey::getLastResortSortProperty)))
            .map(serviceMap::get)
            .toList();
    }

    private void setSortKeyTime(final TravelServiceSortKey travelServiceSortKey, final Collection<T> services) {
        // note that sortkeys with needsTime = true +always+ have a departure date set
        if (travelServiceSortKey.getServiceType() == TravelServiceType.HOTEL) {
            setSortKeyTimeForHotel(travelServiceSortKey);
        } else if (travelServiceSortKey.getServiceType() == TravelServiceType.CARRENTAL) {
            setSortKeyTimeForCarRental(travelServiceSortKey);
        } else if (travelServiceSortKey.getServiceType() == TravelServiceType.MISC) {
            if (travelServiceSortKey.getMiscServiceType() == MiscServiceType.TRANSFER) {
                setSortKeyTimeForTransfer(travelServiceSortKey, sortKeyTranslator.getSameDayItems(travelServiceSortKey.getDeparture(), services));
            } else if (travelServiceSortKey.getMiscServiceType() == MiscServiceType.EVENT) {
                setSortKeyTimeForEvent(travelServiceSortKey);
            } else if (travelServiceSortKey.getMiscServiceType() == MiscServiceType.INSURANCE) {
                setSortKeyTimeForInsurance(travelServiceSortKey);
            } else {
                setSortKeyTimeForMisc(travelServiceSortKey);
            }
        } else {
            travelServiceSortKey.setNeedsTime(false);
        }
    }

    /**
     * Hotel is always the last entry of the day, accepting the mistake for exceptions of hotel checkin > activity.
     */
    private void setSortKeyTimeForHotel(final TravelServiceSortKey travelServiceSortKey) {
        travelServiceSortKey.setDeparture(travelServiceSortKey.getDeparture().toLocalDate().atTime(LocalTime.MAX));
        travelServiceSortKey.setNeedsTime(false);
    }

    /**
     * Rental cars go before MISC events and hotels, but after transfers.
     */
    private void setSortKeyTimeForCarRental(final TravelServiceSortKey travelServiceSortKey) {
        travelServiceSortKey.setDeparture(travelServiceSortKey.getDeparture().toLocalDate().atTime(LocalTime.MAX.minusMinutes(3)));
        travelServiceSortKey.setNeedsTime(false);
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
        travelServiceSortKey.setNeedsTime(false);
    }

    /**
     * Events without time should be before general MISC and before hotel.
     */
    private void setSortKeyTimeForEvent(final TravelServiceSortKey travelServiceSortKey) {
        travelServiceSortKey.setDeparture(travelServiceSortKey.getDeparture().toLocalDate().atTime(LocalTime.MAX.minusMinutes(2)));
        travelServiceSortKey.setNeedsTime(false);
    }

    /**
     * Insurances are always sorted to the bottom.
     */
    private void setSortKeyTimeForInsurance(final TravelServiceSortKey travelServiceSortKey) {
        travelServiceSortKey.setDeparture(null);
        travelServiceSortKey.setNeedsTime(false);
    }

    /**
     * General-purpose MISC services without any time information are list before hotels.
     */
    private void setSortKeyTimeForMisc(final TravelServiceSortKey travelServiceSortKey) {
        travelServiceSortKey.setDeparture(travelServiceSortKey.getDeparture().toLocalDate().atTime(LocalTime.MAX.minusMinutes(1)));
        travelServiceSortKey.setNeedsTime(false);
    }
}
