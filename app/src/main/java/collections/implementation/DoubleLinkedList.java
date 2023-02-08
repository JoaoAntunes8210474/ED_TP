package collections.implementation;


import collections.exceptions.EmptyCollectionException;
import collections.interfaces.ListADT;
import java.util.Iterator;

/**
 * @param <T> Generic type
 */
public abstract class DoubleLinkedList<T> implements ListADT<T> {
    /**
     * The head and tail of the list
     */
    protected DoubleNode<T> head, tail;
    /**
     * The number of elements in the list
     */
    protected int count;

    /**
     * Creates an empty list.
     */
    public DoubleLinkedList() {
        this.head = null;
        this.tail = null;
        this.count = 0;
    }

    /**
     * Removes and returns the first element from this list.
     *
     * @return the first element from this list
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("List");
        DoubleNode<T> current = head;

        head = head.getNext();
        head.setPrevious(null);

        this.count--;
        return current.getElement();
    }

    /**
     * Removes and returns the last element from this list.
     *
     * @return the last element from this list
     */
    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("List");
        DoubleNode<T> current = tail;

        tail = tail.getPrevious();
        tail.setNext(null);

        this.count--;
        return current.getElement();
    }

    /**
     * Removes and returns the specified element from this list.
     *
     * @param element the element to be removed from the list
     * @return the removed element
     */
    @Override
    public T remove(T element) throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("List");

        boolean found = false;
        DoubleNode<T> previous = null;
        DoubleNode<T> current = head;
        while (current != null && !found) {
            if (element.equals(current.getElement()))
                found = true;
            else {
                previous = current;
                current = current.getNext();
            }
        }
        if (!found) throw new EmptyCollectionException("List");
        if (size() == 1) {
            head = tail = null;
        } else if (current.equals(head)) {
            head = current.getNext();
        } else if (current.equals(tail)) {
            tail = previous;
            tail.setNext(null);
        } else {
            previous.setNext(current.getNext());
        }
        count--;
        return current.getElement();
    }

    /**
     * Returns a reference to the element at the front of this list.
     *
     * @return a reference to the first element in this list
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("A lista está vazia");
        T result = head.getElement();
        return result;
    }

    /**
     * Returns a reference to the element at the rear of this list.
     *
     * @return a reference to the last element in this list
     */
    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("A lista está vazia");
        T result = tail.getElement();
        return result;
    }

    /**
     * Searches for the specified target element using the linear search
     * algorithm.
     *
     * @param target the element being sought in the list
     */
    @Override
    public boolean contains(T target) {
        T data = head.getElement();
        boolean found = false;
        int i = 0;

        while (i < this.count && !found) {
            if (target.equals(data)) {
                found = true;
            }
            i++;
        }
        return found;
    }

    /**
     * Checks if the list is empty.
     * @return true if the list is empty
     */
    @Override
    public boolean isEmpty() {
        return (count == 0);
    }

    /**
     * Returns the number of elements in this list.
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return this.count;
    }

    /**
     * Returns an iterator for the list.
     * @return an iterator for the list
     */
    @Override
    public Iterator<T> iterator() {
        return new BasicIterator<>(this);
    }

    /**
     * Creates an iterator for the list.
     */
    public class BasicIterator<T> implements Iterator<T> {
        /**
         * The current node
         */
        private DoubleNode<T> current;

/**
         * Creates a new iterator for the given list.
         *
         * @param list the list to iterate over
         */
        public BasicIterator(DoubleLinkedList<T> list) {
            current = list.head;
        }

        /**
         * Returns true if there are more elements in the iteration.
         * @return true if there are more elements in the iteration
         */
        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        /**
         * Returns the next element in the iteration.
         * @return the next element in the iteration
         */
        @Override
        public T next() {
            T data = current.getElement();
            current = current.getNext();
            return data;
        }
    }


    @Override
    public String toString() {
        DoubleNode<T> current = this.head;
        String s = "DoubleLinkedList:\n";
        while (current != null) {
            s += current.getElement().toString() + "\n";
            current = current.getNext();
        }
        return s;
    }
}
