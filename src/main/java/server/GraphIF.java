package server;

import java.util.List;

public interface GraphIF extends java.rmi.Remote {
    int[] batchRequest(List<String[]> batch, int noQueries) throws java.rmi.RemoteException, InterruptedException;
}