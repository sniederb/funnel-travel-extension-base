package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.util.UUID;

public class BookingCustomerNumber implements Serializable {

    /**
     * Corporate VAT number
     */
    public static final String SYSTEM_VAT_NUMBER = "VAT";
    /**
     * Social security number, SSID or similar
     */
    public static final String SYSTEM_SOCIAL = "Social";
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

    /**
     * Typically this will be an extension system (like "Midoco"), but can also be a general qualifier like {@link #SYSTEM_VAT_NUMBER}.
     *
     * @return
     */
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
