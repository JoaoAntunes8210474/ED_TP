package collections.implementation;

/**
 * Sorting and searching algorithms for a single linked list
 * @param <T> the type of data stored in the list
 */
public class LinkedListSortingAndSearching<T> {

    //------------------------Algoritmos de pesquisa----------------------------

    /**
     * Searches a specified element on a single linked list
     * using a linear search algorithm.
     *
     * @param <T> the type of data stored in the list
     * @param head   beginning of single linked list
     * @param target the element being searched for
     * @return true if the desired element is found
     */
    public static <T extends Comparable<? super T>> boolean linearSearch(LinkedNode head, T target) {
        LinkedNode<T> current = head;
        boolean found = false;
        while (current != null && !found) {
            if (target.equals(current.getElement()))
                found = true;
            else {
                current = current.getNext();
            }
        }
        return found;
    }

/**
     * Searches a specified element on a single linked list
     * using a recursive linear search algorithm.
     *
     * @param <T> the type of data stored in the list
     * @param head   beginning of single linked list
     * @param target the element being searched for
     * @return true if the desired element is found
     */
    public static <T extends Comparable<? super T>> boolean RecursivelinearSearch(LinkedNode<T> head, T target) {
        if (head != null) {
            return false;
        }
        if (target.compareTo(head.getElement()) == 0) {
            return true;
        }
        return RecursivelinearSearch(head.getNext(), target);
    }

    /**
     * Function to find middle element
     *
     * @param head beginning of single linked list
     * @param tail ending of single linked list
     * @return middle element
     */
    public static LinkedNode<Integer> getMiddleNode(LinkedNode<Integer> head, LinkedNode<Integer> tail) {
        if (head == null) {
            return null;
        }

        LinkedNode<Integer> slow = head;
        LinkedNode<Integer> fast = head.getNext();

        while (fast != tail) {
            fast = fast.getNext();
            if (fast != tail) {
                slow = slow.getNext();
                fast = fast.getNext();
            }
        }
        return slow;
    }

    /**
     * function to insert a node at the beginning of the Singly Linked List
     *
     * @param head beginning of single linked list
     * @param value value to be inserted
     * @return head of the list
     */
    public static LinkedNode<Integer> binarySearch(LinkedNode<Integer> head, int value) {
        LinkedNode<Integer> start = head;
        LinkedNode<Integer> last = null;

        do {
            // Find Middle
            LinkedNode<Integer> mid = getMiddleNode(start, last);

            // If middle is empty
            if (mid == null)
                return null;

            // If value is present at middle
            if (mid.getElement() == value)
                return mid;

                // If value is less than mid
            else if (mid.getElement() > value) {
                start = mid.getNext();
            }

            // If the value is more than mid.
            else
                last = mid;
        } while (last == null || last != start);

        // value not present
        return null;
    }

    //------------------------Algoritmos de ordenação----------------------------

    /**
     * Sorts the specified element on a single linked list using the selection
     * sort algorithm.
     *
     * @param <T> the type of data stored in the list
     * @param data element to be sorted
     */
    public static <T extends Comparable<? super T>> void
    selectionSort(T data) {

    }

    /**
     * Sorts the specified array of objects using an insertion sort
     * algorithm.
     *
     * @param <T> the type of data stored in the list
     * @param data the array to be sorted
     */
    public static <T extends Comparable<? super T>> void
    insertionSort(T data) {

    }

    /**
     * Sorts the specified array of objects using a bubble sort
     * algorithm.
     *
     * @param <T> the type of data stored in the list
     * @param data the array to be sorted
     */
    public static <T extends Comparable<? super T>> void bubbleSort(T data) {

    }

    /**
     * Helper function for quickSort
     *
     * @param start beginning of single linked list
     * @param end ending of single linked list
     * @Return node previous to pivot
     */
    private static LinkedNode<Integer> paritionLast(LinkedNode<Integer> start, LinkedNode<Integer> end) {
        if (start == end || start == null || end == null)
            return start;

        LinkedNode<Integer> pivot_prev = start;
        LinkedNode<Integer> curr = start;
        int pivot = end.getElement();

        // iterate till one before the end,
        // no need to iterate till the end
        // because end is pivot
        while (start != end) {
            if (start.getElement() < pivot) {
                // keep tracks of last modified item
                pivot_prev = curr;
                int temp = curr.getElement();
                curr.setElement(start.getElement());
                start.setElement(temp);
                curr = curr.getNext();
            }
            start = start.getNext();
        }

        // swap the position of curr i.e.
        // next suitable index and pivot
        int temp = curr.getElement();
        curr.setElement(pivot);
        end.setElement(temp);

        // return one previous to current
        // because current is now pointing to pivot
        return pivot_prev;
    }

    /**
     * Sorts the specified array of objects using a quick sort
     * algorithm.
     *
     * @param <T> the type of data stored in the list
     * @param start beginning of single linked list
     * @param end ending of single linked list
     */
    public static <T extends Comparable<? super T>> void quickSort(LinkedNode<Integer> start, LinkedNode<Integer> end) {
        if (start == end)
            return;

        // split list and partion recurse
        LinkedNode<Integer> prev = paritionLast(start, end);
        quickSort(start, prev);

        // if pivot is picked and moved to the start,
        // that means start and pivot is same
        // so pick from next of pivot
        if (prev != null && prev == start)
            quickSort(prev.getNext(), end);

            // if pivot is in between of the list,
            // start from next of pivot,
            // since we have pivot_prev, so we move two nodes
        else if (prev != null &&
                prev.getElement() != null)
            quickSort(prev.getNext().getNext(), end);
    }

    /**
     * Sorts the specified array of objects using a merge sort
     * algorithm.
     *
     * @param a first linked node of the list
     * @param b second linked node of the list
     */
    private static LinkedNode<Integer> sortedMerge(LinkedNode<Integer> a, LinkedNode<Integer> b) {
        LinkedNode<Integer> result = null;
        /* Base cases */
        if (a == null)
            return b;
        if (b == null)
            return a;

        /* Pick either a or b, and recur */
        if (a.getElement() <= b.getElement()) {
            result = a;
            result.setNext(sortedMerge(a.getNext(), b));
        } else {
            result = b;
            result.setNext(sortedMerge(a, b.getNext()));
        }
        return result;
    }

    /**
     * Get the middle of the linked list
     * @param head of the linked list
     * @return middle of the linked list
     */
    public static LinkedNode getMiddle(LinkedNode head) {
        if (head == null)
            return head;

        LinkedNode slow = head, fast = head.getNext();

        while (fast != null && fast.getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext();
        }
        return slow;
    }

    /**
     * Sorts the specified array of objects using a merge sort
     * algorithm.
     *
     * @param <T> the type of data stored in the list
     * @param h head of the linked list
     * @return head of the linked list
     */
    public static <T extends Comparable<? super T>> LinkedNode mergeSort(LinkedNode h) {
        // Base case : if head is null
        if (h == null || h.getNext() == null) {
            return h;
        }

        // get the middle of the list
        LinkedNode middle = getMiddle(h);
        LinkedNode nextofmiddle = middle.getNext();

        // set the next of middle node to null
        middle.setNext(null);

        // Apply mergeSort on left list
        LinkedNode left = mergeSort(h);

        // Apply mergeSort on right list
        LinkedNode right = mergeSort(nextofmiddle);

        // Merge the left and right lists
        LinkedNode sortedlist = sortedMerge(left, right);
        return sortedlist;
    }
}
