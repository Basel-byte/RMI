package config;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class Start {

    private static final Logger logger = Logger.getLogger(Start.class.getName());

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream in = new FileInputStream("/home/amr/RMI/RMI/src/main/resources/system.properties")) {
            
            properties.load(in);
        } catch (IOException e) {
            logger.error("Error loading properties: " + e.getMessage());
            return null;
        }
        return properties;
    }

    private static void startServer(SSHExecutor sshExecutor, String username, String serverAddress, String password, String systemPath, CountDownLatch latch) {
        Thread serverThread = new Thread(() -> {
            sshExecutor.executeSingleCommand(username, serverAddress, password, "java -jar " + systemPath + "/out/artifacts/server_jar/RMI.jar", latch);
        });
        serverThread.start();
    }

    private static void startClients(SSHExecutor sshExecutor, String username, String password, String systemPath, int numberOfNodes, Properties systemProps, CountDownLatch latch) {
        for (int i = 0; i < numberOfNodes; i++) {
            int index = i;
            String clientHost = systemProps.getProperty("GSP.node" + index);
            Thread clientThread = new Thread(() -> {
                logger.info("Starting client " + index + " on " + clientHost);
                sshExecutor.executeSingleCommand(username, clientHost, password, "java -jar " + systemPath + "/out/artifacts/client_jar/RMI.jar " + index, latch);
            });
            clientThread.start();
        }
    }
    public static void main(String[] args) {
        Properties systemProps = loadProperties();
        if (systemProps == null) {
            return;
        }

        String serverAddress = systemProps.getProperty("GSP.server");
        int serverPort = Integer.parseInt(systemProps.getProperty("GSP.server.port", "0"));
        String username = systemProps.getProperty("GSP.username");
        int numberOfNodes = Integer.parseInt(systemProps.getProperty("GSP.numberOfnodes", "0"));
        String password = systemProps.getProperty("GSP.password");

        logger.info("Server address: " + serverAddress);
        logger.info("Server port: " + serverPort);
        logger.info("Username: " + username);
        logger.info("Password: " + password);

        CountDownLatch latch = new CountDownLatch(1);
        String systemPath = System.getProperty("user.dir");
        logger.info(systemPath);

        SSHExecutor sshExecutor = new SSHExecutor();

        startServer(sshExecutor, username, serverAddress, password, systemPath, latch);

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Latch interrupted: " + e.getMessage());
            return;
        }

        logger.info("Server started");
        logger.info("Number of nodes: " + numberOfNodes);

        startClients(sshExecutor, username, password, systemPath, numberOfNodes, systemProps, latch);
    }
}
