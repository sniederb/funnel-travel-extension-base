package ch.want.funnel.extension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.want.funnel.extension.model.Traveler;

public abstract class MinimalConverterSettings extends AbstractSettings {

    /**
     * If activated - and only if - includes meal, seat, accommodation preferences.
     */
    public static final String INCLUDE_TRAVEL_PREFERENCES = "include_travel_preferences";
    /**
     * If activated - and only if - includes SR DOCS / AP information
     */
    public static final String INCLUDE_PASSPORT_DATA = "include_passport_data";
    /**
     * If activated - and only if - includes remarks, insurance, etc.
     */
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

    /**
     * If {@code false}, producers should not add passport data to {@link Traveler} entries
     */
    public boolean isIncludePassportData() {
        return Boolean.TRUE.equals(settingValues.get(INCLUDE_PASSPORT_DATA));
    }

    /**
     * If {@code false}, producers should not process remarks
     */
    public boolean isIncludeNonTravelData() {
        return Boolean.TRUE.equals(settingValues.get(INCLUDE_NON_TRAVEL_DATA));
    }
}
