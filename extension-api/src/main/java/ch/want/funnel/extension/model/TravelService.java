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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "uuid")
@JsonIgnoreProperties(value = { "departuredateAsUtilDate", "returndateAsUtilDate" }, allowGetters = true)
public class TravelService implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private UUID bookingUuid;
    private UUID providerUuid;
    private String providerSourcename;
    private TravelServiceType travelServiceType;
    private BookingStatus bookingstatus;
    private String referenceNumber;
    private LocalDate departuredate;
    private LocalDate returndate;
    private String cancellationPolicy;
    private String comment;
    @JsonManagedReference("service-documents")
    private transient List<TransportDocument> transportDocuments = new ArrayList<>();
    @JsonManagedReference("service-priceitems")
    private transient List<PriceItem> priceitems = new ArrayList<>();
    @JsonManagedReference("service-legs")
    private transient List<SegmentedLeg> segmentedLegs = new ArrayList<>();
    @JsonManagedReference("singlesegment")
    private transient SingleSegment singleSegment;
    @JsonManagedReference("references")
    private transient List<TravelServiceReference> travelServiceReferences = new ArrayList<>();

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
     * Use {@link #getReferenceNumber()}
     *
     * @return
     */
    @Deprecated
    public String getReferencenumber() {
        return getReferenceNumber();
    }

    /**
     * Use {@link #setReferenceNumber(String)}
     *
     * @return
     */
    @Deprecated
    public void setReferencenumber(final String referencenumber) {
        setReferenceNumber(referencenumber);
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

    public List<TransportDocument> getTransportDocuments() {
        return transportDocuments;
    }

    public void setTransportDocuments(final List<TransportDocument> transportDocuments) {
        this.transportDocuments = transportDocuments;
    }

    /**
     * Get all price items associated with this service. This will <strong>include</strong> price items bound to a
     * {@link TransportDocument}.
     */
    public List<PriceItem> getPriceitems() {
        return priceitems;
    }

    public void setPriceitems(final List<PriceItem> priceitems) {
        this.priceitems = priceitems;
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
}
