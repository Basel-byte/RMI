package server;

import org.apache.log4j.Logger;

import java.util.List;

public class RequestHandlerSequential extends RequestHandler {
    private static final Logger logger = Logger.getLogger(RequestHandlerSequential.class.getName());

    public RequestHandlerSequential(Graph graph) {
        super(graph);
    }

    @Override
    public synchronized int[] computeBatch(List<String[]> reqSeq, int numQueries) {
        long start = System.nanoTime();

        int[] results = new int[numQueries];
        int i=0;

        for(String[] req : reqSeq){
            switch (req[0]) {
                case "Q" -> {
                    results[i] = bfs.computeShortestPath(Integer.parseInt(req[1]), Integer.parseInt(req[2]));
                    i++;
                }
                case "A" -> graph.addEdge(Integer.parseInt(req[1]), Integer.parseInt(req[2]));
                case "D" -> graph.deleteEdge(Integer.parseInt(req[1]), Integer.parseInt(req[2]));
            }
        }

        logger.info("Sequential: Time taken in Nano sec is "+(System.nanoTime()-start));

        return results;
    }

}
