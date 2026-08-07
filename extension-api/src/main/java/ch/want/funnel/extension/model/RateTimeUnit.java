package ch.want.funnel.extension.model;

public enum RateTimeUnit {
    /**
     * 'D' or 'DY' rate
     */
    DAILY,
    /**
     * 'W' or 'WY' rate
     */
    WEEKLY,
    /**
     * 'M' rate
     */
    MONTHLY,
    /**
     * 'E' or 'WE' rate
     */
    WEEKEND,
    /**
     * Pseudo-time unit, indicating the rate for a single-use (events, activities etc.)
     */
    SINGLE
}
