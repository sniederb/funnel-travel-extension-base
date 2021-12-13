/*
 * (c) Copyright 2019 WaNT GmbH
 */
package ch.want.funnel.extension.tripdata;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import ch.want.funnel.extension.FunnelExtension;

/**
 * Implementations of this interface retrieve trip data from "pull" sources such as e-mail servers,
 * booking websites or a GDS.
 */
public interface TripDataProducer extends TripRawDataConverter {

    /**
     * @param settings
     *            A Map holding the keys defined in {@link FunnelExtension#getSettings()},
     *            and associated values with possible inheritance applied
     * @param locale
     *            Locale for error messages
     * @return A list of raw sources from the external trip data system. These sources will be made available
     *         to the user on the trip UI.
     */
    List<RawTripDataSource> getRawSources(Map<String, Object> settings, Locale locale);

    /**
     * Other producers might attempt to modify booking data originally created by this producer. This method gives
     * the original producer a chance to determine whether cross-origin modifies are allowed or not.
     */
    boolean isModifyAllowedFrom(String crossOriginSourceDomain);
}
