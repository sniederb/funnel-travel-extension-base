package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

import ch.want.funnel.extension.ExtensionResult;
import ch.want.funnel.extension.tripdata.TripDataConsumer;

@JsonIgnoreProperties(value = { "header", "departuredateAsUtilDate", "returndateAsUtilDate" }, allowGetters = true)
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private UUID tripUuid;
    private String midofficeReferenceNumber;
    private UUID providerUuid;
    private BookingLifecycleState lifecycleState = BookingLifecycleState.BOOKED;
    private BookingCustomer customer;
    private OffsetDateTime created;
    private OffsetDateTime lastModified;
    private String providerSourcename;
    private String providerName;
    private String sourceDomain;
    private String supplier;
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
    private BigDecimal depositAmount;
    private LocalDate fullPaymentDueDate;
    private transient JsonNode extensionData;
    private Location destination;
    private String url;
    private SalesChannel salesChannel;
    private String comment;
    private TripImportStrategy importStrategy = TripImportStrategy.FULL;
    private ExtensionCallAction extensionCallAction;
    private String extensionClassname;
    private boolean forceNewTrip;
    private boolean packaged;
    private BookingFormOfPayment formOfPayment;
    private transient List<TravelService> travelservices = new ArrayList<>();
    private transient List<PriceItem> priceitems = new ArrayList<>();
    private transient List<PriceItem> onsitePriceitems = new ArrayList<>();
    private transient List<BookingPayment> payments = new ArrayList<>();
    private transient List<RawSource> rawsources = new ArrayList<>();
    private transient List<Traveler> participants = new ArrayList<>();
    private transient List<CustomFieldValue> customfieldValues = new ArrayList<>();
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

    public BookingFormOfPayment getFormOfPayment() {
        return formOfPayment;
    }

    @JsonIgnore
    public Optional<BookingFormOfPayment> getFormOfPaymentOptional() {
        return Optional.ofNullable(formOfPayment);
    }

    public void setFormOfPayment(final BookingFormOfPayment formOfPayment) {
        this.formOfPayment = formOfPayment;
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

    public BookingLifecycleState getLifecycleState() {
        return lifecycleState;
    }

    public void setLifecycleState(final BookingLifecycleState lifecycleState) {
        this.lifecycleState = lifecycleState;
    }

    /**
     * Name of the company providing the travel service. Note that this value should match a registered funnel.travel provider.
     */
    public String getProviderSourcename() {
        return providerSourcename;
    }

    public void setProviderSourcename(final String providerSourcename) {
        this.providerSourcename = providerSourcename;
    }

    /**
     * Name of the platform which owns the booking, i.e. which created the booking number.
     * This is usually a static value for a given extension.
     */
    public String getSourceDomain() {
        return sourceDomain;
    }

    public void setSourceDomain(final String sourceDomain) {
        this.sourceDomain = sourceDomain;
    }

    /**
     * Name of the company sending the purchase invoice.
     */
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(final String supplier) {
        this.supplier = supplier;
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
     * This is often a UUID or a lengthy hash-type string, but might also be identical to {@link #getReferenceNumber()}
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

    /**
     * <strong>Setting a value here has no effect.</strong> If an extension wants to update the midoffice reference number, it needs to be
     * returned in the {@link ExtensionResult#getMidofficeReferenceNumber()}
     */
    public void setMidofficeReferenceNumber(final String midofficeReferenceNumber) {
        this.midofficeReferenceNumber = midofficeReferenceNumber;
    }

    /**
     * The {@code productkey} has no business-function within funnel.travel, but consumer extensions might use it.
     * Field should indicate something like a package catalog number.
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

    /**
     * The amount payable on booking, usually the non-refundable part of the booking.
     */
    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(final BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    /**
     * The date the full/remaining payment is due. Typically this is the date the cancellation policy turns to 0% refundable.
     */
    public LocalDate getFullPaymentDueDate() {
        return fullPaymentDueDate;
    }

    public void setFullPaymentDueDate(final LocalDate fullPaymentDueDate) {
        this.fullPaymentDueDate = fullPaymentDueDate;
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

    /**
     * Get priceitems. Be careful with object references, as {@link TravelService#getPriceitems()}
     * or {@link TransportDocument#getPriceitems()} may return the same priceitem but with a different
     * object identity. Thus always check using {@link PriceItem#getUuid()}
     *
     * @return
     */
    public List<PriceItem> getPriceitems() {
        return priceitems;
    }

    public void setPriceitems(final List<PriceItem> priceitems) {
        this.priceitems = priceitems;
    }

    /**
     * Get {@link PriceItem} with type {@link PriceItemType#ONSITE}. These are kept separate, as they typically
     * are not included in any 'total' calculation.
     */
    public List<PriceItem> getOnsitePriceitems() {
        return onsitePriceitems;
    }

    public void setOnsitePriceitems(final List<PriceItem> onsitePriceitems) {
        this.onsitePriceitems = onsitePriceitems;
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

    /**
     * @deprecated Use {@link #getCustomfieldValues()}
     */
    @Deprecated(since = "3.0.18")
    @JsonIgnore
    public List<CustomFieldValue> getCustomfields() {
        return getCustomfieldValues();
    }

    /**
     * @deprecated Use {@link #setCustomfieldValues(List)}
     */
    @Deprecated(since = "3.0.18")
    @JsonIgnore
    public void setCustomfields(final List<CustomFieldValue> customfields) {
        setCustomfieldValues(customfields);
    }

    /**
     * Get custom field values on booking-level. Note that there might be further custom
     * field values on services and/or the parent trip.
     */
    public List<CustomFieldValue> getCustomfieldValues() {
        return customfieldValues;
    }

    public void setCustomfieldValues(final List<CustomFieldValue> customfieldValues) {
        this.customfieldValues = customfieldValues;
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
     * If true, the {@link #getTravelservices()} form a package, and consumers must not split these services.
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
        for (final Iterator<PriceItem> iterator = onsitePriceitems.iterator(); iterator.hasNext();) {
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
            for (final Iterator<PriceItem> iterator = service.getOnsitePriceitems().iterator(); iterator.hasNext();) {
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

    private Collection<PriceItem> getPriceitemsIncludingNested() {
        final Map<UUID, PriceItem> savedPriceitems = new HashMap<>();
        final Set<PriceItem> unsavedPriceitems = new HashSet<>();
        getPriceitems().forEach(priceitem -> addPriceitem(priceitem, savedPriceitems, unsavedPriceitems));
        getTravelservices().forEach(srv -> {
            srv.getPriceitems().forEach(priceitem -> addPriceitem(priceitem, savedPriceitems, unsavedPriceitems));
            srv.getTransportDocuments().forEach(doc -> doc.getPriceitems().forEach(priceitem -> addPriceitem(priceitem, savedPriceitems, unsavedPriceitems)));
        });
        return Stream.concat(savedPriceitems.values().stream(), unsavedPriceitems.stream())
            .collect(Collectors.toSet());
    }

    private void addPriceitem(final PriceItem priceitem, final Map<UUID, PriceItem> savedPriceitems, final Set<PriceItem> unsavedPriceitems) {
        if (priceitem.getUuid() != null) {
            savedPriceitems.put(priceitem.getUuid(), priceitem);
        } else {
            unsavedPriceitems.add(priceitem);
        }
    }
}
