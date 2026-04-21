package service.dashboard;

/**
 * Mediator Pattern — Concrete Colleague: Budget status widget.
 */
public class BudgetWidget extends DashboardWidget {

    private double totalBudget;
    private double spent;

    public BudgetWidget(DashboardMediator mediator, double totalBudget) {
        super(mediator);
        this.totalBudget = totalBudget;
        this.spent = 0;
    }

    /** Recalculate estimated cost per guest when guest count changes. */
    public void updateEstimate(int guestCount) {
        double costPerGuest = 500.0; // average EGP per guest
        this.spent = guestCount * costPerGuest;
        System.out.println("[BudgetWidget] Estimated cost updated: EGP "
                + spent + " for " + guestCount + " guests");
    }

    @Override
    public void refresh() {
        double remaining = totalBudget - spent;
        System.out.println("[BudgetWidget] Refreshed — Budget: EGP " + totalBudget
                + " | Spent: EGP " + spent + " | Remaining: EGP " + remaining);
    }
}
