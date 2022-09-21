package ch.want.funnel.extension.model;

public enum SalesChannel {
    /**
     * Classic agent booking, used if a more specific offline channel cannot be determined
     */
    OFFLINE,
    /**
     * Classic self-booking, used if a more specific online channel cannot be determined
     */
    ONLINE,
    /**
     * Offline booking via e-mail
     */
    EMAIL,
    /**
     * Offline booking via phone
     */
    PHONE,
    /**
     * Online booking via OTA
     */
    OTA,
    /**
     * Online booking via any general website
     */
    WEB
}
