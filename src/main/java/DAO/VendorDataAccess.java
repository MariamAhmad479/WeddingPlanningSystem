package DAO;

//import java.io.*;
import java.util.*;

/**
 * 
 */
public class VendorDataAccess {

    /**
     * Default constructor
     */
    public VendorDataAccess() {
    }

    /**
     * 
     */
    private String databaseConnectionPath;

    /**
     * 
     */
    private String queryBuffer;

    /**
     * 
     */
    private String operationResult;

    /**
     * @param criteria 
     * @return
     */
    /*public List<Vendor> findVendors(SearchCriteria criteria) {
        // TODO implement here
        return null;
    }*/

    /**
     * @param vendorId 
     * @return
     */
    /*public List<TimeSlot> fetchAvailability(String vendorId) {
        // TODO implement here
        return null;
    }*/

    /**
     * @param vendorId 
     * @param slot 
     * @return
     */
    /*public void insertAvailability(String vendorId, TimeSlot slot) {
        // TODO implement here
    }*/

    /**
     * @param vendorId 
     * @param slotId 
     * @return
     */
    public void deleteAvailability(String vendorId, String slotId) {
        // TODO implement here
    }

    /**
     * @param vendorId 
     * @param newRating 
     * @return
     */
    public void updateAverageRating(String vendorId, int newRating) {
        // TODO implement here
    }

    /**
     * @param vendorId 
     * @return
     */
    public void incReviewCount(String vendorId) {
        // TODO implement here
    }

}
