/*
 * Created on 18.03.2019
 */
package ch.want.funnel.extension;

import java.util.Map;

/**
 * Extension implementations which need to do one-time processing on application start and stop
 * must implement this interface.
 */
public interface LifecycleAware {

    /**
     * @param environmentSettings
     *            This map will only hold the globally available environment keys defined in {@link SettingItem}
     */
    void onFunnelStarting(Map<String, Object> environmentSettings);

    /**
     * @param environmentSettings
     *            This map will only hold the globally available environment keys defined in {@link SettingItem}
     */
    void onFunnelStopping(Map<String, Object> environmentSettings);
}
