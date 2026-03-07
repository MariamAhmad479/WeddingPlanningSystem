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

    @Override
    public String getUserType() {
        return "VENDOR";
    }
}
