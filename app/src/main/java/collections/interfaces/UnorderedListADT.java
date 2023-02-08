package collections.interfaces;

import collections.exceptions.EmptyCollectionException;

/**
 * An interface for an unordered list collection. Elements are added to the
 * rear of the list, to the front of the list, or after a particular element
 *
 * @param <T> the type of elements in this list
 */
public interface UnorderedListADT<T> extends ListADT<T> {
    /**
     * Adds the specified element to the front of the list
     *
     * @param element the element to be added to this list
     */
    public void addToFront(T element);

    /**
     * Adds the specified element to the rear of the list
     *
     * @param element the element to be added to this list
     */
    public void addToRear(T element);

    /**
     * Adds the specified element after a particular element already in the list
     *
     * @param element the element to be added to this list
     * @param target  the target element already in the list
     * @throws EmptyCollectionException if the list is empty
     */
    public void addAfter(T element, T target) throws EmptyCollectionException;
}
