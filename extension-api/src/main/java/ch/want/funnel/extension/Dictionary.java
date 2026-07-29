package ch.want.funnel.extension;

import ch.want.funnel.extension.model.Booking;
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
     * Typical {@link PriceItem#getDescription()} for a price item describing a fee charged by the agency because of additional
     * administrative work. The most common case would be a re-booking fee. Price items should have type
     * {@link PriceItemType#AUX_PER_BOOKING}.
     *
     * <strong>Note:</strong> be careful not to use {@link PriceItemType#SURCHARGE_PER_BOOKING} for such a fee. Many consumers see
     * surcharges as a "markup" and process that amount separately.
     */
    public static final String PROCESSING_FEES = "Processing fees";
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
    /**
     * Prefix used to define a {@link BookingPayment#getDescription()}, see {@link #getImportedPaymentDescription(Class, String)}.
     */
    public static final String PAYMENT_IMPORT_DESCRPTIONPREFIX = "Import";

    /**
     * @param originatingExtensionClass
     *            The extension class which is providing the payment into funnel.travel
     * @param bookingReference
     *            Typically either {@link Booking#getReferenceNumber()} or {@link Booking#getMidofficeReferenceNumber()}
     * @return
     */
    public static String getImportedPaymentDescription(final Class<?> originatingExtensionClass, final String bookingReference) {
        return PAYMENT_IMPORT_DESCRPTIONPREFIX + "/" + originatingExtensionClass.getSimpleName().replace("Extension", "") + "/" + bookingReference;
    }
}
