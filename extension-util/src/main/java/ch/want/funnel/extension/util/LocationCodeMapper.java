package ch.want.funnel.extension.util;

import java.util.Locale;
import java.util.Optional;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class LocationCodeMapper {

    private static final LocationCodeMapper instance = new LocationCodeMapper();
    private final ResourceBundle messageResource;

    private LocationCodeMapper() {
        this.messageResource = PropertyResourceBundle.getBundle("ch/want/funnel/extension/sncf-locations", Locale.US);
    }

    /**
     * SNCF maintains a list of train stations with codes which do not match the UN location codes.
     * This method maps a SNCF train station code (eg. FRMSC) to the UN location code (eg. MRS/FR)
     */
    public static Optional<String> convertSncfToFunnelLocation(final String gareSncfCode) {
        return instance.getMessage(gareSncfCode);
    }

    private Optional<String> getMessage(final String gareSncfCode) {
        if (gareSncfCode == null) {
            return Optional.empty();
        }
        return messageResource.containsKey(gareSncfCode) ? Optional.of(messageResource.getString(gareSncfCode)) : Optional.empty();
    }
}
