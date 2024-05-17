package client;

import org.apache.log4j.Logger;
import server.GraphIF;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    public static void main(String[] args) throws NotBoundException, IOException, InterruptedException {
        GraphIF graph = new GraphObject();

        while (true) {
            // Prepare a test batch
            int batchNumber = new Random().nextInt(6) + 1;
            List<String[]> batch = Parser.prepareRequests("input/test/batch" + batchNumber);
            int numQueries = Parser.getNumQueries();

            logger.info("Sent!");
            batch.forEach((r) -> logger.info(Arrays.toString(r)));

            // Measure start time
            long startTime = System.currentTimeMillis();

            // Send batch request and get the result
            int[] result = graph.batchRequest(batch, numQueries);

            // Measure elapsed time
            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.info("Elapsed time: " + elapsedTime + " milliseconds");

            logger.info("Received: " + Arrays.toString(result));

            Parser.writeResultToFile("" + batchNumber, result);

            // Random sleep between 1 and 5 seconds before next request
            Random random = new Random();
            int sleepTime = random.nextInt(1000, 5000);
            System.out.println("Sleeping for " + sleepTime + " milliseconds.");
            Thread.sleep(sleepTime);
        }

    }
}
