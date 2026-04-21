package service.event;

/**
 * Observer Pattern — Observer interface (EventListener).
 *
 * Any component that wants to react to booking events
 * implements this interface and registers with the EventManager.
 */
public interface EventListener {

    /**
     * Called by the EventManager when an event is published.
     *
     * @param eventType the type of event (e.g. "BOOKING_CONFIRMED", "BOOKING_CANCELLED")
     * @param data      contextual data about the event
     */
    void update(String eventType, String data);
}
