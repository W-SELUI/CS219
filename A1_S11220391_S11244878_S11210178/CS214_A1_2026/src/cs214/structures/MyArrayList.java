package cs214.structures;

/**
 * MyArrayList<T>
 * --------------
 * A hand-written, resizable array-backed implementation of MyList<T>.
 * Deliberately built from scratch (rather than wrapping java.util.ArrayList)
 * so that the underlying "array" data structure and its cost characteristics
 * (O(1) get/set, amortised O(1) add, doubling growth) are genuinely our own.
 */
@SuppressWarnings("unchecked")
public class MyArrayList<T> implements MyList<T> {

    private Object[] data;  // backing array; may have unused capacity beyond `size`
    private int size;       // number of elements actually stored (<= data.length)

    private static final int DEFAULT_CAPACITY = 16;

    /** Creates an empty list with a small default backing array. */
    public MyArrayList() {
        data = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /** Creates an empty list, pre-sizing the backing array to avoid early resizes. */
    public MyArrayList(int initialCapacity) {
        data = new Object[Math.max(initialCapacity, 1)];
        size = 0;
    }

    @Override
    public void add(T item) {
        ensureCapacity(size + 1); // grow the backing array first if it's full
        data[size] = item;
        size++;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return (T) data[index]; // O(1) -- direct array indexing, no traversal needed
    }

    @Override
    public void set(int index, T value) {
        checkIndex(index);
        data[index] = value; // O(1) -- direct array indexing
    }

    @Override
    public int size() {
        return size;
    }

    /** Doubles the backing array's capacity once it's full, copying existing elements across. */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity <= data.length) return; // already enough room, nothing to do
        int newCapacity = data.length * 2;
        if (newCapacity < minCapacity) newCapacity = minCapacity; // in case doubling still isn't enough
        Object[] newData = new Object[newCapacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + size);
        }
    }
}
