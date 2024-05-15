package rmi;

import java.util.List;

public interface GraphIF extends java.rmi.Remote {
    int[] batchRequest(List<String> batch) throws java.rmi.RemoteException, InterruptedException;
}