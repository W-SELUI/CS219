package cs214.benchmark;

import cs214.model.University;
import cs214.sorting.*;
import cs214.structures.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * BenchmarkRunner
 * ---------------
 * For question 3: it run each algorithm 30 times and collect the results (using
 * counter values) to find the best, mean, median and worst solutions. 
 *
 * For every one of the 7 algorithm/structure combinations, this class:
 *  1. Makes a fresh copy of the base dataset into the right structure
 *     (MyArrayList or MyLinkedList),
 *  2. Randomises its order with a NEW Random draw every trial (so trial 1
 *     and trial 2 see genuinely different input orders, not the same
 *     shuffle twice),
 *  3. Times the sort and records the algorithm's operation counter,
 *  4. Repeats for `trials` runs (default 30) and hands back a
 *     BenchmarkResult with every raw measurement plus best/mean/median/worst.
 */
public class BenchmarkRunner {

    private final MyList<University> baseData;
    private final int trials;

    public BenchmarkRunner(MyList<University> baseData, int trials) {
        this.baseData = baseData;
        this.trials = trials;
    }

    // Runs all 7 required algorithm/structure combinations. 
    public List<BenchmarkResult> runAll() {
        List<BenchmarkResult> results = new ArrayList<>();
        results.add(runCombo(new InsertionSort<>(), "ArrayList", true));
        results.add(runCombo(new InsertionSort<>(), "LinkedList", false));
        results.add(runCombo(new BubbleSort<>(), "ArrayList", true));
        results.add(runCombo(new BubbleSort<>(), "LinkedList", false));
        results.add(runCombo(new MergeSort<>(), "ArrayList", true));
        results.add(runCombo(new MergeSort<>(), "LinkedList", false));
        results.add(runCombo(new BuiltInSort<>(), "ArrayList", true));
        return results;
    }

    private BenchmarkResult runCombo(Sorter<University> sorter, String structureName, boolean useArrayList) {
        double[] times = new double[trials];
        long[] ops = new long[trials];
        Random random = new Random(); // fresh entropy each combo run

        // JIT warm-up: one untimed sort so the JVM has a chance to JIT-compile
        // the hot loop before we start measuring. Without this, the very
        // first timed trial for a combination is often several times slower
        // than the rest purely from interpreter/compilation overhead, not
        // from the algorithm itself -- that would pollute the worst-case
        // figure with a measurement artifact rather than real algorithmic cost.
        MyList<University> warmup = useArrayList
                ? ListUtils.copyToArrayList(baseData)
                : ListUtils.copyToLinkedList(baseData);
        ListUtils.shuffle(warmup, random);
        sorter.sort(warmup);

        for (int t = 0; t < trials; t++) {
            MyList<University> trialData = useArrayList
                    ? ListUtils.copyToArrayList(baseData)
                    : ListUtils.copyToLinkedList(baseData);
            ListUtils.shuffle(trialData, random); // new random order every single trial

            long start = System.nanoTime();
            sorter.sort(trialData);
            long elapsedNanos = System.nanoTime() - start;

            times[t] = elapsedNanos / 1_000_000.0;
            ops[t] = sorter.getOperationCount();

            if (!ListUtils.isSorted(trialData)) {
                throw new IllegalStateException(
                        sorter.getName() + " on " + structureName + " produced an unsorted result on trial " + t);
            }
        }

        return new BenchmarkResult(sorter.getName(), structureName, times, ops);
    }
}
