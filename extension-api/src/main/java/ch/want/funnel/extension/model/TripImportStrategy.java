package ch.want.funnel.extension.model;

public enum TripImportStrategy {
    /**
     * With this strategy, the form object will be mirrored to trip data, i.e. everything not present anymore
     * in the form object will be deleted. The only exception are the raw sources.
     */
    MIRROR,
    /**
     * With this strategy, data not present in the form object will be deleted, except:
     * <ul>
     * <li>Numbered tickets</li>
     * <li>Raw sources</li>
     * <li>Payments</li>
     * </ul>
     */
    FULL,
    /**
     * Same as {@link #FULL}, except that no price items are deleted. This is an apporach commonly used in business travel,
     * where value document are issued and the producer just adds those to the trip.
     */
    FULL_PRICE_ADDITIVE,
    /**
     * Extra mark to indicate that this data package contains <strong>everything</strong>. Consequently,
     * the exceptions of {@link TripImportStrategy#FULL} do not apply here.
     */
    FROM_BACKUP,
    /**
     * With this strategy, data not present in the form object will remain, ie. not be deleted.
     * This is useful for sources which send partial data.
     */
    ADD_ONLY
}
