
package collections.implementation;

import collections.exceptions.EmptyCollectionException;
import collections.exceptions.NonComparableElementException;
import java.util.Iterator;
import collections.interfaces.OrderedListADT;
/**
 *
 * @param <T>
 */
public class OrderedBinarySearchTree<T> extends LinkedBinarySearchTree<T> implements OrderedListADT<T> {

    public OrderedBinarySearchTree() {
        super();
    }

    public OrderedBinarySearchTree(T element) {
        super(element);
    }
    
    @Override
    public void add(T element) throws NonComparableElementException {
        addElement(element);
        iteratorInOrder();
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        T first = removeMin();
        return first;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        T last = removeMax();
        return last;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException {
        T target = removeElement(element);
        return target;
    }

    @Override
    public T first() throws EmptyCollectionException {
        T min = findMin();
        return min;
    }

    @Override
    public T last() throws EmptyCollectionException {
        T max = findMax();
        return max;
    }

    @Override
    public Iterator<T> iterator() {
        return iteratorInOrder();
    }    
}
