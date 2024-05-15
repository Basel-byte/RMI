package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Parser {
    private int num_queries;

    public Parser() {
        this.num_queries = 0;
    }

    public int getNum_queries() {
        return num_queries;
    }

    public void reset_num_q(){
        this.num_queries = 0;
    }

    public List<String[]> prepareRequests(String requestsPath){
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(requestsPath);

        ArrayList<String[]> reqSeq = new ArrayList<String[]>();

        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null && !line.equals("F")) {
                    String[] req = line.split(" ");
                    if (req[0].equals("Q")) this.num_queries++;
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
    public List<String[]> prepareRequests(List<String> batch){
        List<String[]> reqSeq = new ArrayList<>();
        Iterator<String> it = batch.iterator();
        String line;
        while (it.hasNext() && !(line = it.next()).equals("F")) {
            String[] req = line.split(" ");
            if (req[0].equals("Q")) this.num_queries++;
            reqSeq.add(req);
        }
        return reqSeq;
    }
}
