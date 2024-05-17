package client;

import server.GraphRemoteIF;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class GraphObject implements GraphRemoteIF {

    private final GraphRemoteIF graph;

    public GraphObject() {
        graph = RMIClient.init();
    }
    @Override
    public int[] batchRequest(List<String[]> batch, int noQueries) throws RemoteException {
        return graph.batchRequest(batch, noQueries);
    }
}
