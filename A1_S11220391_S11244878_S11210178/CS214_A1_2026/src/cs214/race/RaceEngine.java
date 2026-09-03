package cs214.race;

import cs214.model.University;
import cs214.sorting.*;
import cs214.structures.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RaceEngine
 * ----------
 * Builds the 7 required algorithm/structure combinations and runs each on its own daemon thread so they truly run
 * concurrently. A shared, shuffled sample of the dataset is copied into a
 * fresh MyArrayList or MyLinkedList for every lane, so every algorithm races
 * on identical starting data.
 */
public class RaceEngine {

    private final List<RaceLane> lanes = new ArrayList<>();
    private final int delayMicros;
    private final AtomicInteger finishCounter = new AtomicInteger(0);

    public RaceEngine(MyList<University> sampleData, int delayMicros, long randomSeed) {
        this.delayMicros = delayMicros;
        Random random = new Random(randomSeed);

        addLane("Insertion Sort — ArrayList", new InsertionSort<>(), sampleData, true, random);
        addLane("Insertion Sort — LinkedList", new InsertionSort<>(), sampleData, false, random);
        addLane("Bubble Sort — ArrayList", new BubbleSort<>(), sampleData, true, random);
        addLane("Bubble Sort — LinkedList", new BubbleSort<>(), sampleData, false, random);
        addLane("Merge Sort — ArrayList", new MergeSort<>(), sampleData, true, random);
        addLane("Merge Sort — LinkedList", new MergeSort<>(), sampleData, false, random);
        addLane("Built-in Sort — ArrayList", new BuiltInSort<>(), sampleData, true, random);
    }

    private void addLane(String label, Sorter<University> sorter, MyList<University> baseData,
                          boolean useArrayList, Random random) {
        MyList<University> copy = useArrayList
                ? ListUtils.copyToArrayList(baseData)
                : ListUtils.copyToLinkedList(baseData);
        ListUtils.shuffle(copy, random);
        lanes.add(new RaceLane(label, sorter, copy));
    }

    // Starts every lane concurrently on its own daemon thread. 
    public void startAll() {
        finishCounter.set(0);
        for (RaceLane lane : lanes) {
            Thread t = new Thread(() -> {
                lane.run(delayMicros);
                lane.setFinishPlace(finishCounter.incrementAndGet());
            }, lane.label);
            t.setDaemon(true);
            t.start();
        }
    }

    public List<RaceLane> getLanes() {
        return lanes;
    }

    public boolean allFinished() {
        for (RaceLane lane : lanes) {
            if (!lane.isFinished()) return false;
        }
        return true;
    }
}
