package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import ch.want.funnel.extension.model.travelerprofile.ExtendedProfileData;

public class Traveler implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private UUID userloginUuid;
    private UUID organizationUuid;
    private String tattooNumber;
    private String firstname;
    private String firstnameNonAps;
    private String lastname;
    private String fullname;
    private String email;
    private String sourceId;
    private String sourceDisplayId;
    private LocalDate birthdate;
    private String passportCountry;
    private String passportNumber;
    private LocalDate passportExpiration;
    private String passportPlaceOfIssue;
    private ExtendedProfileData extendedProfileData;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final UUID organizationUuid) {
        this.organizationUuid = organizationUuid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getFirstnameNonAps() {
        return firstnameNonAps;
    }

    public void setFirstnameNonAps(final String firstnameNonAps) {
        this.firstnameNonAps = firstnameNonAps;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    /**
     * Set by extensions which cannot distinguish between first and last name
     *
     * @return
     */
    public String getFullname() {
        return fullname;
    }

    public void setFullname(final String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public UUID getUserloginUuid() {
        return userloginUuid;
    }

    public void setUserloginUuid(final UUID userloginUuid) {
        this.userloginUuid = userloginUuid;
    }

    /**
     * The internal ID of the traveler in the source system
     *
     * @return
     */
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(final String sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * The displayed ID of the traveler in the source system. This can be identical to
     * {@link #getSourceId()} (or - in that case - left empty), but some systems maintain
     * an internal ID (often UUID or sequence) and a display value (often a login name or similar)
     * 
     * @return
     */
    public String getSourceDisplayId() {
        return sourceDisplayId;
    }

    public void setSourceDisplayId(final String sourceDisplayId) {
        this.sourceDisplayId = sourceDisplayId;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(final LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassportCountry() {
        return passportCountry;
    }

    public void setPassportCountry(final String passportCountry) {
        this.passportCountry = passportCountry;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(final String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public LocalDate getPassportExpiration() {
        return passportExpiration;
    }

    public void setPassportExpiration(final LocalDate passportExpiration) {
        this.passportExpiration = passportExpiration;
    }

    public String getPassportPlaceOfIssue() {
        return passportPlaceOfIssue;
    }

    public void setPassportPlaceOfIssue(final String passportPlaceOfIssue) {
        this.passportPlaceOfIssue = passportPlaceOfIssue;
    }

    public Optional<ExtendedProfileData> getExtendedProfileData() {
        return Optional.ofNullable(extendedProfileData);
    }

    public void setExtendedProfileData(final ExtendedProfileData extendedProfileData) {
        this.extendedProfileData = extendedProfileData;
    }

    public String getTattooNumber() {
        return tattooNumber;
    }

    public void setTattooNumber(final String tattooNumber) {
        this.tattooNumber = tattooNumber;
    }
}
