package cs214.race;

import cs214.model.University;
import cs214.sorting.Sorter;
import cs214.structures.MyList;

/**
 * RaceLane
 * --------
 * One row in the visual race: a single (algorithm, structure) combination
 * sorting its own private copy of the dataset on its own thread.
 *
 * Thread-safety note: `snapshot` is a *volatile reference* to an immutable
 * double[] array. Each step of the algorithm builds a brand-new array and
 * publishes it via a single volatile write. The Swing repaint timer (on the
 * Event Dispatch Thread) only ever does a single volatile read of that
 * reference. Because the array itself is never mutated after publication,
 * this is safe without any explicit locking (a classic "immutable snapshot"
 * pattern) and keeps the sorting thread from ever blocking on the UI.
 */
public class RaceLane {

    public final String label;
    private final Sorter<University> sorter;
    private final MyList<University> list;

    private volatile double[] snapshot;
    private volatile long steps = 0;
    private volatile boolean finished = false;
    private volatile long elapsedNanos = 0;
    private volatile int finishPlace = -1; // set by RaceEngine once this lane finishes

    public RaceLane(String label, Sorter<University> sorter, MyList<University> list) {
        this.label = label;
        this.sorter = sorter;
        this.list = list;
        this.snapshot = extract(list);
    }

    // Runs the sort on the calling thread, publishing a snapshot after every step. 
    public void run(int delayMicros) {
        long start = System.nanoTime();
        sorter.sort(list, () -> {
            // this callback fires after every mutation the algorithm makes --
            // it's how progress gets reported back for the animation
            steps++;
            snapshot = extract(list);
            if (delayMicros > 0) {
                // deliberate throttle so fast algorithms don't finish before
                // you can see them move on screen (see RaceFrame's speed slider)
                try {
                    Thread.sleep(delayMicros / 1000, (delayMicros % 1000) * 1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        snapshot = extract(list); // final snapshot, in case the last step's snapshot was mid-sleep
        elapsedNanos = System.nanoTime() - start;
        finished = true;
    }

    // Reads the current overall-score of every element, for the bar-chart display.
    private double[] extract(MyList<University> l) {
        double[] arr = new double[l.size()];
        for (int i = 0; i < l.size(); i++) {
            arr[i] = l.get(i).getOverallScore();
        }
        return arr;
    }

    public double[] getSnapshot() { return snapshot; }
    public long getSteps() { return steps; }
    public boolean isFinished() { return finished; }
    public long getElapsedNanos() { return elapsedNanos; }
    public double getElapsedMs() { return elapsedNanos / 1_000_000.0; }
    public int getFinishPlace() { return finishPlace; }
    public void setFinishPlace(int place) { this.finishPlace = place; }
}
