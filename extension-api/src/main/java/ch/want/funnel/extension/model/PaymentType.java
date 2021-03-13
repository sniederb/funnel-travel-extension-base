package ch.want.funnel.extension.model;

public enum PaymentType {
    INVOICE("INV"), //
    AMEX("AX"), //
    VISA("VI", "BA", "JB"), //
    MASTERCARD("MC", "CA", "EC"), //
    DINERS("DC"), //
    AIRPLUS("TP"),
    //
    OTHER;

    private final String[] codes;

    PaymentType(final String... codes) {
        this.codes = codes;
    }

    public static PaymentType fromCode(final String s) {
        if (s == null) {
            return null;
        }
        final String sUpper = s.toUpperCase();
        for (final PaymentType candidate : values()) {
            if (sUpper.equals(candidate.toString())) {
                return candidate;
            }
            for (final String code : candidate.codes) {
                if (sUpper.equals(code)) {
                    return candidate;
                }
            }
        }
        return OTHER;
    }
}
