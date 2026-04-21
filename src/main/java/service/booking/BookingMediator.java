package service.booking;

/**
 * Mediator Pattern — Mediator interface for the booking workflow.
 *
 * Defines a single notification point so that booking participants
 * (colleagues) communicate through the mediator instead of directly.
 */
public interface BookingMediator {

    /**
     * Called by a colleague to notify the mediator of an event.
     *
     * @param sender the colleague that triggered the event
     * @param event  the event name (e.g. "BOOKING_REQUESTED", "BOOKING_CANCELLED")
     */
    void notify(BookingColleague sender, String event);
}
