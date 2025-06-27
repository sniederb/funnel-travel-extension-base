/*
 * Created on 4 Jan 2019
 */
package ch.want.funnel.extension;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Interface for the main implemenation of a funnel extension. Be sure to register the implementation class by putting
 * a resource file named "funnel-extension.json" in the same package as the implementation:
 *
 * <pre>
 * {
 *   "Implementation": "com.acme.funnelextension.Boom",
 *   "Name": "Acme Boom",
 *   "Description": "An extension which goes boom at trip departure",
 *   "Author": "ACME Ltd.",
 *   "Version": "1.4"
 * }
 * </pre>
 */
public interface FunnelExtension {

    /**
     * Get a list of settings which the implementation requires. The settings will be presented in the UI
     * <strong>in the same order</strong> as provided by the returned list.
     */
    List<SettingItem> getSettings();

    /**
     * Same as {@link #getSettings()}, but takes a map of context setting values as defined in {@link SettingItem}.
     *
     * @param contextSettingValues
     *            a map of context setting values as defined in {@link SettingItem}
     */
    default List<SettingItem> getSettings(final Map<String, Object> contextSettingValues) {
        return getSettings();
    }

    /**
     * Validate the settings which will later be passed to the extension.
     *
     * @param settingValues
     * @param locale
     * @throws IllegalSettingException
     */
    void validateSettings(Map<String, Object> settingValues, Locale locale) throws IllegalSettingException;

    /**
     * Translate a resource bundle key. This is used to display labels for settings, in which case the
     * {@code settingItemKey} will match the {@link SettingItem#getKey()}. A further use is to translate
     * UI elements added to the booking extension data.
     */
    String getLabelForKey(String settingItemKey, Locale locale);

    /**
     * Get an URL pointing to a logo. This can be an external URL, or one obtained by
     *
     * <pre>
     * return this.getClass().getResource("logo.jpg");
     * </pre>
     *
     * @return
     */
    URL getLogoUrl();

    /**
     *
     * @return One of 'image/jpeg', 'image/gif' or 'image/png'
     */
    String getLogoMime();
}
