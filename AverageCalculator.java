

public class AverageCalculator {
    private final int[] data;
    private final int numThreads;

    public AverageCalculator(int[] data, int numThreads) {
        this.data = data;
        this.numThreads = numThreads;
    }

    public double calculateAverage() {
        long startTime = System.currentTimeMillis();

        Thread[] threads = new Thread[numThreads];
        int chunkSize = data.length / numThreads;
        int startIndex = 0;

        // Start threads for each chunk of the array
        for (int i = 0; i < numThreads; i++) {
            int endIndex = (i == numThreads - 1) ? data.length : startIndex + chunkSize;
            threads[i] = new Thread(new AverageTask(data, startIndex, endIndex));
            threads[i].start();
            startIndex = endIndex;
        }

        // Wait for all threads to complete
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Calculate average
        long sum = 0;
        for (int num : data) {
            sum += num;
        }
        double average = (double) sum / data.length;

        long endTime = System.currentTimeMillis();
        System.out.println("Threads used : " + numThreads);
        System.out.println("Time taken (only calculation): " + (endTime - startTime) + " milliseconds");

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
        System.out.println("Sum of chunk [" + startIndex + ", " + endIndex + "): " + sum);
    }
}
