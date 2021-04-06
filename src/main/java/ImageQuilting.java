import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;


public class ImageQuilting {

    // the randomBlock method
    // params: a source image, a user defined block size
    // post: generate a random block based on the source image
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

    // the randomPlacementImage method
    // params: a source image, the synthesis image's width, the synthesis image's height, a user defined block size
    // post: return a random block placement synthesis image (width * height) based on the source image
    public static BufferedImage randomPlacementImage(BufferedImage srcImage, int width, int height, int blockSize) {
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

    // the findMinimumRightBlock method
    // params: the source image, a left block from the synthesis image
    // post: return a right block by calculating the minimum rgb difference between each block from the source image and the left block
    public static BufferedImage findMinimumRightBlock(BufferedImage srcImage, BufferedImage leftBlock) {
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        int blockSize = leftBlock.getWidth();
        int overlap = (int) (0.2 * blockSize);


        BufferedImage leftBlockOverlap = new BufferedImage(overlap, blockSize, leftBlock.getType());
        int leftBlockOverlapRGB = 0;

        for (int i = blockSize - overlap; i < blockSize; i++) {
            for (int j = 0; j < leftBlockOverlap.getHeight(); j++) {
                leftBlockOverlap.setRGB(i - (blockSize - overlap), j, leftBlock.getRGB(i, j));
                leftBlockOverlapRGB += leftBlock.getRGB(i, j);
            }
        }

        int error;
        int previousError = 0;
        BufferedImage result = new BufferedImage(blockSize, blockSize, leftBlock.getType());

        for (int i = 0; i < width - blockSize; i += blockSize) {
            for (int j = 0; j < height - blockSize; j += blockSize) {

                BufferedImage rightBlock = new BufferedImage(blockSize, blockSize, leftBlock.getType());
                BufferedImage rightBlockOverlap = new BufferedImage(overlap, blockSize, leftBlock.getType());
                int rightBlockOverlapRGB = 0;

                for (int k = 0; k < blockSize; k++) {
                    for (int l = 0; l < blockSize; l++) {
                        rightBlock.setRGB(k, l, srcImage.getRGB(i + k, j + l));
                    }
                }

                for (int k = 0; k < rightBlockOverlap.getWidth(); k++) {
                    for (int l = 0; l < rightBlockOverlap.getHeight(); l++) {
                        rightBlockOverlap.setRGB(k, l, rightBlock.getRGB(k, l));
                        rightBlockOverlapRGB += rightBlock.getRGB(k, l);

                    }
                }

                error = Math.abs(rightBlockOverlapRGB - leftBlockOverlapRGB);

                if (i == 0 || error < previousError) {
                    previousError = error;
                    result = rightBlock;
                }
            }
        }

        return result;
    }

    // the findMinimumBottomBlock method
    // params: the source image, a top block from the synthesis image
    // post: return a bottom block by calculating the minimum rgb difference between each block from the source image and the top block
    public static BufferedImage findMinimumBottomBlock(BufferedImage srcImage, BufferedImage topBlock) {
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        int blockSize = topBlock.getHeight();
        int overlap = (int) (0.3 * blockSize);


        BufferedImage topBlockOverlap = new BufferedImage(blockSize, overlap, topBlock.getType());
        int topBlockOverlapRGB = 0;

        for (int i = 0; i < topBlockOverlap.getWidth(); i++) {
            for (int j = blockSize - overlap; j < blockSize; j++) {
                topBlockOverlap.setRGB(i, j - (blockSize - overlap), topBlock.getRGB(i, j));
                topBlockOverlapRGB += topBlock.getRGB(i, j);
            }
        }

        int error = 0;
        int previousError = 0;
        BufferedImage result = new BufferedImage(blockSize, blockSize, topBlock.getType());

        for (int i = 0; i < width - blockSize; i += blockSize) {
            for (int j = 0; j < height - blockSize; j += blockSize) {

                BufferedImage bottomBlock = new BufferedImage(blockSize, blockSize, topBlock.getType());
                BufferedImage bottomBlockOverlap = new BufferedImage(overlap, blockSize, topBlock.getType());
                int bottomBlockOverlapRGB = 0;

                for (int k = 0; k < blockSize; k++) {
                    for (int l = 0; l < blockSize; l++) {
                        bottomBlock.setRGB(k, l, srcImage.getRGB(i + k, j + l));
                    }
                }

                for (int k = 0; k < bottomBlockOverlap.getWidth(); k++) {
                    for (int l = 0; l < bottomBlockOverlap.getHeight(); l++) {
                        bottomBlockOverlap.setRGB(k, l, bottomBlock.getRGB(k, l));
                        bottomBlockOverlapRGB += bottomBlock.getRGB(k, l);

                    }
                }

                error = Math.abs(topBlockOverlapRGB - bottomBlockOverlapRGB);

                if (i == 0 || error < previousError) {
                    previousError = error;
                    result = bottomBlock;
                }
            }
        }

        return result;
    }

    // the findMinimumBlock method
    // params: the source image, a left block from the synthesis image, a top block from the synthesis image
    // post: return a block by calculating the minimum rgb difference between each block from the source image and the left block
    // as well as the top block
    public static BufferedImage findMinimumBlock(BufferedImage srcImage, BufferedImage leftBlock, BufferedImage topBlock) {
        int width = srcImage.getWidth();
        int height = srcImage.getHeight();
        int blockSize = topBlock.getHeight();

        //apple 0.2 0.3, berry 0.15 0.25
        int horizontalOverlap = (int) (0.2 * blockSize);
        int verticalOverlap = (int) (0.3 * blockSize);

        BufferedImage leftBlockOverlap = new BufferedImage(horizontalOverlap, blockSize, leftBlock.getType());
        BufferedImage topBlockOverlap = new BufferedImage(blockSize, verticalOverlap, topBlock.getType());
        int topBlockBottomOverlapRGB = 0;
        int leftBlockRightOverlapRGB = 0;

        for (int i = blockSize - horizontalOverlap; i < blockSize; i++) {
            for (int j = 0; j < leftBlockOverlap.getHeight(); j++) {
                leftBlockOverlap.setRGB(i - (blockSize - horizontalOverlap), j, leftBlock.getRGB(i, j));
                leftBlockRightOverlapRGB += leftBlock.getRGB(i, j);
            }
        }

        for (int i = 0; i < topBlockOverlap.getWidth(); i++) {
            for (int j = blockSize - verticalOverlap; j < blockSize; j++) {
                topBlockOverlap.setRGB(i, j - (blockSize - verticalOverlap), topBlock.getRGB(i, j));
                topBlockBottomOverlapRGB += topBlock.getRGB(i, j);
            }
        }

        long leftError;
        long topError;
        long finalError;

        int blockLeftOverlapRGB = 0;
        int blockTopOverlapRGB = 0;
        long previousFinalError = 0;
        BufferedImage result = new BufferedImage(blockSize, blockSize, topBlock.getType());

        for (int i = 0; i < width - blockSize; i += blockSize) {
            for (int j = 0; j < height - blockSize; j += blockSize) {

                BufferedImage block = new BufferedImage(blockSize, blockSize, topBlock.getType());

                for (int k = 0; k < blockSize; k++) {
                    for (int l = 0; l < blockSize; l++) {
                        block.setRGB(k, l, srcImage.getRGB(i + k, j + l));
                    }
                }

                BufferedImage blockLeftOverlap = new BufferedImage(horizontalOverlap, blockSize, leftBlock.getType());

                for (int k = 0; k < blockLeftOverlap.getWidth(); k++) {
                    for (int l = 0; l < blockLeftOverlap.getHeight(); l++) {
                        blockLeftOverlap.setRGB(k, l, block.getRGB(k, l));
                        blockLeftOverlapRGB += block.getRGB(k, l);

                    }
                }

                BufferedImage blockTopOverlap = new BufferedImage(verticalOverlap, blockSize, topBlock.getType());

                for (int k = 0; k < blockTopOverlap.getWidth(); k++) {
                    for (int l = 0; l < blockTopOverlap.getHeight(); l++) {
                        blockTopOverlap.setRGB(k, l, block.getRGB(k, l));
                        blockTopOverlapRGB += block.getRGB(k, l);

                    }
                }

                leftError = (Math.abs(blockLeftOverlapRGB - leftBlockRightOverlapRGB));
                topError = (Math.abs(topBlockBottomOverlapRGB - blockTopOverlapRGB));

                finalError = leftError + topError;

                if (i == 0 || finalError < previousFinalError) {
                    previousFinalError = finalError;
                    result = block;
                }
            }
        }

        return result;
    }

    // the neighboringBlockPlacementImage method
    // params:a source image, user defined width for the synthesis image, user defined height for the synthesis image, and a user defined block size
    // post: generate a synthesis image using the neighboring block placement method (find the minimum error block)
    public static BufferedImage neighboringBlockPlacementImage(BufferedImage srcImage, int width, int height, int blockSize) {
        BufferedImage result = new BufferedImage(width, height, srcImage.getType());

        // four cases
        for (int i = 0; i < width - blockSize; i += blockSize) {
            for (int j = 0; j < height - blockSize; j += blockSize) {
                if (i == 0 && j == 0) {
                    // 1. the first top left case: generate a random block and place it on the correspond position on the synthesis image
                    BufferedImage topLeftBlock = randomBlock(srcImage, blockSize);
                    for (int k = 0; k < blockSize; k++) {
                        for (int l = 0; l < blockSize; l++) {
                            result.setRGB(i + k, j + l, topLeftBlock.getRGB(k, l));
                        }
                    }
                } else if (i == 0) {
                    // 2. the first col case: find the minimum bottom block from the source image,
                    // and place it on the correspond position on the synthesis image
                    BufferedImage topBlock = new BufferedImage(blockSize, blockSize, srcImage.getType());

                    for (int k = 0; k < blockSize; k++) {
                        for (int l = 0; l < blockSize; l++) {
                            topBlock.setRGB(k, l, result.getRGB(i + k, j + l - blockSize));
                        }
                    }

                    BufferedImage bottomBlock = findMinimumBottomBlock(srcImage, topBlock);
                    for (int k = 0; k < blockSize; k++) {
                        for (int l = 0; l < blockSize; l++) {
                            result.setRGB(i + k, j + l, bottomBlock.getRGB(k, l));
                        }
                    }
                } else if (j == 0) {
                    // 3. the first row case: find the minimum right block from the source image,
                    // and place it on the correspond position on the synthesis image
                    BufferedImage leftBlock = new BufferedImage(blockSize, blockSize, srcImage.getType());

                    for (int k = 0; k < blockSize; k++) {
                        for (int l = 0; l < blockSize; l++) {
                            leftBlock.setRGB(k, l, result.getRGB(i + k - blockSize, j + l));
                        }
                    }

                    BufferedImage rightBlock = findMinimumRightBlock(srcImage, leftBlock);
                    for (int k = 0; k < blockSize; k++) {
                        for (int l = 0; l < blockSize; l++) {
                            result.setRGB(i + k, j + l, rightBlock.getRGB(k, l));
                        }
                    }
                } else {
                    // 4. other cases: Compare the left block and the top block at the same time to find the current block,
                    // and place it on the correspond position on the synthesis image
                    BufferedImage topBlock = new BufferedImage(blockSize, blockSize, srcImage.getType());

                    for (int k = 0; k < blockSize; k++) {
                        for (int l = 0; l < blockSize; l++) {
                            topBlock.setRGB(k, l, result.getRGB(i + k, j + l - blockSize));
                        }
                    }

                    BufferedImage leftBlock = new BufferedImage(blockSize, blockSize, srcImage.getType());

                    for (int k = 0; k < blockSize; k++) {
                        for (int l = 0; l < blockSize; l++) {
                            leftBlock.setRGB(k, l, result.getRGB(i + k - blockSize, j + l));
                        }
                    }

                    BufferedImage block = findMinimumBlock(srcImage, leftBlock, topBlock);
                    for (int k = 0; k < blockSize; k++) {
                        for (int l = 0; l < blockSize; l++) {
                            result.setRGB(i + k, j + l, block.getRGB(k, l));
                        }
                    }

                }
            }
        }
        return result;
    }
}
