package ch.want.funnel.extension.model;

/**
 * This class only defines VAT categories relevant for travel. Many countries have
 * categories e.g. for food or medicine, which are irrelevant in this context.
 */
public enum VatCategory {

    STANDARD, TRAVEL_REDUCED, EXEMPT;

    public static VatCategory fromRate(final String isoCountryCode, final double rate) {
        if ("ch".equalsIgnoreCase(isoCountryCode)) {
            return fromSwissRate(rate);
        }
        if ("fr".equalsIgnoreCase(isoCountryCode)) {
            return fromFrenchRate(rate);
        }
        if ("de".equalsIgnoreCase(isoCountryCode)) {
            return fromGermanRate(rate);
        }
        return EXEMPT;
    }

    /**
     * Swiss VAT (Aug 2022): standard rate is 7.7%, travel reduced rate is 2.4%.
     */
    private static VatCategory fromSwissRate(final double rate) {
        if (rate > 0.07) {
            return STANDARD;
        }
        if (rate > 0) {
            return TRAVEL_REDUCED;
        }
        return EXEMPT;
    }

    /**
     * French VAT (Aug 2022): standard rate is 20%, travel reduced rate is 10%.
     */
    private static VatCategory fromFrenchRate(final double rate) {
        if (rate > 0.17) {
            return STANDARD;
        }
        if (rate > 0) {
            return TRAVEL_REDUCED;
        }
        return EXEMPT;
    }

    /**
     * German VAT (Aug 2022): standard rate is 19%, domestic travel is not reduced.
     */
    private static VatCategory fromGermanRate(final double rate) {
        if (rate > 0.17) {
            return STANDARD;
        }
        return EXEMPT;
    }
}
