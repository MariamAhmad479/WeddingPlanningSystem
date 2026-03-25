package service.vendor;

public class PrintedAlbumDecorator extends VendorServiceDecorator {
    public PrintedAlbumDecorator(VendorService decoratedService) {
        super(decoratedService);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Premium Printed Album";
    }

    @Override
    public double getCost() {
        return super.getCost() + 250.00;
    }
}
