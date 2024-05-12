package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;

public class RequestHandler {
    class BFS_C implements Runnable {
        private BFS bfs;
        private int s;
        private int t;
        private int[] results;
        private int res_idx;

        public BFS_C(BFS bfs, int s, int t, int[] results, int res_idx) {
            this.bfs = bfs;
            this.s = s;
            this.t = t;
            this.results = results;
            this.res_idx = res_idx;
        }

        @Override
        public void run() {
            // Call the specific function in another class
            this.results[this.res_idx] = this.bfs.computeShortestPath(this.s,this.t);
        }
    }

    private static Graph graph;
    private static BFS bfs;
    private int num_queires;

    public RequestHandler(Graph graph) {
        this.graph = graph;
        this.bfs = new BFS(this.graph);
    }

    public int[] computeBatchConc(ArrayList<String[]> reqSeq) throws InterruptedException {
        long start = System.nanoTime();

        int num_req = reqSeq.size();
        int[] results = new int[num_req];
        Thread[] thread = new Thread[num_req];
        int i=0;
        int start_join_idx = 0;

        for(String[] req : reqSeq){
            if (req[0].equals("Q")){
                thread[i] = new Thread(new BFS_C(this.bfs, Integer.parseInt(req[1]), Integer.parseInt(req[2]), results, i));
                thread[i].start();
                i++;

            }else if(req[0].equals("A")){
                for(int j=start_join_idx; j<i; j++){
                    thread[j].join();
                }
                start_join_idx = i;
                this.graph.addEdge(Integer.parseInt(req[1]), Integer.parseInt(req[2]));

            }else if(req[0].equals("D")){
                for(int j=start_join_idx; j<i; j++){
                    thread[j].join();
                }
                start_join_idx = i;
                this.graph.deleteEdge(Integer.parseInt(req[1]), Integer.parseInt(req[2]));

            }
        }

        for(int j=start_join_idx; j<i; j++){
            thread[j].join();

        }

        // Create a new array to store the final results
        int[] finalResults = new int[i];
        System.arraycopy(results, 0, finalResults, 0, i);
        System.out.println("Conccurent: Time taken in Nano sec is "+(System.nanoTime()-start));

        return finalResults;
    }

    public int[] computeBatchSeq(ArrayList<String[]> reqSeq) {
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
