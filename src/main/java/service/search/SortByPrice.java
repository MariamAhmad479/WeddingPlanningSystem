package service.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import entity.Vendor;

/**
 * Strategy Pattern — Concrete Strategy: sort vendors by starting price (ascending).
 */
public class SortByPrice implements VendorSortStrategy {

    @Override
    public List<Vendor> sort(List<Vendor> vendors) {
        List<Vendor> sorted = new ArrayList<>(vendors);
        sorted.sort(Comparator.comparingDouble(Vendor::getStartingPrice));
        System.out.println("[Strategy] Sorted vendors by PRICE (lowest first)");
        return sorted;
    }
}
