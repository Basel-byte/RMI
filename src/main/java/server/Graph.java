package server;

import java.io.*;
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
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("input/initialGraph");
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null && !line.equals("S")) {
                    int[] edge = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
                    addEdge(edge[0], edge[1]);
                }
            } catch (IOException e) {
                System.err.println("Error reading from file: " + e.getMessage());
            }
        } else {
            System.err.println("Initial graph file not found!");
        }
    }
    protected void addEdge(int u, int v) {
        noOfEdges += adjList.computeIfAbsent(u, k -> new HashSet<>()).add(v) ? 1 : 0;
        nodes.add(u);
        nodes.add(v);
    }

    protected void deleteEdge(int u, int v) {
        if (adjList.containsKey(u))
            noOfEdges += adjList.get(u).remove(v) ? -1 : 0;
    }

    protected boolean containsNode(int node) {
        return nodes.contains(node);
    }

    protected int getNoOfNodes() {
        return nodes.size();
    }

    protected int getNoOfEdges() {
        return noOfEdges;
    }

    protected Set<Integer> getNeighbors(int node) {
        return adjList.getOrDefault(node, new HashSet<>());
    }
}
