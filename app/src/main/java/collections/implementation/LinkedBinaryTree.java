package collections.implementation;

import collections.exceptions.ElementNotFoundException;
import collections.exceptions.EmptyCollectionException;
import collections.interfaces.BinaryTreeADT;
import java.util.Iterator;

/**
 * LinkedBinaryTree implements the BinaryTreeADT interface
 *
 * @param <T> the generic type of element in this collection
 */
public class LinkedBinaryTree<T> implements BinaryTreeADT<T> {

    /**
     * Number of elements in the tree
     */
    protected int count;

    /**
     * Reference to the root of the tree
     */
    protected BinaryTreeNode<T> root;

    /**
     * Creates an empty binary tree.
     */
    public LinkedBinaryTree() {
        count = 0;
        root = null;
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element that will become the root of the new binary
     *                tree
     */
    public LinkedBinaryTree(T element) {
        count = 1;
        root = new BinaryTreeNode<T>(element);
    }

    /**
     * Returns a reference to the element at the root
     *
     * @return a reference to the specified target
     * @throws EmptyCollectionException if an empty collection exception occurs
     */
    @Override
    public T getRoot() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("A lista está vazia");
        }
        T result = root.getElement();
        return result;
    }

    /**
     * Returns true if this binary tree is empty and false otherwise.
     *
     * @return true if this binary tree is empty
     */
    @Override
    public boolean isEmpty() {
        return (count == 0);
    }

    /**
     * Returns the integer size of this tree.
     *
     * @return the integer size of this tree
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Returns true if the binary tree contains an element that matches the
     * specified target element and false otherwise.
     *
     * @param targetElement the element being sought in the tree
     * @return true if the tree contains the target element
     */
    @Override
    public boolean contains(T targetElement) {
        return (this.findAgain(targetElement, root) != null);
    }

    /**
     * Returns a reference to the specified target element if it is found in
     * this binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @return a reference to the specified target
     * @throws ElementNotFoundException if the specified target element is not
     *                                  found
     */
    @Override
    public T find(T targetElement) throws ElementNotFoundException {
        BinaryTreeNode<T> current = findAgain(targetElement, root);

        if (current == null) {
            throw new ElementNotFoundException("binary tree");
        }

        return (current.element);
    }

    /**
     * Returns a reference to the specified target element if it is found in
     * this binary tree.
     *
     * @param targetElement the element being sought in this tree
     * @param next          the element to begin searching from
     */
    private BinaryTreeNode<T> findAgain(T targetElement, BinaryTreeNode<T> next) {
        if (next == null) {
            return null;
        }

        if (next.element.equals(targetElement)) {
            return next;
        }

        BinaryTreeNode<T> temp = findAgain(targetElement, next.left);

        if (temp == null) {
            temp = findAgain(targetElement, next.right);
        }

        return temp;
    }

    /**
     * Returns the iterator over the elements in this tree using the in-order algorithm.
     * @return the iterator over the elements in this tree using the in-order algorithm
     */
    @Override
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        inorder(this.root, tempList);

        return tempList.iterator();
    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param node     the node to be used as the root for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void inorder(BinaryTreeNode<T> node,
                           ArrayUnorderedList<T> tempList) {
        if (node != null) {
            inorder(node.left, tempList);
            tempList.addToRear(node.element);
            inorder(node.right, tempList);
        }
    }

    /**
     * Returns the iterator over the elements in this tree using the pre-order algorithm.
     * @return the iterator over the elements in this tree using the pre-order algorithm
     */
    @Override
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        preorder(this.root, tempList);

        return tempList.iterator();
    }

    /**
     * Performs a recursive preorder traversal.
     *
     * @param node     the node to be used as the root for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void preorder(BinaryTreeNode<T> node,
                            ArrayUnorderedList<T> tempList) {
        if (node != null) {
            tempList.addToRear(node.element);
            preorder(node.left, tempList);
            preorder(node.right, tempList);
        }
    }

    /**
     * Returns the iterator over the elements in this tree using the post-order algorithm.
     * @return the iterator over the elements in this tree using the post-order algorithm
     */
    @Override
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        postorder(this.root, tempList);

        return tempList.iterator();
    }

    /**
     * Performs a recursive postorder traversal.
     *
     * @param node     the node to be used as the root for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    protected void postorder(BinaryTreeNode<T> node,
                             ArrayUnorderedList<T> tempList) {
        if (node != null) {
            postorder(node.left, tempList);
            postorder(node.right, tempList);
            tempList.addToRear(node.element);
        }
    }

    /**
     * Returns the iterator over the elements in this tree using the level-order algorithm.
     * @return the iterator over the elements in this tree using the level-order algorithm
     */
    @Override
    public Iterator<T> iteratorLevelOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        levelorder(this.root, tempList);

        return tempList.iterator();
    }

    /**
     * Performs a recursive levelorder traversal.
     *
     * @param root     the node to be used as the root for this traversal
     * @param results the temporary list for use in this traversal
     */
    protected void levelorder(BinaryTreeNode<T> root, ArrayUnorderedList<T> results) {
        LinkedQueue<BinaryTreeNode<T>> nodes = new LinkedQueue<>();
        BinaryTreeNode<T> node = null;
        nodes.enqueue(root);
        while (!nodes.isEmpty()) {
            try {
                node = nodes.dequeue();
                if (node.element != null) {
                    results.addToRear(node.element);

                } else {
                    results.addToRear(null);
                }

                if (node.getLeft() != null) {
                    nodes.enqueue(node.getLeft());
                }
                if (node.getRight() != null) {
                    nodes.enqueue(node.getRight());
                }
            } catch (EmptyCollectionException ex) {
                System.out.println(ex.getMessage());
            }
            ;

        }
    }

    /**
     * Returns a string representation of this binary tree showing the nodes in
     * an levelorder fashion.
     *
     * @return a string representation of this binary tree
     */
    @Override
    public String toString() {
        String s = "Iterator LevelOrder:";
        Iterator<T> itr = iteratorLevelOrder();
        s += " | ";
        while (itr.hasNext()) {
            s += itr.next() + " | ";
        }
        return s;
    }
}
