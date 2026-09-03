package cs214.sorting;

import cs214.structures.MyList;

/**
 * Sorter<T>
 * ---------
 * Common interface implemented by every sorting algorithm in this project
 * (InsertionSort, BubbleSort, MergeSort, BuiltInSort).
 *
 * Each implementation sorts strictly through the MyList<T> interface
 * (get/set/size/add) -- never through concrete MyArrayList or MyLinkedList
 * methods. That is what allows a single InsertionSort object, for example,
 * to correctly sort both a MyArrayList<University> and a
 * MyLinkedList<University> without any change to its code: true
 * polymorphism over the underlying data structure.
 *
 * T extends Comparable<T> so the algorithms work for ANY user-defined type,
 * not just University 
 */
public interface Sorter<T extends Comparable<T>> {

    /** Sorts the given list in place, ascending, according to T's compareTo. */
    default void sort(MyList<T> list) {
        sort(list, () -> { });
    }

    /**
     * Sorts the given list in place, invoking onStep.run() after every
     * meaningful mutation (a set()/swap of the list's contents).
     *  Question 2's race visualiser uses it to animate
     * each algorithm's progress and to throttle speed for display.
     */
    void sort(MyList<T> list, Runnable onStep);

    /** Human-readable algorithm name, used for reporting / labelling results. */
    String getName();

    /**
     * Total number of counted primitive operations (comparisons + data
     * movements) performed by the most recent sort() call. Used by
     * Question 3's empirical benchmarking as a machine-independent counter
     * value, alongside wall-clock time. Returns -1 if this algorithm does
     * not expose a counter.
     */
    default long getOperationCount() {
        return -1;
    }
}
