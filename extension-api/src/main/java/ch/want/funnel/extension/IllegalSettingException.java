/*
 * Created on 10 Jan 2019
 */
package ch.want.funnel.extension;

public class IllegalSettingException extends Exception {

    private static final long serialVersionUID = 1L;

    public IllegalSettingException() {
    }

    public IllegalSettingException(final String message) {
        super(message);
    }

    public IllegalSettingException(final Throwable cause) {
        super(cause);
    }

    public IllegalSettingException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
