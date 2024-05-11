package server;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BFSTest {
    private static Graph graph;
    private static BFS bfs;
    @BeforeAll
    static void beforeAll() {
        graph = new Graph();
        bfs = new BFS(graph);
    }

    @Test
    void batch1Test() {
        assertEquals(2, bfs.computeShortestPath( 1, 3));
        graph.addEdge(4, 5);
        assertEquals(3, bfs.computeShortestPath( 1, 5));
        assertEquals(-1, bfs.computeShortestPath(5, 1));
    }

    @Test
    void batch2Test() {
        graph.addEdge(5, 3);
        assertEquals(2, bfs.computeShortestPath(1, 3));
        graph.deleteEdge(2, 3);
        assertEquals(4, bfs.computeShortestPath( 1, 3));
    }
}