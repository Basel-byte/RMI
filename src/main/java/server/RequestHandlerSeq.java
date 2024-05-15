package server;

import java.util.ArrayList;

public class RequestHandlerSeq extends RequestHandler {

    public RequestHandlerSeq(Graph graph) {
        super(graph);
    }

    @Override
    public int[] computeBatch(ArrayList<String[]> reqSeq) {
        long start = System.nanoTime();

        int num_req = reqSeq.size();
        int[] results = new int[num_req];
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

        // Create a new array to store the final results
        int[] finalResults = new int[i];
        System.arraycopy(results, 0, finalResults, 0, i);
        System.out.println("Sequential: Time taken in Nano sec is "+(System.nanoTime()-start));

        return finalResults;
    }

}
