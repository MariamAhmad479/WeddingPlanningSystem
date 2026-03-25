package service.vendor;

public abstract class VendorServiceDecorator implements VendorService {
    protected VendorService decoratedService;

    public VendorServiceDecorator(VendorService decoratedService) {
        this.decoratedService = decoratedService;
    }

    @Override
    public String getDescription() {
        return decoratedService.getDescription();
    }

    @Override
    public double getCost() {
        return decoratedService.getCost();
    }
}
