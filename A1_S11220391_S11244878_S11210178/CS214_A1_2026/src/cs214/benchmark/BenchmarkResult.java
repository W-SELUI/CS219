package cs214.benchmark;

/**
 * BenchmarkResult
 * ---------------
 * Raw per-trial measurements and derived best/mean/median/worst statistics
 * for one (algorithm, structure) combination, across all 30 (or however
 * many) randomised trials.
 *
 * Two independent counter types are tracked, per Question 3's "using
 * counter values" instruction:
 *  - timesMs:        wall-clock elapsed time per trial (machine-dependent,
 *                     but what a user actually experiences)
 *  - operationCounts: primitive comparisons + data-movements per trial
 *                     (machine-independent -- the same on any computer,
 *                     which is why it's the more meaningful complexity
 *                     signal). -1 per trial for algorithms that don't
 *                     expose this (BuiltInSort).
 */
public class BenchmarkResult {

    public final String algorithmName;
    public final String structureName;
    public final double[] timesMs;
    public final long[] operationCounts;

    public BenchmarkResult(String algorithmName, String structureName,
                            double[] timesMs, long[] operationCounts) {
        this.algorithmName = algorithmName;
        this.structureName = structureName;
        this.timesMs = timesMs;
        this.operationCounts = operationCounts;
    }

    public String getLabel() {
        return algorithmName + " (" + structureName + ")";
    }

    public double bestTimeMs()   { return Stats.min(timesMs); }
    public double meanTimeMs()   { return Stats.mean(timesMs); }
    public double medianTimeMs() { return Stats.median(timesMs); }
    public double worstTimeMs()  { return Stats.max(timesMs); }
    public double stdDevTimeMs() { return Stats.stdDev(timesMs); }

    public boolean hasOperationCounts() {
        return operationCounts.length > 0 && operationCounts[0] >= 0;
    }

    public double bestOps() {
        return hasOperationCounts() ? Stats.min(toDoubleArray(operationCounts)) : -1;
    }
    public double meanOps() {
        return hasOperationCounts() ? Stats.mean(toDoubleArray(operationCounts)) : -1;
    }
    public double medianOps() {
        return hasOperationCounts() ? Stats.median(toDoubleArray(operationCounts)) : -1;
    }
    public double worstOps() {
        return hasOperationCounts() ? Stats.max(toDoubleArray(operationCounts)) : -1;
    }

    private double[] toDoubleArray(long[] longs) {
        double[] d = new double[longs.length];
        for (int i = 0; i < longs.length; i++) d[i] = longs[i];
        return d;
    }
}
