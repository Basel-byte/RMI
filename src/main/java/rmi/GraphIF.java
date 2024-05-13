package rmi;

public interface GraphIF extends java.rmi.Remote {
    String[] batchRequest(String[] batch) throws java.rmi.RemoteException;
}