package server;

import java.util.ArrayList;

public interface RequestHandlerI {
    Graph graph = null;
    BFS bfs = null;

    int[] computeBatch(ArrayList<String[]> reqSeq) throws InterruptedException;
}
