package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This instance will only be present if the providing extension supplies customer data. Note that this
 * is <strong>not a passenger</strong>, but rather the invoice receiver.
 *
 * @see Traveler
 */
public class BookingCustomer implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String street;
    private String addressLine2;
    private String city;
    private String zip;
    private String countryCode;
    private TravelerAgeGroup ageGroup;
    private String salutation;
    private String language;
    private transient List<BookingCustomerNumber> customerNumbers = new ArrayList<>();

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public List<BookingCustomerNumber> getCustomerNumbers() {
        return customerNumbers;
    }

    public void setCustomerNumbers(final List<BookingCustomerNumber> customerNumbers) {
        this.customerNumbers = customerNumbers;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(final String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    public TravelerAgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(final TravelerAgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(final String salutation) {
        this.salutation = salutation;
    }
}
