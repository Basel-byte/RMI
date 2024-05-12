package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Parser {

    private int num_queires;

    public Parser() {
        this.num_queires = 0;
    }

    public int getNum_queires() {
        return num_queires;
    }

    public void reset_num_q(){
        this.num_queires = 0;
    }

    public ArrayList<String[]> prepareRequests(String requestsPath){
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(requestsPath);

        ArrayList<String[]> reqSeq = new ArrayList<String[]>();

        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null && !line.equals("F")) {
                    String[] req = line.split(" ");
                    if (req[0].equals("Q")) this.num_queires++;
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
}
