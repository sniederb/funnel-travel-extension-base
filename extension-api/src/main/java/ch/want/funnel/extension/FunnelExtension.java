/*
 * Created on 4 Jan 2019
 */
package ch.want.funnel.extension;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ch.want.funnel.extension.tripdata.DataFormat;

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
     * Define with what format this extension works
     *
     * @return
     */
    DataFormat getDataFormat();

    /**
     * Get a list of settings which the implementation requires.
     *
     * @return
     */
    List<SettingItem> getSettings();

    /**
     * Validate the settings which will later be passed to the extension.
     *
     * @param settingValues
     * @param locale
     * @throws IllegalSettingException
     */
    void validateSettings(Map<String, Object> settingValues, Locale locale) throws IllegalSettingException;

    /**
     *
     * @param settingItemKey
     * @param locale
     * @return
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
