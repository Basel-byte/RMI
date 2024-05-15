package rmi;

import server.Graph;
import server.RequestHandler;
import server.RequestHandlerSeq;

import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class GraphObject extends UnicastRemoteObject implements GraphIF {

    Graph graph;
    RequestHandler requestHandler;
    public GraphObject() throws java.rmi.RemoteException {
        super();
        graph = new Graph();
        requestHandler = new RequestHandlerSeq(graph);
    }

    @Override
    public String[] batchRequest(String[] batch) throws java.rmi.RemoteException {
        // TODO: implement method
        System.out.println("Received: " + Arrays.toString(batch));
        return batch;
    }
}
