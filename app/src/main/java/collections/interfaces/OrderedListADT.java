package collections.interfaces;

import collections.exceptions.NonComparableElementException;

/**
 * An interface for an ordered list. Elements are ordered
 * according to their natural ordering
 *
 * @param <T> the type of elements in this list
 */
public interface OrderedListADT<T> extends ListADT<T> {
    /**
     * Adds the specified element to this list at
     * the proper location
     *
     * @param element the element to be added to this list
     * @throws NonComparableElementException if the element is not comparable
     */
    public void add(T element) throws NonComparableElementException;
}
