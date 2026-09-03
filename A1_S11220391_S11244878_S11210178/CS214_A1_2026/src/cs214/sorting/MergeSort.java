package cs214.sorting;

import cs214.structures.MyList;

/**
 * MergeSort<T>
 * ------------
 * Classic top-down merge sort, written once against MyList<T>. Reads and
 * writes only go through get(index)/set(index, value)
 *
 * A small Object[] scratch buffer is used for the merge step -- this is our
 * own array, not a call into java.util's sorting logic (that is reserved
 * for BuiltInSort).
 *
 * Time complexity: O(n log n) in all cases (best, average, worst).
 */
@SuppressWarnings("unchecked")
public class MergeSort<T extends Comparable<T>> implements Sorter<T> {

    private long comparisons;

    @Override
    public void sort(MyList<T> list, Runnable onStep) {
        comparisons = 0;
        int n = list.size();
        if (n < 2) return; // 0 or 1 elements are already sorted -- nothing to do
        Object[] buffer = new Object[n]; // scratch space reused by every merge() call, sized once
        mergeSort(list, buffer, 0, n - 1, onStep);
    }

    // Recursively splits [left, right] in half until each half is a single element, then merges back up.
    private void mergeSort(MyList<T> list, Object[] buffer, int left, int right, Runnable onStep) {
        if (left >= right) return; // base case: 0 or 1 elements in this range, already "sorted"
        int mid = left + (right - left) / 2; // avoids overflow vs (left+right)/2 for very large lists
        mergeSort(list, buffer, left, mid, onStep);      // sort the left half
        mergeSort(list, buffer, mid + 1, right, onStep); // sort the right half
        merge(list, buffer, left, mid, right, onStep);   // combine the two sorted halves
    }

    // Merges the two already-sorted sub-ranges [left, mid] and [mid+1, right] back into one sorted range. 
    private void merge(MyList<T> list, Object[] buffer, int left, int mid, int right, Runnable onStep) {
        // Copy the current range out to the scratch buffer first, since we're
        // about to overwrite `list` in place and need the original values.
        for (int k = left; k <= right; k++) {
            buffer[k] = list.get(k);
        }

        int i = left;      // read-pointer into the left half of the buffer
        int j = mid + 1;   // read-pointer into the right half of the buffer
        int k = left;      // write-pointer back into the list

        // Repeatedly take the smaller of the two "next" elements from each half.
        while (i <= mid && j <= right) {
            comparisons++;
            T leftVal = (T) buffer[i];
            T rightVal = (T) buffer[j];
            if (leftVal.compareTo(rightVal) <= 0) {
                list.set(k++, leftVal);
                i++;
            } else {
                list.set(k++, rightVal);
                j++;
            }
            onStep.run(); // report this mutation (used for animation/step-counting)
        }
        // One half may still have leftover elements once the other is
        // exhausted -- they're already sorted, so just copy them across as-is.
        while (i <= mid) {
            list.set(k++, (T) buffer[i++]);
            onStep.run();
        }
        while (j <= right) {
            list.set(k++, (T) buffer[j++]);
            onStep.run();
        }
    }

    public long getComparisons() { return comparisons; }

    @Override
    public long getOperationCount() {
        return comparisons;
    }

    @Override
    public String getName() {
        return "Merge Sort";
    }
}
