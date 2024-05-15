package server;

import java.util.List;

public class RequestHandlerSeq extends RequestHandler {

    public RequestHandlerSeq(Graph graph) {
        super(graph);
    }

    @Override
    public int[] computeBatch(List<String[]> reqSeq, int numQueries) {
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

        System.out.println("Sequential: Time taken in Nano sec is "+(System.nanoTime()-start));

        return results;
    }

}
