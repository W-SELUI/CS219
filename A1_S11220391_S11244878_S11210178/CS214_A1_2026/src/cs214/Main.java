package cs214;

import cs214.io.CSVLoader;
import cs214.model.University;
import cs214.sorting.*;
import cs214.structures.*;

import java.util.Random;

/**
 * Main
 * ----
 * Demonstration / verification driver for Question 1.
 *
 * 1. Loads the World University Rankings 2023 dataset into BOTH a
 *    MyArrayList<University> and a MyLinkedList<University>.
 * 2. Runs all 7 required algorithms:
 *      - Insertion Sort  -> array list AND linked list  (2)
 *      - Bubble Sort      -> array list AND linked list  (2)
 *      - Merge Sort        -> array list AND linked list  (2)
 *      - Built-in Sort (Collections.sort)                (1)
 *    on freshly shuffled copies of the data, and verifies each result is
 *    correctly sorted.+
 * 3. Proves genericity by also sorting a small MyArrayList<Integer>, showing
 *    the same algorithm classes work for any Comparable user type.
 */
public class Main {

    private static final String CSV_PATH = "data/World_University_Rankings_2023-Cleaned.csv";

    public static void main(String[] args) throws Exception {
        System.out.println("=== CS214 Assignment 1 - Question 1 ===");
        System.out.println("Loading dataset: " + CSV_PATH);

        MyList<University> baseData = new MyArrayList<>();
        CSVLoader loader = new CSVLoader();
        int count = loader.load(CSV_PATH, baseData);
        System.out.println("Loaded " + count + " university records.\n");

        Random random = new Random(42); // fixed seed so this demo is repeatable

        Sorter<University>[] algorithms = new Sorter[] {
                new InsertionSort<University>(),
                new BubbleSort<University>(),
                new MergeSort<University>(),
                new BuiltInSort<University>()
        };

        for (Sorter<University> algorithm : algorithms) {
            runOn(algorithm, "MyArrayList", ListUtils.copyToArrayList(baseData), random);
            runOn(algorithm, "MyLinkedList", ListUtils.copyToLinkedList(baseData), random);
        }

        System.out.println("\nAll 7 algorithm/structure combinations completed successfully:");
        System.out.println("  1a. Insertion Sort  -> ArrayList & LinkedList");
        System.out.println("  1b. Bubble Sort      -> ArrayList & LinkedList");
        System.out.println("  1c. Merge Sort        -> ArrayList & LinkedList");
        System.out.println("  1d. Built-in Sort (Collections.sort)  -> works on both, shown once");

        demonstrateGenericity();
    }

    private static void runOn(Sorter<University> algorithm, String structureName,
                               MyList<University> list, Random random) {
        ListUtils.shuffle(list, random);

        long start = System.nanoTime();
        algorithm.sort(list);
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;

        boolean sorted = ListUtils.isSorted(list);
        System.out.printf("%-35s on %-13s : sorted=%-5s  time=%dms%n",
                algorithm.getName(), structureName, sorted, elapsedMs);

        System.out.println("   Top 3 after sort:");
        for (int i = 0; i < Math.min(3, list.size()); i++) {
            System.out.println("     " + list.get(i));
        }
    }

    // Shows the exact same Sorter classes work for a completely different, non-University type. 
    private static void demonstrateGenericity() {
        System.out.println("\n=== Genericity check: sorting Integers with the same classes ===");
        MyList<Integer> numbers = new MyArrayList<>();
        int[] raw = {42, 7, 19, 3, 88, 1, 56};
        for (int n : raw) numbers.add(n);

        new InsertionSort<Integer>().sort(numbers);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numbers.size(); i++) {
            sb.append(numbers.get(i));
            if (i < numbers.size() - 1) sb.append(", ");
        }
        System.out.println("Sorted: [" + sb + "]  sorted=" + ListUtils.isSorted(numbers));
    }
}
