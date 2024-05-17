package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class Start {

    private static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        try (InputStream in = Start.class.getClassLoader().getResourceAsStream(fileName)) {
            if (in == null) {
                throw new IOException(fileName + " file not found");
            }
            properties.load(in);
        } catch (IOException e) {
            System.err.println("Error loading properties: " + e.getMessage());
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
                System.out.println("Start client " + index + " on " + clientHost);
                sshExecutor.executeSingleCommand(username, clientHost, password, "java -jar " + systemPath + "/out/artifacts/client_jar/RMI.jar", latch);
            });
            clientThread.start();
        }
    }
    public static void main(String[] args) {
        Properties systemProps = loadProperties("system.properties");
        if (systemProps == null) {
            return;
        }

        String serverAddress = systemProps.getProperty("GSP.server");
        int serverPort = Integer.parseInt(systemProps.getProperty("GSP.server.port", "0"));
        String username = systemProps.getProperty("GSP.username");
        int numberOfNodes = Integer.parseInt(systemProps.getProperty("GSP.numberOfnodes", "0"));
        String password = systemProps.getProperty("GSP.password");

        System.out.println("Server address: " + serverAddress);
        System.out.println("Server port: " + serverPort);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        CountDownLatch latch = new CountDownLatch(1);
        String systemPath = System.getProperty("user.dir");
        System.out.println(systemPath);

        SSHExecutor sshExecutor = new SSHExecutor();

        startServer(sshExecutor, username, serverAddress, password, systemPath, latch);

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Latch interrupted: " + e.getMessage());
            return;
        }

        System.out.println("Server started");
        System.out.println("Number of nodes: " + numberOfNodes);

        startClients(sshExecutor, username, password, systemPath, numberOfNodes, systemProps, latch);
    }
}
