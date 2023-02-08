package collections.interfaces;

import collections.exceptions.EmptyCollectionException;

/**
 * HeapADT defines the interface to a heap collection.
 *
 * @param <T> the generic type of element in this collection
 */
public interface HeapADT<T> extends BinaryTreeADT<T> {
    /**
     * Adds the specified object to this heap.
     *
     * @param obj the element to added to this head
     */
    public void addElement(T obj);

    /**
     * Removes element with the lowest value from this heap.
     *
     * @return the element with the lowest value from this heap
     * @throws EmptyCollectionException if the heap is empty
     */
    public T removeMin() throws EmptyCollectionException;

    /**
     * Returns a reference to the element with the lowest value in
     * this heap.
     *
     * @return a reference to the element with the lowest value in this heap
     * @throws EmptyCollectionException if the heap is empty
     */
    public T findMin() throws EmptyCollectionException;
}

