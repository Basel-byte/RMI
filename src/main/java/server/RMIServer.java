package server;

import rmi.GraphObject;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            GraphObject graphObject = new GraphObject();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("graph", graphObject);
            System.out.println("Server is ready!");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
