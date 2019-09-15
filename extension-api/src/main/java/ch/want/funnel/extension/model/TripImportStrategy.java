/*
 * Created on 7 Dec 2018
 */
package ch.want.funnel.extension.model;

public enum TripImportStrategy {
    FULL, FULL_WITH_SOURCES,
    /**
     * With this strategy, data not present in the form object will remain, ie. not be deleted.
     * This is useful for sources which send partial data.
     */
    ADD_ONLY
}
