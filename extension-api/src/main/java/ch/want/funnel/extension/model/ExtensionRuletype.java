package ch.want.funnel.extension.model;

public enum ExtensionRuletype {
    /**
     * A rule based on a cron-expression, such as "every 5min", or "last Friday of the month"
     */
    TIME,
    /**
     * A rule based on events (interaction or lifecycle) defined by {@link TripEvent}
     */
    EVENT,
    /**
     * Rule is based on an external system triggering a webhook
     */
    WEBHOOK
}
