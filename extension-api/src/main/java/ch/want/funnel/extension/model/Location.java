package ch.want.funnel.extension.model;

import java.io.Serializable;

public class Location implements Serializable {

    private static final long serialVersionUID = 1L;
    private String iataCode;
    private String unLocationCode;
    private String generalCode;
    private String name;
    private String countryCode;

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(final String iataCode) {
        this.iataCode = isBlank(iataCode) ? null : iataCode;
    }

    /**
     * Value in the funnel.travel UN/Location format, e.g. ZRH/CH
     */
    public String getUnLocationCode() {
        return unLocationCode;
    }

    public void setUnLocationCode(final String unLocationCode) {
        this.unLocationCode = isBlank(unLocationCode) ? null : unLocationCode;
    }

    /**
     * A location code, but none of the above types. For flights this code be an ICAO code.
     * For rail services, this can be the rail station code.
     */
    public String getGeneralCode() {
        return generalCode;
    }

    public void setGeneralCode(final String generalCode) {
        this.generalCode = isBlank(generalCode) ? null : generalCode;
    }

    /**
     * A description of the location, e.g. 'Zürich'
     */
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = isBlank(name) ? null : name;
    }

    public boolean isUndefined() {
        return isBlank(iataCode) && isBlank(unLocationCode) && isBlank(generalCode) && isBlank(name);
    }

    /**
     * Returns the first non-blank of:
     * <ol>
     * <li>{@link #getName()}
     * <li>{@link #getIataCode()}
     * <li>{@link #getUnLocationCode()}
     * </ol>
     * if all above are empty, null is returned.
     */
    public String getGeneralDescription() {
        String result = getName();
        if (isBlank(result)) {
            result = getIataCode();
        }
        if (isBlank(result)) {
            result = getUnLocationCode();
        }
        return result;
    }

    public String getCountryCode() {
        if (countryCode != null) {
            return countryCode;
        }
        if (!isBlank(getUnLocationCode())) {
            return getUnLocationCode().substring(4);
        }
        return null;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return this.getClass() + "{countryCode=" + countryCode + ", entry=" + getGeneralDescription() + "}";
    }

    private static boolean isBlank(final String s) {
        return s == null || s.length() == 0;
    }
}
