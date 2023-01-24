
package collections.implementation;

public class DoubleNode<T> {
    private DoubleNode<T> next;
    private T element;
    private DoubleNode<T> previous;

    public DoubleNode() {
        this.next = null;
        this.element = null;
        this.previous = null;
    }
    
    public DoubleNode(T elem) {
        this.next = null;
        this.element = elem;
        this.previous = null;
    }

    public DoubleNode<T> getNext() {
        return next;
    }

    public void setNext(DoubleNode<T> dnode) {
        this.next = dnode;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T elem) {
        this.element = elem;
    }

    public DoubleNode<T> getPrevious() {
        return previous;
    }

    public void setPrevious(DoubleNode<T> dnode) {
        this.previous = dnode;
    }
    
    @Override
    public String toString() {
        return "LinkedNode{" + "element=" + element + '}';
    }
}
