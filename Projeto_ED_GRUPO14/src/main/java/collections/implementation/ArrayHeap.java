
package collections.implementation;
import collections.exceptions.EmptyCollectionException;
import collections.interfaces.HeapADT;

/**
 *
 * @param <T>
 */
public class ArrayHeap<T> extends ArrayBinaryTree<T> implements HeapADT<T> {

    public ArrayHeap() {
        super();
    }

    /**
     * Adds the specified element to this heap in the appropriate position
     * according to its key value. Note that equal elements are added to the
     * right.
     *
     * @param obj the element to be added to this heap
     */
    @Override
    public void addElement(T obj) {
        if (count == size()) {
            expandCapacity();
        }

        tree[count] = obj;
        count++;

        if (count > 1) {
            heapifyAdd();
        }
    }

    private void expandCapacity() {
        T[] temp = (T[]) new Object[tree.length * 2];
        for (int ct = 0; ct < tree.length; ct++) {
            temp[ct] = tree[ct];
        }
        tree = temp;
    }

    /**
     * Reorders this heap to maintain the ordering property after adding a node.
     */
    private void heapifyAdd() {
        T temp;
        int next = count - 1;

        while ((next != 0) && (((Comparable<T>)tree[next]).compareTo(tree[(next - 1) / 2]) < 0)) {
            temp = tree[next];
            tree[next] = tree[(next - 1) / 2];
            tree[(next - 1) / 2] = temp;
            next = (next - 1) / 2;
        }
    }

    /**
     * Remove the element with the lowest value in this heap and returns a
     * reference to it. Throws an EmptyCollectionException if the heap is empty.
     *
     * @return a reference to the element with the lowest value in this head
     * @throws EmptyCollectionException if an empty collection exception occurs
     */
    @Override
    public T removeMin() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("Empty Heap");
        T minElement = tree[0];
        tree[0] = tree[count - 1];
        tree[count - 1] = null;
        
        count--;
        heapifyRemove();

        return minElement;
    }

    /**
     * Reorders this heap to maintain the ordering property.
     */
    private void heapifyRemove() {
        // declaração de variáveis auxiliares
        
        // nó para fazer o swap dos índices
        T temp;
        
        // raiz da heap
        int node = 0;
        // filho esquerdo da heap 
        int left = 1;
        // filho direito da heap
        int right = 2;
        // elemento próximo da heap
        int next;
        
        // Se a heap não tiver filhos o próximo elemento fica igual ao número de nós 
        if ( (tree[left] == null) && (tree[right] == null) ) {
            next = count;
        } else if (tree[left] == null) {
            next = right;
        } else if (tree[right] == null) {
            next = left;
        } else if (((Comparable<T>)tree[left]).compareTo(tree[right]) < 0) {
            next = left;
        } else {
            next = right;
        }
        //temp = tree[node]; // valor do último nó
        
        while ((next < count) && (((Comparable<T>)tree[next]).compareTo(tree[node]) < 0)) {
            temp = tree[node];
            //Substitui-se o último nó pelo nó menor
            tree[node] = tree[next];
            tree[next] = temp;
            //System.out.println("Troquei a " + tree[node] + " com " + tree[next]);
            node = next;
            left = 2 * node + 1;
            right = 2 * (node + 1);
            if ((tree[left] == null) && (tree[right] == null)) {
                next = count;
            } else if (tree[left] == null) {
                next = right;
            } else if (tree[right] == null) {
                next = left;
            } else if (((Comparable<T>)tree[left]).compareTo(tree[right]) < 0) {
                next = left;
            } else {
                next = right;
            }
            //tree[node] = temp;
        }
    }

    @Override
    public T findMin() throws EmptyCollectionException {
        if(isEmpty()) throw new EmptyCollectionException("A lista está vazia");
        return tree[0];
    }
}
