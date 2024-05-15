package client;

import rmi.GraphIF;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Arrays;
import java.util.List;

public class Client {
    public static void main(String[] args) throws NotBoundException, IOException, InterruptedException {
        GraphIF graph = new GraphObject();
        // Trying a test batch
        List<String[]> batch = Parser.prepareRequests("input/batch1");
        int numQueries = Parser.getNumQueries();

        System.out.println("Sent: ");
        batch.forEach((r) -> System.out.println(Arrays.toString(r)));
        int[] result = graph.batchRequest(batch, numQueries);
        System.out.println("Received: " + Arrays.toString(result));
    }
}
