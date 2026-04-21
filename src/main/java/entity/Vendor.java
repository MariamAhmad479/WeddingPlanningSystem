package entity;

/**
 * 
 */
public class Vendor implements User {

    /**
     * Default constructor
     */
    public Vendor() {
    }

    /**
     * 
     */
    private String vendorId;

    /**
     * 
     */
    private String businessName;

    /**
     * 
     */
    private String serviceCategory;

    /**
     * 
     */
    // private VendorStatus status;

    /**
     * 
     */
    private String location;

    /**
     * 
     */
    private float rating;

    /**
     * 
     */
    private double startingPrice;

    /**
     * 
     */
    private double averageRating;

    /**
     * 
     */
    private int totalReviews;

    public Vendor(String vendorId, String businessName, String serviceCategory,
            double startingPrice, double averageRating, int totalReviews) {
        this.vendorId = vendorId;
        this.businessName = businessName;
        this.serviceCategory = serviceCategory;
        this.startingPrice = startingPrice;
        this.averageRating = averageRating;
        this.totalReviews = totalReviews;
    }

    public String getVendorId() {
        return vendorId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public String getLocation() {
        return location;
    }

    public float getRating() {
        return rating;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    @Override
    public String getUserType() {
        return "VENDOR";
    }
}
