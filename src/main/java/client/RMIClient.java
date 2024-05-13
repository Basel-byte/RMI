package client;

import rmi.GraphIF;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class RMIClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            GraphIF graph = (GraphIF) registry.lookup("graph");

            // Generate batch
            String[] batch = {"Q 1 3", "A 4 5", "Q 1 5", "Q 5 1", "F"};

            String[] result = graph.batchRequest(batch);

            System.out.println("Sent: " + Arrays.toString(result));
        }
        catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
