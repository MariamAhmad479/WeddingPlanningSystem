package entity.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import entity.GehazItem;

/**
 * Iterator Pattern — Custom filtered iterator for GehazItem collections.
 *
 * Accepts a Predicate filter so callers can iterate over only
 * matching items (e.g. only PURCHASED, only ELECTRONICS) without
 * scattering filter logic across the codebase.
 */
public class GehazItemIterator implements Iterator<GehazItem> {

    private final List<GehazItem> items;
    private final Predicate<GehazItem> filter;
    private int cursor;

    /**
     * @param items  the full list of gehaz items
     * @param filter a predicate — only items matching this filter are returned
     */
    public GehazItemIterator(List<GehazItem> items, Predicate<GehazItem> filter) {
        this.items = items;
        this.filter = filter;
        this.cursor = 0;
        advanceToNext(); // position cursor at the first matching item
    }

    @Override
    public boolean hasNext() {
        return cursor < items.size();
    }

    @Override
    public GehazItem next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more matching gehaz items.");
        }
        GehazItem item = items.get(cursor);
        cursor++;
        advanceToNext();
        return item;
    }

    /** Skip non-matching items until we find the next match or reach the end. */
    private void advanceToNext() {
        while (cursor < items.size() && !filter.test(items.get(cursor))) {
            cursor++;
        }
    }
}
