package server;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import client.Utils;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BFS_Seq_Test {
    private static RequestHandler requestHandler;
    @BeforeAll
    static void beforeAll() {
        Graph graph = new Graph();
        requestHandler = new RequestHandlerSequential(graph);
    }

    @Test
    void batch1Test() throws InterruptedException {
        List<String[]> reqSeq = Utils.prepareRequests("input/batch1");
        assertArrayEquals(new int[]{2, 3, -1}, requestHandler.computeBatch(reqSeq, Utils.getNumQueries()));
    }

    @Test
    void batch2Test() throws InterruptedException {
        List<String[]> reqSeq = Utils.prepareRequests("input/batch2");
        assertArrayEquals(new int[]{2, 4}, requestHandler.computeBatch(reqSeq, Utils.getNumQueries()));
    }

    @Test
    void batch3Test() throws InterruptedException {
        List<String[]> reqSeq = Utils.prepareRequests("input/batch3");
        assertArrayEquals(new int[]{1, 2, 2, 3, 4}, requestHandler.computeBatch(reqSeq, Utils.getNumQueries()));
    }
}