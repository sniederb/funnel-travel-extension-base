package ch.want.funnel.extension.util;

import java.util.Locale;
import java.util.Optional;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

public class LocationCodeMapper {

    private static final LocationCodeMapper instance = new LocationCodeMapper();
    private final ResourceBundle messageResource;

    private LocationCodeMapper() {
        this.messageResource = PropertyResourceBundle.getBundle("ch/want/funnel/extension/railstations", Locale.US);
    }

    /**
     * Returns true if the {@code system} indicates to be part of the "Benerail/SNCF/Thalys" group. If this method returns true,
     * use {@link #convertSncfToFunnelLocation(String)} for location codes.
     */
    public static boolean isSncfGroup(final String system) {
        if (system == null) {
            return false;
        }
        return StringUtils.containsAny(system.toLowerCase(), "bene", "sncf", "thalys");
    }

    /**
     * AMTRAK uses three-letter rail station codes, which don't correspond with UN location codes.
     * This method maps an AMTRAK train station code (eg. ARB) to the UN location code (eg. ARB/US)
     */
    public static Optional<String> convertAmtrakToFunnelLocation(final String amtrakStationCode) {
        if (StringUtils.isBlank(amtrakStationCode)) {
            return Optional.empty();
        }
        return instance.getMessage("amtrak." + amtrakStationCode);
    }

    /**
     * DB (Deutsche Bahn) maintains a list of train stations with proprietary codes (RL100). This method maps a DB train station code (eg.
     * XSZH) to the UN location code (eg. ZRH/CH)
     */
    public static Optional<String> convertDeutscheBahnToFunnelLocation(final String dbRl100Code) {
        if (StringUtils.isBlank(dbRl100Code)) {
            return Optional.empty();
        }
        return instance.getMessage("deutschebahn." + dbRl100Code.replace(' ', '_'));
    }

    /**
     * SNCF maintains a list of train stations with codes which do not match the UN location codes.
     * This method maps a SNCF train station code (eg. FRMSC) to the UN location code (eg. MRS/FR)
     */
    public static Optional<String> convertSncfToFunnelLocation(final String gareSncfCode) {
        if (StringUtils.isBlank(gareSncfCode)) {
            return Optional.empty();
        }
        return instance.getMessage("sncf." + gareSncfCode);
    }

    private Optional<String> getMessage(final String gareSncfCode) {
        if (gareSncfCode == null) {
            return Optional.empty();
        }
        return messageResource.containsKey(gareSncfCode) ? Optional.of(messageResource.getString(gareSncfCode)) : Optional.empty();
    }
}
