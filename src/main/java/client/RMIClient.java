package client;

import rmi.GraphIF;
import server.RMIServer;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class RMIClient {
    public static void main(String[] args) {
        try {
            // Load properties
            InputStream is = RMIServer.class.getClassLoader().getResourceAsStream("system.properties");
            Properties properties = new Properties();
            properties.load(is);

            int port = Integer.parseInt(properties.getProperty("GSP.rmiRegistry.port", "1099"));
            String remoteObjectName = properties.getProperty("GSP.rmiRegistry.remoteObjectName", "RMIServer");
            System.out.println("Connecting to port " + port + " with remote object name " + remoteObjectName);

            // Get a reference to the remote object Registry for the localhost on the specified port.
            Registry registry = LocateRegistry.getRegistry(port);
            GraphIF graph = (GraphIF) registry.lookup(remoteObjectName);

            // Generate a test batch
            List<String> batch = List.of("Q 1 3", "A 4 5", "Q 1 5", "Q 5 1", "F");

            System.out.println("Sent: " + batch);
            int[] result = graph.batchRequest(batch);
            System.out.println("Received: " + Arrays.toString(result));
        }
        catch (NotBoundException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
