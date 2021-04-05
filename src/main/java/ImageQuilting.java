import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;


public class ImageQuilting {
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

    public static BufferedImage findMinimumRightBlock(BufferedImage srcImage, BufferedImage leftBlock) {
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        int blockSize = leftBlock.getWidth();
        int overlap = (int) (0.5 * blockSize);

        BufferedImage leftBlockOverlap = new BufferedImage(overlap, blockSize, leftBlock.getType());
        int leftBlockOverlapRGB = 0;

        for (int i = blockSize - leftBlockOverlap.getWidth(); i < blockSize; i++) {
            for (int j = 0; j < leftBlockOverlap.getHeight(); j++) {
                leftBlockOverlap.setRGB(i - (blockSize - leftBlockOverlap.getWidth()), j, leftBlock.getRGB(i, j));
                leftBlockOverlapRGB += leftBlock.getRGB(i, j);
            }
        }

        int error = 0;
        int previousError = 0;
        BufferedImage result = new BufferedImage(blockSize, blockSize, leftBlock.getType());

        for (int i = 0; i < width - blockSize; i += blockSize) {
            for (int j = 0; j < height - blockSize; j += blockSize) {

                BufferedImage rightBlock = new BufferedImage(blockSize, blockSize, leftBlock.getType());
                BufferedImage rightBlockOverlap = new BufferedImage(overlap, blockSize, leftBlock.getType());
                int rightBlockOverlapRGB = 0;

                for (int k = 0; k < blockSize; k++) {
                    for (int l = 0; l < blockSize; l++) {
                        rightBlock.setRGB(i + k, j + l, srcImage.getRGB(k, l));
                    }
                }

                for (int k = 0; k < rightBlockOverlap.getWidth(); k++) {
                    for (int l = 0; l < rightBlockOverlap.getHeight(); l++) {
                        rightBlockOverlap.setRGB(k, l, rightBlock.getRGB(k, l));
                        rightBlockOverlapRGB += rightBlock.getRGB(k, l);
                    }
                }
                error = Math.abs(rightBlockOverlapRGB - leftBlockOverlapRGB);
                if(i == 0 || error < previousError) {
                    previousError = error;
                    result = rightBlock;
                }
            }
        }

        return result;
    }

    public static BufferedImage FindMinimumUpBlock(BufferedImage srcImage, BufferedImage rightBlock) {
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        int blockSize = rightBlock.getWidth();
        int overlap = (int) (0.5 * blockSize);
        BufferedImage result = new BufferedImage(blockSize, blockSize, rightBlock.getType());

        return result;
    }

    // neighboring block constrained by overlap placement
    public static BufferedImage neighboringBlockPlacement(BufferedImage srcImage, int width, int height, int blockSize) {
        BufferedImage result = new BufferedImage(width, height, srcImage.getType());

        for (int i = 0; i < width - blockSize; i += blockSize) {
            for (int j = 0; j < height - blockSize; j += blockSize) {
                // the first random block
                if (i == 0 && j == 0) {
                    BufferedImage blockImage = randomBlock(srcImage, blockSize);
                    for (int k = 0; k < blockSize; k++) {
                        for (int l = 0; l < blockSize; l++) {
                            result.setRGB(i + k, j + l, blockImage.getRGB(k, l));
                        }
                    }
                } else if (i == 0) {
                    // the first col
                } else if (j == 0) {
                    // the first row
                } else {
                    // other cases
                }
            }
        }

        return result;
    }
}
