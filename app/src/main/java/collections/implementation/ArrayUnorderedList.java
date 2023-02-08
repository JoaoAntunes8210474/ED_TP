package collections.implementation;

import collections.exceptions.EmptyCollectionException;
import collections.interfaces.UnorderedListADT;

/**
 * @param <T> Generic type
 */
public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {

    /**
     * Constructor
     */
    public ArrayUnorderedList() {
        super();
    }

    /**
     * Adds the specified element to the front of this list.
     * @param element the element to be added to this list
     */
    @Override
    public void addToFront(T element) {
        T[] unorderedList = list;
        if (rear == unorderedList.length - 1) {
            expandCapacity();
        }
        for (int i = rear; i > front; i--) {
            unorderedList[i] = unorderedList[i - 1];
        }
        unorderedList[front] = element;
        rear++;
        modCount++;
    }

    /**
     * Expands the capacity of the list by one
     */
    private void expandCapacity() {
        T[] unorderedList = list;
        int tam = unorderedList.length + 1;
        T[] temp = (T[]) (new Object[tam]);
        for (int i = 0; i < rear; i++) {
            temp[i] = unorderedList[i];
        }
        unorderedList = temp;
    }

    /**
     * Adds the specified element to the rear of this list.
     * @param element the element to be added to this list
     */
    @Override
    public void addToRear(T element) {
        T[] unorderedList = list;
        if (rear == unorderedList.length - 1) {
            expandCapacity();
        }
        unorderedList[rear] = element;
        rear++;
        modCount++;
    }

    /**
     * Adds the specified element after the specified target element.
     * @param element the element to be added after the target element
     * @param target the target that the element is to be added after
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public void addAfter(T element, T target) throws EmptyCollectionException {
        T[] unorderedList = list;
        if (rear == unorderedList.length - 1) {
            expandCapacity();
        }
        int position = 0;
        for (int i = 0; i < rear; i++) {
            if (target.equals(unorderedList[i])) {
                position = i;
            }
            i++;
        }

        for (int j = rear; j > position; j--) {
            unorderedList[j] = unorderedList[j - 1];
        }
        unorderedList[position + 1] = element;
        rear++;
        modCount++;
    }

}
