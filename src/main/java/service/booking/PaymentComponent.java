package service.booking;

/**
 * Mediator Pattern — Concrete Colleague: Payment component.
 *
 * Handles deposit charges and refunds during the booking workflow.
 */
public class PaymentComponent extends BookingColleague {

    public PaymentComponent(BookingMediator mediator) {
        super(mediator);
    }

    public void chargeDeposit(double amount) {
        System.out.println("[Payment] Deposit of EGP " + amount + " charged ✓");
    }

    public void refundDeposit(double amount) {
        System.out.println("[Payment] Deposit of EGP " + amount + " refunded ✓");
    }
}
