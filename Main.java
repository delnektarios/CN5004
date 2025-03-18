public class Main {

    static final int THREADS_NUM = 100;
    public static void main(String[] args) {

        int[] data = generateLargeArray(1000000000); 

        AverageCalculator calculator = new AverageCalculator(data, THREADS_NUM);

        long startTime = System.currentTimeMillis();
        double average = calculator.calculateAverage();
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken (whole implementation): " + (endTime - startTime) + " milliseconds");

        System.out.println("Average: " + average);

        // AverageCalculatorExecutor e_calculator = new AverageCalculatorExecutor(data, THREADS_NUM);
        // double e_average = e_calculator.calculateAverage();
        // System.out.println("Average: " + e_average);
    }

    private static int[] generateLargeArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 100);
        }
        return array;
    }

    
}
