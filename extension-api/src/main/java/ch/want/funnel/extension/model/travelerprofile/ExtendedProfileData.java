package ch.want.funnel.extension.model.travelerprofile;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ExtendedProfileData implements Serializable {

    private static final long serialVersionUID = 1L;
    private String idInProfileTool;
    private String title;
    private String firstname;
    private String middlename;
    private String surname;
    private String surname2;
    private String mainEmailAddress;
    private final HashSet<String> emailAddresses = new HashSet<>();
    private LocalDate birthdate;
    private Gender gender;
    private String phoneMobile;
    private String phonePrivate;
    private String phoneBusiness;
    private String nationality;
    private Locale language;
    private ContactInformation companyContact;
    // using concrete collection classes here to ensure serialization
    private final HashSet<OfficialDocument> officialDocuments = new HashSet<>();
    private OfficialDocument mainPassport;
    private final HashSet<Membership> memberships = new HashSet<>();
    private final HashMap<String, String> additionalData = new HashMap<>();

    public String getIdInProfileTool() {
        return idInProfileTool;
    }

    public void setIdInProfileTool(final String idInProfileTool) {
        this.idInProfileTool = idInProfileTool;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(final String middlename) {
        this.middlename = middlename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    /**
     * Some countries like Spain use two surnames. From Wikipedia:
     *
     * <em>Historically, the first surname was the father's first surname, and the second the mother's first surname</em>
     *
     * This field holds the second surname, if any.
     */
    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(final String surname2) {
        this.surname2 = surname2;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(final LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public String getPhoneMobile() {
        return phoneMobile;
    }

    public void setPhoneMobile(final String phoneMobile) {
        this.phoneMobile = phoneMobile;
    }

    public String getPhonePrivate() {
        return phonePrivate;
    }

    public void setPhonePrivate(final String phonePrivate) {
        this.phonePrivate = phonePrivate;
    }

    public String getPhoneBusiness() {
        return phoneBusiness;
    }

    public void setPhoneBusiness(final String phoneBusiness) {
        this.phoneBusiness = phoneBusiness;
    }

    public String getMainEmailAddress() {
        return mainEmailAddress;
    }

    public void setMainEmailAddress(final String mainEmailAddress) {
        if (mainEmailAddress != null) {
            this.mainEmailAddress = mainEmailAddress;
            this.emailAddresses.add(mainEmailAddress);
        }
    }

    public Set<String> getEmailAddresses() {
        return emailAddresses;
    }

    /**
     * Nationality is an ISO 3166-1 alpha-2 code
     */
    public String getNationality() {
        return nationality;
    }

    public void setNationality(final String nationality) {
        this.nationality = nationality;
    }

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(final Locale language) {
        this.language = language;
    }

    public Optional<OfficialDocument> getMainPassport() {
        return Optional.ofNullable(mainPassport);
    }

    public void setMainPassport(final OfficialDocument mainPassport) {
        if (mainPassport != null) {
            this.mainPassport = mainPassport;
            this.officialDocuments.add(mainPassport);
        }
    }

    public Set<OfficialDocument> getOfficialDocuments() {
        return officialDocuments;
    }

    public Set<Membership> getMemberships() {
        return memberships;
    }

    public Map<String, String> getAdditionalData() {
        return additionalData;
    }

    public Optional<ContactInformation> getCompanyContact() {
        return Optional.ofNullable(companyContact);
    }

    public void setCompanyContact(final ContactInformation companyContact) {
        this.companyContact = companyContact;
    }
}
