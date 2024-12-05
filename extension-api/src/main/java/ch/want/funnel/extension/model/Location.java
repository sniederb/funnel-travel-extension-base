package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Location implements Serializable {

    private static final long serialVersionUID = 1L;
    private String iataCode;
    private String unLocationCode;
    private String generalCode;
    private String name;
    private String countryCode;
    private String countryName;

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

    @JsonIgnore
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
    @JsonIgnore
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

    /**
     * Returns the first non-blank of:
     * <ol>
     * <li>{@link #getIataCode()}
     * <li>{@link #getGeneralCode()}
     * <li>{@link #getUnLocationCode()}
     * </ol>
     * if all above are empty, null is returned.
     */
    @JsonIgnore
    public String getAsCode() {
        String result = getIataCode();
        if (isBlank(result)) {
            result = getGeneralCode();
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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(final String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return this.getClass() + "{countryCode=" + countryCode + ", entry=" + getGeneralDescription() + "}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        final Location other = (Location) obj;
        if (!isBlank(iataCode) || !isBlank(other.iataCode)) {
            return Objects.equals(iataCode, other.iataCode);
        }
        if (!isBlank(unLocationCode) || !isBlank(other.unLocationCode)) {
            return Objects.equals(unLocationCode, other.unLocationCode);
        }
        if (!isBlank(generalCode) || !isBlank(other.generalCode)) {
            return Objects.equals(generalCode, other.generalCode) &&
                Objects.equals(countryCode, other.countryCode);
        }
        return Objects.equals(name, other.name) &&
            Objects.equals(countryCode, other.countryCode);
    }

    private static boolean isBlank(final String s) {
        return s == null || s.length() == 0;
    }
}
