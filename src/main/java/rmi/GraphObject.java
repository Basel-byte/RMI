package rmi;

import server.Graph;
import server.Parser;
import server.RequestHandler;
import server.RequestHandlerSeq;

import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;

public class GraphObject extends UnicastRemoteObject implements GraphIF {

    Graph graph;
    RequestHandler requestHandler;
    Parser parser;
    public GraphObject() throws java.rmi.RemoteException {
        super();
        graph = new Graph();
        requestHandler = new RequestHandlerSeq(graph);
        parser = new Parser();
    }

    @Override
    public int[] batchRequest(List<String> batch) throws java.rmi.RemoteException, InterruptedException {
        // TODO: implement method
        System.out.println("Received: " + batch.toString());
        int[] result = requestHandler.computeBatch(parser.prepareRequests(batch));
        System.out.println("Sent: " + Arrays.toString(result));
        return result;
    }
}
