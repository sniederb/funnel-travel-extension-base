package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SingleSegment implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String SERVICE_TYPECODE_INSURANCE = "INSURANCE";
    public static final String SERVICE_TYPECODE_REFUND = "REFUND";
    public static final String SERVICE_TYPECODE_TRANSFER = "TRANSFER";
    /**
     * An event, activity.
     */
    public static final String SERVICE_TYPECODE_EVENT = "EVENT";
    private UUID uuid;
    @JsonBackReference("singlesegment")
    private TravelService travelService;
    private Location startLocation;
    private Location endLocation;
    private String providerDescription;
    private String serviceDescription;
    private String additionalDescription;
    private String primaryTraveller;
    private String liability;
    private String upgrades;
    private String serviceTypeCode;
    private Integer serviceCount;
    private String startConditions;
    private String startTime;
    private String endConditions;
    private String endTime;
    private String street;
    private String zip;
    private String city;
    private String rateCode;
    private String rateCurrency;
    private BigDecimal rate;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public TravelService getTravelService() {
        return travelService;
    }

    public void setTravelService(final TravelService travelService) {
        this.travelService = travelService;
    }

    /**
     * Get {@link Location} object with:
     * <ul>
     * <li>{@link Location#getName()} holding the producer-provided name, if present. Otherwise, the location name</li>
     * <li>{@link Location#getIataCode()} is only set if the producer provided an IATA code or the location lookup determined one</li>
     * <li>{@link Location#getCountry()} holding the producer-provided country, if present. Otherwise, the location's country</li>
     * </ul>
     *
     * @return
     */
    public Location getStartLocation() {
        if (startLocation == null) {
            startLocation = new Location();
        }
        return startLocation;
    }

    public void setStartLocation(final Location startLocation) {
        this.startLocation = startLocation;
    }

    /**
     * General-purpose getter for the description of the provider ("who"). See {@link #getHotelName()} or {@link #getRentalcarCompany()}.
     */
    public String getProviderDescription() {
        return providerDescription;
    }

    @JsonIgnore
    public String getHotelName() {
        return getProviderDescription();
    }

    @JsonIgnore
    public String getRentalcarCompany() {
        return getProviderDescription();
    }

    @JsonIgnore
    @Deprecated
    public String getMiscServiceDescription() {
        return getProviderDescription();
    }

    public void setProviderDescription(final String providerDescription) {
        this.providerDescription = providerDescription;
    }

    /**
     * General-purpose getter for the description of the service provided ("what"). See {@link #getRoomDescription()},
     * {@link #getVehicleDescription()} resp.
     */
    public String getServiceDescription() {
        return serviceDescription;
    }

    @JsonIgnore
    public String getRoomDescription() {
        return getServiceDescription();
    }

    @JsonIgnore
    public String getVehicleDescription() {
        return getServiceDescription();
    }

    @JsonIgnore
    public String getTransferDescription() {
        return getServiceDescription();
    }

    public void setServiceDescription(final String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    /**
     * For hotels, these are check-in instructions. For rental cars, these are car pickup instructions. For a transfer, this field adds more
     * detailed information about the start of the transfer (location details).
     */
    public String getStartConditions() {
        return startConditions;
    }

    public void setStartConditions(final String startConditions) {
        this.startConditions = startConditions;
    }

    /**
     * For hotels, these are check-out instructions. For rental cars, these are car drop off instructions. For a transfer, this field adds
     * more detailed information about the destination of the transfer (location details).
     */
    public String getEndConditions() {
        return endConditions;
    }

    public void setEndConditions(final String endConditions) {
        this.endConditions = endConditions;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    /**
     * Get {@link Location} object with:
     * <ul>
     * <li>{@link Location#getName()} holding the producer-provided name, if present. Otherwise, the location name</li>
     * <li>{@link Location#getIataCode()} is only set if the producer provided an IATA code or the location lookup determined one</li>
     * <li>{@link Location#getCountry()} holding the producer-provided country, if present. Otherwise, the location's country</li>
     * </ul>
     *
     * @return
     */
    public Location getEndLocation() {
        if (endLocation == null) {
            endLocation = new Location();
        }
        return endLocation;
    }

    public void setEndLocation(final Location endLocation) {
        this.endLocation = endLocation;
    }

    /**
     * Multi-purpose, additional description field. See {@link #getRentalCarIncluded()} and {@link #getHotelAndPensionDescription()}
     */
    public String getAdditionalDescription() {
        return additionalDescription;
    }

    @JsonIgnore
    @Deprecated(forRemoval = true)
    public String getRentalCarExtras() {
        return getAdditionalDescription();
    }

    @JsonIgnore
    public String getRentalCarIncluded() {
        return getAdditionalDescription();
    }

    @JsonIgnore
    public String getHotelAndPensionDescription() {
        return getAdditionalDescription();
    }

    public void setAdditionalDescription(final String additionalDescription) {
        this.additionalDescription = additionalDescription;
    }

    /**
     * A code describing the service type, such as a room code or a car code. Provider should make sure to send the more generally
     * applicable code. If e.g. there is a RoomTypeCode (A1D) and a RoomCode (A1DBX8), the former should be used (and 'BX8' set as
     * {@link #getRateCode()}).
     */
    public String getServiceTypeCode() {
        return serviceTypeCode;
    }

    @JsonIgnore
    public boolean isRefund() {
        return SERVICE_TYPECODE_REFUND.equalsIgnoreCase(serviceTypeCode);
    }

    @JsonIgnore
    public boolean isInsurance() {
        return SERVICE_TYPECODE_INSURANCE.equalsIgnoreCase(serviceTypeCode);
    }

    @JsonIgnore
    public boolean isTransfer() {
        return SERVICE_TYPECODE_TRANSFER.equalsIgnoreCase(serviceTypeCode);
    }

    @JsonIgnore
    public boolean isEvent() {
        return SERVICE_TYPECODE_EVENT.equalsIgnoreCase(serviceTypeCode);
    }

    public void setServiceTypeCode(final String serviceTypeCode) {
        this.serviceTypeCode = serviceTypeCode;
    }

    public void setRefund(final boolean isRefund) {
        if (isRefund) {
            setServiceTypeCode(SERVICE_TYPECODE_REFUND);
        } else if (isRefund()) {
            setServiceTypeCode(null);
        }
    }

    public void setInsurance(final boolean isInsurance) {
        if (isInsurance) {
            setServiceTypeCode(SERVICE_TYPECODE_INSURANCE);
        } else if (isInsurance()) {
            setServiceTypeCode(null);
        }
    }

    public void setTransfer(final boolean isTransfer) {
        if (isTransfer) {
            setServiceTypeCode(SERVICE_TYPECODE_TRANSFER);
        } else if (isTransfer()) {
            setServiceTypeCode(null);
        }
    }

    public void setEvent(final boolean isEvent) {
        if (isEvent) {
            setServiceTypeCode(SERVICE_TYPECODE_EVENT);
        } else if (isTransfer()) {
            setServiceTypeCode(null);
        }
    }

    /**
     * Get the number of hotel rooms or number of rental cars
     */
    public Integer getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(final Integer serviceCount) {
        this.serviceCount = serviceCount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(final String endTime) {
        this.endTime = endTime;
    }

    /**
     * A code describing the rate, e.g. a room rate code. This can be a corporate rate or a promo code. Room rate codes are usually 3-4
     * letters. Sometimes the room type code is added, but here that should be stored separately in {@link #getServiceTypeCode()}.
     */
    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(final String rateCode) {
        this.rateCode = rateCode;
    }

    public String getRateCurrency() {
        return rateCurrency;
    }

    public void setRateCurrency(final String rateCurrency) {
        this.rateCurrency = rateCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(final BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * Get rental car main driver, or passenger name a hotel room is booked on.
     */
    public String getPrimaryTraveller() {
        return primaryTraveller;
    }

    public void setPrimaryTraveller(final String primaryTraveller) {
        this.primaryTraveller = primaryTraveller;
    }

    /**
     * Insurance description, mainly for rental cars.
     */
    public String getLiability() {
        return liability;
    }

    public void setLiability(final String liability) {
        this.liability = liability;
    }

    /**
     * Priced extras for which the provider only has a text description (if structured data is present, consider populating
     * {@link TravelServiceAuxiliary}.
     */
    public String getUpgrades() {
        return upgrades;
    }

    public void setUpgrades(final String upgrades) {
        this.upgrades = upgrades;
    }
}
