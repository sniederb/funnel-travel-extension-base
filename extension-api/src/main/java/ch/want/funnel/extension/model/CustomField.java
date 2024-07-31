/*
 * Created on 14.09.2019
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "uuid")
public class CustomField implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    @JsonBackReference("customfield")
    private CustomFieldValue customFieldValuesHolder;
    private CustomFieldDataType datatype;
    private CustomFieldDirective editDirective;
    private String name;
    @JsonManagedReference("customfield-extensionnames")
    private List<CustomfieldExtensionName> extensionFieldNames = new ArrayList<>();

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public CustomFieldDataType getDatatype() {
        return datatype;
    }

    public void setDatatype(final CustomFieldDataType datatype) {
        this.datatype = datatype;
    }

    public CustomFieldDirective getEditDirective() {
        return editDirective;
    }

    public void setEditDirective(final CustomFieldDirective editDirective) {
        this.editDirective = editDirective;
    }

    /**
     * Get the field name. When writing a custom field, extensions can set their own extension-specific
     * name, funnel.travel will match that against the mapping. When reading a custom field, extensions
     * will want to check {@link #getExtensionFieldNames()} and filter on their own
     * {@link CustomfieldExtensionName#getExtensionClassName()}.
     */
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<CustomfieldExtensionName> getExtensionFieldNames() {
        return extensionFieldNames;
    }

    public void setExtensionFieldNames(final List<CustomfieldExtensionName> extensionFieldNames) {
        this.extensionFieldNames = extensionFieldNames;
    }

    @Deprecated
    public CustomFieldValue getCustomFieldValuesHolder() {
        return customFieldValuesHolder;
    }

    public void setCustomFieldValuesHolder(final CustomFieldValue customFieldValuesHolder) {
        this.customFieldValuesHolder = customFieldValuesHolder;
    }
}
