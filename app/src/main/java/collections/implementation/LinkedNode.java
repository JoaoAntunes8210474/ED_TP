package collections.implementation;

/**
 * Nó de uma lista ligada que contém a informação do seu próximo, cria a sua
 * referência e armazena a sua informação
 *
 * @param <T> Generic type of the element
 */
public class LinkedNode<T> {
    /**
     * Reference to the next node
     */
    private LinkedNode<T> next;

    /**
     * Element of the node
     */
    private T element;

    /**
     * Default constructor
     */
    public LinkedNode() {
    }

    /**
     * Constructor with element
     *
     * @param element Element of the node
     */
    public LinkedNode(T element) {
        this.element = element;
        this.next = null;
    }

    /**
     * Returns the next node
     * @return Next node
     */
    public LinkedNode<T> getNext() {
        return this.next;
    }

    /**
     * Sets the next node
     * @param next Next node
     */
    public void setNext(LinkedNode<T> next) {
        this.next = next;
    }

    /**
     * Returns the element of the node
     * @return Element of the node
     */
    public T getElement() {
        return element;
    }

    /**
     * Sets the element of the node
     * @param element Element of the node
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * Returns the string representation of the node
     * @return String representation of the node
     */
    @Override
    public String toString() {
        return "LinkedNode{" + "element=" + element + '}';
    }
}
