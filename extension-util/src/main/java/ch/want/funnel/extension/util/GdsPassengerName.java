package ch.want.funnel.extension.util;

import org.apache.commons.lang3.StringUtils;

public class GdsPassengerName {

    private final String lastname;
    private final String firstname;
    private final String title;
    private final String salutation;

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
        this.salutation = getSuffix(remainingComposite, "MR", "MRS", "MS");
        if (StringUtils.isNotBlank(salutation)) {
            remainingComposite = remainingComposite.substring(0, remainingComposite.length() - this.salutation.length()).trim();
        }
        this.title = getSuffix(remainingComposite, "DR", "PROF");
        if (StringUtils.isNotBlank(title)) {
            remainingComposite = remainingComposite.substring(0, remainingComposite.length() - this.title.length()).trim();
        }
        this.firstname = remainingComposite;
    }

    private String getSuffix(final String s, final String... possibleSuffixes) {
        for (final String suffix : possibleSuffixes) {
            if (s.endsWith(suffix)) {
                return suffix;
            }
        }
        return "";
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
}
