package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GraphRemoteIF extends Remote {
    int[] batchRequest(List<String[]> batch, int noQueries) throws RemoteException;
}