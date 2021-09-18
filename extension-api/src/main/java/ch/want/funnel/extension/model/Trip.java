package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "header", "departuredateAsUtilDate", "returndateAsUtilDate" }, allowGetters = true)
public class Trip implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private Integer funnelIdentifier;
    private String accountIdentifier;
    private UUID accountUuid;
    private UUID organizationUuid;
    private OffsetDateTime created;
    private String title;
    private LocalDate departuredate;
    private LocalDate returndate;
    private String destination;
    private String comment;
    private transient List<Booking> bookings = new ArrayList<>();
    private transient List<CustomFieldValue> customfieldValues = new ArrayList<>();
    private transient List<OrganizationUnitAgencyCode> agencyCodes = new ArrayList<>();

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public Integer getFunnelIdentifier() {
        return funnelIdentifier;
    }

    public void setFunnelIdentifier(final Integer funnelIdentifier) {
        this.funnelIdentifier = funnelIdentifier;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(final String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    public UUID getAccountUuid() {
        return accountUuid;
    }

    public void setAccountUuid(final UUID accountUuid) {
        this.accountUuid = accountUuid;
    }

    public UUID getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final UUID organizationUuid) {
        this.organizationUuid = organizationUuid;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(final OffsetDateTime created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
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

    public void setReturndate(final LocalDate returndate) {
        this.returndate = returndate;
    }

    /**
     * Some extensions might use libraries which still lack proper java.time support.
     *
     * @return
     */
    public Date getReturndateAsUtilDate() {
        return returndate == null ? null : java.util.Date.from(returndate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(final String destination) {
        this.destination = destination;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(final List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<CustomFieldValue> getCustomfieldValues() {
        return customfieldValues;
    }

    public void setCustomfieldValues(final List<CustomFieldValue> customfieldValues) {
        this.customfieldValues = customfieldValues;
    }

    public List<OrganizationUnitAgencyCode> getAgencyCodes() {
        return agencyCodes;
    }

    public void setAgencyCodes(final List<OrganizationUnitAgencyCode> agencyCodes) {
        this.agencyCodes = agencyCodes;
    }
}
