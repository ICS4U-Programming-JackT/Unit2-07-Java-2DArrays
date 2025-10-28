import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * Program that reads names/assignments from a file, converts
 * them into a 2d array and exports it to a csv file.
 *
 * @author Jack
 * @version 1.0
 * @since 2025-10-24
 */
public final class TwoDimArrays {

    /** Private constructor to prevent instantiation. */
    private TwoDimArrays() {
        throw new IllegalStateException("Utility Class");
    }

    /**
     * Convert a file's contents into a 1d array.
     * @return Array
     * @param file File input
     */
    public static String[] fileToArray(final File file) {
        try {
            // Initialize scanner and array list
            Scanner fileScanner = new Scanner(file);
            ArrayList<String> arrayToReturn = new ArrayList<>();

            // Read each line and add to array list
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                arrayToReturn.add(line);
            }

            //Close scanner
            fileScanner.close();

            // Convert array list to array and return
            String[] linesArray = new String[arrayToReturn.size()];
            for (int i = 0; i < linesArray.length; i++) {
                linesArray[i] = arrayToReturn.get(i);
            }
            return linesArray;
        } catch (FileNotFoundException error) {
            // Print error message if file not found
            System.out.println(
                "\nError: A crucial file was not found."
                + " Please ensure it exists in the same directory."
            );
            return new String[0];
        }
    }

    /**
     * Converts 2d array into csv file.
     * @param data stores students x assignments
     * @param filePath path to save csv
     */
    public static void writeArrayToCsv(final String[][] data,
            final String filePath) {
        // Try writing to file
        try (BufferedWriter writer = new
        BufferedWriter(new FileWriter(filePath))) {
            // Loop through 2d array and write to file
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    writer.write(data[i][j]);
                    if (j < data[i].length - 1) {
                        writer.write(", ");
                    }
                }
                // Write each line
                writer.newLine();
            }
            // Let user know csv file has been populated
            System.out.println(
                "2D array successfully written to CSV file: "
                + filePath
            );
        } catch (IOException e) {
            // Print error for CSV file
            System.err.println(
                "Error writing to CSV file: " + e.getMessage()
            );
        }
    }

    /**
     * Main entry point.
     * @param args command-line arguments (not used)
     */
    public static void main(final String[] args) {
        // Initialize random and file arrays
        Random rand = new Random();

        File file1 = new File("students.txt");
        String[] students = fileToArray(file1);

        File file2 = new File("assignments.txt");
        String[] assignments = fileToArray(file2);

        // If both arrays have data, create 2d array
        if (students.length > 0 && assignments.length > 0) {
            // Initialize 2d array
            String[][] grades = new String[students.length + 1]
            [assignments.length + 1];

            // Add header to 2d array
            grades[0][0] = "Names/Assignments";

            // Add studentsto 2d array
            for (int r = 1; r <= students.length; r++) {
                grades[r][0] = students[r - 1];
            }

            // Add assignments to 2d array
            for (int c = 1; c <= assignments.length; c++) {
                grades[0][c] = assignments[c - 1];
            }

            // Add numbers to 2d array
            for (int y = 1; y <= students.length; y++) {
                for (int x = 1; x <= assignments.length; x++) {
                    int randomInt = (int) (rand.nextGaussian() * 10 + 75);
                    grades[y][x] = String.valueOf(randomInt);
                }
            }

            // Write 2d array to csv file
            writeArrayToCsv(grades, "grades.csv");
        }
    }
}
