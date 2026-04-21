package service.report;

/**
 * Concrete Template Method — Budget Summary Report.
 *
 * Overrides each hook to produce a gehaz/wedding budget breakdown
 * showing total budget, amount spent, and remaining balance.
 */
public class BudgetReportGenerator extends ReportGenerator {

    private final double totalBudget;
    private final double totalSpent;

    private double remaining;
    private int spentPercentage;

    public BudgetReportGenerator(double totalBudget, double totalSpent) {
        this.totalBudget = totalBudget;
        this.totalSpent = totalSpent;
    }

    @Override
    protected void gatherData() {
        // Compute derived values from the raw data
        this.remaining = totalBudget - totalSpent;
        if (remaining < 0) remaining = 0;
        this.spentPercentage = totalBudget > 0
                ? (int) ((totalSpent / totalBudget) * 100)
                : 0;
        System.out.println("[BudgetReport] Data gathered — computing budget breakdown...");
    }

    @Override
    protected String buildHeader() {
        return "======================================\n"
             + "       WEDDING BUDGET REPORT          \n"
             + "======================================";
    }

    @Override
    protected String buildBody() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("  Total Budget   : EGP %,.2f%n", totalBudget));
        sb.append(String.format("  Amount Spent   : EGP %,.2f%n", totalSpent));
        sb.append(String.format("  Remaining      : EGP %,.2f%n", remaining));
        sb.append(String.format("  Spent %%        : %d%%%n", spentPercentage));

        // Simple progress bar
        int bars = spentPercentage / 5;
        sb.append("  Progress       : [");
        for (int i = 0; i < 20; i++) {
            sb.append(i < bars ? "#" : "-");;
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    protected String buildFooter() {
        if (remaining <= 0) {
            return "⚠  WARNING: Budget fully spent!";
        }
        return "✓  Budget is on track.";
    }
}
