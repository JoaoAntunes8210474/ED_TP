
package collections.implementation;

import collections.exceptions.ElementNotFoundException;
import collections.exceptions.EmptyCollectionException;
import java.util.Iterator;
import collections.interfaces.BinaryTreeADT;



public class ArrayBinaryTree<T> implements BinaryTreeADT<T> {
    protected int count;
    protected T[] tree;
    private final int CAPACITY = 50;
   
    /**
    * Creates an empty binary tree.
    */
    public ArrayBinaryTree() {
        this.count = 0;
        this.tree = (T[])(new Object[CAPACITY]);
    }
   
    /**
    * Creates a binary tree with the specified element as its root.
    *
    * @param element the element which will become the root of the new tree
    */
    public ArrayBinaryTree (T element) {
        this.count = 1;
        this.tree = (T[])(new Object[CAPACITY]);
        this.tree[0] = element;
    }

    @Override
    public T getRoot() throws EmptyCollectionException {
        if(isEmpty()) throw new EmptyCollectionException("A lista está vazia");
        return this.tree[0];
    }

    @Override
    public boolean isEmpty() {
        return (this.count == 0);
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public boolean contains(T targetElement) {
        boolean found = false;
        int i = 0;
        while (i < this.count && !found) {
            if (targetElement.equals(tree[i])) {
                found = true;
            }
            i++;
        }
        return found;
    }
    
    /**
    * Returns a reference to the specified target element if it is
    * found in this binary tree. Throws a NoSuchElementException if
    * the specified target element is not found in the binary tree.
    *
    * @param targetElement the element being sought in the tree
    * @return true if the element is in the tree
    * @throws ElementNotFoundException if an element not found
    * exception occurs
    */
    public T find (T targetElement) throws ElementNotFoundException {
        T temp = null;
        boolean found = false;
      
        for (int ct = 0; ct<this.count && !found; ct++) {
            if (targetElement.equals(tree[ct])) {
                found = true;
            }
            temp = tree[ct];
        }
        if (!found) throw new ElementNotFoundException("binary tree");
        
        return temp;
    }

    @Override
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();
        inorder(0, tempList);
        return tempList.iterator();
    }
    
    protected void inorder (int node, ArrayUnorderedList<T> tempList) {
        if (node < count) {
            if (tree[node] != null) {
                inorder ((node+1)*2-1, tempList);
                tempList.addToRear(tree[node]);
                inorder ((node+1)*(2+1)-1, tempList);
            }
        }
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<>();
        preorder(0, templist);
        return templist.iterator();
    }
    
    protected void preorder (int node, ArrayUnorderedList<T> templist) {
        if (node < count) {
            if (tree[node] != null) {
                templist.addToRear(tree[node]);
                preorder (node*2+1, templist); 
                preorder ((node+1)*2, templist);
            }
        }
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<>();
        postorder(0, templist);
        return templist.iterator();
    }
    
    protected void postorder (int node, ArrayUnorderedList<T> templist) {
        if (node < count) {
            if (tree[node] != null) {         
                postorder (node*2+1, templist); 
                postorder ((node+1)*2, templist);
                templist.addToRear(tree[node]);
            }
        }
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();
        levelorder(0, tempList);

        return tempList.iterator();
    }
    
    protected void levelorder(int root, ArrayUnorderedList<T> results) {
        // Queue de índices da árvore
        CircularArrayQueue<Integer> nodes = new CircularArrayQueue<>();
        int temp;
        nodes.enqueue(root);
        while (!nodes.isEmpty()) {
            try {
               temp = nodes.dequeue();
               if (tree[temp] != null) {
                    results.addToRear(tree[temp]);

                } else {
                    results.addToRear(null);
                }

                if (tree[temp*2+1] != null) {
                    nodes.enqueue(temp*2+1);
                }
                if (tree[(temp+1)*2] != null) {
                    nodes.enqueue(temp*2+2);
                }
                
            } catch (EmptyCollectionException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
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
