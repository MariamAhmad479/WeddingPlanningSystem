package service.dashboard;

/**
 * Mediator Pattern — Abstract colleague for dashboard widgets.
 *
 * Each widget holds a mediator reference and communicates
 * state changes through it.
 */
public abstract class DashboardWidget {

    protected DashboardMediator mediator;

    public DashboardWidget(DashboardMediator mediator) {
        this.mediator = mediator;
    }

    public void setMediator(DashboardMediator mediator) {
        this.mediator = mediator;
    }

    /** Refresh the widget's display. */
    public abstract void refresh();
}
