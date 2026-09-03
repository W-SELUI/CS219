package cs214.complexity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * ComplexityReportWriter
 * -----------------------
 * Writes n-vs-time/operations CSV data — one row per (size, algorithm,
 * structure) — so growth curves can be plotted. This is what ComplexityChartFrame's GUI plots.
 */
public class ComplexityReportWriter {

    public static void write(List<ComplexitySample> samples, String path) throws IOException {
        ensureParentDirExists(path);
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.println("N,Algorithm,Structure,BestMs,MeanMs,MedianMs,WorstMs,BestOps,MeanOps,MedianOps,WorstOps");
            for (ComplexitySample s : samples) {
                out.printf("%d,%s,%s,%.4f,%.4f,%.4f,%.4f,%.1f,%.1f,%.1f,%.1f%n",
                        s.n, s.result.algorithmName, s.result.structureName,
                        s.result.bestTimeMs(), s.result.meanTimeMs(), s.result.medianTimeMs(), s.result.worstTimeMs(),
                        s.result.bestOps(), s.result.meanOps(), s.result.medianOps(), s.result.worstOps());
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
