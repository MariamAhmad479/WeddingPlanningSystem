package service.report;

/**
 * Template Method Pattern — Abstract class defining the skeleton
 * for generating any wedding-related report.
 *
 * The template method generateReport() is final, enforcing the
 * step order. Subclasses override the hooks to fill in content.
 */
public abstract class ReportGenerator {

    /**
     * Template method — defines the invariant report generation algorithm.
     * Subclasses cannot override this.
     */
    public final String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append(buildHeader());
        report.append("\n");
        gatherData();
        report.append(buildBody());
        report.append("\n");
        report.append(buildFooter());
        System.out.println(report.toString());
        return report.toString();
    }

    /** Step 1: Gather/load the data needed for the report. */
    protected abstract void gatherData();

    /** Step 2: Build the report header (title, date, etc.). */
    protected abstract String buildHeader();

    /** Step 3: Build the main body of the report. */
    protected abstract String buildBody();

    /** Step 4: Build the footer (totals, signatures, etc.). */
    protected abstract String buildFooter();
}
