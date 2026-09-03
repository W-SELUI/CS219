package cs214.benchmark;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * BenchmarkReportWriter
 * ----------------------
 * Writes the raw per-trial data and the summary statistics to CSV files so
 * they can be graphed (Question 4 will reuse this exact raw-trial CSV for
 * the worst-case complexity charts.
 */
public class BenchmarkReportWriter {

    // One row per trial per combination -- the full raw dataset. 
    public static void writeRawTrials(List<BenchmarkResult> results, String path) throws IOException {
        ensureParentDirExists(path);
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.println("Algorithm,Structure,Trial,TimeMs,OperationCount");
            for (BenchmarkResult r : results) {
                for (int t = 0; t < r.timesMs.length; t++) {
                    out.printf("%s,%s,%d,%.4f,%d%n",
                            r.algorithmName, r.structureName, t + 1, r.timesMs[t], r.operationCounts[t]);
                }
            }
        }
    }

    // One row per combination -- the best/mean/median/worst summary. 
    public static void writeSummary(List<BenchmarkResult> results, String path) throws IOException {
        ensureParentDirExists(path);
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.println("Algorithm,Structure,BestMs,MeanMs,MedianMs,WorstMs,StdDevMs,BestOps,MeanOps,MedianOps,WorstOps");
            for (BenchmarkResult r : results) {
                out.printf("%s,%s,%.4f,%.4f,%.4f,%.4f,%.4f,%.1f,%.1f,%.1f,%.1f%n",
                        r.algorithmName, r.structureName,
                        r.bestTimeMs(), r.meanTimeMs(), r.medianTimeMs(), r.worstTimeMs(), r.stdDevTimeMs(),
                        r.bestOps(), r.meanOps(), r.medianOps(), r.worstOps());
            }
        }
    }

    private static void ensureParentDirExists(String path) {
        File file = new File(path);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }
}
