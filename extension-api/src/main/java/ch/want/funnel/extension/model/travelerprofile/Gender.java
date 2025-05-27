package ch.want.funnel.extension.model.travelerprofile;

import java.util.Set;

public enum Gender {

    MALE, FEMALE, OTHER;

    public static Gender fromGdsCode(final String code) {
        return fromGdsCode(code, Gender.OTHER);
    }

    public static Gender fromGdsCode(final String code, final Gender defaultGender) {
        return fromString(code, defaultGender);
    }

    public static Gender fromString(final String s, final Gender defaultGender) {
        if ((s != null) && (s.length() > 0)) {
            if (Set.of("MR", "MISTER", "MASTER").contains(s.toUpperCase())) {
                return Gender.MALE;
            }
            if (Set.of("MRS", "MS", "MISS", "MISSES").contains(s.toUpperCase())) {
                return Gender.FEMALE;
            }
        }
        return defaultGender;
    }
}
