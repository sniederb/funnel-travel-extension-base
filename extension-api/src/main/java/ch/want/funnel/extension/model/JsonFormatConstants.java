/*
 * Created on 23 Feb 2018
 */
package ch.want.funnel.extension.model;

import com.fasterxml.jackson.databind.ser.std.SqlDateSerializer;

public final class JsonFormatConstants {

    /**
     * Date format yyyy-MM-dd. Beware that the Jackson {@link SqlDateSerializer} will process this field. To prevent
     * weird timezone issues, {@code timezone = JsonFormat.DEFAULT_TIMEZONE} should always be provided
     */
    public static final String JS_DATE_FORMAT = "yyyy-MM-dd";

    private JsonFormatConstants() {
        // hide c'tor
    }
}
