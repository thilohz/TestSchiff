import java.io.*;
import java.util.concurrent.*;
import java.nio.file.*;

public class BookExtraction {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // Create ExecutorService with 2 threads (one for each file)
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Submit tasks for both files
        Future<?> childTask = executor.submit(() -> processFile("child.txt", "child_updated.txt"));
        Future<?> puffTask = executor.submit(() -> processFile("puff.txt", "puff_updated.txt"));

        try {
            // Wait for both tasks to complete
            childTask.get();
            puffTask.get();

            long endTime = System.currentTimeMillis();
            System.out.println("Multi-threaded processing completed in: " + (endTime - startTime) + " ms");

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error during processing: " + e.getMessage());
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    /**
     * Reads a file, converts all text to uppercase, and writes to a new file
     * @param inputFile the source file to read
     * @param outputFile the destination file to write
     */
    private static void processFile(String inputFile, String outputFile) {
        System.out.println(Thread.currentThread().getName() + " - Processing " + inputFile);

        try {
            // Read entire file content
            String content = new String(Files.readAllBytes(Paths.get(inputFile)));

            // Convert to uppercase (creates new String due to immutability)
            String uppercaseContent = content.toUpperCase();

            // Write to output file
            Files.write(Paths.get(outputFile), uppercaseContent.getBytes());

            System.out.println(Thread.currentThread().getName() + " - Completed " + inputFile +
                             " -> " + outputFile);

        } catch (IOException e) {
            System.err.println("Error processing " + inputFile + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}