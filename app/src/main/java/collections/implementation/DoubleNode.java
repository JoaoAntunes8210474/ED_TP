package collections.implementation;

/**
 * DoubleNode class
 * @param <T> Generic type
 */
public class DoubleNode<T> {
    /**
     * Next node
     */
    private DoubleNode<T> next;

    /**
     * Element
     */
    private T element;

    /**
     * Previous node
     */
    private DoubleNode<T> previous;

    /**
     * Constructor
     */
    public DoubleNode() {
        this.next = null;
        this.element = null;
        this.previous = null;
    }

    /**
     * Constructor with element
     * @param elem Element
     */
    public DoubleNode(T elem) {
        this.next = null;
        this.element = elem;
        this.previous = null;
    }

    /**
     * Returns next node
     * @return Next node
     */
    public DoubleNode<T> getNext() {
        return next;
    }

    /**
     * Sets next node
     * @param node Next node
     */
    public void setNext(DoubleNode<T> node) {
        this.next = node;
    }

    /**
     * Returns element
     * @return Element
     */
    public T getElement() {
        return element;
    }

    /**
     * Sets element
     * @param elem Element
     */
    public void setElement(T elem) {
        this.element = elem;
    }

    /**
     * Returns previous node
     * @return Previous node
     */
    public DoubleNode<T> getPrevious() {
        return previous;
    }

    /**
     * Sets previous node
     * @param dnode Previous node
     */
    public void setPrevious(DoubleNode<T> dnode) {
        this.previous = dnode;
    }

    /**
     * Returns string representation of node
     * @return String representation of node
     */
    @Override
    public String toString() {
        return "LinkedNode{" + "element=" + element + '}';
    }
}
