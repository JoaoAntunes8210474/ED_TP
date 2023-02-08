package collections.implementation;

import collections.exceptions.NullException;


/**
 * @param <T> Generic type
 */
public class LinkedStack<T> {
    /**
     * Number of elements in the stack
     */
    int count;

    /**
     * Top of the stack
     */
    LinkedNode<T> top;


    /**
     * Constructor
     */
    public LinkedStack() {
        this.count = 0;
        this.top = null;
    }


    /**
     * Adds an element to the top of the stack
     * @param element Element to be added
     */
    public void push(T element) {
        //1º Definir LinkedNode
        LinkedNode<T> newNode = new LinkedNode<>(element);
        newNode.setElement(element);
        if (this.top == null) {
            this.top = newNode;
        } else {
            newNode.setNext(top);
            top = newNode;
        }
        this.count++;
    }

    /**
     * Removes the top element of the stack
     * @return Element removed
     * @throws NullException If the stack is empty
     */
    public T pop() throws NullException {
        if (isEmpty()) throw new NullException("Não existem elementos na lista ligada");

        T result = top.getElement();
        top = top.getNext();
        count--;

        return result;
    }

    /**
     * Returns the top element of the stack
     * @return Top element
     * @throws NullException If the stack is empty
     */
    public T peek() throws NullException {
        if (isEmpty()) throw new NullException("Stack");
        LinkedNode<T> temp = this.top;
        return temp.getElement();
    }

    /**
     * Checks if the stack is empty
     * @return True if the stack is empty, false otherwise
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Returns the number of elements in the stack
     * @return Number of elements
     */
    public int size() {
        return count;
    }

    /**
     * Returns a string representation of the stack
     * @return String representation of the stack
     */
    @Override
    public String toString() {
        LinkedNode<T> current = top;
        String s = "LinkedList:\n";
        while (current != null) {
            s += current.getElement().toString() + "\n";
            current = current.getNext();
        }
        return s;
    }

}
