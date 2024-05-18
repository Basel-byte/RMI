package server;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class BFS {
    private final Graph graph;
    public BFS(Graph graph) {
        this.graph = graph;
    }

    protected int computeShortestPath(int s, int t) {
        if (!graph.containsNode(s) || !graph.containsNode(t))
            return -1;
        if (s == t)
            return 0;

        Queue<Integer> q = new ArrayDeque<>();
        Map<Integer, Integer> d = new HashMap<>();
        q.add(s);
        d.put(s, 0);

        while (!q.isEmpty()) {
            int node = q.poll();
            for (int neighbor : graph.getNeighbors(node)) {
                if (!d.containsKey(neighbor)) {
                    d.put(neighbor, d.get(node) + 1);
                    if (neighbor == t)
                        return d.get(neighbor);
                    q.add(neighbor);
                }
            }
        }
        return -1;
    }
}
