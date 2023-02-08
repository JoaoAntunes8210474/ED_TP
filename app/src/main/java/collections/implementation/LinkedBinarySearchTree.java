package collections.implementation;

import collections.exceptions.ElementNotFoundException;
import collections.interfaces.BinarySearchTreeADT;

/**
 * @param <T> the generic type of elements in this tree
 */
public class LinkedBinarySearchTree<T> extends LinkedBinaryTree<T>
        implements BinarySearchTreeADT<T> {

    /**
     * Creates an empty binary search tree.
     */
    public LinkedBinarySearchTree() {
        super();
    }

    /**
     * Creates a binary search with the specified element as its root.
     *
     * @param element the element that will be the root of the new
     *                binary search tree
     */
    public LinkedBinarySearchTree(T element) {
        super(element);
    }

    /**
     * Adds the specified object to the binary search tree in the appropriate
     * position according to its key value. Note that equal elements are added
     * to the right.
     *
     * @param element the element to be added to the binary search tree
     */
    @Override
    public void addElement(T element) {
        BinaryTreeNode<T> temp = new BinaryTreeNode<T>(element);
        Comparable<T> comparableElement = (Comparable<T>) element;

        if (isEmpty()) {
            root = temp;
        } else {
            BinaryTreeNode<T> current = root;
            boolean added = false;
            while (!added) {
                if (comparableElement.compareTo(current.getElement()) < 0) {
                    if (current.getLeft() == null) {
                        current.setLeft(temp);
                        added = true;
                    } else {
                        current = current.getLeft();
                    }
                } else {
                    if (current.getRight() == null) {
                        current.setRight(temp);
                        added = true;
                    } else {
                        current = current.getRight();
                    }
                }
            }
        }
        count++;
    }

    /**
     * Removes the first element that matches the specified target element
     * from the binary search tree and returns a reference to it. Throws a
     * ElementNotFoundException if the specified target element is not
     * found in the binary search tree.
     *
     * @param targetElement the element being sought in the binary search tree
     * @return a reference to the first element in the binary search tree
     * that matches the specified target element
     * @throws ElementNotFoundException if an element not found exception occurs
     */
    @Override
    public T removeElement(T targetElement) throws ElementNotFoundException {
        T result = null;
        if (!isEmpty()) {
            if (((Comparable) targetElement).equals(root.getElement())) {
                result = root.getElement();
                root = replacement(root);
                count--;
            } else {
                BinaryTreeNode<T> current, parent = root;
                boolean found = false;

                if (((Comparable) targetElement).compareTo(root.getElement()) < 0)
                    current = root.getLeft();
                else
                    current = root.getRight();

                while (current != null && !found) {
                    if (targetElement.equals(current.getElement())) {
                        found = true;
                        count--;
                        result = current.getElement();

                        if (current == parent.getLeft()) {
                            parent.setLeft(replacement(current));
                        } else {
                            parent.setRight(replacement(current));
                        }
                    } else {
                        parent = current;

                        if (((Comparable) targetElement).compareTo(current.getElement()) < 0)
                            current = current.getLeft();
                        else
                            current = current.getRight();
                    }
                } //while

                if (!found) throw new ElementNotFoundException("binary search tree");
            }
        } // end outer if
        return result;
    }

    /**
     * Returns the node replaced by the node sent as parameter
     * @param node the node to be replaced
     * @return the node that was replaced
     */
    protected BinaryTreeNode<T> replacement(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> result = null;

        if ((node.getLeft() == null) && (node.getRight() == null)) {
            result = null;
        } else if ((node.getLeft() != null) && (node.getRight() == null)) {
            result = node.getLeft();
        } else if ((node.getLeft() == null) && (node.getRight() != null)) {
            result = node.getRight();
        } else {
            BinaryTreeNode<T> current = node.getRight();
            BinaryTreeNode<T> parent = node;

            while (current.getLeft() != null) {
                parent = current;
                current = current.getLeft();
            }
            if (node.getRight() == current)
                current.setLeft(node.getLeft());
            else {
                parent.setLeft(current.getRight());
                current.setRight(node.getRight());
                current.setLeft(node.getLeft());
            }
            result = current;
        }
        return result;
    }

    /**
     * Removes elements that match the specified target element from the
     * binary search tree. Throws a ElementNotFoundException if the
     * specified target element is not found in this tree.
     *
     * @param targetElement the element being sought in this tree
     * @throws ElementNotFoundException if an element not found exception occurs
     */
    @Override
    public void removeAllOccurrences(T targetElement) throws ElementNotFoundException {
        try {
            while (contains(targetElement)) {
                removeElement(targetElement);
            }
        } catch (Exception ElementNotFoundException) {
            ;
        }
    }

    /**
     * Removes the node with the smallest element from the binary search tree
     * @return the element of the removed node
     */
    @Override
    public T removeMin() {
        T result = null;
        if (findMin() != null) {
            result = removeElement(findMin());
        }
        return result;
    }

    /**
     * Removes the node with the largest element from the binary search tree
     * @return the element of the removed node
     */
    @Override
    public T removeMax() {
        T result = null;
        if (findMax() != null) {
            result = removeElement(findMax());
        }
        return result;
    }

    /**
     * Returns a reference to the element with the smallest value in the binary
     * search tree. Throws a ElementNotFoundException if the binary search tree
     * is empty.
     *
     * @return a reference to the smallest element in the binary search tree
     * @throws ElementNotFoundException if an element not found exception occurs
     */
    @Override
    public T findMin() {
        if (isEmpty()) throw new ElementNotFoundException("árvore vazia");

        BinaryTreeNode<T> current = root;

        /**
         * O ciclo só testa a parte esquerda porque os elementos menores que 
         o root são inseridos na parte esquerda
         */
        while (current.getLeft() != null) {
            current = current.getLeft();
        }

        return current.getElement();
    }

    /**
     * Returns a reference to the element with the largest value in the binary
     * search tree. Throws a ElementNotFoundException if the binary search tree
     * is empty.
     *
     * @return a reference to the largest element in the binary search tree
     * @throws ElementNotFoundException if an element not found exception occurs
     */
    @Override
    public T findMax() {
        if (isEmpty()) throw new ElementNotFoundException("árvore vazia");

        BinaryTreeNode<T> current = root;

        while (current.getRight() != null) {
            current = current.getRight();
        }

        return current.getElement();
    }

}
