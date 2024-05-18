package perfomance_analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static List<Double> responseTimes = new ArrayList<Double>();

    private static void parseResponseTime(String logPath) {
        File dir = new File(logPath);

        String regex = "Elapsed time: (\\d+)";
        Pattern pattern = Pattern.compile(regex);

        // Loop through files in the directory
        for (File file : dir.listFiles()) {
            if (file.isFile() && file.getName().contains("client")) {
              try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                  // Find the match in the log line
                  Matcher matcher = pattern.matcher(line);
                  if (matcher.find()) {
                    // Extract and convert time taken (milliseconds)
                    String timeTakenStr = matcher.group(1);
                    double timeTaken = Double.parseDouble(timeTakenStr);
                    responseTimes.add(timeTaken);
                  }
                }
              } catch (IOException e) {
                System.err.println("Error reading file: " + file.getName() + ", " + e.getMessage());
              } finally {
                // Delete the processed file in the finally block
                if (file.delete()) {
                  System.out.println("Deleted file: " + file.getName());
                } else {
                  System.err.println("Error deleting file: " + file.getName());
                }
              }
            }
          }
    }

    private static double getMedianResponseTime() {
        Collections.sort(responseTimes);
        return responseTimes.get(responseTimes.size() / 2);
    }

    private static double getAverageResponseTime() {
        double ans = 0;
        for (int i = 0; i < responseTimes.size(); i++) {
            ans += (double) responseTimes.get(i) / responseTimes.size();
        }
        return ans;
    }

    public static void main(String[] args) {
        String currentDir = System.getProperty("user.dir");
        parseResponseTime(currentDir + "/output/");
        System.out.println(getAverageResponseTime());
        System.out.println(getMedianResponseTime());
    }
}
