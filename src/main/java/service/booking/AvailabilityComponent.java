package service.booking;

/**
 * Mediator Pattern — Concrete Colleague: Availability component.
 *
 * Manages time-slot reservation and release for vendor bookings.
 */
public class AvailabilityComponent extends BookingColleague {

    public AvailabilityComponent(BookingMediator mediator) {
        super(mediator);
    }

    public void reserveSlot(String vendorId, String date) {
        System.out.println("[Availability] Slot reserved for vendor " + vendorId + " on " + date + " ✓");
    }

    public void releaseSlot(String vendorId, String date) {
        System.out.println("[Availability] Slot released for vendor " + vendorId + " on " + date + " ✓");
    }

    /** Bride initiates a booking through the mediator. */
    public void requestBooking(String vendorId, String date) {
        System.out.println("[Availability] Booking requested for vendor " + vendorId + " on " + date);
        mediator.notify(this, "BOOKING_REQUESTED");
    }

    /** Bride cancels a booking through the mediator. */
    public void requestCancellation(String vendorId, String date) {
        System.out.println("[Availability] Cancellation requested for vendor " + vendorId + " on " + date);
        mediator.notify(this, "BOOKING_CANCELLED");
    }
}
