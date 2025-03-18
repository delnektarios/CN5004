public class RequestHandlerDemo {
    private static final int NUM_REQUESTS = 100;
    private static final int NUM_THREADS = 10;

    public static void main(String[] args) {
        System.out.println("Handling requests with a single thread:");
        handleRequests(1);

        System.out.println("\nHandling requests with multiple threads:");
        handleRequests(NUM_THREADS);
    }

    private static void handleRequests(int numThreads) {
        long startTime = System.currentTimeMillis();

        Thread[] threads = new Thread[NUM_REQUESTS];

        for (int i = 0; i < NUM_REQUESTS; i++) {
            threads[i] = new Thread(new RequestHandler(i + 1));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                if (thread != null) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken with " + numThreads + " thread(s): " + (endTime - startTime) + " milliseconds");
    }
}

class RequestHandler implements Runnable {
    private final int requestId;

    public RequestHandler(int requestId) {
        this.requestId = requestId;
    }

    @Override
    public void run() {
        // System.out.println("Processing request " + requestId);
        try {
            // Simulate some processing time
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // System.out.println("Request " + requestId + " processed");
    }
}
