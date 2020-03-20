package ch.want.funnel.extension.model.travelerprofile;

public enum Gender {
    MALE, FEMALE, OTHER;

    public static Gender fromGdsCode(final String code) {
        return fromGdsCode(code, Gender.OTHER);
    }

    public static Gender fromGdsCode(final String code, final Gender defaultGender) {
        if ("MR".equals(code)) {
            return Gender.MALE;
        }
        if ("MRS".equals(code)) {
            return Gender.FEMALE;
        }
        return defaultGender;
    }
}
