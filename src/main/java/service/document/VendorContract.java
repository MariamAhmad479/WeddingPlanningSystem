package service.document;

public class VendorContract extends Document {
    private String vendorName;
    private String serviceDetails;

    public VendorContract(DocumentRenderer renderer, String vendorName, String serviceDetails) {
        super(renderer);
        this.vendorName = vendorName;
        this.serviceDetails = serviceDetails;
    }

    @Override
    public void exportDocument() {
        System.out.println("Exporting Vendor Contract...");
        System.out.println(renderer.renderHeader("Contract for " + vendorName));
        System.out.println(renderer.renderContent("Services Provided: " + serviceDetails));
        System.out.println(renderer.renderFooter());
    }
}
