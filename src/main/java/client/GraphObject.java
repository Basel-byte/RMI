package client;

import rmi.GraphIF;
import server.RMIServer;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Properties;

public class GraphObject implements GraphIF {

    private final GraphIF graph;

    public GraphObject() throws NotBoundException, IOException {
        graph = RMIClient.init();
    }
    @Override
    public int[] batchRequest(List<String> batch) throws RemoteException, InterruptedException {
        return graph.batchRequest(batch);
    }
}
