package ch.want.funnel.extension.tripdata;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountMetadata implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String settingItemKey;
    private final String settingItemValue;

    @JsonCreator
    public AccountMetadata(final @JsonProperty("settingItemKey") String settingItemKey, final @JsonProperty("settingItemValue") String settingItemValue) {
        this.settingItemKey = settingItemKey;
        this.settingItemValue = settingItemValue;
    }

    /**
     * Case-sensitive key.
     */
    public String getSettingItemKey() {
        return settingItemKey;
    }

    /**
     * Case-insensitive value.
     */
    public String getSettingItemValue() {
        return settingItemValue;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + settingItemKey + "=" + settingItemValue + "}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((settingItemKey == null) ? 0 : settingItemKey.hashCode());
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
        final AccountMetadata other = (AccountMetadata) obj;
        return Objects.equals(settingItemKey, other.settingItemKey) && Objects.equals(settingItemValue, other.settingItemValue);
    }
}
