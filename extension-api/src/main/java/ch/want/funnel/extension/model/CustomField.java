/*
 * Created on 14.09.2019
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomField implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private CustomFieldDataType datatype;
    private CustomFieldDirective editDirective;
    private String name;
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
     * Get the funnel.travel name. Most likely, extensions will want to access {@link #getExtensionFieldNames()}
     * and filter on their own {@link CustomfieldExtensionName#getExtensionClassName()}.
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
}
