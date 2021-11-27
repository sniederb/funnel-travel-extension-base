package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Location implements Serializable {

    private static final long serialVersionUID = 1L;
    private final EnumMap<EntryType, String> entries = new EnumMap<>(EntryType.class);
    private String countryCode;

    @JsonIgnore
    public Optional<String> get(final EntryType entryType) {
        return Optional.ofNullable(entries.get(entryType));
    }

    public Location set(final EntryType entryType, final String entryValue) {
        if ((entryValue != null) && (entryValue.length() > 0)) {
            entries.put(entryType, entryValue);
        }
        return this;
    }

    public Map<EntryType, String> getEntries() {
        return entries;
    }

    public void setEntries(final Map<EntryType, String> entries) {
        this.entries.clear();
        this.entries.putAll(entries);
    }

    public boolean isUndefined() {
        return entries.isEmpty();
    }

    /**
     * Returns the first non-null of:
     * <ol>
     * <li>{@link EntryType#GENERAL}
     * <li>{@link EntryType#IATA}
     * <li>{@link EntryType#UNLOCATION}
     * </ol>
     * if all above are empty, an empty string is returned.
     */
    public String getGeneralDescription() {
        Optional<String> result = get(Location.EntryType.GENERAL);
        if (!result.isPresent()) {
            result = get(Location.EntryType.IATA);
        }
        if (!result.isPresent()) {
            result = get(Location.EntryType.UNLOCATION);
        }
        return result.orElse("");
    }

    public String getCountryCode() {
        if (countryCode != null) {
            return countryCode;
        }
        return get(EntryType.UNLOCATION)
            .map(s -> s.substring(4))
            .orElse(null);
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((entries == null) ? 0 : entries.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        return Objects.equals(entries, other.entries) && Objects.equals(countryCode, other.countryCode);
    }

    @Override
    public String toString() {
        return this.getClass() + "{countryCode=" + countryCode + ", entries=" + entries + "}";
    }

    public enum EntryType {
        IATA, UNLOCATION, SNCF, DB, AMTRAK, GENERAL, GENERAL_CODE
    }
}
