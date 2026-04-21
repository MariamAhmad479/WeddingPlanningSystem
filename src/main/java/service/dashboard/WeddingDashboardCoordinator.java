package service.dashboard;

/**
 * Mediator Pattern — Concrete Mediator for the wedding dashboard.
 *
 * Coordinates interactions between GuestWidget, ChecklistWidget,
 * and BudgetWidget so that none of them reference each other directly.
 */
public class WeddingDashboardCoordinator implements DashboardMediator {

    private GuestWidget guestWidget;
    private ChecklistWidget checklistWidget;
    private BudgetWidget budgetWidget;

    public WeddingDashboardCoordinator() {
    }

    public void setGuestWidget(GuestWidget widget) {
        this.guestWidget = widget;
    }

    public void setChecklistWidget(ChecklistWidget widget) {
        this.checklistWidget = widget;
    }

    public void setBudgetWidget(BudgetWidget widget) {
        this.budgetWidget = widget;
    }

    @Override
    public void notify(DashboardWidget widget, String event) {
        switch (event) {
            case "GUEST_ADDED":
            case "GUEST_REMOVED":
                System.out.println("[DashboardMediator] Guest list changed → updating budget estimate...");
                budgetWidget.updateEstimate(guestWidget.getGuestCount());
                budgetWidget.refresh();
                break;

            case "CHECKLIST_UPDATED":
                System.out.println("[DashboardMediator] Checklist updated → refreshing overall progress...");
                checklistWidget.refresh();
                break;

            default:
                System.out.println("[DashboardMediator] Unknown event: " + event);
        }
    }
}
