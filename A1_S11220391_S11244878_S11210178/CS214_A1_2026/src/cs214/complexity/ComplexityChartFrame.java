package cs214.complexity;

import cs214.io.CSVLoader;
import cs214.model.University;
import cs214.structures.MyArrayList;
import cs214.structures.MyList;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * ComplexityChartFrame
 * ----------------------
 * GUI for Question 4. Runs the full multi-size benchmark on a background
 * thread and then displays the
 * resulting worst-case growth-curve chart.
 */
public class ComplexityChartFrame extends JFrame {

    private static final String CSV_PATH = "data/World_University_Rankings_2023-Cleaned.csv";
    private static final int[] DEFAULT_SIZES = {100, 300, 500, 700, 900, 1100, 1300, 1500, 1697};
    private static final int DEFAULT_TRIALS = 5;

    private JPanel chartHolder;
    private JLabel statusLabel;
    private JButton runButton;
    private JComboBox<String> metricSelector;
    private MyList<University> fullDataset;
    private List<ComplexitySample> lastEmpiricalSamples;

    public ComplexityChartFrame() throws Exception {
        super("CS214 Assignment 1 - Question 4: Worst-Case Time Complexity");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        loadDataset();

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        runButton = new JButton("Run Benchmark & Plot");
        runButton.addActionListener(e -> runBenchmark());
        controls.add(runButton);

        metricSelector = new JComboBox<>(new String[]{"Worst-case Time (ms)", "Worst-case Operations"});
        metricSelector.addActionListener(e -> redraw());
        controls.add(new JLabel("Y axis:"));
        controls.add(metricSelector);

        statusLabel = new JLabel("Ready. Click 'Run Benchmark & Plot' (~2-3 minutes on the full size range).");
        controls.add(statusLabel);
        add(controls, BorderLayout.NORTH);

        chartHolder = new JPanel(new BorderLayout());
        add(chartHolder, BorderLayout.CENTER);

        setPreferredSize(new Dimension(1150, 720));
        pack();
        setLocationRelativeTo(null);
    }

    private void loadDataset() throws Exception {
        fullDataset = new MyArrayList<>();
        int count = new CSVLoader().load(CSV_PATH, fullDataset);
        System.out.println("Loaded " + count + " university records.");
    }

    private void runBenchmark() {
        runButton.setEnabled(false);
        statusLabel.setText("Running... this covers " + DEFAULT_SIZES.length + " input sizes x "
                + DEFAULT_TRIALS + " trials x 7 algorithms. Please wait.");

        SwingWorker<List<ComplexitySample>, Void> worker = new SwingWorker<List<ComplexitySample>, Void>() {
            @Override
            protected List<ComplexitySample> doInBackground() throws Exception {
                ComplexityRunner runner = new ComplexityRunner();
                List<ComplexitySample> samples = runner.runEmpirical(fullDataset, DEFAULT_SIZES, DEFAULT_TRIALS);
                ComplexityReportWriter.write(samples, "results/complexity_results.csv");
                return samples;
            }

            @Override
            protected void done() {
                try {
                    lastEmpiricalSamples = get();
                    statusLabel.setText("Done. " + lastEmpiricalSamples.size()
                            + " data points plotted. CSV written to results/complexity_results.csv");
                    redraw();
                } catch (Exception ex) {
                    statusLabel.setText("Error: " + ex.getMessage());
                    ex.printStackTrace();
                } finally {
                    runButton.setEnabled(true);
                }
            }
        };
        worker.execute();
    }

    private void redraw() {
        if (lastEmpiricalSamples == null) return;
        boolean useOps = metricSelector.getSelectedIndex() == 1;
        chartHolder.removeAll();
        chartHolder.add(new ComplexityChartPanel(lastEmpiricalSamples, useOps), BorderLayout.CENTER);
        chartHolder.revalidate();
        chartHolder.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                ComplexityChartFrame frame = new ComplexityChartFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Failed to start: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
