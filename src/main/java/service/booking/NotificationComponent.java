package service.booking;

/**
 * Mediator Pattern — Concrete Colleague: Notification component.
 *
 * Sends confirmation and cancellation notices to bride/vendor.
 */
public class NotificationComponent extends BookingColleague {

    public NotificationComponent(BookingMediator mediator) {
        super(mediator);
    }

    public void sendConfirmation(String brideId, String vendorId) {
        System.out.println("[Notification] Booking confirmation sent to bride " + brideId
                + " and vendor " + vendorId + " ✓");
    }

    public void sendCancellationNotice(String brideId, String vendorId) {
        System.out.println("[Notification] Cancellation notice sent to bride " + brideId
                + " and vendor " + vendorId + " ✓");
    }
}
