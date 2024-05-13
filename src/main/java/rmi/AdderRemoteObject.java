package rmi;

import java.rmi.server.UnicastRemoteObject;

public class AdderRemoteObject extends UnicastRemoteObject implements AdderInterface {
    public AdderRemoteObject() throws java.rmi.RemoteException {
        super();
    }

    @Override
    public int add(int x, int y) throws java.rmi.RemoteException {
        return x + y;
    }
}
