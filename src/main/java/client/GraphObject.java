package client;

import rmi.GraphIF;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class GraphObject implements GraphIF {

    private final GraphIF graph;

    public GraphObject() throws NotBoundException, IOException {
        graph = RMIClient.init();
    }
    @Override
    public int[] batchRequest(List<String[]> batch, int noQueries) throws RemoteException, InterruptedException {
        return graph.batchRequest(batch, noQueries);
    }
}
