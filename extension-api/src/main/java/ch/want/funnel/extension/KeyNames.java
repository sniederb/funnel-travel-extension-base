package ch.want.funnel.extension;

/**
 * This class defines constants used to add extension data which eventually will be displayed in funnel.travel.
 * For this the extension must create an array with key {@link #ARRAYNODE_FOR_UI_ELEMENTS}, then add objects
 * with the provided {@code UI_ELEMENT_xxx} keys.
 */
public final class KeyNames {

    public static final String ARRAYNODE_FOR_UI_ELEMENTS = "ui";
    public static final String UI_ELEMENT_CONTEXTTYPE = "contextType";
    public static final String UI_ELEMENT_CONTEXTUUID = "contextUuid";
    /**
     * funnel.travel will try to translate the value for this key using its internal messages. Most likely,
     * this property should remain empty, and the entire message string should be saved in {@link #UI_ELEMENT_VALUE}
     */
    public static final String UI_ELEMENT_LABELKEY = "labelKey";
    public static final String UI_ELEMENT_VALUETYPE = "valueType";
    public static final String UI_ELEMENT_VALUE = "value";

    private KeyNames() {
    }

    public enum ContextType {
        BOOKING, SERVICE, LEG, SEGMENT, TRAVELER
    }
}
