package ch.want.funnel.extension.model;

import java.io.Serializable;

/**
 * Extension-specific name for a {@link CustomField}.
 */
public class CustomfieldExtensionName implements Serializable {

    private static final long serialVersionUID = 1L;
    private CustomField customField;
    private String extensionClassName;
    private String fieldName;

    public CustomField getCustomField() {
        return customField;
    }

    public void setCustomField(final CustomField customField) {
        this.customField = customField;
    }

    public String getExtensionClassName() {
        return extensionClassName;
    }

    public void setExtensionClassName(final String extensionClassName) {
        this.extensionClassName = extensionClassName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(final String fieldName) {
        this.fieldName = fieldName;
    }
}
