/*
 * Created on 24 Nov 2017
 */
package ch.want.funnel.extension.model;

public enum CustomFieldDirective {
    /**
     * The field is visible and editable. A journey can be saved without providing a value
     */
    OPTIONAL,
    /**
     * The field is visible and editable. A journey can not be saved without providing a value
     */
    MANDATORY,
    /**
     * The field is not visible
     */
    HIDDEN,
    /**
     * The field is visible, but cannot be edited
     */
    READONLY
}