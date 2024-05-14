package server;

import rmi.GraphObject;

import java.io.FileInputStream;
import java.io.InputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

public class RMIServer {
    public static void main(String[] args) {
        try {
            // Load properties
            InputStream is = RMIServer.class.getClassLoader().getResourceAsStream("system.properties");
            Properties properties = new Properties();
            properties.load(is);

            int port = Integer.parseInt(properties.getProperty("GSP.rmiRegistry.port", "1099"));
            String remoteObjectName = properties.getProperty("GSP.rmiRegistry.remoteObjectName", "RMIServer");

            // Create a Registry instance on localhost that accepts requests on the specified port
            Registry registry = LocateRegistry.createRegistry(port);

            // Registering a name for the remote object
            GraphObject graphObject = new GraphObject();
            registry.bind(remoteObjectName, graphObject);

            System.out.println("Server is listening on port " + port + " with remote object name " + remoteObjectName);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}