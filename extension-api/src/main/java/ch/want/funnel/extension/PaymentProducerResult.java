package ch.want.funnel.extension;

import ch.want.funnel.extension.model.Booking;
import ch.want.funnel.extension.model.BookingPayment;
import ch.want.funnel.extension.model.Trip;

public class PaymentProducerResult {

    private String midofficeReferenceNumber;
    private String bookingReferenceNumber;
    private Boolean depositPayment;
    private Boolean finalPayment;
    private BookingPayment payment;

    /**
     * If set, funnel.travel core will match {@link #payment} based on the {@link Trip#getAccountIdentifier()}.
     *
     * @return
     */
    public String getMidofficeReferenceNumber() {
        return midofficeReferenceNumber;
    }

    public void setMidofficeReferenceNumber(final String midofficeReferenceNumber) {
        this.midofficeReferenceNumber = midofficeReferenceNumber;
    }

    /**
     * If set, funnel.travel will match the {@link #payment} based on {@link Booking#getReferenceNumber()}.
     *
     * @return
     */
    public String getBookingReferenceNumber() {
        return bookingReferenceNumber;
    }

    public void setBookingReferenceNumber(final String bookingReferenceNumber) {
        this.bookingReferenceNumber = bookingReferenceNumber;
    }

    /**
     * If deposit/final payment nature is known, this method returns true or false resp. Otherwise this method returns null.
     *
     * @return
     */
    public Boolean getDepositPayment() {
        return depositPayment;
    }

    public void setDepositPayment(final Boolean depositPayment) {
        this.depositPayment = depositPayment;
    }

    /**
     * If deposit/final payment nature is known, this method returns true or false resp. Otherwise this method returns null.
     *
     * @return
     */
    public Boolean getFinalPayment() {
        return finalPayment;
    }

    public void setFinalPayment(final Boolean finalPayment) {
        this.finalPayment = finalPayment;
    }

    public BookingPayment getPayment() {
        return payment;
    }

    public void setPayment(final BookingPayment payment) {
        this.payment = payment;
    }
}
