package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class Start {

    public static void main(String[] args) {
        try {
            // Load system properties from file
            Properties systemProps = new Properties();
            InputStream in = Start.class.getClassLoader().getResourceAsStream("system.properties");
            systemProps.load(in);
            in.close();

            // Read properties
            String serverAddress = systemProps.getProperty("GSP.server");
            System.out.println("Server address: " + serverAddress);
            int serverPort = Integer.parseInt(systemProps.getProperty("GSP.server.port"));
            System.out.println("Server port: " + serverPort);
            String username = systemProps.getProperty("GSP.username");
            int numberOfNodes = systemProps.getProperty("GSP.numberOfnodes") == null ? 0 : Integer.parseInt(systemProps.getProperty("GSP.numberOfnodes"));
            String password = systemProps.getProperty("GSP.password");
            SSHExecutor sshExecutor = new SSHExecutor();
            // Create a CountDownLatch with a count of 1
            CountDownLatch latch = new CountDownLatch(1);
            String systemPath = System.getProperty("user.dir");
            System.out.println(systemPath);
            Thread serverThread = new Thread(() -> sshExecutor.executeSingleCommand(username, serverAddress, password, "java -jar "+ systemPath +"/out/artifacts/server_jar/RMI.jar", latch));
            serverThread.start();
            latch.await();
            System.out.println("Server started");
            System.out.println("number of nodes: " + numberOfNodes);


            // Start clients
            for (int i = 0; i < numberOfNodes; i++) {
                int index = i;
                System.out.println("Start client " + index);
                Thread clientThread = new Thread(() -> {
                    String clientHost = systemProps.getProperty("GSP.node" + index);
                    System.out.println("Start client " + index + " on " + clientHost);
                    sshExecutor.executeSingleCommand(username, clientHost, password, "java -jar "+ systemPath + "/out/artifacts/client_jar/RMI.jar", latch);
                });
                clientThread.start();
            }


        } catch (IOException e) {
            System.err.println("Error loading system properties: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
