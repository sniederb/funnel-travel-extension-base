package ch.want.funnel.extension;

import ch.want.funnel.extension.model.PriceItem;
import ch.want.funnel.extension.model.TransportDocument;

/**
 * Class for strings used a across Funnel extensions as additional descriptions for PriceItem and TransportDocument among others.
 *
 * Unless specified otherwise, they can be used as needed irrespective of the object which they are describing.
 */
public class Dictionary {

    private Dictionary() {
    }

    /**
     * Use for PriceItem of type SUPPLIER to indicate a commission.
     *
     * <dt>Commission</dt>
     * <dd>Commission granted by the tour operator to the agency. In an agency-collection scenario, this effectively reduces the total
     * purchase price. In a direct-collection scenario, this amount must be passed on to the agency. <strong>This method returns false for supplier fees
     * (positive purchase amount)</strong>.</dd>
     */
    public static final String COMMISSION = "Commission";
    /**
     * Used as a prefix to determine if the PriceItem is a discount.
     *
     * <dt>Discount</dt>
     * <dd>A retail price reduction. Typically, a discount influences the revenue/commission calculation.</dd>
     */
    public static final String DISCOUNT = "Discount";
    public static final String FARE = "Fare";
    public static final String MARKUP = "Markup";
    /**
     * 'Off ticket' fees (OB/OC/OD fees). {@link PriceItem}s of this type should be attached to a {@link TransportDocument}, which itself has a
     * {@link TransportDocument#getTraveler()} set
     */
    public static final String OFF_TICKET_FEE = "Off-ticket fee";
    /**
     * Used as a prefix to determine if the PriceItem is a promotion.
     *
     * <dt>Promotion</dt>
     * <dd>A retail price reduction which takes the form of a pseudo-payment. Using a gift coupon would be similar to a promotion code.
     * Typically, a promo code does not influence the revenue/commission calculation.</dd>
     */
    public static final String PROMOTION = "Promotion";
    /**
     * Added to a TravelService if it is a refund.
     */
    public static final String REFUND = "Refund";
    /**
     * Added TransportDocument that specifically detail seating arrangements.
     */
    public static final String SEAT_ASSIGNMENT = "Seat assignment";
    /**
     * Used to determine if the PriceItem is a service charge.
     *
     * <dt>Service charge</dt>
     * <dd>A fee added by the agency. This amount must not affect revenue/commission calculations</dd>
     */
    public static final String SERVICE_CHARGE = "Service charge";
    public static final String TAX = "Tax";
    public static final String TOTAL = "Total";
}
