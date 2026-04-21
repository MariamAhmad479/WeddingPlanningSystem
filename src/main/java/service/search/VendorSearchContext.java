package service.search;

import java.util.List;
import entity.Vendor;

/**
 * Strategy Pattern — Context class for vendor search sorting.
 *
 * Holds a VendorSortStrategy and delegates sorting to it.
 * The strategy can be swapped at runtime via setStrategy().
 */
public class VendorSearchContext {

    private VendorSortStrategy strategy;

    public VendorSearchContext(VendorSortStrategy strategy) {
        this.strategy = strategy;
    }

    /** Change the sort strategy at runtime. */
    public void setStrategy(VendorSortStrategy strategy) {
        this.strategy = strategy;
    }

    /** Delegate sorting to the current strategy. */
    public List<Vendor> sortVendors(List<Vendor> vendors) {
        return strategy.sort(vendors);
    }
}
