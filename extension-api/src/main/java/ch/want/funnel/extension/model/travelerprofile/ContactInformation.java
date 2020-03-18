package ch.want.funnel.extension.model.travelerprofile;

public class ContactInformation {

    private String refNumber;
    private String fullname;
    private String street;
    private String street2;
    private String zip;
    private String place;
    private String district;
    private String country;

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(final String refNumber) {
        this.refNumber = refNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(final String fullname) {
        this.fullname = fullname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(final String street2) {
        this.street2 = street2;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(final String place) {
        this.place = place;
    }

    /**
     * Area information additional to ZIP and place. For the USA, this is the state.
     * For the UK this will hold the post town if unequal to the dependent locality.
     */
    public String getDistrict() {
        return district;
    }

    public void setDistrict(final String district) {
        this.district = district;
    }

    /**
     * Country is an ISO 3166-1 alpha-2 code
     */
    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }
}
