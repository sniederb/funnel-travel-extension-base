package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(value = { "header", "departuredateAsUtilDate", "returndateAsUtilDate" }, allowGetters = true)
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private UUID tripUuid;
    private String midofficeReferenceNumber;
    private UUID providerUuid;
    private OffsetDateTime created;
    private OffsetDateTime lastModified;
    private String providerSourcename;
    private String sourceDomain;
    private LocalDate departuredate;
    private LocalDate returndate;
    private String referencenumber;
    private String sourceInternalReferencenumber;
    private String productkey;
    private String travelagencyId;
    private String ticketingTravelagencyId;
    private String agentSign;
    private String customerAccountId;
    private String totalpricecurrency;
    private BigDecimal totalprice;
    private BigDecimal purchaseprice;
    private transient JsonNode extensionData;
    private String destinationCode;
    private String destinationName;
    private String url;
    private String providerName;
    private String comment;
    private TripImportStrategy importStrategy = TripImportStrategy.FULL;
    private ExtensionCallAction extensionCallAction;
    private String extensionClassname;
    private boolean forceNewTrip;
    private boolean packaged;
    private transient List<TravelService> travelservices = new ArrayList<>();
    private transient List<PriceItem> priceitems = new ArrayList<>();
    private transient List<BookingPayment> payments = new ArrayList<>();
    private transient List<RawSource> rawsources = new ArrayList<>();
    private transient List<Traveler> participants = new ArrayList<>();
    private transient List<CustomFieldValue> customfields = new ArrayList<>();
    private transient List<VatDistribution> vatDistribution = new ArrayList<>();

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

    public OffsetDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(final OffsetDateTime lastModified) {
        this.lastModified = lastModified;
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

    /**
     * The {@code referencenumber} reflects the number presented to the customer. If the source system
     * uses a different, internal ID, be sure to set {@link #setSourceInternalReferencenumber(String)}
     */
    public String getReferencenumber() {
        return referencenumber;
    }

    public void setReferencenumber(final String referencenumber) {
        this.referencenumber = referencenumber;
    }

    /**
     * The {@code sourceInternalReferencenumber} reflects the ID for this booking internal to the source system.
     * This is often a UUID or a length hash-type string.
     */
    public String getSourceInternalReferencenumber() {
        return sourceInternalReferencenumber;
    }

    public void setSourceInternalReferencenumber(final String sourceInternalReferencenumber) {
        this.sourceInternalReferencenumber = sourceInternalReferencenumber;
    }

    /**
     * The {@code midofficeReferenceNumber} reflects the ID for this booking in the customer's midoffice / expense system..
     */
    public String getMidofficeReferenceNumber() {
        return midofficeReferenceNumber;
    }

    public void setMidofficeReferenceNumber(final String midofficeReferenceNumber) {
        this.midofficeReferenceNumber = midofficeReferenceNumber;
    }

    /**
     * The {@code productkey} has no business-function within funnel.travel, but consumer extensions
     * might use it.
     */
    public String getProductkey() {
        return productkey;
    }

    public void setProductkey(final String productkey) {
        this.productkey = productkey;
    }

    /**
     * The travel agency key, eg. an Amadeus OID, Galileo PCC, or CETS agency locator.
     */
    public String getTravelagencyId() {
        return travelagencyId;
    }

    public void setTravelagencyId(final String travelagencyId) {
        this.travelagencyId = travelagencyId;
    }

    public String getAgentSign() {
        return agentSign;
    }

    public void setAgentSign(final String agentSign) {
        this.agentSign = agentSign;
    }

    public String getTotalpricecurrency() {
        return totalpricecurrency;
    }

    public void setTotalpricecurrency(final String totalpricecurrency) {
        this.totalpricecurrency = totalpricecurrency;
    }

    /**
     * The total booking price, in {@link #getTotalpricecurrency()}
     *
     * @return
     */
    public BigDecimal getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(final BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    public void updateTotalpriceFromPriceitems() {
        final Map<String, BigDecimal> amountPerCurrency = getPriceitemsIncludingNested().stream()
            .collect(Collectors.groupingBy(PriceItem::getCurrency))
            .entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().stream().map(PriceItem::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add)));
        if (amountPerCurrency.size() == 1) {
            final Map.Entry<String, BigDecimal> bookingAmount = amountPerCurrency.entrySet().iterator().next();
            setTotalpricecurrency(bookingAmount.getKey());
            setTotalprice(bookingAmount.getValue());
        }
    }

    /**
     * The total booking purchase price, in {@link #getTotalpricecurrency()}
     *
     * @return
     */
    public BigDecimal getPurchaseprice() {
        return purchaseprice;
    }

    public void setPurchaseprice(final BigDecimal purchaseprice) {
        this.purchaseprice = purchaseprice;
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

    private Set<PriceItem> getPriceitemsIncludingNested() {
        final Set<PriceItem> allpriceitems = new HashSet<>();
        allpriceitems.addAll(getPriceitems());
        getTravelservices().forEach(srv -> {
            allpriceitems.addAll(srv.getPriceitems());
            srv.getTransportDocuments().forEach(doc -> {
                allpriceitems.addAll(doc.getPriceitems());
            });
        });
        return allpriceitems;
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

    public List<VatDistribution> getVatDistribution() {
        return vatDistribution;
    }

    public void setVatDistribution(final List<VatDistribution> vatDistribution) {
        this.vatDistribution = vatDistribution;
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

    public void addCommentLine(final String line) {
        if ((this.comment == null) || (this.comment.length() == 0)) {
            this.comment = line;
        } else {
            this.comment = this.comment + "\n" + line;
        }
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public ExtensionCallAction getExtensionCallAction() {
        return extensionCallAction;
    }

    public void setExtensionCallAction(final ExtensionCallAction extensionCallAction) {
        this.extensionCallAction = extensionCallAction;
    }

    /**
     * If true, the {@link #getTravelservices()} form a package, and consumers must not split these services
     *
     * @return
     */
    public boolean isPackaged() {
        return packaged;
    }

    public void setPackaged(final boolean packaged) {
        this.packaged = packaged;
    }

    public String getTicketingTravelagencyId() {
        return ticketingTravelagencyId;
    }

    public void setTicketingTravelagencyId(final String ticketingTravelagencyId) {
        this.ticketingTravelagencyId = ticketingTravelagencyId;
    }

    /**
     * Reference to customer account. In Amadeus this is the AIAN.
     */
    public String getCustomerAccountId() {
        return customerAccountId;
    }

    public void setCustomerAccountId(final String customerAccountId) {
        this.customerAccountId = customerAccountId;
    }
}
