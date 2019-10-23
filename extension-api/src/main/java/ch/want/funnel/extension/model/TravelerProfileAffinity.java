package ch.want.funnel.extension.model;

public enum TravelerProfileAffinity {
    NONE,
    /**
     * Indicates that this extension profits from having extended traveler profile data available,
     * but can still function without (possibly with a limited quality of service)
     */
    NICE_TO_HAVE,
    /**
     * Indicates that this extension requires having extended traveler profile data available.
     * Activating the extension on an account with a traveler profile suite integration will
     * fail.
     *
     * <strong>Note that extension implementations must still validate that extended traveler profile data
     * is present when called for consume or modify, as funnel.travel will only ensure a consistent setup, but not that
     * profile data is found for every booking / pax.</strong>
     */
    NEED_TO_HAVE
}
