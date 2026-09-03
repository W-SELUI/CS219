package cs214.benchmark;

import cs214.io.CSVLoader;
import cs214.model.University;
import cs214.structures.MyArrayList;
import cs214.structures.MyList;

import java.util.List;

/**
 * BenchmarkMain
 * -------------
 * Runs each of the 7 algorithm/structure
 * combinations 30 times against the full dataset, with the data order
 * re-randomised on every single trial, and reports best/mean/median/worst
 * time plus operation counts.
 */
public class BenchmarkMain {

    private static final String CSV_PATH = "data/World_University_Rankings_2023-Cleaned.csv";
    private static final int DEFAULT_TRIALS = 30;

    public static void main(String[] args) throws Exception {
        int trials = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_TRIALS;

        System.out.println("=== CS214 Assignment 1 - Question 3: Empirical Benchmarking ===");
        System.out.println("Loading dataset: " + CSV_PATH);

        MyList<University> baseData = new MyArrayList<>();
        int count = new CSVLoader().load(CSV_PATH, baseData);
        System.out.println("Loaded " + count + " university records.");
        System.out.println("Running " + trials + " randomised trials per algorithm/structure combination...\n");

        long overallStart = System.currentTimeMillis();
        BenchmarkRunner runner = new BenchmarkRunner(baseData, trials);
        List<BenchmarkResult> results = runner.runAll();
        long overallSeconds = (System.currentTimeMillis() - overallStart) / 1000;

        printTable(results);
        printFastest(results);

        BenchmarkReportWriter.writeRawTrials(results, "results/benchmark_raw_trials.csv");
        BenchmarkReportWriter.writeSummary(results, "results/benchmark_summary.csv");
        System.out.println("\nWrote results/benchmark_raw_trials.csv and results/benchmark_summary.csv");
        System.out.println("(Question 4 reuses the raw-trials file for its worst-case complexity graphs.)");
        System.out.println("Total benchmarking time: " + overallSeconds + "s");
    }

    private static void printTable(List<BenchmarkResult> results) {
        System.out.printf("%-35s %10s %10s %10s %10s %12s%n",
                "Algorithm (Structure)", "Best(ms)", "Mean(ms)", "Median(ms)", "Worst(ms)", "Mean Ops");
        StringBuilder divider = new StringBuilder();
        for (int i = 0; i < 95; i++) divider.append('-');
        System.out.println(divider);
        for (BenchmarkResult r : results) {
            String opsDisplay = r.hasOperationCounts() ? String.format("%,.0f", r.meanOps()) : "n/a (black box)";
            System.out.printf("%-35s %10.3f %10.3f %10.3f %10.3f %12s%n",
                    r.getLabel(), r.bestTimeMs(), r.meanTimeMs(), r.medianTimeMs(), r.worstTimeMs(), opsDisplay);
        }
    }

    private static void printFastest(List<BenchmarkResult> results) {
        BenchmarkResult fastest = results.get(0);
        for (BenchmarkResult r : results) {
            if (r.meanTimeMs() < fastest.meanTimeMs()) fastest = r;
        }
        System.out.println("\n>>> Fastest algorithm by mean empirical time: " + fastest.getLabel()
                + String.format(" (%.3fms mean over %d trials)", fastest.meanTimeMs(), fastest.timesMs.length));
    }
}
