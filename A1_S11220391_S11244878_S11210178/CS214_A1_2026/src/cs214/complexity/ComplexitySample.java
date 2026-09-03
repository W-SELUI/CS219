package cs214.complexity;

import cs214.benchmark.BenchmarkResult;

/**
 * ComplexitySample
 * ----------------
 * One (input size, algorithm, structure) data point: the input size `n`
 * plus the full best/mean/median/worst statistics already computed by
 * Question 3's BenchmarkResult. Question 4 collects many of these across a
 * range of `n` values to plot growth curves.
 */
public class ComplexitySample {

    public final int n;
    public final BenchmarkResult result;

    public ComplexitySample(int n, BenchmarkResult result) {
        this.n = n;
        this.result = result;
    }

    public String getLabel() {
        return result.getLabel();
    }
}
