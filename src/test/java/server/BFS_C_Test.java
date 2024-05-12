package server;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class BFS_C_Test {
    private static Graph graph;
    private static RequestHandler requestHandler;
    private static Parser parser;
    @BeforeAll
    static void beforeAll() {
        graph = new Graph();
        requestHandler = new RequestHandler(graph);
        parser = new Parser();
    }

    @Test
    void batch1Test() throws InterruptedException {
        ArrayList<String[]> reqSeq = parser.prepareRequests("input/batch1");
        int num_queries = parser.getNum_queires();
        parser.reset_num_q();
        long start = System.nanoTime();
        assertArrayEquals(new int[]{2, 3, -1}, requestHandler.computeBatch(reqSeq, num_queries));
        System.out.println("Conccurent: Time taken by test 1 in Nano sec is "+(System.nanoTime()-start));
    }

    @Test
    void batch2Test() throws InterruptedException {
        ArrayList<String[]> reqSeq = parser.prepareRequests("input/batch2");
        int num_queries = parser.getNum_queires();
        parser.reset_num_q();
        long start = System.nanoTime();
        assertArrayEquals(new int[]{2, 4}, requestHandler.computeBatch(reqSeq, num_queries));
        System.out.println("Conccurent: Time taken by test 2 in Nano sec is "+(System.nanoTime()-start));
    }

    @Test
    void batch3Test() throws InterruptedException {
        ArrayList<String[]> reqSeq = parser.prepareRequests("input/batch3");
        int num_queries = parser.getNum_queires();
        parser.reset_num_q();
        long start = System.nanoTime();
        assertArrayEquals(new int[]{1, 2, 2, 3, 4}, requestHandler.computeBatch(reqSeq, num_queries));
        System.out.println("Conccurent: Time taken by test 3 in Nano sec is "+(System.nanoTime()-start));
    }
}