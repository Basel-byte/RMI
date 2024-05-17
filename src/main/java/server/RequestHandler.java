package server;

import java.util.List;

public abstract class RequestHandler {
    protected Graph graph;
    protected BFS bfs;

    public RequestHandler(Graph graph) {
        this.graph = graph;
        bfs = new BFS(this.graph);
    }
    public abstract int[] computeBatch(List<String[]> reqSeq, int numQueries);
}
