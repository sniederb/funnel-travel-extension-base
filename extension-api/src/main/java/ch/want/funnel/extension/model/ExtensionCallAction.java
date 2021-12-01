package ch.want.funnel.extension.model;

public enum ExtensionCallAction {
    /**
     * Indicates that a booking has just been created
     */
    CREATE,
    /**
     * Indicates that a booking has been modified.
     * <strong>Design tip:</strong> if feeding into a third-party system, it might make more sense to
     * check the presence of an identifier in {@link Booking#getExtensionData()} to determine whether
     * this booking has previously been sent to that third-party system.
     */
    MODIFY,
    /**
     * Indicates that a booking has been deleted. In this case the {@link Booking} will be virtually empty;
     * only {@link Booking#getReferenceNumber()} and {@link Booking#getExtensionData()} will be populated.
     */
    DELETE
}
