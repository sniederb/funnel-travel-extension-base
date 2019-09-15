/*
 * Created on 14.09.2019
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;

public class CustomField implements Serializable {

    private static final long serialVersionUID = 1L;
    private CustomFieldDataType datatype;
    private CustomFieldDirective editDirective;
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
