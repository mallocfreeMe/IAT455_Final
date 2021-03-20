import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;


public class ImageQuilting

{
    // random block
    public static BufferedImage randomBlock(BufferedImage srcImage, int blockSize) {
        int w = srcImage.getWidth();
        int h = srcImage.getHeight();

        int i = ThreadLocalRandom.current().nextInt(w - blockSize);
        int j = ThreadLocalRandom.current().nextInt(h - blockSize);

        BufferedImage result = new BufferedImage(blockSize, blockSize, srcImage.getType());

        for (int x = i; x < i + blockSize; x++) {
            for (int y = j; y < j + blockSize; y++) {
                result.setRGB(i + blockSize - x - 1, j + blockSize - y - 1, srcImage.getRGB(x, y));
            }
        }

        return result;
    }

    // random block placement
    public static BufferedImage randomImage(BufferedImage srcImage, int width, int height, int blockSize) {
        BufferedImage result = new BufferedImage(width, height, srcImage.getType());

        for (int i = 0; i < width - blockSize; i += blockSize) {
            for (int j = 0; j < height - blockSize; j += blockSize) {
                BufferedImage blockImage = randomBlock(srcImage, blockSize);
                for (int k = 0; k < blockSize; k++) {
                    for (int l = 0; l < blockSize; l++) {
                        result.setRGB(i + k, j + l, blockImage.getRGB(k, l));
                    }
                }
            }
        }

        return result;
    }
}
