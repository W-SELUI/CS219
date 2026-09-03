package cs214.structures;

import java.util.Random;

/**
 * ListUtils
 * ---------
 * Small helper methods used to test the sorting algorithms and (later,
 * Question 3) to randomise input order between runs. Operates purely
 * through the MyList<T> interface so it works for any implementation.
 */
public final class ListUtils {

    private ListUtils() {}

    /** Fisher–Yates shuffle, in place, through the MyList<T> interface only. */
    public static <T> void shuffle(MyList<T> list, Random random) {
        // Walk backwards from the last index; for each position i, swap it
        // with a uniformly-random earlier-or-equal position j. This is the
        // standard Fisher-Yates algorithm and produces every permutation
        // with equal probability.
        for (int i = list.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            T temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }
    }

    /** Returns true if the list is sorted ascending according to compareTo. */
    public static <T extends Comparable<T>> boolean isSorted(MyList<T> list) {
        // A list is sorted ascending if every element is <= the one after it.
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).compareTo(list.get(i)) > 0) {
                return false; // found a pair out of order
            }
        }
        return true;
    }

    /** Copies all elements of source into a brand new MyArrayList. */
    public static <T> MyList<T> copyToArrayList(MyList<T> source) {
        MyArrayList<T> copy = new MyArrayList<>(Math.max(source.size(), 1));
        for (int i = 0; i < source.size(); i++) {
            copy.add(source.get(i));
        }
        return copy;
    }

    /** Copies all elements of source into a brand new MyLinkedList. */
    public static <T> MyList<T> copyToLinkedList(MyList<T> source) {
        MyLinkedList<T> copy = new MyLinkedList<>();
        for (int i = 0; i < source.size(); i++) {
            copy.add(source.get(i));
        }
        return copy;
    }

    /**
     * Returns a freshly-shuffled random sample of `n` elements from source,
     * as a new MyArrayList. Used by Question 4 to build progressively
     * larger (or smaller) input sizes from the full dataset while keeping
     * the *selection* of records random each time, not just their order.
     */
    public static <T> MyList<T> sampleOfSize(MyList<T> source, int n, Random random) {
        // Shuffle a full copy first, then just take the first `n` -- this
        // makes the *set* of chosen records random too, not only their order.
        MyList<T> shuffledCopy = copyToArrayList(source);
        shuffle(shuffledCopy, random);
        int limit = Math.min(n, shuffledCopy.size());
        MyArrayList<T> sample = new MyArrayList<>(limit);
        for (int i = 0; i < limit; i++) {
            sample.add(shuffledCopy.get(i));
        }
        return sample;
    }

    /**
     * Builds a list already in strictly descending order (the true
     * worst-case input for Insertion Sort and Bubble Sort, which are both
     * comparison/swap-count-driven and maximise their work on
     * reverse-sorted data). Used by Question 4 alongside empirical
     * randomised worst-case measurements.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> MyList<T> sampleDescending(MyList<T> source, int n) {
        MyList<T> ascendingSample = copyToArrayList(source);
        int limit = Math.min(n, ascendingSample.size());

        // Sort a plain array descending (once, untimed -- this is just test
        // setup, not part of the algorithm being measured).
        Object[] arr = new Object[limit];
        for (int i = 0; i < limit; i++) arr[i] = ascendingSample.get(i);
        java.util.Arrays.sort(arr, new java.util.Comparator<Object>() {
            @Override
            public int compare(Object x, Object y) {
                return ((T) y).compareTo((T) x); // reversed operand order => descending
            }
        });

        MyArrayList<T> descending = new MyArrayList<>(limit);
        for (Object o : arr) descending.add((T) o);
        return descending;
    }
}
