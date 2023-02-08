package collections.implementation;

import collections.exceptions.EmptyCollectionException;
import collections.interfaces.StackADT;

/**
 * @param <T> Generic type
 */
public class ArrayStack<T> implements StackADT<T> {
    /**
     * Default capacity of the stack
     */
    private final int DEFAULT_CAPACITY = 100;
    /**
     * Index of the top element of the stack
     */
    private int top;
    /**
     * Array that stores the elements of the stack
     */
    private T[] stack;

    /**
     * Creates an empty stack using the default capacity.
     */
    public ArrayStack() {
        top = 0;
        stack = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Creates an empty stack using the specified capacity.
     *
     * @param initialCapacity the initial size of the array
     */
    public ArrayStack(int initialCapacity) {
        top = 0;
        stack = (T[]) (new Object[initialCapacity]);
    }

    /**
     * Adds one element to the top of this stack.
     *
     * @param element element to be pushed onto stack
     */
    @Override
    public void push(T element) {
        if (size() == stack.length) {
            expandCapacity();
        }
        stack[top] = element;
        top++;
    }

    /**
     * Creates a new array to store the contents of this stack with
     * twice the capacity of the old one.
     */
    private void expandCapacity() {
        int tam = top + 1;
        T[] temp = (T[]) (new Object[tam]);
        for (int i = 0; i < top; i++) {
            temp[i] = stack[i];
        }
        stack = temp;
    }

    /**
     * Removes and returns the top element from this stack.
     *
     * @return T element removed from top of stack
     * @throws EmptyCollectionException if stack is empty
     */
    @Override
    public T pop() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("Stack");
        top--;
        T result = stack[top];
        stack[top] = null;
        return result;
    }

    /**
     * Returns without removing the top element of this stack.
     *
     * @return T element on top of stack
     * @throws EmptyCollectionException if stack is empty
     */
    @Override
    public T peek() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("Stack");
        return stack[top - 1];
    }

    /**
     * Returns true if this stack contains no elements.
     *
     * @return boolean true if this stack is empty
     */
    @Override
    public boolean isEmpty() {
        return top == 0;
    }

    /**
     * Returns the number of elements in this stack.
     *
     * @return int the number of elements in the stack
     */
    @Override
    public int size() {
        return top;
    }

    /**
     * Returns a string representation of this stack.
     *
     * @return String representation of the stack
     */
    @Override
    public String toString() {
        String s = "ArrayStack:\n";
        for (int i = 0; i < top; i++) {
            s += stack[i] + "\n";
        }
        return s;
    }
}
