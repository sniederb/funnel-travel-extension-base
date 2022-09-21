package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.util.UUID;

public class BookingCustomerNumber implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private String system;
    private String customerNumber;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(final String system) {
        this.system = system;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(final String customerNumber) {
        this.customerNumber = customerNumber;
    }
}
