/*
 * Saksham and Sanskaar - 12/4/25
 *
 * This program reads two text files (child.txt and puff.txt),
 * saves a copy of the original content as "Original_<filename>.txt",
 * then performs a very inefficient single-threaded processing of
 * the text:
 *   - Converts every character to uppercase using repeated string concatenation
 *   - Adds extra loops to slow down processing
 *   - Reverses the text multiple times
 * The processed text is saved as "Modified_<filename>.txt".
 * Timing is measured to show how long the single-threaded processing takes.
 */

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class BookExtraction {

    public static void main(String[] args) {
        String[] files = {"child.txt", "puff.txt"};

        long startTime = System.nanoTime(); // Start timing

        for (String fileName : files) {
            try {
                // Read the entire content of the file
                String content = Files.readString(Paths.get(fileName));

                // Write the original content to a new file
                String originalOutputFile = "Original_" + fileName;
                Files.writeString(Paths.get(originalOutputFile), content);
                System.out.println(fileName + " original content saved as " + originalOutputFile);

                // Inefficient processing: convert all text to uppercase character by character
                // and then rebuild the string using repeated concatenation
                String modified = "";
                for (int i = 0; i < content.length(); i++) {
                    char c = content.charAt(i);

                    // Extra useless loop to make it slower
                    for (int j = 0; j < 100; j++) {
                        c = Character.toUpperCase(c);
                    }

                    // Extremely inefficient: create new string each time
                    modified = modified + c;
                }

                // Additional useless processing: reverse the string multiple times
                for (int k = 0; k < 10; k++) {
                    modified = new StringBuilder(modified).reverse().toString();
                }

                // Write the inefficiently processed string to a new file
                String outputFile = "Modified_" + fileName;
                Files.writeString(Paths.get(outputFile), modified);
                System.out.println(fileName + " processed and saved as " + outputFile);

            } catch (IOException e) {
                System.out.println("Error processing " + fileName + ": " + e.getMessage());
            }
        }

        long endTime = System.nanoTime();
        double seconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Single-threaded inefficient processing completed in " + seconds + " seconds.");
    }
}
