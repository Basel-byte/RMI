package server;

import java.util.ArrayList;

public abstract class RequestHandler {
    protected Graph graph;
    protected BFS bfs;

    public RequestHandler(Graph graph) {
        this.graph = graph;
        bfs = new BFS(this.graph);
    }
    public abstract int[] computeBatch(ArrayList<String[]> reqSeq) throws InterruptedException;
}
