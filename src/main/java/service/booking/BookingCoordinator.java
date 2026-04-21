package service.booking;

/**
 * Mediator Pattern — Concrete Mediator for the booking workflow.
 *
 * Coordinates interactions between AvailabilityComponent,
 * PaymentComponent, and NotificationComponent so that none of
 * them reference each other directly.
 */
public class BookingCoordinator implements BookingMediator {

    private final AvailabilityComponent availability;
    private final PaymentComponent payment;
    private final NotificationComponent notification;

    // Context for the current booking
    private String brideId;
    private String vendorId;
    private String date;
    private double depositAmount;

    public BookingCoordinator(AvailabilityComponent availability,
                              PaymentComponent payment,
                              NotificationComponent notification) {
        this.availability = availability;
        this.payment = payment;
        this.notification = notification;

        // Wire colleagues to this mediator
        this.availability.setMediator(this);
        this.payment.setMediator(this);
        this.notification.setMediator(this);
    }

    public void setBookingContext(String brideId, String vendorId, String date, double depositAmount) {
        this.brideId = brideId;
        this.vendorId = vendorId;
        this.date = date;
        this.depositAmount = depositAmount;
    }

    @Override
    public void notify(BookingColleague sender, String event) {
        switch (event) {
            case "BOOKING_REQUESTED":
                System.out.println("\n[Mediator] Coordinating BOOKING flow...");
                availability.reserveSlot(vendorId, date);
                payment.chargeDeposit(depositAmount);
                notification.sendConfirmation(brideId, vendorId);
                System.out.println("[Mediator] Booking flow complete.\n");
                break;

            case "BOOKING_CANCELLED":
                System.out.println("\n[Mediator] Coordinating CANCELLATION flow...");
                availability.releaseSlot(vendorId, date);
                payment.refundDeposit(depositAmount);
                notification.sendCancellationNotice(brideId, vendorId);
                System.out.println("[Mediator] Cancellation flow complete.\n");
                break;

            default:
                System.out.println("[Mediator] Unknown event: " + event);
        }
    }
}
