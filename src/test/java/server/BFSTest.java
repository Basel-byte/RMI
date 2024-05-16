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
        long start = System.nanoTime();
        assertEquals(2, bfs.computeShortestPath( 1, 3));
        graph.addEdge(4, 5);
        assertEquals(3, bfs.computeShortestPath( 1, 5));
        assertEquals(-1, bfs.computeShortestPath(5, 1));
        System.out.println("Sequential: Time taken by test 1 in Nano sec is "+(System.nanoTime()-start));
    }

    @Test
    void batch2Test() {
        long start = System.nanoTime();
        graph.addEdge(5, 3);
        assertEquals(2, bfs.computeShortestPath(1, 3));
        graph.deleteEdge(2, 3);
        assertEquals(4, bfs.computeShortestPath( 1, 3));
        System.out.println("Sequential: Time taken by test 2 in Nano sec is "+(System.nanoTime()-start));
    }

    @Test
    void batch3Test() throws InterruptedException {
        long start = System.nanoTime();
        assertEquals(1, bfs.computeShortestPath(4, 1));
        assertEquals(2, bfs.computeShortestPath(4, 3));
        assertEquals(2, bfs.computeShortestPath(4, 2));
        graph.deleteEdge(4, 1);
        assertEquals(3, bfs.computeShortestPath( 4, 1));
        assertEquals(4, bfs.computeShortestPath( 4, 2));
        System.out.println("Sequential: Time taken by test 3 in Nano sec is "+(System.nanoTime()-start));
    }

}