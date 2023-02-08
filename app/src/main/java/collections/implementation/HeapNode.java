package collections.implementation;


/**
 * A node in a heap
 * @param <T> the type of data stored in the heap
 */
public class HeapNode<T> extends BinaryTreeNode<T> {
    /**
     * The parent of this node
     */
    protected HeapNode<T> parent;

    /**
     * Creates a new heap node with the specified data
     *
     * @param obj the data to be contained within the new heap nodes
     */
    public HeapNode(T obj) {
        super(obj);
        this.parent = null;
    }

    /**
     * Returns the parent of this node
     * @return the parent of this node
     */
    public HeapNode<T> getParent() {
        return parent;
    }

    /**
     * Sets the parent of this node
     * @param parent the new parent of this node
     */
    public void setParent(HeapNode<T> parent) {
        this.parent = parent;
    }
}
