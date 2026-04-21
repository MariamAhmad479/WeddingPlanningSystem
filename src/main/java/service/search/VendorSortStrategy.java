package service.search;

import java.util.List;
import entity.Vendor;

/**
 * Strategy Pattern — Strategy interface for sorting vendor search results.
 *
 * Different implementations provide different sort orders
 * (by rating, by price, by name), swappable at runtime.
 */
public interface VendorSortStrategy {

    /**
     * Sort the given list of vendors and return the sorted result.
     */
    List<Vendor> sort(List<Vendor> vendors);
}
