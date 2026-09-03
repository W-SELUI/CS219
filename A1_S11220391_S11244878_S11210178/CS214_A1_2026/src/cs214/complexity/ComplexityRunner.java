package cs214.complexity;

import cs214.benchmark.BenchmarkResult;
import cs214.benchmark.BenchmarkRunner;
import cs214.model.University;
import cs214.sorting.BubbleSort;
import cs214.sorting.InsertionSort;
import cs214.sorting.Sorter;
import cs214.structures.ListUtils;
import cs214.structures.MyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ComplexityRunner
 * ----------------
 *
 * Deliberately reuses Question 3's BenchmarkRunner as-is (rather than
 * duplicating its trial/shuffle/verification logic) — for each input size
 * `n` in `sizes`, it draws a fresh random sample of `n` universities and
 * hands it to a BenchmarkRunner for `trialsPerSize` randomised trials. The
 * resulting best/mean/median/worst statistics at each `n` are what let us
 * trace out a growth curve (n vs. worst-case time) per algorithm/structure.
 *
 * Two complementary worst-case measurements are produced:
 *  1. Empirical worst-case: the slowest of several *randomly ordered*
 *     trials at each size.
 *  2. Constructed worst-case: for Insertion Sort and Bubble Sort
 *     specifically, a single run on a deliberately reverse-sorted (strictly
 *     descending) input — their true theoretical worst case — included for
 *     comparison against the empirical figures.
 */
public class ComplexityRunner {

    // Empirical worst-case: several randomised trials at each size. 
    public List<ComplexitySample> runEmpirical(MyList<University> fullData, int[] sizes, int trialsPerSize) {
        List<ComplexitySample> samples = new ArrayList<>();
        Random selector = new Random();

        for (int n : sizes) {
            MyList<University> sample = ListUtils.sampleOfSize(fullData, n, selector);
            BenchmarkRunner runner = new BenchmarkRunner(sample, trialsPerSize);
            List<BenchmarkResult> results = runner.runAll();
            for (BenchmarkResult r : results) {
                samples.add(new ComplexitySample(n, r));
            }
        }
        return samples;
    }

    /**
     * Constructed worst-case: Insertion Sort and Bubble Sort on a
     * deliberately descending-order input, one run per size.
     * Returns a BenchmarkResult-shaped single-trial result so it can reuse
     * the same reporting/plotting code as the empirical results.
     */
    public List<ComplexitySample> runConstructedWorstCase(MyList<University> fullData, int[] sizes) {
        List<ComplexitySample> samples = new ArrayList<>();

        for (int n : sizes) {
            samples.add(timeSingleRun("Insertion Sort", new InsertionSort<University>(), fullData, n, true));
            samples.add(timeSingleRun("Insertion Sort", new InsertionSort<University>(), fullData, n, false));
            samples.add(timeSingleRun("Bubble Sort", new BubbleSort<University>(), fullData, n, true));
            samples.add(timeSingleRun("Bubble Sort", new BubbleSort<University>(), fullData, n, false));
        }
        return samples;
    }

    private ComplexitySample timeSingleRun(String algorithmName, Sorter<University> sorter,
                                            MyList<University> fullData, int n, boolean useArrayList) {
        MyList<University> descending = ListUtils.sampleDescending(fullData, n);
        MyList<University> trialData = useArrayList
                ? ListUtils.copyToArrayList(descending)
                : ListUtils.copyToLinkedList(descending);

        long start = System.nanoTime();
        sorter.sort(trialData);
        long elapsedNanos = System.nanoTime() - start;

        if (!ListUtils.isSorted(trialData)) {
            throw new IllegalStateException(algorithmName + " failed to sort the constructed worst-case input.");
        }

        double[] times = { elapsedNanos / 1_000_000.0 };
        long[] ops = { sorter.getOperationCount() };
        String structureName = useArrayList ? "ArrayList" : "LinkedList";
        return new ComplexitySample(n, new BenchmarkResult(algorithmName, structureName, times, ops));
    }
}
