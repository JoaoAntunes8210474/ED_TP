package collections.implementation;

/**
 * BinaryTreeNode represents a node in a binary tree with a left and right child.
 */
public class BinaryTreeNode<T> {
    /**
     * The information stored in this node.
     */
    protected T element;
    /**
     * Reference to the left child and right child.
     */
    protected BinaryTreeNode<T> left, right;

    /**
     * Creates a new tree node with the specified data.
     *
     * @param obj the element that will become a part of the new tree node
     */
    public BinaryTreeNode(T obj) {
        this.element = obj;
        this.left = null;
        this.right = null;
    }

    /**
     * Returns the element stored in this node.
     *
     * @return the element stored in this node
     */
    public T getElement() {
        return element;
    }

    /**
     * Sets the element stored in this node.
     *
     * @param element the new element to be stored in this node
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * Returns a reference to the left child.
     *
     * @return a reference to the left child.
     */
    public BinaryTreeNode<T> getLeft() {
        return left;
    }

    /**
     * Sets this node's left child to the BinaryTreeNode node.
     *
     * @param left a reference to the node that will become the left child
     */
    public void setLeft(BinaryTreeNode<T> left) {
        this.left = left;
    }

    /**
     * Returns a reference to the right child.
     *
     * @return a reference to the right child.
     */
    public BinaryTreeNode<T> getRight() {
        return right;
    }

    /**
     * Sets this node's right child to the BinaryTreeNode node.
     *
     * @param right a reference to the node that will become the right child
     */
    public void setRight(BinaryTreeNode<T> right) {
        this.right = right;
    }

    /**
     * Returns the number of non-null children of this node.
     * This method may be able to be written more efficiently.
     *
     * @return the integer number of non-null children of this node
     */
    public int numChildren() {
        int children = 0;

        if (left != null)
            children = 1 + left.numChildren();

        if (right != null)
            children = children + 1 + right.numChildren();

        return children;
    }
}

