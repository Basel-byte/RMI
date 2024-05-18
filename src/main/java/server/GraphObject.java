package server;

import org.apache.log4j.Logger;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;

public class GraphObject extends UnicastRemoteObject implements GraphIF {

    private final RequestHandler requestHandler;
    private static final Logger logger = Logger.getLogger(GraphObject.class.getName());

    public GraphObject(Boolean concurrent) throws java.rmi.RemoteException {
        super();
        Graph graph = new Graph();
        requestHandler = concurrent ? new RequestHandlerConcurrent(graph) : new RequestHandlerSequential(graph);
    }

    @Override
    public int[] batchRequest(List<String[]> batch, int noQueries) throws RemoteException, InterruptedException {
        logger.info("Received: ");
        batch.forEach((r) -> logger.info(Arrays.toString(r)));
        int[] result = requestHandler.computeBatch(batch, noQueries);
        logger.info("Sent: " + Arrays.toString(result));
        return result;
    }
}
