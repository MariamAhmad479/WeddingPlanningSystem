package service.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import entity.Vendor;

/**
 * Strategy Pattern — Concrete Strategy: sort vendors by average rating (descending).
 */
public class SortByRating implements VendorSortStrategy {

    @Override
    public List<Vendor> sort(List<Vendor> vendors) {
        List<Vendor> sorted = new ArrayList<>(vendors);
        sorted.sort(Comparator.comparingDouble(Vendor::getAverageRating).reversed());
        System.out.println("[Strategy] Sorted vendors by RATING (highest first)");
        return sorted;
    }
}
