package client;


import rmi.AdderInterface;
import rmi.AdderRemoteObject;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            AdderInterface adder = (AdderInterface) registry.lookup("Adder");
            System.out.println(adder.add(6, 4));
        }
        catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
