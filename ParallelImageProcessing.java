import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelImageProcessing {
    private static final int NUM_THREADS = 10;

    public static void main(String[] args) {
        try {
            BufferedImage originalImage = ImageIO.read(new File("./input_img/Intel-Celeron-D-320-Laughs-Microscopically.png"));
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();

            int[] resultPixels = new int[width * height];

            int chunkSize = height / NUM_THREADS;

            ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

            long startTime = System.currentTimeMillis();
            for (int i = 0; i < NUM_THREADS; i++) {
                int startY = i * chunkSize;
                int endY = (i == NUM_THREADS - 1) ? height : (i + 1) * chunkSize;
                executor.execute(new GrayscaleConversionTask(originalImage, resultPixels, width, startY, endY));
            }

            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            long endTime = System.currentTimeMillis();

            BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            resultImage.setRGB(0, 0, width, height, resultPixels, 0, width);

            ImageIO.write(resultImage, "png", new File("./output_img/output.png"));
            System.out.println("Grayscale conversion complete.");
            
            System.out.println("Number of threads used: " + NUM_THREADS);
            System.out.println("Time taken: " + (endTime - startTime) + " milliseconds");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class GrayscaleConversionTask implements Runnable {
    private final BufferedImage originalImage;
    private final int[] resultPixels;
    private final int width;
    private final int startY;
    private final int endY;

    public GrayscaleConversionTask(BufferedImage originalImage, int[] resultPixels, int width, int startY, int endY) {
        this.originalImage = originalImage;
        this.resultPixels = resultPixels;
        this.width = width;
        this.startY = startY;
        this.endY = endY;
    }

    @Override
    public void run() {
        for (int y = startY; y < endY; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = originalImage.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                int gray = (r + g + b) / 3;
                int grayValue = (gray << 16) | (gray << 8) | gray;
                resultPixels[y * width + x] = grayValue;
            }
        }
    }
}
