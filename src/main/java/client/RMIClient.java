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
    public static GraphIF init() {
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

            return (GraphIF) registry.lookup(remoteObjectName);
        }
        catch (NotBoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
