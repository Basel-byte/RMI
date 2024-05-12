package server;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class BFS_C_Test {
    private static Graph graph;
    private static RequestHandler requestHandler;
    @BeforeAll
    static void beforeAll() {
        graph = new Graph();
        requestHandler = new RequestHandler(graph);
    }

    @Test
    void batch1Test() throws InterruptedException {
        ArrayList<String[]> reqSeq = requestHandler.prepareRequests("input/batch1");
        long start = System.nanoTime();
        assertArrayEquals(new int[]{2, 3, -1}, requestHandler.computeBatch(reqSeq));
        System.out.println("Conccurent: Time taken by test 1 in Nano sec is "+(System.nanoTime()-start));
    }

    @Test
    void batch2Test() throws InterruptedException {
        ArrayList<String[]> reqSeq = requestHandler.prepareRequests("input/batch2");
        long start = System.nanoTime();
        assertArrayEquals(new int[]{2, 4}, requestHandler.computeBatch(reqSeq));
        System.out.println("Conccurent: Time taken by test 2 in Nano sec is "+(System.nanoTime()-start));
    }

    @Test
    void batch3Test() throws InterruptedException {
        ArrayList<String[]> reqSeq = requestHandler.prepareRequests("input/batch3");
        long start = System.nanoTime();
        assertArrayEquals(new int[]{1, 2, 2, 3, 4}, requestHandler.computeBatch(reqSeq));
        System.out.println("Conccurent: Time taken by test 3 in Nano sec is "+(System.nanoTime()-start));
    }
}