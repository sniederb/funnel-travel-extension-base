/*
 * Created on 8 Jan 2019
 */
package ch.want.funnel.extension;

public class SettingItem {

    public static final String KEY_FILESTORE = "funnel.environment.filestore";
    public static final String KEY_SERVERCONTEXT = "funnel.environment.servercontext";
    public static final String KEY_ISPRODUCTION = "funnel.environment.isproduction";
    private String key;
    private SettingItemValueType valueType = SettingItemValueType.STRING;
    private boolean mandatory;
    private SettingItemInheritance inheritance = SettingItemInheritance.ACCOUNT_ONLY;

    public SettingItem() {
        // default bean c'tor for Jackson
    }

    public SettingItem(final String key) {
        if ((key == null) || (key.length() == 0)) {
            throw new IllegalArgumentException("Settings 'key' must not be empty");
        }
        this.key = key;
    }

    public SettingItem(final String key, final SettingItemValueType valueType) {
        this(key);
        if (valueType == null) {
            throw new IllegalArgumentException("Settings 'valueType' must not be null");
        }
        this.valueType = valueType;
    }

    public String getKey() {
        return key;
    }

    public SettingItem setKey(final String key) {
        if ((key == null) || (key.length() == 0)) {
            throw new IllegalArgumentException("Settings 'key' must not be empty");
        }
        this.key = key;
        return this;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public SettingItem setMandatory(final boolean mandatory) {
        this.mandatory = mandatory;
        return this;
    }

    public SettingItemInheritance getInheritance() {
        return inheritance;
    }

    public SettingItem setInheritance(final SettingItemInheritance inheritance) {
        if (inheritance == null) {
            throw new IllegalArgumentException("Settings 'inheritance' must not be null");
        }
        this.inheritance = inheritance;
        return this;
    }

    public SettingItemValueType getValueType() {
        return valueType;
    }

    public SettingItem setValueType(final SettingItemValueType valueType) {
        if (valueType == null) {
            throw new IllegalArgumentException("Settings 'valueType' must not be null");
        }
        this.valueType = valueType;
        return this;
    }
}
