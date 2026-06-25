package ch.want.funnel.extension;

import ch.want.funnel.extension.model.PriceItem;
import ch.want.funnel.extension.model.TransportDocument;
import ch.want.funnel.extension.model.TravelService;

/**
 * Class for strings used a across Funnel extensions as additional descriptions for PriceItem and TransportDocument among others.
 *
 * Unless specified otherwise, they can be used as needed irrespective of the object which they are describing.
 */
public class Dictionary {

    private Dictionary() {
    }

    /**
     * Use for {@link PriceItem} of type SUPPLIER to indicate a commission.
     *
     * <dt>Commission</dt>
     * <dd>Commission granted by the tour operator to the agency. In an agency-collection scenario, this effectively reduces the total
     * purchase price. In a direct-collection scenario, this amount must be passed on to the agency.</dd>
     */
    public static final String COMMISSION = "Commission";
    /**
     * Special type of ticket.
     *
     * <dt>Conjunction</dt>
     * <dd>For conjunction tickets. Consumers should process these with price 0.00, or drop them entirely.</dd>
     */
    public static final String CONJUNCTION = "Conjunction";
    /**
     * Used as a prefix to determine if the {@link PriceItem} is for a booking with a discount.
     *
     * <dt>Discount</dt>
     * <dd>A retail price reduction. Typically, a discount influences the revenue/commission calculation.</dd>
     */
    public static final String DISCOUNT = "Discount";
    public static final String FARE = "Fare";
    public static final String MARKUP = "Markup";
    /**
     * 'Off ticket' fees (OB/OC/OD fees). {@link PriceItem}s of this type should be attached to a {@link TransportDocument}.
     */
    public static final String OFF_TICKET_FEE = "Off-ticket fee";
    /**
     * Used as a prefix to determine if the {@link PriceItem} is a promotion.
     *
     * <dt>Promotion</dt>
     * <dd>A retail price reduction which takes the form of a pseudo-payment. Using a gift coupon would be similar to a promotion code.
     * Typically, a promo code does not influence the revenue/commission calculation.</dd>
     */
    public static final String PROMOTION = "Promotion";
    /**
     * Usually added to a {@link TravelService} if it is a refund. Can also be found as a {@link PriceItem}, BookingPayment or BookingAction type.
     */
    public static final String REFUND = "Refund";
    /**
     * Special type of ticket.
     *
     * <dt>Revalidation</dt>
     * <dd>For revalidated tickets. Usually, funnel.travel will hold the new flight segments, but consumers might opt to process these with price 0.00</dd>
     */
    public static final String REVALIDATION = "Revalidation";
    /**
     * Added {@link TransportDocument} that specifically detail seating arrangements.
     */
    public static final String SEAT_ASSIGNMENT = "Seat assignment";
    /**
     * Used to determine if the {@link PriceItem} is a service charge.
     *
     * <dt>Service charge</dt>
     * <dd>A fee added by the agency. This amount must not affect revenue/commission calculations</dd>
     */
    public static final String SERVICE_CHARGE = "Service charge";
    public static final String TAX = "Tax";
    public static final String TOTAL = "Total";
    /**
     * Special type of ticket.
     *
     * <dt>Void</dt>
     * <dd>For voided tickets. Ticket amount should usually be 0.00, unless there's a handling markup.</dd>
     */
    public static final String VOID = "Void";
}
