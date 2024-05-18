package client;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import server.GraphIF;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;


public class Client {
    private static Logger logger;

    public static void main(String[] args) throws NotBoundException, IOException, InterruptedException {
        GraphIF graph = new GraphObject();
        int client_id = Integer.parseInt(args[0]);
        configureLogging(client_id);
        while (true) {
            // Prepare a test batch
            int batchNumber = new Random().nextInt(6) + 1;
            List<String[]> batch = Utils.prepareRequests(Utils.generateRandomBatch(0.5, 100));
            int numQueries = Utils.getNumQueries();

            logger.info("Sent:");
            batch.forEach((r) -> logger.info(Arrays.toString(r)));

            // Measure start time
            long startTime = System.currentTimeMillis();

            // Send batch request and get the result
            int[] result = graph.batchRequest(batch, numQueries);

            // Measure elapsed time
            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.info("Elapsed time: " + elapsedTime + " milliseconds");

            logger.info("Received: " + Arrays.toString(result));

            Utils.writeResultToFile("" + batchNumber, result);

            // Random sleep between 1 and 5 seconds before next request
            //Random random = new Random();
            //int sleepTime = 5000;
            //logger.info("Sleeping for " + sleepTime + " milliseconds.");
            //Thread.sleep(sleepTime);
        }

    }

    private static void configureLogging(int clientId) {
        Properties props = new Properties();
        // Path to the log4j-client.properties file
        String propertiesFilePath = "/home/amr/RMI/RMI/src/main/resources/log4j-client.properties";
        System.out.println("Loading properties file from: " + new File(propertiesFilePath).getAbsolutePath());
        
        try (InputStream configStream = new FileInputStream(propertiesFilePath)) {
            props.load(configStream);
            System.out.println("Loaded properties: " + props);
            
    
            // Ensure the output directory exists
            File outputDir = new File("/home/amr/RMI/RMI/output");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
                System.out.println("Created output directory: " + outputDir.getAbsolutePath());
            }
    
            // Set the log file name dynamically
            String logFilePath = outputDir + "/client" + clientId + ".log";
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
