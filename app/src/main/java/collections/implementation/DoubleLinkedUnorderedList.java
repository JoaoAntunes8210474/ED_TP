package collections.implementation;

import collections.exceptions.EmptyCollectionException;
import collections.interfaces.UnorderedListADT;

/**
 * @param <T>
 */
public class DoubleLinkedUnorderedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {

    public DoubleLinkedUnorderedList() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addToFront(T element) {
        DoubleNode<T> newNode = new DoubleNode<T>(element);
        newNode.setElement(element);
        if (head == null) {
            head = newNode;
            tail = newNode;
        }
        newNode.setNext(head);
        head.setPrevious(newNode);
        head = newNode;
        count++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addToRear(T element) {
        DoubleNode<T> newNode = new DoubleNode<T>(element);
        newNode.setElement(element);
        if (head == null) {
            newNode.setPrevious(null);
            // initialize both front and rear
            head = newNode;
            tail = newNode;
        }

        newNode.setPrevious(tail);
        tail.setNext(newNode);
        tail = newNode;
        count++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAfter(T element, T target) throws EmptyCollectionException {
        DoubleNode<T> newnode = new DoubleNode<T>(element);
        newnode.setElement(element);
        boolean found = false;

        // 1º caso - A lista está vazia
        if (isEmpty()) {
            head = newnode;
            tail = newnode;
        } else {
            DoubleNode<T> current = head;
            while (current != null && !found) {
                if (target.equals(current.getElement())) {
                    found = true;
                } else {
                    current = current.getNext();
                }
            }

            if (current.getNext() == null) {
                current.setNext(newnode);
                newnode.setPrevious(current);
                tail = newnode;
            } else {
                DoubleNode<T> next = current.getNext();
                current.setNext(newnode);
                newnode.setPrevious(current);
                newnode.setNext(next);
                next.setPrevious(newnode);
            }
        }
        count++;
    }

}
