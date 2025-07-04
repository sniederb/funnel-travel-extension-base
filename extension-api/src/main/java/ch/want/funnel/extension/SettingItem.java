/*
 * Created on 8 Jan 2019
 */
package ch.want.funnel.extension;

import java.util.SortedMap;
import java.util.UUID;

public class SettingItem {

    public static final String KEY_FILESTORE = "funnel.environment.filestore";
    /**
     * Settings value will be something like {@code /be/purple}, i.e. the pure context
     *
     * @deprecated
     */
    @Deprecated
    public static final String KEY_SERVERCONTEXT = "funnel.environment.servercontext";
    /**
     * Settings value will be an absolute URL of the webhook for this extension. Note that all extensions receive a value, but only
     * produces with 'WEBHOOK' setting will eventually be able to process the URL.
     */
    public static final String KEY_OWN_WEBHOOK = "funnel.environment.ownwebhook";
    /**
     * UUID of the extension settings. This should be used if {@link #KEY_ACCOUNT_UUID} might actually hold
     * multiple settings of the same extension type. The setting value type is {@link UUID}.
     */
    public static final String KEY_SETTING_UUID = "funnel.environment.settings-uuid";
    /**
     * Key to retrieve whether or not the extension is running on production. Typically this is used to
     * determine web service endpoints. Value is a boolean
     */
    public static final String KEY_ISPRODUCTION = "funnel.environment.isproduction";
    /**
     * UUID of the extension owning account. The setting value type is {@link UUID}.
     */
    public static final String KEY_ACCOUNT_UUID = "funnel.environment.account-uuid";
    /**
     * Key to retrieve the billing account's default currency. Value will be a string.
     */
    public static final String KEY_ACCOUNT_CURRENCY = "funnel.environment.account-currency";
    /**
     * Key to retrieve the billing account's default locale. Value will be a valid ISO 639 string in the Java format (de_CH).
     */
    public static final String KEY_ACCOUNT_LOCALE = "funnel.environment.account-locale";
    /**
     * Key to retrieve the billing account's code. This can be useful if processing logic is specific to a certain account
     */
    public static final String KEY_ACCOUNT_CODE = "funnel.environment.account-code";
    /**
     * Key to retrieve the comma-separated list of communities associated with the booking.
     */
    public static final String KEY_COMMUNITY_LIST = "funnel.environment.community-list";
    /**
     * Two-letter code of the country the account is based in.
     */
    public static final String KEY_ACCOUNT_COUNTRY = "funnel.environment.account-country";
    private String key;
    private SettingItemValueType valueType = SettingItemValueType.STRING;
    private boolean mandatory;
    private Object defaultValue;
    private SettingItemInheritance inheritance = SettingItemInheritance.ACCOUNT_ONLY;
    private SortedMap<Object, String> options;

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

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public SettingItem setDefaultValue(final Object obj) {
        this.defaultValue = obj;
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

    /**
     * Beware: this will be null if {@link #setOptions(SortedMap)} was never called
     */
    public SortedMap<Object, String> getOptions() {
        return options;
    }

    public void setOptions(final SortedMap<Object, String> options) {
        this.options = options;
    }
}
