/*
 * Created on 8 Jan 2019
 */
package ch.want.funnel.extension;

import java.util.Date;

public enum SettingItemValueType {
    /**
     * String value (default)
     */
    STRING,
    /**
     * Handled internally same as {@link #STRING}, but is masked in the UI
     */
    PASSWORD,
    /**
     * Values will be provided as {@link Date} instances, truncated to day-of-month.
     */
    DATE,
    /**
     * Integer value (typically for HTTP ports)
     */
    INT,
    /**
     * Boolean value
     */
    BOOLEAN
}
