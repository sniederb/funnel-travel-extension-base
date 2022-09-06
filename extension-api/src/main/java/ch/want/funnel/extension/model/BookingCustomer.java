package ch.want.funnel.extension.model;

import java.io.Serializable;
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
    private String customerNumber;

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

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(final String customerNumber) {
        this.customerNumber = customerNumber;
    }
}
