package server;

import rmi.AdderRemoteObject;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            AdderRemoteObject adder = new AdderRemoteObject();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Adder", adder);
            System.out.println("Server is ready!");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
