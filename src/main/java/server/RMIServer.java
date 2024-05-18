package server;

import org.apache.log4j.Logger;
import java.io.InputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

public class RMIServer {
    private static final Logger logger = Logger.getLogger(RMIServer.class.getName());

    public static void main(String[] args) {
        try {
            // Load properties
            InputStream is = RMIServer.class.getClassLoader().getResourceAsStream("system.properties");
            Properties properties = new Properties();
            properties.load(is);

            int port = Integer.parseInt(properties.getProperty("GSP.rmiRegistry.port", "1099"));
            String remoteObjectName = properties.getProperty("GSP.rmiRegistry.remoteObjectName", "RMIServer");
            boolean concurrent = Boolean.parseBoolean(properties.getProperty("GSP.server.batch.concurrent", "false"));

            // Create a Registry instance on localhost that accepts requests on the specified port
            Registry registry = LocateRegistry.createRegistry(port);

            // Registering a name for the remote object
            GraphObject graphObject = new GraphObject(concurrent);
            registry.bind(remoteObjectName, graphObject);
            System.out.println("Graph initialized successfully!");
            logger.info("RMIServer is listening on port " + port + " with remote object name " + remoteObjectName);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}