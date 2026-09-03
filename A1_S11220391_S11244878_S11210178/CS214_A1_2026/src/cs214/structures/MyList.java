package cs214.structures;

/**
 * MyList<T>
 * ---------
 * This is the "superclass" (interface) referred to in the assignment brief:
 * "Using the polymorphism technique with list data structure as the superclass
 *  wherever applicable...".
 *
 * Both MyArrayList<T> and MyLinkedList<T> implement this interface. Every
 * sorting algorithm in the cs214.sorting package is written ONCE against this
 * interface, and therefore works correctly on either concrete implementation
 * purely through polymorphism (dynamic dispatch on get/set/size/add).
 *
 * This is also what makes the algorithms generic enough to sort "a list or
 * array of any given user-defined data type" (T), not just University.
 */
public interface MyList<T> {

    /** Appends an item to the end of the list. */
    void add(T item);

    /** Returns the element at the given index. */
    T get(int index);

    /** Replaces the element at the given index. */
    void set(int index, T value);

    /** Number of elements currently stored. */
    int size();

    /** True if the list has no elements. */
    default boolean isEmpty() {
        return size() == 0;
    }
}
