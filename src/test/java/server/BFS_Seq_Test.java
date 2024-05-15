package server;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BFS_Seq_Test {
    private static RequestHandler requestHandler;
    private static Parser parser;
    @BeforeAll
    static void beforeAll() {
        Graph graph = new Graph();
        requestHandler = new RequestHandlerSeq(graph);
        parser = new Parser();
    }

    @Test
    void batch1Test() throws InterruptedException {
        ArrayList<String[]> reqSeq = parser.prepareRequests("input/batch1");
        assertArrayEquals(new int[]{2, 3, -1}, requestHandler.computeBatch(reqSeq));
    }

    @Test
    void batch2Test() throws InterruptedException {
        ArrayList<String[]> reqSeq = parser.prepareRequests("input/batch2");
        assertArrayEquals(new int[]{2, 4}, requestHandler.computeBatch(reqSeq));
    }

    @Test
    void batch3Test() throws InterruptedException {
        ArrayList<String[]> reqSeq = parser.prepareRequests("input/batch3");
        assertArrayEquals(new int[]{1, 2, 2, 3, 4}, requestHandler.computeBatch(reqSeq));
    }
}