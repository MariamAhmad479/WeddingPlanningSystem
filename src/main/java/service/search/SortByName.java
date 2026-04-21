package service.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import entity.Vendor;

/**
 * Strategy Pattern — Concrete Strategy: sort vendors alphabetically by business name.
 */
public class SortByName implements VendorSortStrategy {

    @Override
    public List<Vendor> sort(List<Vendor> vendors) {
        List<Vendor> sorted = new ArrayList<>(vendors);
        sorted.sort(Comparator.comparing(Vendor::getBusinessName, String.CASE_INSENSITIVE_ORDER));
        System.out.println("[Strategy] Sorted vendors by NAME (A-Z)");
        return sorted;
    }
}
