package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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

    private Graph graph;
    private BFS bfs;
    private int num_queires;

    public RequestHandler(Graph graph) {
        this.graph = graph;
        this.bfs = new BFS(this.graph);
        this.num_queires = 0;
    }

    public int[] computeBatch(ArrayList<String[]> reqSeq, int num_queires) throws InterruptedException {
        int[] results = new int[num_queires];
        Thread[] thread = new Thread[num_queires];
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
        return results;
    }
}
