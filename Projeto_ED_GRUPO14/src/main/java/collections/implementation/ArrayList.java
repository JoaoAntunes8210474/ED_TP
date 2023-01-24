
package collections.implementation;

import collections.exceptions.EmptyCollectionException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import collections.interfaces.ListADT;

/**
 *
 */
public abstract class ArrayList<T> implements ListADT<T>  {
    private final int SIZE = 100;
    protected T[] list;
    protected int front;
    protected int rear;
    protected int modCount;

    public ArrayList() {
        this.list = (T[])(new Object[SIZE]);
        this.front = 0;
        this.rear = 0;
        this.modCount = 0;
    }
    
    
    @Override
    public T removeFirst() throws EmptyCollectionException {
        if(isEmpty()) throw new EmptyCollectionException("A lista está vazia");
        T result = list[front];
        list[front] = null;
        for (int i = 0; i < this.rear; i++) {
            this.list[i] = this.list[i+1];
        }
        this.rear--;
        this.modCount++;
        return result;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if(isEmpty()) throw new EmptyCollectionException("A lista está vazia");
        this.rear--;
        T result = list[rear];
        list[rear] = null;
        this.modCount++;
        
        return result;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException {
        
        if(isEmpty()) throw new EmptyCollectionException("A lista está vazia");
        if(!contains(element)) throw new EmptyCollectionException("Elemento não existe");
        int position = 0;
        for (int i = 0; i < this.rear; i++) {
            if (element.equals(list[i])) {
                position = i;
            }
        }
        
        T result = list[position];
        list[position] = null;
        for (int j = position; j < this.rear; j++) {
            this.list[j] = this.list[j+1];
        }
        this.rear--;
        this.modCount++;
        return result;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if(isEmpty()) throw new EmptyCollectionException("A lista está vazia");
        return list[front];
    }

    @Override
    public T last() throws EmptyCollectionException {
        if(isEmpty()) throw new EmptyCollectionException("A lista está vazia");
        return list[rear-1];
    }

    public T get(int i) {
        if(i >= this.rear || i < 0) throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + i);
        return list[i];
    }

    @Override
    public boolean contains(T target) {
        boolean found = false;
        int i = 0;
        while (i < this.rear && !found) {
            if (target.equals(list[i])) {
                found = true;
            }
            i++;
        }
        return found;
    }

    @Override
    public boolean isEmpty() {
        return rear == 0;
    }

    @Override
    public int size() {
        return this.rear;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new BasicIterator<>();
    }
    
    @Override
    public String toString() {
        String s = "ArrayList:\n";
        for (int i = 0; i < this.rear; i++) {
            s += list[i] + "\n";
        }
        return s;
    }
    
    public class BasicIterator<T> implements Iterator<T> {
        private final int size;
        private final T[] items;
        private int current;
        private int expectedModCount;
    
        public BasicIterator() {
            this.items = (T[]) ArrayList.this.list;
            this.size = ArrayList.this.rear;
            this.current = 0;
            this.expectedModCount = ArrayList.this.modCount;
        }
    
        @Override
        public boolean hasNext() {
            if(expectedModCount != modCount) throw new ConcurrentModificationException("Concorrência");
            return (this.items[this.current] != null);
        }
    
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
