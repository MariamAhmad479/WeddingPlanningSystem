package service.event;

/**
 * Observer Pattern — Concrete Observer: syncs external calendar on booking events.
 */
public class CalendarEventListener implements EventListener {

    @Override
    public void update(String eventType, String data) {
        switch (eventType) {
            case "BOOKING_CONFIRMED":
                System.out.println("  📅 [CalendarListener] Adding appointment to calendar: " + data);
                break;
            case "BOOKING_CANCELLED":
                System.out.println("  📅 [CalendarListener] Removing appointment from calendar: " + data);
                break;
            default:
                System.out.println("  📅 [CalendarListener] Event received: " + eventType + " — " + data);
        }
    }
}
