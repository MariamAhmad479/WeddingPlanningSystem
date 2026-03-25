package service.vendor;

public class DronePhotographyDecorator extends VendorServiceDecorator {
    public DronePhotographyDecorator(VendorService decoratedService) {
        super(decoratedService);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Aerial Drone Shots";
    }

    @Override
    public double getCost() {
        return super.getCost() + 300.00;
    }
}
