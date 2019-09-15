/*
 * Created on 9 Mar 2018
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.util.UUID;

public class CustomFieldValue implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID tripUuid;
    private UUID fieldtypeUuid;
    private String internalvalue;
    private CustomField customField;

    public UUID getTripUuid() {
        return tripUuid;
    }

    public void setTripUuid(final UUID tripUuid) {
        this.tripUuid = tripUuid;
    }

    public UUID getFieldtypeUuid() {
        return fieldtypeUuid;
    }

    public void setFieldtypeUuid(final UUID fieldtypeUuid) {
        this.fieldtypeUuid = fieldtypeUuid;
    }

    public String getInternalvalue() {
        return internalvalue;
    }

    public void setInternalvalue(final String internalvalue) {
        this.internalvalue = internalvalue;
    }

    public CustomField getCustomField() {
        return customField;
    }

    public void setCustomField(final CustomField customField) {
        this.customField = customField;
    }
}
