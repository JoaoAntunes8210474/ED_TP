package collections.interfaces;


import collections.exceptions.ElementNotFoundException;

/**
 * An interface for a binary search tree. A binary search tree is a binary tree
 * in which each node has a Comparable element such that for any node, the
 * elements in the left subtree are less than the element in the node, and the
 * elements in the right subtree are greater than the element in the node.
 *
 * @param <T> the type of element in this tree
 */
public interface BinarySearchTreeADT<T> extends BinaryTreeADT<T> {

    /**
     * Adds the specified element to the proper location in this tree.
     *
     * @param element the element to be added to this tree
     */
    public void addElement(T element);

    /**
     * Removes and returns the specified element from this tree.
     *
     * @param targetElement the element to be removed from this tree
     * @return the element removed from this tree
     * @throws ElementNotFoundException if the specified element is not found in this tree
     */
    public T removeElement(T targetElement) throws ElementNotFoundException;

    /**
     * Removes all occurences of the specified element from this tree.
     *
     * @param targetElement the element that the list will have all instances of it removed
     * @throws ElementNotFoundException if the specified element is not found in this tree
     */
    public void removeAllOccurrences(T targetElement) throws ElementNotFoundException;

    /**
     * Removes and returns the smallest element from this tree.
     *
     * @return the smallest element from this tree.
     */
    public T removeMin();

    /**
     * Removes and returns the largest element from this tree.
     *
     * @return the largest element from this tree
     */
    public T removeMax();

    /**
     * Returns a reference to the smallest element in this tree.
     *
     * @return a reference to the smallest element in this tree
     * @throws ElementNotFoundException if the tree is empty
     */
    public T findMin() throws ElementNotFoundException;

    /**
     * Returns a reference to the largest element in this tree.
     *
     * @return a reference to the largest element in this tree
     */
    public T findMax();
}

