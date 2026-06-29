package ch.want.funnel.extension;

import ch.want.funnel.extension.model.BookingPayment;
import ch.want.funnel.extension.model.PriceItem;
import ch.want.funnel.extension.model.PriceItemType;
import ch.want.funnel.extension.model.TransportDocument;
import ch.want.funnel.extension.model.TransportDocumentType;
import ch.want.funnel.extension.model.TravelService;

/**
 * Class for strings used a across Funnel extensions as additional descriptions for PriceItem and TransportDocument among others.
 *
 * Unless specified otherwise, they can be used as needed irrespective of the object which they are describing.
 */
public final class Dictionary {

    private Dictionary() {
    }

    /**
     * Use for {@link PriceItem} of type {@link PriceItemType#SUPPLIER_ONLY} to indicate a commission granted by the tour operator to the
     * agency. In an agency-collection scenario, this effectively reduces the total purchase price. In a direct-collection scenario, this
     * amount must be passed on to the agency.
     */
    public static final String COMMISSION = "Commission";
    /**
     * {@link TransportDocument#getDescription()} used to indicate a conjunction ticket. Consumers should process these with price 0.00, or
     * drop them entirely.
     */
    public static final String CONJUNCTION = "Conjunction";
    /**
     * Used as a prefix to mark a {@link PriceItem} as a discount. Typically, a discount influences the revenue/commission calculation.
     */
    public static final String DISCOUNT = "Discount";
    /**
     * Standard {@link PriceItem#getDescription()} for a price item of type {@link PriceItemType#FARE_PER_PAX}.
     */
    public static final String FARE = "Fare";
    /**
     * Typical {@link PriceItem#getDescription()} for a price item of type {@link PriceItemType#SURCHARGE_PER_PAX} (typically related to
     * flight tickets) or {@link PriceItemType#SURCHARGE_PER_BOOKING}.
     *
     * @See {@link #SERVICE_CHARGE}
     */
    public static final String MARKUP = "Markup";
    /**
     * 'Off ticket' fees (OB/OC/OD fees). {@link PriceItem}s of this type ({@link PriceItemType#OFF_TICKET_PER_PAX}) should be attached to a
     * {@link TransportDocument}.
     */
    public static final String OFF_TICKET_FEE = "Off-ticket fee";
    /**
     * Used as a prefix to determine if the {@link PriceItem} is a promotion, i.e. retail price reduction which takes the form of a
     * pseudo-payment. Using a gift coupon would be similar to a promotion code. Typically, a promo code does not influence the
     * revenue/commission calculation.
     */
    public static final String PROMOTION = "Promotion";
    /**
     * Usually added to a {@link TravelService} if it is a refund. Can also be found as a {@link PriceItem} or {@link BookingPayment}.
     */
    public static final String REFUND = "Refund";
    /**
     * Special type of ticket.
     *
     * <dt>Revalidation</dt>
     * <dd>For revalidated tickets. Usually, funnel.travel will hold the new flight segments, but consumers might opt to process these with
     * price 0.00</dd>
     */
    public static final String REVALIDATION = "Revalidation";
    /**
     * Used for {@link TransportDocumentType#EMD} reg. seating arrangements.
     */
    public static final String SEAT_ASSIGNMENT = "Seat assignment";
    /**
     * Typical {@link PriceItem#getDescription()} for a price item of type {@link PriceItemType#SURCHARGE_PER_PAX} (typically related to
     * flight tickets) or {@link PriceItemType#SURCHARGE_PER_BOOKING}.
     *
     * <strong>Note:</strong> if for some reason using {@link PriceItemType#AUX_PER_BOOKING}, a price item must use this description to be
     * identified as a service charge / surcharge.
     */
    public static final String SERVICE_CHARGE = "Service charge";
    /**
     * Standard {@link PriceItem#getDescription()} for a price item of type {@link PriceItemType#TAX_PER_PAX}.
     */
    public static final String TAX = "Tax";
    public static final String TOTAL = "Total";
    /**
     * {@link TransportDocument#getDescription()} used to indicate a voided ticket. Ticket amount should usually be 0.00, unless there's a
     * handling markup.
     */
    public static final String VOID = "Void";
}
