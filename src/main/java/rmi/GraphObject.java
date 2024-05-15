package rmi;

import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class GraphObject extends UnicastRemoteObject implements GraphIF {
    public GraphObject() throws java.rmi.RemoteException {
        super();
    }

    @Override
    public String[] batchRequest(String[] batch) throws java.rmi.RemoteException {
        // TODO: Implement this method
        System.out.println("Received: " + Arrays.toString(batch));
        return batch;
    }
}
