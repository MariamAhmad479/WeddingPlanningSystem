package entity.iterator;

import java.util.function.Predicate;

import entity.GehazItem;
import enumeration.GehazCategory;
import enumeration.GehazStatus;

/**
 * Iterator Pattern — Utility class providing common filter predicates
 * for GehazItemIterator.
 *
 * Usage:
 *   gehazList.filteredIterator(GehazItemFilter.byStatus(GehazStatus.PURCHASED));
 *   gehazList.filteredIterator(GehazItemFilter.byCategory(GehazCategory.ELECTRONICS));
 *   gehazList.filteredIterator(GehazItemFilter.byCostAbove(5000));
 */
public class GehazItemFilter {

    private GehazItemFilter() { } // utility class — no instances

    /** Filter items by their purchase status. */
    public static Predicate<GehazItem> byStatus(GehazStatus status) {
        return item -> item.getStatus() == status;
    }

    /** Filter items by their category. */
    public static Predicate<GehazItem> byCategory(GehazCategory category) {
        return item -> item.getCategory() == category;
    }

    /** Filter items with cost above a threshold. */
    public static Predicate<GehazItem> byCostAbove(double threshold) {
        return item -> item.getCost() > threshold;
    }

    /** Accept all items (no filtering). */
    public static Predicate<GehazItem> all() {
        return item -> true;
    }
}
