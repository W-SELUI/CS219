package cs214.complexity;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.List;

/**
 * ComplexityChartPanel
 * ---------------------
 * A hand-drawn line chart (no external charting library needed) plotting
 * input size (n, linear x-axis) against worst-case time in milliseconds
 * (log10 y-axis). A log y-axis is essential here: at n≈1700, Bubble Sort on
 * a linked list takes ~4 seconds while Merge Sort takes ~1 millisecond — a
 * ~4000x range that would make the faster algorithms invisible on a linear
 * scale. On a log scale, an O(n²) algorithm's line curves upward with
 * increasing steepness, while an O(n log n) algorithm's line stays nearly
 * straight/flat — visually distinguishing the complexity classes. 
 */
public class ComplexityChartPanel extends JPanel {

    private final List<ComplexitySample> samples;
    private final boolean useOperationsInsteadOfTime;

    private static final Color[] SERIES_COLORS = {
            new Color(0x4C6EF5), new Color(0x228BE6), new Color(0x15AABF),
            new Color(0x12B886), new Color(0x40C057), new Color(0xFAB005),
            new Color(0xFA5252)
    };

    public ComplexityChartPanel(List<ComplexitySample> samples, boolean useOperationsInsteadOfTime) {
        this.samples = samples;
        this.useOperationsInsteadOfTime = useOperationsInsteadOfTime;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(950, 620));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (samples.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int marginLeft = 90, marginRight = 220, marginTop = 50, marginBottom = 60;
        int chartX = marginLeft, chartY = marginTop;
        int chartWidth = getWidth() - marginLeft - marginRight;
        int chartHeight = getHeight() - marginTop - marginBottom;
        if (chartWidth < 50 || chartHeight < 50) return;

        // --- group samples by series label, sorted by n ---
        Map<String, List<ComplexitySample>> series = new LinkedHashMap<>();
        for (ComplexitySample s : samples) {
            series.computeIfAbsent(s.getLabel(), k -> new ArrayList<>()).add(s);
        }
        for (List<ComplexitySample> list : series.values()) {
            list.sort(Comparator.comparingInt(a -> a.n));
        }

        // --- axis ranges ---
        int minN = Integer.MAX_VALUE, maxN = Integer.MIN_VALUE;
        double minVal = Double.MAX_VALUE, maxVal = -Double.MAX_VALUE;
        for (ComplexitySample s : samples) {
            minN = Math.min(minN, s.n);
            maxN = Math.max(maxN, s.n);
            double v = valueOf(s);
            if (v <= 0) continue;
            minVal = Math.min(minVal, v);
            maxVal = Math.max(maxVal, v);
        }
        if (minVal == Double.MAX_VALUE) return;
        double logMin = Math.floor(Math.log10(minVal));
        double logMax = Math.ceil(Math.log10(maxVal));
        if (logMax <= logMin) logMax = logMin + 1;

        // --- title ---
        g2.setColor(Color.DARK_GRAY);
        g2.setFont(new Font("SansSerif", Font.BOLD, 16));
        String title = useOperationsInsteadOfTime
                ? "Worst-Case Operation Count vs Input Size (log scale)"
                : "Worst-Case Time vs Input Size (log scale)";
        g2.drawString(title, marginLeft, 25);

        // --- y gridlines (powers of 10) + labels ---
        g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        for (int p = (int) logMin; p <= (int) logMax; p++) {
            int y = chartY + chartHeight - (int) ((p - logMin) / (logMax - logMin) * chartHeight);
            g2.setColor(new Color(0xEDEDED));
            g2.drawLine(chartX, y, chartX + chartWidth, y);
            g2.setColor(Color.GRAY);
            String label = formatPow10(p, useOperationsInsteadOfTime);
            g2.drawString(label, marginLeft - 80, y + 4);
        }

        // --- x axis (linear n) ---
        g2.setColor(Color.GRAY);
        g2.drawLine(chartX, chartY + chartHeight, chartX + chartWidth, chartY + chartHeight);
        int xTicks = 6;
        for (int i = 0; i <= xTicks; i++) {
            int n = minN + (maxN - minN) * i / xTicks;
            int x = chartX + chartWidth * i / xTicks;
            g2.drawLine(x, chartY + chartHeight, x, chartY + chartHeight + 5);
            g2.drawString(String.valueOf(n), x - 10, chartY + chartHeight + 20);
        }
        g2.drawString("Input size (n)", chartX + chartWidth / 2 - 30, chartY + chartHeight + 42);

        // --- plot each series ---
        int colorIdx = 0;
        int legendY = marginTop;
        for (Map.Entry<String, List<ComplexitySample>> entry : series.entrySet()) {
            Color color = SERIES_COLORS[colorIdx % SERIES_COLORS.length];
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2.2f));

            List<ComplexitySample> points = entry.getValue();
            Integer prevX = null, prevY = null;
            for (ComplexitySample s : points) {
                double v = valueOf(s);
                if (v <= 0) continue;
                int x = chartX + (int) ((s.n - minN) / (double) Math.max(1, maxN - minN) * chartWidth);
                double logV = Math.log10(v);
                int y = chartY + chartHeight - (int) ((logV - logMin) / (logMax - logMin) * chartHeight);
                if (prevX != null) {
                    g2.drawLine(prevX, prevY, x, y);
                }
                g2.fill(new Ellipse2D.Double(x - 3, y - 3, 6, 6));
                prevX = x;
                prevY = y;
            }

            // legend entry
            g2.setColor(color);
            g2.fillRect(chartX + chartWidth + 25, legendY, 12, 12);
            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
            g2.drawString(entry.getKey(), chartX + chartWidth + 42, legendY + 11);
            legendY += 22;
            colorIdx++;
        }
    }

    private double valueOf(ComplexitySample s) {
        return useOperationsInsteadOfTime ? s.result.worstOps() : s.result.worstTimeMs();
    }

    private String formatPow10(int p, boolean ops) {
        double val = Math.pow(10, p);
        if (ops) {
            return val >= 1 ? String.format("%,.0f", val) : String.valueOf(val);
        }
        return val >= 1 ? String.format("%,.0fms", val) : String.format("%.2fms", val);
    }
}
