package cs214.sorting;

import cs214.structures.MyList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BuiltInSort<T>
 * --------------
 * Extracts the elements out through
 * MyList<T>'s interface into a temporary java.util.ArrayList, sorts with
 * java.util.Collections.sort (a highly-optimised Timsort), then writes the
 * result back through the same MyList<T> interface.
 *
 * Time complexity: O(n log n) worst case (Timsort).
 */
public class BuiltInSort<T extends Comparable<T>> implements Sorter<T> {

    @Override
    public void sort(MyList<T> list, Runnable onStep) {
        int n = list.size();
        List<T> temp = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            temp.add(list.get(i));
        }
        /* Collections.sort (Timsort) is a black box internally, so we can't
        * report a step per internal comparison/merge the way the other
        *  algorithms do. We instead animate the final write-back, which is
        *  the only part of the process visible through the MyList interface.
        */
        Collections.sort(temp);
        for (int i = 0; i < n; i++) {
            list.set(i, temp.get(i));
            onStep.run();
        }
    }

    @Override
    public String getName() {
        return "Built-in Sort (Collections.sort)";
    }
}
