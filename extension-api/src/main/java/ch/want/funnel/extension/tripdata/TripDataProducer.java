/*
 * (c) Copyright 2019 WaNT GmbH
 */
package ch.want.funnel.extension.tripdata;

import java.util.Collection;
import java.util.Collections;
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
     * Produce a list of raw sources. These will subsequently be fed into {@link #convertRawSourceToTripData(byte[], Map, Locale)}.
     *
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
     *
     * @param crossOriginSourceDomain
     *            The sourceDomain of the 'other' producer
     * @param settingValues
     *            Current extension settings
     * @return True if modify is allowed, false if booking should create a separate trip.
     * @throws DiscardPayloadException
     *             if payload should be processed neither as modify nor as new trip
     */
    default boolean isModifyAllowedFrom(final String crossOriginSourceDomain, final Map<String, Object> settingValues) {
        return true;
    }

    /**
     * Implementations can return one or more setting keys, for which funnel.travel will validate that only 1 installation
     * is present for a given value.
     */
    default Collection<String> getUniqueSettingKeys() {
        return Collections.emptySet();
    }
}
