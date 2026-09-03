OVERVIEW
------------
CS214 Assignment 1 project. It's about comparing sorting algorithms
and data structures - The tasks involves importing the world University Ranking 2023 dataset from a CSV file, storing it in two custom-built data structures (an array list and a linked list), and sorting the data using four different algorithms across both structures - producing the seven required combinations. Beyond the core requirements, the project includes a live race visualizer, a benchmarking tool that runs 30 trials per algorithm, and a chart illustrating how each algorithm's performance scales as the dataset size increases. 

Packages:
> model      - the University class (the thing being sorted)
> structures - Polymorphic list layer (MyList, MyArrayList, MyLinkedList, ListUtils)
> sorting    - the 4 sorting algorithms
> io         - loads the CSV
> race       - the live race window (RaceFrame)
> benchmark  - runs the 30-trial benchmark (BenchmarkMain)
> complexity - the worst-case complexity graph (ComplexityChartFrame)

FEATURES
--------
> 4 sorting algorithms (Insertion, Bubble, Merge, Built-in) x 2 data
  structures (custom ArrayList and LinkedList) = 7 combinations, all using
  the same algorithm code via polymorphism.
> CSV loader that deals with the messy parts of the dataset - commas inside
  numbers, percentage signs, score ranges, etc.
> A race window where all 7 algorithms sort at the same time so you can
  actually watch them compete.
> A benchmark that runs each combo 30 times with randomised input order and
  works out the best/mean/median/worst time, plus which one is fastest.
> A graph showing how the algorithms' worst-case time grows as the input
  size grows, on a log scale so you can actually see the slow ones next to
  the fast ones.

PROJECT STRUCTURE
-------------------
CS214_A1_2026/
|-- data/
|   |---World_University_Rankings_2023-Cleaned.csv
|-- results/
|   |---benchmark_raw_trials.csv
|   |---benchmark_summary.csv
|   |---complexity_empirical.csv
|   |---complexity_constructed_worstcase.csv
|-- src/
|   |---cs214/Main.java
|   |---cs214/model/University.java
|   |---cs214/structures/MyList.java
|   |---cs214/structures/MyArrayList.java
|   |---cs214/structures/MyLinkedList.java
|   |---cs214/structures/ListUtils.java
|   |---cs214/sorting/Sorter.java
|   |---cs214/sorting/InsertionSort.java
|   |---cs214/sorting/BubbleSort.java
|   |---cs214/sorting/MergeSort.java
|   |---cs214/sorting/BuiltInSort.java
|   |---cs214/io/CSVLoader.java
|   |---cs214/race/RaceEngine.java
|   |---cs214/race/RaceLane.java
|   |---cs214/race/RacePanel.java
|   |---cs214/race/RaceFrame.java
|   |---cs214/benchmark/BenchmarkRunner.java
|   |---cs214/benchmark/BenchmarkResult.java
|   |---cs214/benchmark/Stats.java
|   |---cs214/benchmark/BenchmarkReportWriter.java
|   |---cs214/benchmark/BenchmarkMain.java
|   |---cs214/complexity/ComplexityRunner.java
|   |---cs214/complexity/ComplexitySample.java
|   |---cs214/complexity/ComplexityReportWriter.java
|   |---cs214/complexity/ComplexityChartPanel.java
|   |---cs214/complexity/ComplexityChartFrame.java
|   |---cs214/complexity/ComplexityMain.java
|--README.txt

HOW TO RUN
-----------
1. Open VS Code and install the "Extension Pack for Java" if you don't
   already have it.
2. Open the folder with src/ and data/ inside it (has to be this folder
   specifically, since the code looks for the CSV using a relative path).
3. Open src/cs214/Main.java and hit Run - that's Question 1. You should see
   "sorted=true" printed for all 7 combinations.
4. Open src/cs214/race/RaceFrame.java and hit Run. Its for Question 2.Thus, a
   window pops up, click "Start Race" and watch all 7 algorithms sort at
   once.
5. Open src/cs214/benchmark/BenchmarkMain.java and hit Run - that's
   Question 3. Takes about 3-5 minutes since it's running 30 trials per
   combo on the full dataset. Writes its results to results/.
6. Open src/cs214/complexity/ComplexityChartFrame.java and hit Run - that's
   Question 4. Click "Run Benchmark & Plot" to get
   the growth chart. (takes about 2-3 minutes)
7. If you just want the numbers for Question 4 without waiting for the
   chart, run ComplexityMain.java instead. It will prints everything straight to
   the terminal.

SAMPLE OUTPUT
--------------
Main.java (Q1):
Insertion Sort   on MyArrayList   : sorted=true  time=57ms
Merge Sort       on MyLinkedList  : sorted=true  time=36ms
All 7 algorithm/structure combinations completed successfully.

BenchmarkMain.java (Q3):
Algorithm (Structure)           Best(ms)  Mean(ms)  Worst(ms)   Mean Ops
Merge Sort (ArrayList)              0.78      0.94       1.71     15,416
>>> Fastest algorithm by mean empirical time: Merge Sort (ArrayList)

ACKNOWLEDGMENT OF AI USE
--------------------------
We used Claude (Anthropic's AI assistant) to help build this project. 

CONTRIBUTORS
-------------
Inoke Tuco - S11220391
William Selui - S11244878
Tomasi Bainiyatu - S11210178
