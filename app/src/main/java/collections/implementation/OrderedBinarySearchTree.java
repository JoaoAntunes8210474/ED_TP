package collections.implementation;

import collections.exceptions.EmptyCollectionException;
import collections.exceptions.NonComparableElementException;
import collections.interfaces.OrderedListADT;

import java.util.Iterator;

/**
 * @param <T> the generic type of elements in this collection
 */
public class OrderedBinarySearchTree<T> extends LinkedBinarySearchTree<T> implements OrderedListADT<T> {

    /**
     * Creates an empty binary search tree.
     */
    public OrderedBinarySearchTree() {
        super();
    }

    /**
     * Creates a binary search with the specified element as its root.
     *
     * @param element the element that will become the root of the new binary search tree
     */
    public OrderedBinarySearchTree(T element) {
        super(element);
    }

    /**
     * Adds the specified object to this binary search tree in the appropriate
     * position according to its key value. Note that equal elements are added
     * to the right.
     *
     * @param element the element to be added to this binary search tree
     * @throws NonComparableElementException if the element does not implement
     */
    @Override
    public void add(T element) throws NonComparableElementException {
        addElement(element);
        iteratorInOrder();
    }

    /**
     * Removes the first element from this list and returns a reference to it.
     * @return the first element from this list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {
        T first = removeMin();
        return first;
    }

    /**
     * Removes the last element from this list and returns a reference to it.
     * @return the last element from this list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T removeLast() throws EmptyCollectionException {
        T last = removeMax();
        return last;
    }

    /**
     * Removes and returns the specified element from this list.
     * @param element the element to be removed from the list
     * @return the removed element
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T remove(T element) throws EmptyCollectionException {
        T target = removeElement(element);
        return target;
    }

    /**
     * Returns a reference to the first element in this list.
     * @return a reference to the first element in this list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T first() throws EmptyCollectionException {
        T min = findMin();
        return min;
    }

    /**
     * Returns a reference to the last element in this list.
     * @return a reference to the last element in this list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T last() throws EmptyCollectionException {
        T max = findMax();
        return max;
    }

    /**
     * Return an iterator for the elements in this list.
     * @return an iterator over the elements in this list
     */
    @Override
    public Iterator<T> iterator() {
        return iteratorInOrder();
    }
}
