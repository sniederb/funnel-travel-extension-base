/*
 * Created on 26 Apr 2018
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "uuid")
@JsonIgnoreProperties(value = { "departuredateAsUtilDate", "returndateAsUtilDate" }, allowGetters = true)
public class TravelService implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private UUID bookingUuid;
    private UUID providerUuid;
    private String providerSourcename;
    private String providerName;
    private TravelServiceType travelServiceType;
    private BookingStatus bookingstatus;
    private String referenceNumber;
    private String sourceInternalReferenceNumber;
    private LocalDate departuredate;
    private LocalDate returndate;
    private String cancellationPolicy;
    private String comment;
    private transient JsonNode extensionData;
    private Boolean nonBspFlight;
    private Boolean ndcFlight;
    private Boolean paidOnSite;
    private Location destination;
    @JsonManagedReference("service-documents")
    private transient List<TransportDocument> transportDocuments = new ArrayList<>();
    @JsonManagedReference("service-priceitems")
    private transient List<PriceItem> priceitems = new ArrayList<>();
    @JsonManagedReference("service-priceitems")
    private transient List<PriceItem> onsitePriceitems = new ArrayList<>();
    @JsonManagedReference("service-payments")
    private transient List<BookingPayment> bookingPayments = new ArrayList<>();
    @JsonManagedReference("service-legs")
    private transient List<SegmentedLeg> segmentedLegs = new ArrayList<>();
    @JsonManagedReference("singlesegment")
    private transient SingleSegment singleSegment;
    @JsonManagedReference("references")
    private transient List<TravelServiceReference> travelServiceReferences = new ArrayList<>();
    @JsonManagedReference("service-auxiliaries")
    private transient List<TravelServiceAuxiliary> auxiliaries = new ArrayList<>();
    @JsonIdentityReference(alwaysAsId = true)
    private transient List<Traveler> assignedTravelers = new ArrayList<>();
    private transient List<CustomFieldValue> customfieldValues = new ArrayList<>();

    public TravelService() {
        // default c'tor
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getBookingUuid() {
        return bookingUuid;
    }

    public void setBookingUuid(final UUID bookingUuid) {
        this.bookingUuid = bookingUuid;
    }

    public UUID getProviderUuid() {
        return providerUuid;
    }

    public void setProviderUuid(final UUID providerUuid) {
        this.providerUuid = providerUuid;
    }

    /**
     * Get the provider name as delivered from the source system. From a GDS, this will typically be a
     * two- or three-letter GDS code (airline, hotel chain)
     */
    public String getProviderSourcename() {
        return providerSourcename;
    }

    public void setProviderSourcename(final String providerSourcename) {
        this.providerSourcename = providerSourcename;
    }

    /**
     * The funnel.travel provider name, associated to {@link #getProviderUuid()}
     *
     * @return
     */
    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(final String providerName) {
        this.providerName = providerName;
    }

    public TravelServiceType getTravelServiceType() {
        return travelServiceType;
    }

    public void setTravelServiceType(final TravelServiceType travelServiceType) {
        this.travelServiceType = travelServiceType;
    }

    public BookingStatus getBookingstatus() {
        return bookingstatus;
    }

    public void setBookingstatus(final BookingStatus bookingstatus) {
        this.bookingstatus = bookingstatus;
    }

    /**
     * The reference number of this service at the source, e.g. a hotel confirmation number. For
     * references in one or multiple booking platforms, use {@link TravelServiceReference}
     */
    public String getReferenceNumber() {
        return referenceNumber;
    }

    @JsonIgnore
    public String getRefundedTicketNumber() {
        return getReferenceNumber();
    }

    public void setReferenceNumber(final String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * The {@code sourceInternalReferenceNumber} reflects the ID for this service internal to the source system.
     * This is often a UUID or a lengthy hash-type string, but might also be identical to {@link #getReferenceNumber()}
     */
    public String getSourceInternalReferenceNumber() {
        return sourceInternalReferenceNumber;
    }

    public void setSourceInternalReferenceNumber(final String sourceInternalReferenceNumber) {
        this.sourceInternalReferenceNumber = sourceInternalReferenceNumber;
    }

    /**
     * The start date of the service, using 'flight' naming. Same as {@link #getCheckInDate()}
     */
    public LocalDate getDeparturedate() {
        return departuredate;
    }

    @JsonIgnore
    public LocalDate getCheckInDate() {
        return getDeparturedate();
    }

    /**
     * Some extensions might use libraries which still lack proper java.time support.
     *
     * @return
     */
    @JsonIgnore
    public Date getDeparturedateAsUtilDate() {
        return departuredate == null ? null : java.util.Date.from(departuredate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public void setDeparturedate(final LocalDate departuredate) {
        this.departuredate = departuredate;
    }

    /**
     * The end date of the service, using 'flight' naming. Same as {@link #getCheckOutDate()}
     *
     * @return
     */
    public LocalDate getReturndate() {
        return returndate;
    }

    @JsonIgnore
    public LocalDate getCheckOutDate() {
        return getReturndate();
    }

    /**
     * Some extensions might use libraries which still lack proper java.time support.
     *
     * @return
     */
    @JsonIgnore
    public Date getReturndateAsUtilDate() {
        return returndate == null ? null : java.util.Date.from(returndate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public void setReturndate(final LocalDate returndate) {
        this.returndate = returndate;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(final String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    /**
     * Get list of {@link TransportDocument}, sorted by {@link TransportDocument#getTransportDocumentType()},
     * {@link TransportDocument#getReferenceNumber()} and {@link TransportDocument#getDescription()}.
     *
     * @return
     */
    public List<TransportDocument> getTransportDocuments() {
        return transportDocuments;
    }

    public void setTransportDocuments(final List<TransportDocument> transportDocuments) {
        this.transportDocuments = transportDocuments;
    }

    /**
     * Get all price items associated with this service. This will <strong>include</strong> price items bound to a
     * {@link TransportDocument}. Note that {@link PriceItem}s might not have object-identity.
     */
    public List<PriceItem> getPriceitems() {
        return priceitems;
    }

    public void setPriceitems(final List<PriceItem> priceitems) {
        this.priceitems = priceitems;
    }

    public List<PriceItem> getOnsitePriceitems() {
        return onsitePriceitems;
    }

    public void setOnsitePriceitems(final List<PriceItem> onsitePriceitems) {
        this.onsitePriceitems = onsitePriceitems;
    }

    public List<SegmentedLeg> getSegmentedLegs() {
        return segmentedLegs;
    }

    public void setSegmentedLegs(final List<SegmentedLeg> segmentedLegs) {
        this.segmentedLegs = segmentedLegs;
    }

    public SingleSegment getSingleSegment() {
        return singleSegment;
    }

    public void setSingleSegment(final SingleSegment singleSegment) {
        this.singleSegment = singleSegment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public List<TravelServiceReference> getTravelServiceReferences() {
        return travelServiceReferences;
    }

    public void setTravelServiceReferences(final List<TravelServiceReference> travelServiceReferences) {
        this.travelServiceReferences = travelServiceReferences;
    }

    /**
     * If true, the {@link #getProviderSourcename()} is not a BSP-airline. As such, BSP-style 13-digit tickets are probably meaningless, and
     * vendor locators should be used instead. Irrelevant for all service types except {@link TravelServiceType#FLIGHT}.
     */
    public Boolean getNonBspFlight() {
        return nonBspFlight;
    }

    public void setNonBspFlight(final Boolean nonBspFlight) {
        this.nonBspFlight = nonBspFlight;
    }

    /**
     * If true, the flight was booked via an NDC platform. Irrelevant for all service types except {@link TravelServiceType#FLIGHT}.
     */
    public Boolean getNdcFlight() {
        return ndcFlight;
    }

    public void setNdcFlight(final Boolean ndcFlight) {
        this.ndcFlight = ndcFlight;
    }

    /**
     * If true, the customer will pay for this service on-site.
     */
    public Boolean getPaidOnSite() {
        return paidOnSite;
    }

    public void setPaidOnSite(final Boolean paidOnSite) {
        this.paidOnSite = paidOnSite;
    }

    /**
     * Derived destination. For segmented services, this is the calculated destination. For all other, this is the
     * {@link SingleSegment#getStartLocation()}.
     */
    public Location getDestination() {
        return destination;
    }

    public void setDestination(final Location destination) {
        this.destination = destination;
    }

    public List<BookingPayment> getBookingPayments() {
        return bookingPayments;
    }

    public void setBookingPayments(final List<BookingPayment> bookingPayments) {
        this.bookingPayments = bookingPayments;
    }

    public JsonNode getExtensionData() {
        return extensionData;
    }

    public void setExtensionData(final JsonNode extensionData) {
        this.extensionData = extensionData;
    }

    /**
     * Get custom field values on service-level. Note that there might be further custom
     * field values on the parent booking and/or trip.
     */
    public List<CustomFieldValue> getCustomfieldValues() {
        return customfieldValues;
    }

    public void setCustomfieldValues(final List<CustomFieldValue> customfieldValues) {
        this.customfieldValues = customfieldValues;
    }

    /**
     * Get the travelers assigned to this service. This collection might be empty, in
     * which case all travelers are to be considered 'assigned'. Note that the collection items might
     * <strong>not have object identity</strong> with {@link Booking#getParticipants()}
     */
    public List<Traveler> getAssignedTravelers() {
        return assignedTravelers;
    }

    public void setAssignedTravelers(final List<Traveler> assignedTravelers) {
        this.assignedTravelers = assignedTravelers;
    }

    public List<TravelServiceAuxiliary> getAuxiliaries() {
        return auxiliaries;
    }

    public void setAuxiliaries(final List<TravelServiceAuxiliary> auxiliaries) {
        this.auxiliaries = auxiliaries;
    }
}
