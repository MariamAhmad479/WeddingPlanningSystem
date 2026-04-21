package service.report;

/**
 * Concrete Template Method — Guest List Summary Report.
 *
 * Overrides each hook to produce a guest attendance breakdown
 * showing total invited, attending, pending, and declined counts.
 */
public class GuestListReportGenerator extends ReportGenerator {

    private final int totalGuests;
    private final int attending;
    private final int declined;

    private int pending;
    private int attendancePercentage;

    public GuestListReportGenerator(int totalGuests, int attending, int declined) {
        this.totalGuests = totalGuests;
        this.attending = attending;
        this.declined = declined;
    }

    @Override
    protected void gatherData() {
        this.pending = totalGuests - attending - declined;
        this.attendancePercentage = totalGuests > 0
                ? (int) ((attending * 100.0) / totalGuests)
                : 0;
        System.out.println("[GuestListReport] Data gathered — computing attendance summary...");
    }

    @Override
    protected String buildHeader() {
        return "======================================\n"
             + "     GUEST LIST SUMMARY REPORT        \n"
             + "======================================";
    }

    @Override
    protected String buildBody() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("  Total Invited  : %d%n", totalGuests));
        sb.append(String.format("  Attending      : %d%n", attending));
        sb.append(String.format("  Declined       : %d%n", declined));
        sb.append(String.format("  Pending RSVP   : %d%n", pending));
        sb.append(String.format("  Attendance %%   : %d%%", attendancePercentage));
        return sb.toString();
    }

    @Override
    protected String buildFooter() {
        if (pending > 0) {
            return "⏳  " + pending + " guest(s) have not yet responded.";
        }
        return "✓  All guests have responded.";
    }
}
