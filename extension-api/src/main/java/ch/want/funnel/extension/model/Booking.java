/*
 * Created on 7 Jan 2019
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private UUID tripUuid;
    private UUID providerUuid;
    private OffsetDateTime created;
    private String providerSourcename;
    private String sourceDomain;
    private LocalDate departuredate;
    private LocalDate returndate;
    private String referencenumber;
    private String totalpricecurrency;
    private BigDecimal totalprice;
    private transient JsonNode extensionData;
    private String destinationCode;
    private String destinationName;
    private String url;
    private String providerName;
    private String comment;
    private TripImportStrategy importStrategy = TripImportStrategy.FULL;
    private String extensionClassname;
    private boolean forceNewTrip;
    private transient List<TravelService> travelservices = new ArrayList<>();
    private transient List<PriceItem> priceitems = new ArrayList<>();
    private transient List<BookingPayment> payments = new ArrayList<>();
    private transient List<RawSource> rawsources = new ArrayList<>();
    private transient List<Traveler> participants = new ArrayList<>();
    private transient List<CustomFieldValue> customfields = new ArrayList<>();

    public Booking() {
        // default c'tor
    }

    private static boolean isBlank(final String s) {
        return s == null || s.length() == 0;
    }

    public String getHeader() {
        final StringJoiner joiner = new StringJoiner(", ");
        if (!isBlank(getProviderSourcename())) {
            joiner.add(getProviderSourcename());
        } else if (!isBlank(getProviderName())) {
            joiner.add(getProviderName());
        }
        if (!isBlank(getSourceDomain())) {
            joiner.add(getSourceDomain());
        }
        if (!isBlank(getReferencenumber())) {
            joiner.add(getReferencenumber());
        }
        if ((getTotalprice() != null) && (getTotalprice().compareTo(BigDecimal.ZERO) != 0)) {
            joiner.add(getTotalpricecurrency() + " " + new DecimalFormat("#,##0.00").format(getTotalprice()));
        }
        return joiner.toString();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getTripUuid() {
        return tripUuid;
    }

    public void setTripUuid(final UUID tripUuid) {
        this.tripUuid = tripUuid;
    }

    public UUID getProviderUuid() {
        return providerUuid;
    }

    public void setProviderUuid(final UUID providerUuid) {
        this.providerUuid = providerUuid;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(final OffsetDateTime created) {
        this.created = created;
    }

    public String getProviderSourcename() {
        return providerSourcename;
    }

    public void setProviderSourcename(final String providerSourcename) {
        this.providerSourcename = providerSourcename;
    }

    public String getSourceDomain() {
        return sourceDomain;
    }

    public void setSourceDomain(final String sourceDomain) {
        this.sourceDomain = sourceDomain;
    }

    public LocalDate getDeparturedate() {
        return departuredate;
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

    public LocalDate getReturndate() {
        return returndate;
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

    public String getReferencenumber() {
        return referencenumber;
    }

    public void setReferencenumber(final String referencenumber) {
        this.referencenumber = referencenumber;
    }

    public String getTotalpricecurrency() {
        return totalpricecurrency;
    }

    public void setTotalpricecurrency(final String totalpricecurrency) {
        this.totalpricecurrency = totalpricecurrency;
    }

    public BigDecimal getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(final BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    public JsonNode getExtensionData() {
        return extensionData;
    }

    public void setExtensionData(final JsonNode extensionData) {
        this.extensionData = extensionData;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(final String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(final String destinationName) {
        this.destinationName = destinationName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(final String providerName) {
        this.providerName = providerName;
    }

    public TripImportStrategy getImportStrategy() {
        return importStrategy;
    }

    public void setImportStrategy(final TripImportStrategy importStrategy) {
        this.importStrategy = importStrategy;
    }

    public String getExtensionClassname() {
        return extensionClassname;
    }

    public void setExtensionClassname(final String extensionClassname) {
        this.extensionClassname = extensionClassname;
    }

    public boolean isForceNewTrip() {
        return forceNewTrip;
    }

    public void setForceNewTrip(final boolean forceNewTrip) {
        this.forceNewTrip = forceNewTrip;
    }

    public List<TravelService> getTravelservices() {
        return travelservices;
    }

    public void setTravelservices(final List<TravelService> travelservices) {
        this.travelservices = travelservices;
    }

    public List<PriceItem> getPriceitems() {
        return priceitems;
    }

    public void setPriceitems(final List<PriceItem> priceitems) {
        this.priceitems = priceitems;
    }

    public List<BookingPayment> getPayments() {
        return payments;
    }

    public void setPayments(final List<BookingPayment> payments) {
        this.payments = payments;
    }

    public List<RawSource> getRawsources() {
        return rawsources;
    }

    public void setRawsources(final List<RawSource> rawsources) {
        this.rawsources = rawsources;
    }

    public List<Traveler> getParticipants() {
        return participants;
    }

    public void setParticipants(final List<Traveler> participants) {
        this.participants = participants;
    }

    public List<CustomFieldValue> getCustomfields() {
        return customfields;
    }

    public void setCustomfields(final List<CustomFieldValue> customfields) {
        this.customfields = customfields;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }
}
