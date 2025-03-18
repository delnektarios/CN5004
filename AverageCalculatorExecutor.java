import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AverageCalculatorExecutor {
    private final int[] data;
    private final int numThreads;

    public AverageCalculatorExecutor(int[] data, int numThreads) {
        this.data = data;
        this.numThreads = numThreads;
    }

    public double calculateAverage() {
        long startTime = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        int chunkSize = data.length / numThreads;
        int startIndex = 0;

        for (int i = 0; i < numThreads; i++) {
            int endIndex = (i == numThreads - 1) ? data.length : startIndex + chunkSize;
            executor.execute(new AverageTask(data, startIndex, endIndex));
            startIndex = endIndex;
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Calculate the average
        long sum = 0;
        for (int num : data) {
            sum += num;
        }
        double average = (double) sum / data.length;

        long endTime = System.currentTimeMillis();
        System.out.println("Threads used : " + numThreads);
        System.out.println("Time taken: " + (endTime - startTime) + " milliseconds");

        return average;
    }

}

class AverageTask implements Runnable {
    private final int[] data;
    private final int startIndex;
    private final int endIndex;

    public AverageTask(int[] data, int startIndex, int endIndex) {
        this.data = data;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        long sum = 0;
        for (int i = startIndex; i < endIndex; i++) {
            sum += data[i];
        }
        // Printing out stats for every chunk
        System.out.println("Sum of chunk [" + startIndex + ", " + endIndex + "): " + sum);
    }
}
