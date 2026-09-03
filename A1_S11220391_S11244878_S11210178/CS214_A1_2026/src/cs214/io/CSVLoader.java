package cs214.io;

import cs214.model.University;
import cs214.structures.MyList;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * CSVLoader
 * ---------
 * Reads World_University_Rankings_2023-Cleaned.csv and populates any
 * MyList<University> (MyArrayList or MyLinkedList) supplied to it -- again
 * relying on the MyList<T> interface rather than a concrete type.
 *
 * Handles the real-world messiness of this dataset.
 */
public class CSVLoader {

    /**
     * Loads the CSV at the given path into the supplied list.
     * @param filePath path to the CSV file
     * @param target   an empty MyList<University> (MyArrayList or MyLinkedList)
     * @return the number of rows successfully loaded
     */
    public int load(String filePath, MyList<University> target) throws IOException {
        int loaded = 0;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            String headerLine = reader.readLine(); // skip header row
            if (headerLine == null) return 0;

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] fields = splitCsvLine(line);
                if (fields.length < 13) continue; // skip malformed rows

                try {
                    University u = parseRow(fields);
                    target.add(u);
                    loaded++;
                } catch (Exception rowError) {
                    // Skip a single bad row rather than aborting the whole load.
                    System.err.println("Skipped malformed row: " + rowError.getMessage());
                }
            }
        }
        return loaded;
    }

    /** Builds a University from one row's raw string fields, converting/cleaning each column. */
    private University parseRow(String[] f) {
        // Column order matches the CSV header exactly:
        // 0=Rank, 1=Name, 2=Location, 3=No of student, 4=Students per staff,
        // 5=International %, 6=Female:Male ratio, 7=Overall, 8=Teaching,
        // 9=Research, 10=Citations, 11=Industry Income, 12=International Outlook
        int rank = parseIntSafe(f[0]);
        String name = f[1].trim();
        String location = f[2].trim();
        int numStudents = parseIntSafe(stripCommasAndQuotes(f[3])); // e.g. "20,965" -> 20965
        double studentPerStaff = parseDoubleSafe(f[4]);
        double intlStudentPct = parsePercent(f[5]);                 // e.g. "42%" -> 42.0
        String femaleMaleRatio = f[6].trim();
        double overallScore = parseScoreOrRange(f[7]);
        double teachingScore = parseScoreOrRange(f[8]);
        double researchScore = parseScoreOrRange(f[9]);
        double citationsScore = parseScoreOrRange(f[10]);
        double industryIncomeScore = parseScoreOrRange(f[11]);
        double internationalOutlookScore = parseScoreOrRange(f[12]);

        return new University(rank, name, location, numStudents, studentPerStaff,
                intlStudentPct, femaleMaleRatio, overallScore, teachingScore,
                researchScore, citationsScore, industryIncomeScore, internationalOutlookScore);
    }

    // Handles values by averaging the two ends. 
    private double parseScoreOrRange(String raw) {
        if (raw == null) return 0.0;
        String s = raw.trim();
        if (s.isEmpty()) return 0.0;
        // dataset uses an en-dash (–) as the range separator, but tolerate a plain hyphen too
        String[] parts = s.split("[\u2013\u2012-]");
        if (parts.length == 2) {
            try {
                double lo = Double.parseDouble(parts[0].trim());
                double hi = Double.parseDouble(parts[1].trim());
                return (lo + hi) / 2.0;
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return parseDoubleSafe(s);
    }

    private double parsePercent(String raw) {
        if (raw == null) return 0.0;
        String s = raw.trim().replace("%", "");
        return parseDoubleSafe(s);
    }

    private String stripCommasAndQuotes(String raw) {
        if (raw == null) return "";
        return raw.replace(",", "").replace("\"", "").trim();
    }

    private int parseIntSafe(String raw) {
        if (raw == null) return 0;
        String s = raw.trim();
        if (s.isEmpty()) return 0;
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            // Some rank values could theoretically appear as ranges too; take the lower bound.
            String[] parts = s.split("[\u2013\u2012-]");
            try {
                return Integer.parseInt(parts[0].trim());
            } catch (Exception ex) {
                return 0;
            }
        }
    }

    private double parseDoubleSafe(String raw) {
        if (raw == null) return 0.0;
        String s = raw.trim();
        if (s.isEmpty()) return 0.0;
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    // Minimal CSV line splitter that respects double-quoted fields
    private String[] splitCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false; // true while we're inside a "..." quoted field

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes; // toggle quote state
            } else if (c == ',' && !inQuotes) {
                // a real field separator (not a comma hiding inside quotes)
                fields.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        fields.add(current.toString()); // the last field has no trailing comma to trigger on
        return fields.toArray(new String[0]);
    }
}
