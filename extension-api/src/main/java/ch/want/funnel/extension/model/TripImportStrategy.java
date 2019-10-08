package ch.want.funnel.extension.model;

public enum TripImportStrategy {
    /**
     * With this strategy, data not present in the form object will be deleted.
     */
    FULL,
    /**
     * Extra mark to indicate that this data package contains <strong>all sources</strong>. Consequently,
     * sources present on the database but not in the data structure will be deleted.
     */
    FULL_WITH_SOURCES,
    /**
     * With this strategy, data not present in the form object will remain, ie. not be deleted.
     * This is useful for sources which send partial data.
     */
    ADD_ONLY
}
