package service.event;

/**
 * Observer Pattern — Concrete Observer: sends notifications on booking events.
 */
public class NotificationEventListener implements EventListener {

    @Override
    public void update(String eventType, String data) {
        switch (eventType) {
            case "BOOKING_CONFIRMED":
                System.out.println("  📧 [NotificationListener] Sending confirmation email for: " + data);
                break;
            case "BOOKING_CANCELLED":
                System.out.println("  📧 [NotificationListener] Sending cancellation notice for: " + data);
                break;
            default:
                System.out.println("  📧 [NotificationListener] Event received: " + eventType + " — " + data);
        }
    }
}
