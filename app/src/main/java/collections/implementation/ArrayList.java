package collections.implementation;

import collections.exceptions.EmptyCollectionException;
import collections.interfaces.ListADT;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * @param <T> Generic type
 */
public abstract class ArrayList<T> implements ListADT<T> {
    /**
     * Default size of the list
     */
    private final int SIZE = 100;
    /**
     * Array of generic type
     */
    protected T[] list;
    /**
     * Front of the list
     */
    protected int front;

    /**
     * Rear of the list
     */
    protected int rear;

    /**
     * Number of modifications
     */
    protected int modCount;

    /**
     * Constructor
     */
    public ArrayList() {
        this.list = (T[]) (new Object[SIZE]);
        this.front = 0;
        this.rear = 0;
        this.modCount = 0;
    }

    /**
     * Remove the first element of the list
     * @return first element of the list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("A lista está vazia");
        T result = list[front];
        list[front] = null;
        for (int i = 0; i < this.rear; i++) {
            this.list[i] = this.list[i + 1];
        }
        this.rear--;
        this.modCount++;
        return result;
    }

    /**
     * Remove the last element of the list
     * @return last element of the list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("A lista está vazia");
        this.rear--;
        T result = list[rear];
        list[rear] = null;
        this.modCount++;

        return result;
    }

    /**
     * Remove a specific element of the list
     * @param element the element to be removed from the list
     * @return the element removed
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T remove(T element) throws EmptyCollectionException {

        if (isEmpty()) throw new EmptyCollectionException("A lista está vazia");
        if (!contains(element)) throw new EmptyCollectionException("Elemento não existe");
        int position = 0;
        for (int i = 0; i < this.rear; i++) {
            if (element.toString().equals(list[i].toString())) {
                position = i;
            }
        }

        T result = list[position];
        list[position] = null;
        for (int j = position; j < this.rear; j++) {
            this.list[j] = this.list[j + 1];
        }
        this.rear--;
        this.modCount++;
        return result;
    }

    /**
     * Search for the first element of the list
     * @return the first element of the list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("A lista está vazia");
        return list[front];
    }

    /**
     * Search for the last element of the list
     * @return the last element of the list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("A lista está vazia");
        return list[rear - 1];
    }

    /**
     * Search for a specific element of the list
     * @param i the index of the element to be searched
     * @return the element searched
     */
    public T get(int i) {
        if (i >= this.rear || i < 0) throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + i);
        return list[i];
    }

    /**
     * Search for a specific element of the list
     * @param target the target that is being sought in the list
     * @return true if the element is in the list, false otherwise
     */
    @Override
    public boolean contains(T target) {
        boolean found = false;
        int i = 0;
        while (i < this.rear && !found) {
            if (target.toString().equals(list[i].toString())) {
                found = true;
            }
            i++;
        }
        return found;
    }

    /**
     * Check if the list is empty
     * @return true if the list is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return rear == 0;
    }

    /**
     * Return the size of the list
     * @return the size of the list
     */
    @Override
    public int size() {
        return this.rear;
    }

    /**
     * Return a string representation of the list
     * @return a string representation of the list
     */
    @Override
    public Iterator<T> iterator() {
        return new BasicIterator<>();
    }

    /**
     * Return a string representation of the list
     * @return a string representation of the list
     */
    @Override
    public String toString() {
        String s = "ArrayList:\n";
        for (int i = 0; i < this.rear; i++) {
            s += list[i] + "\n";
        }
        return s;
    }

    /**
     * Inner class to represent an iterator over the list
     */
    public class BasicIterator<T> implements Iterator<T> {
        /**
         * Size of the list
         */
        private final int size;
        /**
         * Array of the list
         */
        private final T[] items;
        /**
         * Current position of the iterator
         */
        private int current;
        /**
         * Expected number of modifications
         */
        private int expectedModCount;

        /**
         * Constructor of the iterator
         */
        public BasicIterator() {
            this.items = (T[]) ArrayList.this.list;
            this.size = ArrayList.this.rear;
            this.current = 0;
            this.expectedModCount = ArrayList.this.modCount;
        }

        /**
         * Check if the list has a next element
         * @return true if the list has a next element, false otherwise
         */
        @Override
        public boolean hasNext() {
            if (expectedModCount != modCount) throw new ConcurrentModificationException("Concorrência");
            return (this.items[this.current] != null);
        }

        /**
         * Return the next element of the list
         * @return the next element of the list
         */
        @Override
        public T next() {
            T temp = items[this.current];
            if (hasNext()) {
                this.current++;
            }
            return temp;
        }
    }
}
