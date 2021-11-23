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
    private UUID uuid;
    @JsonBackReference("singlesegment")
    private TravelService travelService;
    private String destination;
    private String destinationName;
    private String endDestination;
    private String endDestinationName;
    private String providerDescription;
    private String serviceDescription;
    private String additionalDescription;
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

    public String getDestination() {
        return destination;
    }

    /**
     * Set the destination in a best-effort:
     * <ul>
     * <li>If available, set &lt;three-letter location code&gt;/&lt;two-letter country code&gt; (eg ZRH/CH)</li>
     * <li>If a destination description is available, set that</li>
     * <li>If a two-letter country code is available, add it with a trailing slash (eg. Heathrow/GB)</li>
     * </ul>
     *
     * @param destination
     */
    public void setDestination(final String destination) {
        this.destination = destination;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(final String destinationName) {
        this.destinationName = destinationName;
    }

    /**
     * General-purpose getter for the description of the provider.
     * See {@link #getHotelName()}, {@link #getRentalcarCompany()} and {@link #getMiscServiceDescription()} resp.
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
    public String getMiscServiceDescription() {
        return getProviderDescription();
    }

    public void setProviderDescription(final String providerDescription) {
        this.providerDescription = providerDescription;
    }

    /**
     * General-purpose getter for the description of the service provided.
     * See {@link #getRoomDescription()}, {@link #getVehicleDescription()} resp.
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

    public String getStartConditions() {
        return startConditions;
    }

    public void setStartConditions(final String startConditions) {
        this.startConditions = startConditions;
    }

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

    public String getEndDestination() {
        return endDestination;
    }

    public void setEndDestination(final String endDestination) {
        this.endDestination = endDestination;
    }

    public String getEndDestinationName() {
        return endDestinationName;
    }

    public void setEndDestinationName(final String endDestinationName) {
        this.endDestinationName = endDestinationName;
    }

    /**
     * Multi-purpose, additional description field. See {@link #getRentalCarExtras()} and {@link #getHotelAndPensionDescription()}
     */
    public String getAdditionalDescription() {
        return additionalDescription;
    }

    @JsonIgnore
    public String getRentalCarExtras() {
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
     * A code describing the service type, such as a room code or a car code.
     * Provider should make sure to send the more generally applicable code. If e.g. there
     * is a RoomTypeCode (A1D) and a RoomCode (A1DBX8), the former should be used.
     */
    public String getServiceTypeCode() {
        return serviceTypeCode;
    }

    public boolean isRefund() {
        return SERVICE_TYPECODE_REFUND.equalsIgnoreCase(serviceTypeCode);
    }

    public boolean isInsurance() {
        return SERVICE_TYPECODE_INSURANCE.equalsIgnoreCase(serviceTypeCode);
    }

    public boolean isTransfer() {
        return SERVICE_TYPECODE_TRANSFER.equalsIgnoreCase(serviceTypeCode);
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
     * A code describing the rate, e.g. a room rate code. This can be a corporate rate or a promo code.
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
}
