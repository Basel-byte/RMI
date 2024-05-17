package client;

import org.apache.log4j.Logger;
import server.GraphRemoteIF;
import server.RMIServer;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

public class RMIClient {
    private static final Logger logger = Logger.getLogger(RMIClient.class.getName());
    public static GraphRemoteIF init() {
        try {
            // Load properties
            InputStream is = RMIServer.class.getClassLoader().getResourceAsStream("system.properties");
            Properties properties = new Properties();
            properties.load(is);

            int port = Integer.parseInt(properties.getProperty("GSP.rmiRegistry.port", "1099"));
            String remoteObjectName = properties.getProperty("GSP.rmiRegistry.remoteObjectName", "RMIServer");
            logger.info("Connecting to port " + port + " with remote object name " + remoteObjectName);

            // Get a reference to the remote object Registry for the localhost on the specified port.
            Registry registry = LocateRegistry.getRegistry(port);

            return (GraphRemoteIF) registry.lookup(remoteObjectName);
        }
        catch (NotBoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
