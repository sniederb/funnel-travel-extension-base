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
     * Internally the same as {@link #STRING}, but will be presented in a textarea in the UI
     */
    LONG_STRING,
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
    BOOLEAN,
    /**
     * This value type signifies a usage, rather than a value class. {@code INTERNAL} settings are
     * never revealed to the user. Rather, they allow an extension to maintain cross-execution
     * state by updating the internal value whenever called.
     */
    INTERNAL
}
