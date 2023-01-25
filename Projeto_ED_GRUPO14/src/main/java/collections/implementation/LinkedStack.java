
package collections.implementation;

import collections.exceptions.NullException;



public class LinkedStack<T> {
    int count;
    LinkedNode<T> top;

    public LinkedStack() {
        this.count = 0;
        this.top = null;
    }
    
    public void push(T element) {
        //1º Definir LinkedNode
        LinkedNode<T> newNode = new LinkedNode<>(element);
        newNode.setElement(element);
        if(this.top == null) {
            this.top = newNode;
        }
        else {
            newNode.setNext(top);
            top = newNode;
        }
        this.count++;
    }

    public T pop() throws NullException {
        if (isEmpty()) throw new NullException("Não existem elementos na lista ligada");

        T result = top.getElement();
        top = top.getNext();
        count--;

        return result;
    }
    
    public T peek() throws NullException {
        if (isEmpty()) throw new NullException("Stack");
        LinkedNode<T> temp = this.top;
        return temp.getElement();
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    @Override
    public String toString() {
        LinkedNode<T> current = top;
        String s = "LinkedList:\n";
        while(current != null) {
            s += current.getElement().toString() + "\n";
            current = current.getNext();
        }
        return s;
    }  
    
}
