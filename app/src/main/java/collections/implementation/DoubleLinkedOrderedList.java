package collections.implementation;

import collections.exceptions.NonComparableElementException;
import collections.interfaces.OrderedListADT;

/**
 * @param <T>
 */
public class DoubleLinkedOrderedList<T extends Comparable> extends DoubleLinkedList<T> implements OrderedListADT<T> {

    public DoubleLinkedOrderedList() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(T element) throws NonComparableElementException {
        Comparable<T> temp = (Comparable<T>) element;

        DoubleNode<T> traverse = head;
        DoubleNode<T> newnode = new DoubleNode<T>(element);

        // 1º caso - A lista está vazia
        if (isEmpty()) {
            head = newnode;
            tail = newnode;
        }
        // 2º caso - Inserir nó no fim da lista duplamente ligada
        else if (temp.compareTo(tail.getElement()) >= 0) {
            tail.setNext(newnode);
            newnode.setPrevious(tail);
            newnode.setNext(null);
            tail = newnode;
        }
        // 3º caso - Inserir nó no início da lista duplamente ligada
        else if (temp.compareTo(head.getElement()) <= 0) {
            head.setPrevious(newnode);
            newnode.setNext(head);
            newnode.setPrevious(null);
            head = newnode;
        } else {
            while ((temp.compareTo(traverse.getElement()) > 0)) {
                traverse = traverse.getNext();
            }

            newnode.setNext(traverse);
            newnode.setPrevious(traverse.getPrevious());
            traverse.getPrevious().setNext(newnode);
            traverse.setPrevious(newnode);
        }

        count++;
    }

}
