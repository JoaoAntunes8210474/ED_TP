package collections.implementation;

import collections.interfaces.QueueADT;
import collections.exceptions.EmptyCollectionException;

/**
 * A linked implementation of a queue.
 *
 * @param <T> the type of elements in this queue
 */
public class LinkedQueue<T> implements QueueADT<T> {
    /**
     * The first node in the queue.
     */
    private int count;

    /**
     * The front and rear of the queue.
     */
    private LinkedNode<T> front, rear;

    /**
     * Creates an empty queue.
     */
    public LinkedQueue() {
        this.count = 0;
        this.front = null;
        this.rear = null;
    }

    /**
     * Inserts the specified element into the rear of this queue.
     *
     * @param element the element to be added to the rear of this queue
     */
    @Override
    public void enqueue(T element) {
        // Allocate the new node in the heap
        LinkedNode<T> node = new LinkedNode<T>(element);
        //System.out.println("Inserting" +  element + "\n");

        // special case: queue was empty
        if (front == null) {
            // initialize both front and rear
            front = node;
            rear = node;
        } else {
            // update rear
            rear.setNext(node);
            rear = node;
        }
        this.count++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("A lista está vazia");

        LinkedNode<T> temp = this.front;
        //System.out.println("Removing " + temp.getElement() + "\n");

        // advance front to the next node
        this.front = this.front.getNext();

        // if list becomes empty
        if (front == null) {
            this.rear = null;
        }

        // deallocate the memory of removed node and
        // optionally return the removed item
        T item = temp.getElement();
        this.count--;
        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("A lista está vazia");
        T result = front.getElement();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return (count == 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return this.count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        LinkedNode<T> current = this.front;
        String s = "LinkedQueue:\n";
        while(current != null) {
            s += current.getElement().toString() + "\n";
            current = current.getNext();
        }
        return s;
    }
}
