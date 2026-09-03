package cs214.race;

import cs214.io.CSVLoader;
import cs214.model.University;
import cs214.structures.ListUtils;
import cs214.structures.MyArrayList;
import cs214.structures.MyList;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * RaceFrame
 * ---------
 * The GUI window for Question 2. Loads a random sample of the dataset,
 * builds a RaceEngine with all 7 algorithm/structure combinations, and
 * animates them sorting simultaneously via a Swing Timer that repaints
 * RacePanel roughly 30 times a second.
 *
 * Controls:
 *  - Sample size: how many universities each lane races with (smaller =
 *    faster, easier to watch; larger = closer to the full dataset).
 *  - Speed slider: adds a small delay after every algorithm step so the
 *    O(n log n) algorithms don't finish instantly and the race stays
 *    watchable.
 *  - Start Race: builds fresh shuffled data and launches all 7 threads.
 */
public class RaceFrame extends JFrame {

    private static final String CSV_PATH = "data/World_University_Rankings_2023-Cleaned.csv";

    private RacePanel racePanel;
    private JPanel raceHolder;
    private JLabel statusLabel;
    private JSlider speedSlider;
    private JSpinner sampleSizeSpinner;
    private JButton startButton;
    private Timer repaintTimer;
    private MyList<University> fullDataset;

    public RaceFrame() throws Exception {
        super("CS214 Assignment 1 - Question 2: Sorting Algorithm Race");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        loadDataset();
        buildControls();

        raceHolder = new JPanel(new BorderLayout());
        add(raceHolder, BorderLayout.CENTER);

        setPreferredSize(new Dimension(1050, 750));
        pack();
        setLocationRelativeTo(null);
    }

    private void loadDataset() throws Exception {
        fullDataset = new MyArrayList<>();
        CSVLoader loader = new CSVLoader();
        int count = loader.load(CSV_PATH, fullDataset);
        System.out.println("Loaded " + count + " university records for the race.");
    }

    private void buildControls() {
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));

        controls.add(new JLabel("Sample size:"));
        sampleSizeSpinner = new JSpinner(new SpinnerNumberModel(60, 10, 300, 10));
        controls.add(sampleSizeSpinner);

        controls.add(new JLabel("Speed:"));
        speedSlider = new JSlider(0, 100, 40); // 0 = fastest, 100 = slowest
        speedSlider.setPreferredSize(new Dimension(160, 20));
        controls.add(speedSlider);
        controls.add(new JLabel("(slower ⟵ ⟶ faster is left)"));

        startButton = new JButton("Start Race");
        startButton.addActionListener(e -> startRace());
        controls.add(startButton);

        statusLabel = new JLabel("Ready. Click Start Race.");
        controls.add(statusLabel);

        add(controls, BorderLayout.NORTH);
    }

    private void startRace() {
        if (repaintTimer != null) repaintTimer.stop();
        startButton.setEnabled(false);
        statusLabel.setText("Racing...");

        int sampleSize = (Integer) sampleSizeSpinner.getValue();
        int delayMicros = speedSlider.getValue() * 300; // 0 - 30,000 microseconds per step

        MyList<University> sample = buildSample(sampleSize);
        RaceEngine engine = new RaceEngine(sample, delayMicros, System.nanoTime());

        raceHolder.removeAll();
        racePanel = new RacePanel(engine.getLanes());
        JScrollPane scrollPane = new JScrollPane(racePanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        raceHolder.add(scrollPane, BorderLayout.CENTER);
        raceHolder.revalidate();

        engine.startAll();

        repaintTimer = new Timer(33, e -> {
            racePanel.repaint();
            if (engine.allFinished()) {
                repaintTimer.stop();
                startButton.setEnabled(true);
                statusLabel.setText("All algorithms finished! (See finish order & times on each lane.)");
            }
        });
        repaintTimer.start();
    }

    // Takes a random sample of `size` universities from the full dataset. 
    private MyList<University> buildSample(int size) {
        Random random = new Random();
        MyList<University> shuffledFull = ListUtils.copyToArrayList(fullDataset);
        ListUtils.shuffle(shuffledFull, random);

        MyArrayList<University> sample = new MyArrayList<>(size);
        int limit = Math.min(size, shuffledFull.size());
        for (int i = 0; i < limit; i++) {
            sample.add(shuffledFull.get(i));
        }
        return sample;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                RaceFrame frame = new RaceFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Failed to start race: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
