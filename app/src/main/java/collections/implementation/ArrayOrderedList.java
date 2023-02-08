package collections.implementation;

import collections.exceptions.NonComparableElementException;
import collections.interfaces.OrderedListADT;

/**
 * @param <T> Generic type
 */
public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T> {

    /**
     * Construtor padrão
     */
    public ArrayOrderedList() {
        super();
    }

    /**
     * Add the specified element to this list at the proper location
     * @param element the element to be added to this list
     * @throws NonComparableElementException if the element is not comparable
     */
    @Override
    public void add(T element) throws NonComparableElementException {
        if (!(element instanceof Comparable))
            throw new NonComparableElementException("O elemento ou classe não é comparável");
        if (size() == list.length) {
            expandCapacity();
        }

        Comparable<T> temp = (Comparable<T>) element;
        int index = 0;

        while (index < rear && temp.compareTo(list[index]) > 0) {
            index++;
        }

        for (int j = this.rear; j > index; j--) {
            list[j] = list[j - 1];
        }
        this.list[index] = element;
        rear++;
        modCount++;
    }

    /**
     * Expand the capacity of the list
     */
    private void expandCapacity() {
        int tam = list.length + 1;
        T[] temp = (T[]) (new Object[tam]);
        for (int i = 0; i < rear; i++) {
            temp[i] = list[i];
        }
        list = temp;
    }
}
