package server;

import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;

public class GraphObject extends UnicastRemoteObject implements GraphIF {

    Graph graph;
    RequestHandler requestHandler;
    public GraphObject(Boolean concurrent) throws java.rmi.RemoteException {
        super();
        graph = new Graph();
        requestHandler = concurrent ? new RequestHandlerConcurrent(graph) : new RequestHandlerSequential(graph);
    }

    @Override
    public int[] batchRequest(List<String[]> batch, int noQueries) throws java.rmi.RemoteException, InterruptedException {
        System.out.println("Received: ");
        batch.forEach((r) -> System.out.println(Arrays.toString(r)));

        int[] result = requestHandler.computeBatch(batch, noQueries);

        System.out.println("Sent: " + Arrays.toString(result));
        return result;
    }
}
