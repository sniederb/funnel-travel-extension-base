package ch.want.funnel.extension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class MinimalConverterSettings extends AbstractSettings {

    public static final String INCLUDE_TRAVEL_PREFERENCES = "include_travel_preferences";
    public static final String INCLUDE_PASSPORT_DATA = "include_passport_data";
    public static final String INCLUDE_NON_TRAVEL_DATA = "include_non_travel_data";
    protected final Map<String, Object> settingValues;

    protected MinimalConverterSettings(final Map<String, Object> settingValues) {
        this.settingValues = settingValues;
    }

    protected static List<SettingItem> createMinimalSettings() {
        final List<SettingItem> result = new ArrayList<>();
        result.add(new SettingItem(INCLUDE_TRAVEL_PREFERENCES, SettingItemValueType.BOOLEAN));
        result.add(new SettingItem(INCLUDE_PASSPORT_DATA, SettingItemValueType.BOOLEAN));
        result.add(new SettingItem(INCLUDE_NON_TRAVEL_DATA, SettingItemValueType.BOOLEAN));
        return result;
    }

    public boolean isIncludeTravelPreferences() {
        return Boolean.TRUE.equals(settingValues.get(INCLUDE_TRAVEL_PREFERENCES));
    }

    public boolean isIncludePassportData() {
        return Boolean.TRUE.equals(settingValues.get(INCLUDE_PASSPORT_DATA));
    }

    public boolean isIncludeNonTravelData() {
        return Boolean.TRUE.equals(settingValues.get(INCLUDE_NON_TRAVEL_DATA));
    }
}
