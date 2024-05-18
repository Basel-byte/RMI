package server;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import client.Client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

public class RMIServer {
    private static Logger logger;

    public static void main(String[] args) {
        try {
            configureLogging();
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
    private static void configureLogging() {
        Properties props = new Properties();
        
        try (InputStream configStream = RMIServer.class.getClassLoader().getResourceAsStream("log4j-server.properties");) {
            props.load(configStream);
            System.out.println("Loaded properties: " + props);
            
    
            // Ensure the output directory exists
            File outputDir = new File("/home/amr/RMI/RMI/output");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
                System.out.println("Created output directory: " + outputDir.getAbsolutePath());
            }
    
            // Set the log file name dynamically
            String logFilePath = outputDir + "/server.log";
            props.setProperty("log4j.appender.file.File", logFilePath);
            System.out.println("Logging to: " + new File(logFilePath).getAbsolutePath());
    
            // Configure logger with the properties
            PropertyConfigurator.configure(props);
    
            // Initialize the logger for this class
            logger = Logger.getLogger(Client.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}