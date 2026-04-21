package service.dashboard;

/**
 * Mediator Pattern — Concrete Colleague: Checklist progress widget.
 */
public class ChecklistWidget extends DashboardWidget {

    private int totalTasks;
    private int completedTasks;

    public ChecklistWidget(DashboardMediator mediator) {
        super(mediator);
        this.totalTasks = 10; // default wedding tasks
        this.completedTasks = 0;
    }

    public int getProgressPercentage() {
        return totalTasks > 0 ? (completedTasks * 100) / totalTasks : 0;
    }

    /** Mark a checklist task as done — notify the mediator. */
    public void completeTask(String taskName) {
        completedTasks++;
        System.out.println("[ChecklistWidget] Task completed: " + taskName
                + " | Progress: " + getProgressPercentage() + "%");
        mediator.notify(this, "CHECKLIST_UPDATED");
    }

    @Override
    public void refresh() {
        System.out.println("[ChecklistWidget] Refreshed — progress: "
                + getProgressPercentage() + "% (" + completedTasks + "/" + totalTasks + ")");
    }
}
