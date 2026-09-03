package cs214.sorting;

import cs214.structures.MyList;

/**
 * InsertionSort<T>
 * ----------------
 * Classic insertion sort, written once against MyList<T>. Because
 * MyArrayList and MyLinkedList both implement MyList<T>, this single class
 *
 * Time complexity: O(n^2) worst/average case, O(n) best case (nearly sorted).
 */
public class InsertionSort<T extends Comparable<T>> implements Sorter<T> {

    private long comparisons;
    private long shifts;

    @Override
    public void sort(MyList<T> list, Runnable onStep) {
        comparisons = 0;
        shifts = 0;
        int n = list.size();

        // Start from the second element (index 1) - a single-element list is
        // trivially "sorted", so there's nothing to insert on the first pass.
        for (int i = 1; i < n; i++) {
            T key = list.get(i);   // the element we're about to insert into the sorted-so-far region [0..i-1]
            int j = i - 1;

            // Shift every element bigger than 'key' one position to the right,
            // opening up a gap for 'key' to drop into once we find its place.
            while (j >= 0) {
                comparisons++;
                if (list.get(j).compareTo(key) > 0) {
                    list.set(j + 1, list.get(j)); // shift the bigger element right
                    shifts++;
                    onStep.run(); // report this mutation (used for animation/step-counting)
                    j--;
                } else {
                    break; // found key's correct position -- everything before j is already <= key
                }
            }
            list.set(j + 1, key); // drop 'key' into the gap we just opened
            onStep.run();
        }
    }

    public long getComparisons() { return comparisons; }
    public long getShifts() { return shifts; }

    @Override
    public long getOperationCount() {
        return comparisons + shifts;
    }

    @Override
    public String getName() {
        return "Insertion Sort";
    }
}
