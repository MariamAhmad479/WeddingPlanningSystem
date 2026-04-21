package service.dashboard;

/**
 * Mediator Pattern — Mediator interface for the wedding dashboard.
 *
 * Dashboard widgets communicate through this mediator instead of
 * referencing each other directly.
 */
public interface DashboardMediator {

    /**
     * Called by a widget to notify the mediator of a change.
     *
     * @param widget the widget that triggered the event
     * @param event  the event name (e.g. "GUEST_ADDED", "CHECKLIST_UPDATED")
     */
    void notify(DashboardWidget widget, String event);
}
