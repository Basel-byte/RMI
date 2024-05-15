package rmi;

import server.Graph;
import server.RequestHandler;
import server.RequestHandlerSeq;

import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;

public class GraphObject extends UnicastRemoteObject implements GraphIF {

    Graph graph;
    RequestHandler requestHandler;
    public GraphObject() throws java.rmi.RemoteException {
        super();
        graph = new Graph();
        requestHandler = new RequestHandlerSeq(graph);
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
