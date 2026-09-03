package cs214.structures;

/**
 * MyLinkedList<T>
 * ---------------
 * A hand-written doubly linked list implementation of MyList<T>.
 *
 * get(index)/set(index, value) are provided so that the SAME sorting
 * algorithm code (written against MyList<T>) can run on this structure too.
 * To keep this honest, traversal starts from whichever end (head or tail)
 * is closer to the requested index, but a single get/set is still O(n) in
 * the worst case -- this cost difference vs. MyArrayList's O(1) access is
 * exactly the kind of thing Question 3/4 (empirical timing & worst-case
 * complexity) will expose.
 */
public class MyLinkedList<T> implements MyList<T> {

    /** A single doubly-linked node holding one element plus pointers to its neighbours. */
    private static class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;
        Node(T value) { this.value = value; }
    }

    private Node<T> head; // first node, or null if the list is empty
    private Node<T> tail; // last node -- kept so add() is O(1) instead of having to walk to the end
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(T item) {
        Node<T> node = new Node<>(item);
        if (tail == null) {
            // list was empty -- the new node is both the head and the tail
            head = node;
            tail = node;
        } else {
            // link the new node in after the current tail, then move tail to it
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public T get(int index) {
        return nodeAt(index).value; // O(n) worst case -- see nodeAt()
    }

    @Override
    public void set(int index, T value) {
        nodeAt(index).value = value; // O(n) worst case -- see nodeAt()
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Walks the list to find the node at `index`. Starts from whichever end
     * (head or tail) is closer, which roughly halves the average traversal
     * distance compared to always starting from head -- but a single call
     * is still O(n) in the worst case, unlike MyArrayList's O(1) get/set.
     */
    private Node<T> nodeAt(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < size / 2) {
            // index is in the first half -- walk forward from head
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            // index is in the second half -- walk backward from tail instead
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + size);
        }
    }
}
