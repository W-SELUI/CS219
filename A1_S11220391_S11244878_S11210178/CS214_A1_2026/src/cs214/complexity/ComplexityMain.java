package cs214.complexity;

import cs214.benchmark.BenchmarkResult;
import cs214.io.CSVLoader;
import cs214.model.University;
import cs214.structures.MyArrayList;
import cs214.structures.MyList;

import java.util.*;

/**
 * ComplexityMain
 * --------------
 * Runs the full multi-size
 * empirical benchmark, the constructed-worst-case benchmark for Insertion
 * and Bubble Sort, writes both to CSV, and prints a growth-ratio analysis:
 * for each doubling of n, does worst-case time roughly x4 (consistent with
 * O(n^2)) or grow much more slowly (consistent with O(n log n))?
 *
 * This is what to run on a machine without a display, or to regenerate the
 * CSV data that ComplexityChartFrame's GUI plots.
 *
 */
public class ComplexityMain {

    private static final String CSV_PATH = "data/World_University_Rankings_2023-Cleaned.csv";
    private static final int[] DEFAULT_SIZES = {100, 300, 500, 700, 900, 1100, 1300, 1500, 1697};
    private static final int[] QUICK_SIZES = {100, 400, 800, 1600};
    private static final int DEFAULT_TRIALS = 5;
    private static final int QUICK_TRIALS = 2;

    public static void main(String[] args) throws Exception {
        boolean quick = args.length > 0 && args[0].equalsIgnoreCase("quick");
        int[] sizes = quick ? QUICK_SIZES : DEFAULT_SIZES;
        int trials = quick ? QUICK_TRIALS : DEFAULT_TRIALS;

        System.out.println("=== CS214 Assignment 1 - Question 4: Worst-Case Complexity Analysis ===");
        System.out.println("Loading dataset: " + CSV_PATH);

        MyList<University> fullData = new MyArrayList<>();
        int count = new CSVLoader().load(CSV_PATH, fullData);
        System.out.println("Loaded " + count + " university records.");
        System.out.println("Sizes: " + Arrays.toString(sizes) + " | trials per size: " + trials + "\n");

        long overallStart = System.currentTimeMillis();

        ComplexityRunner runner = new ComplexityRunner();
        List<ComplexitySample> empirical = runner.runEmpirical(fullData, sizes, trials);
        List<ComplexitySample> constructed = runner.runConstructedWorstCase(fullData, sizes);

        long overallSeconds = (System.currentTimeMillis() - overallStart) / 1000;

        ComplexityReportWriter.write(empirical, "results/complexity_empirical.csv");
        ComplexityReportWriter.write(constructed, "results/complexity_constructed_worstcase.csv");

        printGrowthTable(empirical);
        printGrowthRatioAnalysis(empirical);
        printConstructedVsEmpirical(empirical, constructed);

        System.out.println("\nWrote results/complexity_empirical.csv and results/complexity_constructed_worstcase.csv");
        System.out.println("Total analysis time: " + overallSeconds + "s");
    }

    private static void printGrowthTable(List<ComplexitySample> samples) {
        Map<String, List<ComplexitySample>> byLabel = groupByLabel(samples);
        System.out.println("--- Worst-case time (ms) by input size ---");
        for (Map.Entry<String, List<ComplexitySample>> entry : byLabel.entrySet()) {
            System.out.println(entry.getKey() + ":");
            for (ComplexitySample s : entry.getValue()) {
                System.out.printf("   n=%-5d worst=%10.3fms  mean=%10.3fms  worstOps=%s%n",
                        s.n, s.result.worstTimeMs(), s.result.meanTimeMs(),
                        s.result.hasOperationCounts() ? String.format("%,.0f", s.result.worstOps()) : "n/a");
            }
        }
    }

    /**
     * For each series, compares worst-case time at each n to the previous
     * (smaller) n, printing the ratio. A ratio near 4x per doubling points
     * to O(n^2); a ratio only slightly more than 2x per doubling points to
     * O(n log n).
     */
    private static void printGrowthRatioAnalysis(List<ComplexitySample> samples) {
        System.out.println("\n--- Growth ratio analysis (how much slower as n grows) ---");
        Map<String, List<ComplexitySample>> byLabel = groupByLabel(samples);
        for (Map.Entry<String, List<ComplexitySample>> entry : byLabel.entrySet()) {
            List<ComplexitySample> points = entry.getValue();
            System.out.println(entry.getKey() + ":");
            for (int i = 1; i < points.size(); i++) {
                ComplexitySample prev = points.get(i - 1);
                ComplexitySample curr = points.get(i);
                double nRatio = curr.n / (double) prev.n;
                double timeRatio = prev.result.worstTimeMs() > 0
                        ? curr.result.worstTimeMs() / prev.result.worstTimeMs() : Double.NaN;
                System.out.printf("   n:%d->%d (x%.2f)   time ratio: x%.2f%n",
                        prev.n, curr.n, nRatio, timeRatio);
            }
        }
        System.out.println("\n   Reading this: if input size roughly doubles (x2.0) and the time ratio is");
        System.out.println("   close to x4, that is consistent with O(n^2). If the time ratio is closer");
        System.out.println("   to x2-x2.5, that is consistent with O(n log n).");
    }

    private static void printConstructedVsEmpirical(List<ComplexitySample> empirical, List<ComplexitySample> constructed) {
        System.out.println("\n--- Constructed worst-case (strictly descending input) vs empirical worst-case ---");
        Map<String, ComplexitySample> empByKey = new HashMap<>();
        for (ComplexitySample s : empirical) {
            empByKey.put(s.n + "|" + s.getLabel(), s);
        }
        for (ComplexitySample c : constructed) {
            ComplexitySample e = empByKey.get(c.n + "|" + c.getLabel());
            if (e == null) continue;
            System.out.printf("   %-28s n=%-5d constructed=%10.3fms   empirical-worst=%10.3fms%n",
                    c.getLabel(), c.n, c.result.worstTimeMs(), e.result.worstTimeMs());
        }
    }

    private static Map<String, List<ComplexitySample>> groupByLabel(List<ComplexitySample> samples) {
        Map<String, List<ComplexitySample>> byLabel = new LinkedHashMap<>();
        for (ComplexitySample s : samples) {
            byLabel.computeIfAbsent(s.getLabel(), k -> new ArrayList<>()).add(s);
        }
        for (List<ComplexitySample> list : byLabel.values()) {
            list.sort(Comparator.comparingInt(a -> a.n));
        }
        return byLabel;
    }
}
