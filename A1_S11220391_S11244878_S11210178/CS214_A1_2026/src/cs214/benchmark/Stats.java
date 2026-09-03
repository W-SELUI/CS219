package cs214.benchmark;

import java.util.Arrays;

/**
 * Stats
 * -----
 * Tiny statistics helper used to turn 30 raw trial measurements into the
 * best / mean / median / worst summary Question 3 asks for.
 */
public final class Stats {

    private Stats() {}

    public static double min(double[] values) {
        double m = Double.MAX_VALUE;
        for (double v : values) if (v < m) m = v;
        return m;
    }

    public static double max(double[] values) {
        double m = -Double.MAX_VALUE;
        for (double v : values) if (v > m) m = v;
        return m;
    }

    public static double mean(double[] values) {
        double sum = 0;
        for (double v : values) sum += v;
        return sum / values.length;
    }

    public static double median(double[] values) {
        double[] sorted = values.clone();
        Arrays.sort(sorted);
        int n = sorted.length;
        if (n % 2 == 1) {
            return sorted[n / 2];
        } else {
            return (sorted[n / 2 - 1] + sorted[n / 2]) / 2.0;
        }
    }

    public static double stdDev(double[] values) {
        double m = mean(values);
        double sumSq = 0;
        for (double v : values) sumSq += (v - m) * (v - m);
        return Math.sqrt(sumSq / values.length);
    }
}
