package server;

import java.util.List;

public class RequestHandlerConcurrent extends RequestHandler {
    static class BFS_C implements Runnable {
        private final BFS bfs;
        private final int s;
        private final int t;
        private final int[] results;
        private final int res_idx;

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

    public RequestHandlerConcurrent(Graph graph) {
        super(graph);
    }

    @Override
    public synchronized int[] computeBatch(List<String[]> reqSeq, int numQueries) {
        long start = System.nanoTime();

        int num_req = reqSeq.size();
        int[] results = new int[numQueries];
        Thread[] thread = new Thread[num_req];
        int i=0;
        int start_join_idx = 0;

        try {
            for(String[] req : reqSeq){
                switch (req[0]) {
                    case "Q" -> {
                        thread[i] = new Thread(new BFS_C(bfs, Integer.parseInt(req[1]), Integer.parseInt(req[2]), results, i));
                        thread[i].start();
                        i++;
                    }
                    case "A" -> {
                        for (int j = start_join_idx; j < i; j++) {
                            thread[j].join();
                        }
                        start_join_idx = i;
                        graph.addEdge(Integer.parseInt(req[1]), Integer.parseInt(req[2]));
                    }
                    case "D" -> {
                        for (int j = start_join_idx; j < i; j++) {
                            thread[j].join();
                        }
                        start_join_idx = i;
                        graph.deleteEdge(Integer.parseInt(req[1]), Integer.parseInt(req[2]));
                    }
                }
            }

            for(int j=start_join_idx; j<i; j++){
                thread[j].join();
            }
        }
        catch (InterruptedException e) {
            System.err.println("Error in thread join: " + e.getMessage());
        }

        System.out.println("Concurrent: Time taken in Nano sec is "+(System.nanoTime()-start));

        return results;
    }
}
