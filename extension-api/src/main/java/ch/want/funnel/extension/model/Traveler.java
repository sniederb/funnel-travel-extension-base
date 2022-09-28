package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import ch.want.funnel.extension.model.travelerprofile.ExtendedProfileData;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "uuid")
public class Traveler implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private UUID userloginUuid;
    private UUID organizationUuid;
    private String tattooNumber;
    private String salutation;
    private TravelerAgeGroup ageGroup;
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

    public String getSalutation() {
        return salutation;
    }

    /**
     * Beware that if salutation is left empty, funnel.travel will attempt to determine MR/MRS
     * based on the {@link #getFirstname()}
     *
     * @param salutation
     */
    public void setSalutation(final String salutation) {
        this.salutation = salutation;
    }

    public TravelerAgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(final TravelerAgeGroup ageGroup) {
        this.ageGroup = ageGroup;
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
        this.sourceDisplayId = emptyToNull(sourceDisplayId);
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(final LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * Get the ISO-3166-1 alpha-2 country code
     */
    public String getPassportCountry() {
        return passportCountry;
    }

    /**
     * Set the ISO-3166-1 alpha-2 country code. This method throws an {@link IllegalArgumentException}
     * if {@code passportCountry} is not-null and length is not equal 2.
     */
    public void setPassportCountry(final String passportCountry) {
        final String strippedPassportCountry = emptyToNull(passportCountry);
        if ((strippedPassportCountry != null) && (strippedPassportCountry.length() != 2)) {
            throw new IllegalArgumentException("This is not an ISO-3166-1 alpha-2 country code: " + passportCountry);
        }
        this.passportCountry = strippedPassportCountry;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(final String passportNumber) {
        this.passportNumber = emptyToNull(passportNumber);
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
        this.passportPlaceOfIssue = emptyToNull(passportPlaceOfIssue);
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
        this.tattooNumber = emptyToNull(tattooNumber);
    }

    private static String emptyToNull(final String s) {
        if ((s == null) || (s.trim().length() == 0)) {
            return null;
        }
        return s;
    }
}
