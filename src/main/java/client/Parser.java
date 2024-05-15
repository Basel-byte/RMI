package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
}
