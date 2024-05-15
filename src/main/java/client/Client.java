package client;

import rmi.GraphIF;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Arrays;
import java.util.List;

public class Client {
    public static void main(String[] args) throws NotBoundException, IOException, InterruptedException {
        GraphIF graph = new GraphObject();
        // Generate a test batch
        List<String> batch = List.of("Q 1 3", "A 4 5", "Q 1 5", "Q 5 1", "F");

        System.out.println("Sent: " + batch);
        int[] result = graph.batchRequest(batch);
        System.out.println("Received: " + Arrays.toString(result));
    }
}
