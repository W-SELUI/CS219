package cs214.race;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * RacePanel
 * ---------
 * Draws all 7 lanes as horizontal bar-chart strips, redrawn on a Swing
 * Timer (Event Dispatch Thread). Each lane's bars represent that lane's
 * current University overall-score order — as an algorithm sorts, the
 * bars visibly settle into descending height, left to right (because a
 * lower rank corresponds to a higher overall score).
 */
public class RacePanel extends JPanel {

    private final List<RaceLane> lanes;
    private static final Color[] LANE_COLORS = {
            new Color(0x4C6EF5), new Color(0x228BE6), new Color(0x15AABF),
            new Color(0x12B886), new Color(0x40C057), new Color(0xFAB005),
            new Color(0xFA5252)
    };

    public RacePanel(List<RaceLane> lanes) {
        this.lanes = lanes;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1000, 60 + lanes.size() * 90));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 13));

        int panelWidth = getWidth();
        int laneHeight = 90;
        int topMargin = 10;
        int labelWidth = 210;
        int chartWidth = panelWidth - labelWidth - 30;
        double maxScore = 100.0;

        for (int i = 0; i < lanes.size(); i++) {
            RaceLane lane = lanes.get(i);
            int y = topMargin + i * laneHeight;
            Color color = LANE_COLORS[i % LANE_COLORS.length];

            // Label + stats
            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("SansSerif", Font.BOLD, 13));
            g2.drawString(lane.label, 10, y + 15);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 12));

            String status;
            if (lane.isFinished()) {
                status = String.format("Finished — #%d place, %.1fms, %d steps",
                        lane.getFinishPlace(), lane.getElapsedMs(), lane.getSteps());
                g2.setColor(new Color(0x2B8A3E));
            } else {
                status = String.format("Sorting... %d steps", lane.getSteps());
                g2.setColor(Color.GRAY);
            }
            g2.drawString(status, 10, y + 32);

            // Bars
            double[] values = lane.getSnapshot();
            if (values.length == 0) continue;
            double barWidth = Math.max(1.0, (double) chartWidth / values.length);
            int chartBottom = y + laneHeight - 8;
            int chartTop = y + 40;
            int chartHeight = chartBottom - chartTop;

            g2.setColor(lane.isFinished() ? color : color.brighter());
            for (int idx = 0; idx < values.length; idx++) {
                double v = Math.max(0, values[idx]);
                int barHeight = (int) (chartHeight * (v / maxScore));
                int x = labelWidth + (int) (idx * barWidth);
                int barW = Math.max(1, (int) Math.ceil(barWidth));
                g2.fillRect(x, chartBottom - barHeight, barW, barHeight);
            }

            // Baseline
            g2.setColor(new Color(0xE0E0E0));
            g2.drawLine(labelWidth, chartBottom, labelWidth + chartWidth, chartBottom);
        }
    }
}
