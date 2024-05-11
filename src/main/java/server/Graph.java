package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
    private final Map<Integer, Set<Integer>> adjList;
    private final Set<Integer> nodes;
    private int noOfEdges;

    public Graph() {
        adjList = new HashMap<>();
        nodes = new HashSet<>();
        noOfEdges = 0;
        instantiate();
    }

    private void instantiate() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/input/initialGraph"))) {
            String line;
            while ((line = reader.readLine()) != null && !line.equals("S")) {
                int[] edge = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
                addEdge(edge[0], edge[1]);
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }

    private void addEdge(int u, int v) {
        noOfEdges += adjList.computeIfAbsent(u, k -> new HashSet<>()).add(v) ? 1 : 0;
        nodes.add(u);
        nodes.add(v);
    }

    private void removeEdge(int u, int v) {
        if (adjList.containsKey(u))
            noOfEdges += adjList.get(u).remove(v) ? -1 : 0;
        nodes.remove(u);
        nodes.remove(v);
    }
}
