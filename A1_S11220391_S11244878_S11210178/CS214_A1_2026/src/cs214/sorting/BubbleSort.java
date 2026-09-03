package cs214.sorting;

import cs214.structures.MyList;

/**
 * BubbleSort<T>
 * -------------
 * Classic bubble sort with the standard "no swaps this pass -> already
 * sorted, stop early" optimisation. Written once against MyList<T>, so this
 * single class satisfies both "Bubble sort with linked list" and
 * "Bubble sort with array list" from the brief (1b).
 *
 * Time complexity: O(n^2) worst/average case, O(n) best case (already sorted,
 * thanks to the early-exit flag).
 */
public class BubbleSort<T extends Comparable<T>> implements Sorter<T> {

    private long comparisons;
    private long swaps;

    @Override
    public void sort(MyList<T> list, Runnable onStep) {
        comparisons = 0;
        swaps = 0;
        int n = list.size();
        boolean swapped;

        // Each outer pass "bubbles" the largest remaining element to its
        // final position at the end of the unsorted region, so we shrink
        // the inner loop's range by one each time (n-1-i).
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                comparisons++;
                if (list.get(j).compareTo(list.get(j + 1)) > 0) {
                    // adjacent pair out of order -- swap them
                    T temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swaps++;
                    swapped = true;
                    onStep.run(); // report this mutation (used for animation/step-counting)
                }
            }
            // If a full pass made zero swaps, the list is already sorted --
            // stop early instead of doing the remaining (wasted) passes.
            if (!swapped) break;
        }
    }

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }

    @Override
    public long getOperationCount() {
        return comparisons + swaps;
    }

    @Override
    public String getName() {
        return "Bubble Sort";
    }
}
