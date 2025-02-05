package ch.want.funnel.extension.model;

public enum TripImportStrategy {
    /**
     * <p>
     * With this strategy, the form object will be mirrored to trip data, i.e. everything not present anymore
     * in the form object will be deleted. The only exception are the raw sources.
     * </p>
     * <p>
     * A typical use-case for this strategy is a visual edit of the entire trip.
     * </p>
     */
    MIRROR,
    /**
     * With this strategy, data not present in the form object will be deleted, except:
     * <ul>
     * <li>Numbered documents (ticket, EMD)</li>
     * <li>Refunds</li>
     * <li>Raw sources</li>
     * <li>Payments</li>
     * </ul>
     * <p>
     * A typical use-case for this strategy is an import from a producer which always contains the full trip.
     * </p>
     */
    FULL,
    /**
     * <p>
     * Same as {@link #FULL}, except that no price items are updated or deleted. This is an approach commonly used in
     * business travel, where value document are issued and the producer just adds those to the trip.
     * </p>
     * <p>
     * A typical use-case are ticket refunds or void, where the producer sends all flight (segment) data, but only additional, new
     * price items.
     * </p>
     */
    FULL_PRICE_ADDITIVE,
    /**
     * <p>
     * Extensions which - on (partial) cancellation of a booking - cannot provide details of the individual
     * services originally booked should use this strategy to indicate that missing services should be
     * updated to cancelled, and prices should be set to 0.00.
     * </p>
     * <p>
     * Note that this cancellation strategy only applies to {@link TravelService}.
     * </p>
     */
    FULL_CANCEL_MISSING,
    /**
     * <p>
     * Extra mark to indicate that this data package contains <strong>everything</strong>. Consequently,
     * the exceptions of {@link TripImportStrategy#FULL} do not apply here.
     * </p>
     * <p>
     * A typical use-case is a backup/restore process.
     * </p>
     */
    FROM_BACKUP,
    /**
     * <p>
     * With this strategy, data not present in the form object will remain, ie. not be deleted.
     * This is useful for sources which send partial data. Data with matching keys will still be modified.
     * </p>
     * <p>
     * A typical use-case are ticket refunds or void, where often the segment data is not included anymore.
     * </p>
     */
    ADD_ONLY;
}
