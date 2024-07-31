package ch.want.funnel.extension.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class CustomFieldValue implements Serializable {

    private static final long serialVersionUID = 1L;
    private String internalvalue;
    @JsonManagedReference("customfield")
    private CustomField customField;

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
