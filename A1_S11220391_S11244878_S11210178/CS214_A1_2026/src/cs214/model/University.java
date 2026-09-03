package cs214.model;

/**
 * University
 * ----------
 * The user-defined data type used throughout this assignment.
 * Represents a single row of the World University Rankings 2023 dataset.
 *
 * Implements Comparable<University> so that ANY of the generic sorting
 * algorithms (InsertionSort<T>, BubbleSort<T>, MergeSort<T>, BuiltInSort<T>)
 * can sort University objects purely through the Comparable contract,
 * without knowing anything about universities specifically.
 *
 * Natural ordering: ascending by World Ranking (rank 1 = best, comes first).
 */
public class University implements Comparable<University> {

    private final int rank;
    private final String name;
    private final String location;
    private final int numStudents;
    private final double studentPerStaff;
    private final double internationalStudentPercent;
    private final String femaleMaleRatio;
    private final double overallScore;
    private final double teachingScore;
    private final double researchScore;
    private final double citationsScore;
    private final double industryIncomeScore;
    private final double internationalOutlookScore;

    public University(int rank, String name, String location, int numStudents,
                       double studentPerStaff, double internationalStudentPercent,
                       String femaleMaleRatio, double overallScore, double teachingScore,
                       double researchScore, double citationsScore,
                       double industryIncomeScore, double internationalOutlookScore) {
        this.rank = rank;
        this.name = name;
        this.location = location;
        this.numStudents = numStudents;
        this.studentPerStaff = studentPerStaff;
        this.internationalStudentPercent = internationalStudentPercent;
        this.femaleMaleRatio = femaleMaleRatio;
        this.overallScore = overallScore;
        this.teachingScore = teachingScore;
        this.researchScore = researchScore;
        this.citationsScore = citationsScore;
        this.industryIncomeScore = industryIncomeScore;
        this.internationalOutlookScore = internationalOutlookScore;
    }

    public int getRank() { return rank; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public int getNumStudents() { return numStudents; }
    public double getStudentPerStaff() { return studentPerStaff; }
    public double getInternationalStudentPercent() { return internationalStudentPercent; }
    public String getFemaleMaleRatio() { return femaleMaleRatio; }
    public double getOverallScore() { return overallScore; }
    public double getTeachingScore() { return teachingScore; }
    public double getResearchScore() { return researchScore; }
    public double getCitationsScore() { return citationsScore; }
    public double getIndustryIncomeScore() { return industryIncomeScore; }
    public double getInternationalOutlookScore() { return internationalOutlookScore; }

    /**
     * Natural ordering used by every sort algorithm in this project:
     * ascending by rank (rank 1 first). Ties broken by higher overall score first.
     */
    @Override
    public int compareTo(University other) {
        int rankCompare = Integer.compare(this.rank, other.rank);
        if (rankCompare != 0) return rankCompare;
        return Double.compare(other.overallScore, this.overallScore);
    }

    @Override
    public String toString() {
        return String.format("#%-5d %-45s %-20s Overall=%.1f", rank, truncate(name, 45), truncate(location, 20), overallScore);
    }

    private String truncate(String s, int len) {
        if (s == null) return "";
        return s.length() <= len ? s : s.substring(0, len - 1) + "…";
    }
}
