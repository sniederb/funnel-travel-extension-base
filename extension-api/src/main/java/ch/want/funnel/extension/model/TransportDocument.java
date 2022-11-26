/*
 * Created on 26 Apr 2018
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "uuid")
public class TransportDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String REVALIDATION = "Revalidation";
    public static final String VOID = "Void";
    private UUID uuid;
    private LocalDateTime issueTimestamp;
    @JsonBackReference("service-documents")
    private TravelService travelService;
    private Traveler traveler;
    private TransportDocumentType transportDocumentType;
    private String issuingAirline;
    private String referenceNumber;
    private String exchangeForReferenceNumber;
    private String internalReference;
    private boolean associated;
    private String description;
    private String reasonCode;
    private String reasonSubCode;
    private String ancillaryValue;
    private FareType fareType = FareType.PUBLIC;
    @JsonManagedReference("document-prices")
    private transient List<PriceItem> priceitems = new ArrayList<>();
    @JsonManagedReference("document-payments")
    private transient List<BookingPayment> bookingPayments = new ArrayList<>();
    private transient List<String> associatedSegments = new ArrayList<>();

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

    public Traveler getTraveler() {
        return traveler;
    }

    public void setTraveler(final Traveler traveler) {
        this.traveler = traveler;
    }

    public TransportDocumentType getTransportDocumentType() {
        return transportDocumentType;
    }

    public void setTransportDocumentType(final TransportDocumentType transportDocumentType) {
        this.transportDocumentType = transportDocumentType;
    }

    /**
     * For flight-related documents, this method indicate the ticket issuing airline (2-letter airline code).
     *
     * @return
     */
    public String getIssuingAirline() {
        return issuingAirline;
    }

    public void setIssuingAirline(final String issuingAirline) {
        this.issuingAirline = issuingAirline;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(final String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getDescription() {
        return description;
    }

    /**
     * For EMD: add general-purpose description. This might be the RFIC description, esp. if the RFIC itself is unavailable.
     * For TICKET: leave empty. In case of a revalidation, set {@link #REVALIDATION} or a string starting with {@link #REVALIDATION}.
     *
     * @param description
     * @see #setReasonCode(String)
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    public List<PriceItem> getPriceitems() {
        return priceitems;
    }

    public void setPriceitems(final List<PriceItem> priceitems) {
        this.priceitems = priceitems;
    }

    /**
     * Get a list of elements, the count of which must match the count of all segments present in the parent.
     * An association will be created/updated if the value at the matching index is truthy (true, TRUE), or if
     * the list contains the UUID of the segment (at any position!)
     */
    public List<String> getAssociatedSegments() {
        return associatedSegments;
    }

    public void initializeAssociatedSegments(final TravelService segmentedService) {
        initializeAssociatedSegments(segmentedService, false);
    }

    public void initializeAssociatedSegments(final TravelService segmentedService, final boolean defaultValue) {
        associatedSegments.clear();
        segmentedService.getSegmentedLegs().stream()
            .flatMap(leg -> leg.getSegments().stream())
            .forEach(seg -> associatedSegments.add(Boolean.toString(defaultValue)));
    }

    public void setAssociatedSegments(final List<String> associatedSegments) {
        this.associatedSegments = associatedSegments;
    }

    /**
     * A technical reference which is used to if {@link #getReferenceNumber()} is empty. Producers should populate this
     * with something like an ID, UUID or tattoo number if present.
     */
    public String getInternalReference() {
        return internalReference;
    }

    public void setInternalReference(final String internalReference) {
        this.internalReference = internalReference;
    }

    /**
     * Set this to true for documents which are bound to the segments of {@link #getAssociatedSegments()},
     * i.e. the document is rendered obsolete if the segment is cancelled. Tickets and EMD-S should be
     * left at {@code associated} = false
     *
     * @return
     */
    public boolean isAssociated() {
        return associated;
    }

    public void setAssociated(final boolean associated) {
        this.associated = associated;
    }

    public LocalDateTime getIssueTimestamp() {
        return issueTimestamp;
    }

    public void setIssueTimestamp(final LocalDateTime issueTimestamp) {
        this.issueTimestamp = issueTimestamp;
    }

    public String getExchangeForReferenceNumber() {
        return exchangeForReferenceNumber;
    }

    public void setExchangeForReferenceNumber(final String exchangeForReferenceNumber) {
        this.exchangeForReferenceNumber = exchangeForReferenceNumber;
    }

    /**
     * For EMD, this is the RFIC
     *
     * @return
     */
    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(final String reasonCode) {
        this.reasonCode = reasonCode;
    }

    /**
     * For EMD, this is the RFISC
     *
     * @return
     */
    public String getReasonSubCode() {
        return reasonSubCode;
    }

    public void setReasonSubCode(final String reasonSubCode) {
        this.reasonSubCode = reasonSubCode;
    }

    /**
     * Value or additional description of EMD service. This field holds the seat number,
     * or possibly a meal code (if booked as ancillary)
     *
     * @return
     */
    public String getAncillaryValue() {
        return ancillaryValue;
    }

    public void setAncillaryValue(final String ancillaryValue) {
        this.ancillaryValue = ancillaryValue;
    }

    public FareType getFareType() {
        return fareType;
    }

    public void setFareType(final FareType fareType) {
        this.fareType = fareType;
    }

    public List<BookingPayment> getBookingPayments() {
        return bookingPayments;
    }

    public void setBookingPayments(final List<BookingPayment> bookingPayments) {
        this.bookingPayments = bookingPayments;
    }

    public boolean isRevalidated() {
        return description != null && description.startsWith(REVALIDATION);
    }
}
