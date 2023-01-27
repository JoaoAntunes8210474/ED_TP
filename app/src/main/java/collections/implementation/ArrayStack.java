package collections.implementation;

import collections.exceptions.EmptyCollectionException;
import collections.interfaces.StackADT;

/**
 * @param <T>
 */
public class ArrayStack<T> implements StackADT<T> {
    private final int DEFAULT_CAPACITY = 100;
    private int top;
    private T[] stack;

    public ArrayStack() {
        top = 0;
        stack = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    public ArrayStack(int initialCapacity) {
        top = 0;
        stack = (T[]) (new Object[initialCapacity]);
    }

    @Override
    public void push(T element) {
        if (size() == stack.length) {
            expandCapacity();
        }
        stack[top] = element;
        top++;
    }

    private void expandCapacity() {
        int tam = top + 1;
        T[] temp = (T[]) (new Object[tam]);
        for (int i = 0; i < top; i++) {
            temp[i] = stack[i];
        }
        stack = temp;
    }

    @Override
    public T pop() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("Stack");
        top--;
        T result = stack[top];
        stack[top] = null;
        return result;
    }

    @Override
    public T peek() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("Stack");
        return stack[top - 1];
    }

    @Override
    public boolean isEmpty() {
        return top == 0;
    }

    @Override
    public int size() {
        return top;
    }

    @Override
    public String toString() {
        String s = "ArrayStack:\n";
        for (int i = 0; i < top; i++) {
            s += stack[i] + "\n";
        }
        return s;
    }
}
