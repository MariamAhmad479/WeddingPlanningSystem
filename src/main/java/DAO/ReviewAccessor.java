package DAO;

//import java.io.*;
import java.util.*;

/**
 * 
 */
public class ReviewAccessor {

    /**
     * Default constructor
     */
    public ReviewAccessor() {
    }

    /**
     * 
     */
    public String connectionString;

    /**
     * @param vendorId 
     * @param brideId 
     * @param rating 
     * @param comment 
     * @return
     */
    public Boolean insertReview(String vendorId, String brideId, int rating, String comment) {
        // TODO implement here
        return null;
    }

    /**
     * @param reviewId 
     * @return
     */
    public void deleteReview(String reviewId) {
        // TODO implement here
    }

    /**
     * @param vendorId 
     * @return
     */
    public List getReviewsByVendor(String vendorId) {
        // TODO implement here
        return null;
    }

    /**
     * @param vendorId 
     * @return
     */
    /*public double getAverageRating(String vendorId) {
        // TODO implement here
    }*/

    /**
     * @param vendorId 
     * @param rating
     */
    public void updateVendorScore(String vendorId, double rating) {
        // TODO implement here
    }

}
