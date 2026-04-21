package service.event;

/**
 * Observer Pattern — Concrete Observer: auto-marks checklist task on booking confirmation.
 */
public class ChecklistEventListener implements EventListener {

    @Override
    public void update(String eventType, String data) {
        if ("BOOKING_CONFIRMED".equals(eventType)) {
            System.out.println("  ✅ [ChecklistListener] Auto-marking 'Book Vendor' task as done for: " + data);
        } else if ("BOOKING_CANCELLED".equals(eventType)) {
            System.out.println("  ↩️  [ChecklistListener] Reverting 'Book Vendor' task for: " + data);
        }
    }
}
