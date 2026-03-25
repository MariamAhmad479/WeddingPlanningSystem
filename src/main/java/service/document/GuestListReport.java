package service.document;

public class GuestListReport extends Document {
    private int totalGuests;
    private int attendingGuests;

    public GuestListReport(DocumentRenderer renderer, int totalGuests, int attendingGuests) {
        super(renderer);
        this.totalGuests = totalGuests;
        this.attendingGuests = attendingGuests;
    }

    @Override
    public void exportDocument() {
        System.out.println("Exporting Guest List Report...");
        System.out.println(renderer.renderHeader("Wedding Guest List Summary"));
        System.out.println(renderer.renderContent(attendingGuests + " out of " + totalGuests + " guests are attending."));
        System.out.println(renderer.renderFooter());
    }
}
