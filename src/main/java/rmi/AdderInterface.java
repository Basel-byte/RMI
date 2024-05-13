package rmi;

public interface AdderInterface extends java.rmi.Remote {
    int add(int x, int y) throws java.rmi.RemoteException;
}
