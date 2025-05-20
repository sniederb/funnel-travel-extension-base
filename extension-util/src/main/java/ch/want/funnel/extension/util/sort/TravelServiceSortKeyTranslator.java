package ch.want.funnel.extension.util.sort;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import ch.want.funnel.extension.model.TravelServiceType;

public interface TravelServiceSortKeyTranslator<T> {

    TravelServiceSortKey getSortKey(T obj);

    Collection<T> getSameDayItems(LocalDateTime departure, Collection<T> allItems);

    TravelServiceType getTravelServiceType(T obj);

    /**
     * Use for flight and train services, returning the departure date/time of the first segment.
     */
    Optional<LocalDateTime> getFirstSegmentDeparture(T obj);
}
