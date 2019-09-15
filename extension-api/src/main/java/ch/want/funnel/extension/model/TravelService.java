/*
 * Created on 26 Apr 2018
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TravelService implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private UUID bookingUuid;
    private UUID providerUuid;
    private String providerSourcename;
    private TravelServiceType travelServiceType;
    private BookingStatus bookingstatus;
    private String referencenumber;
    private LocalDate departuredate;
    private LocalDate returndate;
    private String cancellationPolicy;
    private transient List<TransportDocument> transportDocuments = new ArrayList<>();
    private transient List<PriceItem> priceitems = new ArrayList<>();
    private transient List<SegmentedLeg> segmentedLegs = new ArrayList<>();
    private transient SingleSegment singleSegment;

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

    public String getReferencenumber() {
        return referencenumber;
    }

    public void setReferencenumber(final String referencenumber) {
        this.referencenumber = referencenumber;
    }

    public LocalDate getDeparturedate() {
        return departuredate;
    }

    public void setDeparturedate(final LocalDate departuredate) {
        this.departuredate = departuredate;
    }

    public LocalDate getReturndate() {
        return returndate;
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
}
