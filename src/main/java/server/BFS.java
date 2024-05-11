package server;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class BFS {
    private final Graph graph;
    private final Map<Integer, Map<Integer, Integer>> cache;
    private int currNoOfEdges;
    public BFS(Graph graph) {
        this.graph = graph;
        cache = new HashMap<>();
        currNoOfEdges = graph.getNoOfEdges();
    }

    protected int computeShortestPath(int s, int t) {
        invalidateCacheIfNecessary();
        if (!graph.containsNode(s) || !graph.containsNode(t))
            return -1;
        if (s == t)
            return 0;
        if (cache.containsKey(s) && cache.get(s).containsKey(t))
            return cache.get(s).get(t);

        Queue<Integer> q = new ArrayDeque<>();
        Map<Integer, Integer> d = new HashMap<>();
        var localCache = cache.computeIfAbsent(s, k -> new HashMap<>());
        q.add(s);
        d.put(s, 0);

        while (!q.isEmpty()) {
            int node = q.poll();
            for (int neighbor : graph.getNeighbors(node)) {
                if (!d.containsKey(neighbor)) {
                    d.put(neighbor, d.get(node) + 1);
                    localCache.put(neighbor, d.get(neighbor));
                    if (neighbor == t)
                        return d.get(neighbor);
                    q.add(neighbor);
                }
            }
        }
        return -1;
    }

    private void invalidateCacheIfNecessary() {
        if (currNoOfEdges != graph.getNoOfEdges()) {
            cache.clear();
            currNoOfEdges = graph.getNoOfEdges();
        }
    }
}
