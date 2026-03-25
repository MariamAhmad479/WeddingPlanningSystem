package service.vendor;

public class BasicPhotography implements VendorService {
    @Override
    public String getDescription() {
        return "Basic Photography Coverage (4 Hours)";
    }

    @Override
    public double getCost() {
        return 1000.00;
    }
}
