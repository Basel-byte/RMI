package server;

import java.util.ArrayList;

public class RequestHandlerSeq implements RequestHandlerI {
    private static Graph graph;
    private static BFS bfs;

    public RequestHandlerSeq(Graph graph) {
        this.graph = graph;
        this.bfs = new BFS(this.graph);
    }

    public int[] computeBatch(ArrayList<String[]> reqSeq) {
        long start = System.nanoTime();

        int num_req = reqSeq.size();
        int[] results = new int[num_req];
        int i=0;

        for(String[] req : reqSeq){
            if (req[0].equals("Q")){
                results[i] = this.bfs.computeShortestPath(Integer.parseInt(req[1]), Integer.parseInt(req[2]));
                i++;

            }else if(req[0].equals("A")){
                this.graph.addEdge(Integer.parseInt(req[1]), Integer.parseInt(req[2]));

            }else if(req[0].equals("D")){
                this.graph.deleteEdge(Integer.parseInt(req[1]), Integer.parseInt(req[2]));

            }
        }

        // Create a new array to store the final results
        int[] finalResults = new int[i];
        System.arraycopy(results, 0, finalResults, 0, i);
        System.out.println("Sequential: Time taken in Nano sec is "+(System.nanoTime()-start));

        return finalResults;
    }

}
