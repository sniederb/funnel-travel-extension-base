package ch.want.funnel.extension.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class GdsPassengerName {

    private static final Set<String> MALE_SALUATIONS;
    private static final Set<String> FEMALE_SALUATIONS;
    private final String lastname;
    private final String firstname;
    private String title;
    private String salutation;
    private String passengerType;
    private String passengerId;
    private String infantName;
    private LocalDate infantBirthdate;
    static {
        MALE_SALUATIONS = new HashSet<>();
        MALE_SALUATIONS.add("MR");
        MALE_SALUATIONS.add("MR.");
        FEMALE_SALUATIONS = new HashSet<>();
        FEMALE_SALUATIONS.add("MRS");
        FEMALE_SALUATIONS.add("MRS.");
        FEMALE_SALUATIONS.add("MS");
        FEMALE_SALUATIONS.add("MS.");
        FEMALE_SALUATIONS.add("MISS");
    }

    /**
     * GDS will use salutations MR, MRS, CHD and INF, while other booking sources might use
     * Mr., Ms., Mrs., MISS, MS etc. This method returns a standard set of saluations:
     * <ul>
     * <li>'MRS' for any female-type salutations</li>
     * <li>'MR' for any male-type salutation</li>
     * <li>An empty string for unknown salutations, or those indicating age instead of gender</li>
     * </ul>
     *
     * @param salutation
     * @return
     */
    public static String normalizeSalutation(final String salutation) {
        if (salutation == null) {
            return null;
        }
        if (MALE_SALUATIONS.contains(salutation.toUpperCase())) {
            return "MR";
        }
        if (FEMALE_SALUATIONS.contains(salutation.toUpperCase())) {
            return "MRS";
        }
        return "";
    }

    /**
     * Parse a complete name as typically sent by a GDS. eg.
     *
     * <pre>
     * NIEDERBERGER/SIMON MR
     * </pre>
     *
     * @param fullname
     */
    public GdsPassengerName(final String fullname) {
        if (fullname == null) {
            throw new IllegalArgumentException("Cannot parse a 'null' name");
        }
        final int firstSlash = fullname.indexOf('/');
        if (firstSlash < 0) {
            throw new IllegalArgumentException("A GDS name always contains a slash .. are you sure " + fullname + " originates from a GDS?");
        }
        this.lastname = fullname.substring(0, firstSlash);
        String remainingComposite = fullname.substring(firstSlash + 1);
        remainingComposite = extractAdditionalInformation(remainingComposite);
        remainingComposite = extractSalutation(remainingComposite);
        remainingComposite = extractTitle(remainingComposite);
        this.firstname = remainingComposite;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getTitle() {
        return title;
    }

    public String getSalutation() {
        return salutation;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public String getInfantName() {
        return infantName;
    }

    public LocalDate getInfantBirthdate() {
        return infantBirthdate;
    }

    private String extractAdditionalInformation(final String s) {
        String remainingComposite = s;
        int lastBracket = remainingComposite.lastIndexOf('(');
        while (lastBracket >= 0) {
            final PassengerNameAddOn addOn = new PassengerNameAddOn(remainingComposite.substring(lastBracket));
            switch (addOn.getAddOnType()) {
            case "ID":
                this.passengerId = addOn.getFirstValueElement();
                break;
            case "INF":
                this.infantName = addOn.getFirstValueElement();
                this.infantBirthdate = addOn.getBirthdate();
                break;
            case "CHD":
            case "ADT":
                this.passengerType = addOn.getAddOnType();
                break;
            }
            remainingComposite = removeTrailing(remainingComposite, addOn.getEntireAddOn());
            lastBracket = remainingComposite.lastIndexOf('(');
        }
        return remainingComposite;
    }

    private String extractSalutation(final String s) {
        this.salutation = getSuffix(s, "MR", "MRS", "MS");
        if (StringUtils.isNotBlank(salutation)) {
            return removeTrailing(s, this.salutation);
        }
        return s;
    }

    private String extractTitle(final String s) {
        this.title = getSuffix(s, "DR", "PROF");
        if (StringUtils.isNotBlank(title)) {
            return removeTrailing(s, this.title);
        }
        return s;
    }

    private String getSuffix(final String s, final String... possibleSuffixes) {
        for (final String suffix : possibleSuffixes) {
            if (s.endsWith(suffix)) {
                return suffix;
            }
        }
        return "";
    }

    private String removeTrailing(final String base, final String suffix) {
        return base.substring(0, base.length() - suffix.length()).trim();
    }

    private static class PassengerNameAddOn {

        private static final DateTimeFormatter DDMMMYY = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("ddMMMyy")
            .toFormatter(Locale.US);
        private final String addOn;
        private final String addOnType;
        private final String firstValueElement;
        private LocalDate birthdate;

        PassengerNameAddOn(final String addOn) {
            this.addOn = addOn;
            final String payload = addOn.replaceAll("[\\(\\)]", "");
            if (payload.startsWith("ID")) {
                addOnType = "ID";
                firstValueElement = payload.substring(2);
                birthdate = null;
            } else {
                final String[] parts = payload.split("/");
                addOnType = parts[0];
                firstValueElement = parts.length > 1 ? parts[1] : "";
                birthdate = parts.length > 2 ? parseBirthdate(parts[2]) : null;
            }
        }

        /**
         * Format: 04OCT10
         * 
         * @param dateValue
         * @return
         */
        private LocalDate parseBirthdate(final String dateValue) {
            return LocalDate.parse(dateValue, DDMMMYY);
        }

        String getEntireAddOn() {
            return addOn;
        }

        String getAddOnType() {
            return addOnType;
        }

        LocalDate getBirthdate() {
            return birthdate;
        }

        String getFirstValueElement() {
            return firstValueElement;
        }
    }
}
