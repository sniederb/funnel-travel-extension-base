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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

import ch.want.funnel.extension.tripdata.TripDataConsumer;

@JsonIgnoreProperties(value = { "header", "departuredateAsUtilDate", "returndateAsUtilDate" }, allowGetters = true)
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private UUID tripUuid;
    private String midofficeReferenceNumber;
    private UUID providerUuid;
    private BookingCustomer customer;
    private OffsetDateTime created;
    private OffsetDateTime lastModified;
    private String providerSourcename;
    private String sourceDomain;
    private LocalDate departuredate;
    private LocalDate returndate;
    private String referenceNumber;
    private String sourceInternalReferencenumber;
    private String productkey;
    private String travelagencyId;
    private String ticketingTravelagencyId;
    private String agentSign;
    private String totalpricecurrency;
    private BigDecimal totalprice;
    private transient JsonNode extensionData;
    private Location destination;
    private String url;
    private String providerName;
    private SalesChannel salesChannel;
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
        if (!isBlank(getReferenceNumber())) {
            joiner.add(getReferenceNumber());
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

    /**
     * Nullable! This is not mapped as Optional in order to adhere to JavaBeans specs.
     */
    public BookingCustomer getCustomer() {
        return customer;
    }

    @JsonIgnore
    public Optional<BookingCustomer> getCustomerOptional() {
        return Optional.ofNullable(customer);
    }

    public void setCustomer(final BookingCustomer customer) {
        this.customer = customer;
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
    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(final String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * The {@code sourceInternalReferencenumber} reflects the ID for this booking internal to the source system.
     * This is often a UUID or a length hash-type string, but might also be identical to {@link #getReferencenumber()}
     */
    public String getSourceInternalReferencenumber() {
        return sourceInternalReferencenumber;
    }

    public void setSourceInternalReferencenumber(final String sourceInternalReferencenumber) {
        this.sourceInternalReferencenumber = sourceInternalReferencenumber;
    }

    /**
     * The {@code midofficeReferenceNumber} reflects the ID for this booking in the customer's midoffice / expense system.
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

    public JsonNode getExtensionData() {
        return extensionData;
    }

    public void setExtensionData(final JsonNode extensionData) {
        this.extensionData = extensionData;
    }

    public Location getDestination() {
        if (destination == null) {
            destination = new Location();
        }
        return destination;
    }

    public void setDestination(final Location destination) {
        this.destination = destination;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
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

    /**
     * The raw sources, sorted by {@link RawSource#getEntrydate()}. Note that {@link RawSource#getSource()}
     * will be null, unless {@link TripDataConsumer#isPayloadFromDatabase()} is set to true.
     */
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

    public SalesChannel getSalesChannel() {
        return salesChannel;
    }

    public void setSalesChannel(final SalesChannel salesChannel) {
        this.salesChannel = salesChannel;
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
     * By default, all {@link #priceitems} collections hold all entries including nested. Thus {@link #getPriceitems()} will
     * return <strong>all</strong> priceitems, including those on {@link TravelService} and {@link TransportDocument}
     * level. Calling this method removes priceitems from all collections except the most local one.
     */
    public void removeMultiReferencedPriceitems() {
        for (final Iterator<PriceItem> iterator = priceitems.iterator(); iterator.hasNext();) {
            final PriceItem priceitem = iterator.next();
            if (priceitem.getTravelService() != null) {
                iterator.remove();
            }
        }
        for (final TravelService service : travelservices) {
            for (final Iterator<PriceItem> iterator = service.getPriceitems().iterator(); iterator.hasNext();) {
                final PriceItem priceitem = iterator.next();
                if (priceitem.getTransportDocument() != null) {
                    iterator.remove();
                }
            }
        }
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
}
