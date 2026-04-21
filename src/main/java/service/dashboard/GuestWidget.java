package service.dashboard;

/**
 * Mediator Pattern — Concrete Colleague: Guest count widget.
 */
public class GuestWidget extends DashboardWidget {

    private int guestCount;

    public GuestWidget(DashboardMediator mediator) {
        super(mediator);
        this.guestCount = 0;
    }

    public int getGuestCount() {
        return guestCount;
    }

    /** Bride adds a guest — notify the mediator so other widgets can react. */
    public void addGuest(String guestName) {
        guestCount++;
        System.out.println("[GuestWidget] Guest added: " + guestName + " | Total: " + guestCount);
        mediator.notify(this, "GUEST_ADDED");
    }

    /** Bride removes a guest. */
    public void removeGuest(String guestName) {
        if (guestCount > 0) guestCount--;
        System.out.println("[GuestWidget] Guest removed: " + guestName + " | Total: " + guestCount);
        mediator.notify(this, "GUEST_REMOVED");
    }

    @Override
    public void refresh() {
        System.out.println("[GuestWidget] Refreshed — current guest count: " + guestCount);
    }
}
