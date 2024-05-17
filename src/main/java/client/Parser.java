package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Parser {

    private static int numQueries = 0;


    public static int getNumQueries() {
        return numQueries;
    }

    private static void resetNumQueries(){
        numQueries = 0;
    }
    public static List<String[]> prepareRequests(String requestsPath) {
        resetNumQueries();
        ClassLoader classLoader = Parser.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(requestsPath);

        List<String[]> reqSeq = new ArrayList<>();

        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null && !line.equals("F")) {
                    String[] req = line.split(" ");
                    if (req[0].equals("Q")) numQueries++;
                    reqSeq.add(req);
                }
            } catch (IOException e) {
                System.err.println("Error reading from file: " + e.getMessage());
            }
        } else {
            System.err.println("Initial batch file not found!");
        }
        return reqSeq;
    }

    public static List<String[]> prepareRequests(List<String> batch) {
        resetNumQueries();
        List<String[]> reqSeq = new ArrayList<>();
        Iterator<String> it = batch.iterator();
        String line;
        while (it.hasNext() && !(line = it.next()).equals("F")) {
            String[] req = line.split(" ");
            if (req[0].equals("Q")) numQueries++;
            reqSeq.add(req);
        }
        return reqSeq;
    }

    public static void writeResultToFile(String resultFileName, int[] result) {
        try {

            // Get the root path of the project
            Path outputDirPath = Path.of(Paths.get("").toAbsolutePath() + "/output");

            // Ensure the output directory exists
            if (!Files.exists(outputDirPath)) {
                Files.createDirectories(outputDirPath);
            }

            // Construct the full path to the result file
            Path resultFilePath = outputDirPath.resolve("result" + resultFileName);

            // Convert the result array to a string with newline characters
            StringBuilder sb = new StringBuilder();
            for (int i : result) {
                sb.append(i).append("\n");
            }
            sb.deleteCharAt(sb.length() - 1); // Remove the last newline character

            // Write the result to the file
            Files.write(resultFilePath, sb.toString().getBytes());

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
