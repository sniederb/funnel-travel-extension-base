package ch.want.funnel.extension;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class AbstractSettings {

    protected abstract String translate(String errorcode);

    protected void validateStringNotEmpty(final Object value, final String errorcode) throws IllegalSettingException {
        if (!(value instanceof String)) {
            throw new IllegalSettingException(translate(errorcode));
        }
        final String stringValue = (String) value;
        if (stringValue.length() == 0) {
            throw new IllegalSettingException(translate(errorcode));
        }
    }

    protected void validateUrl(final String value, final String errorcode) throws IllegalSettingException {
        try {
            new URL(value);
        } catch (final MalformedURLException e) {
            throw new IllegalSettingException(translate(errorcode), e);
        }
    }
}
