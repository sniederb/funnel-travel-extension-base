package ch.want.funnel.extension.model;

public enum TripImportStrategy {
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
