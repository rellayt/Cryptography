package pl.mainpackage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.Random;

public class VisualCryptography {
    private BufferedImage sourceImage;
    private static final Random random;
    private static final Map<Integer, int[]> whiteMatrix, blackMatrix;
    private static final int white, black;
    private static int width, height;

    static {
        random = new Random();
        white = new Color(255, 255, 255).getRGB();
        black = new Color(0, 0, 0).getRGB();

        whiteMatrix = Map.of(
                0, new int[]{black, black},
                1, new int[]{white, white});

        blackMatrix = Map.of(
                0, new int[]{white, black},
                1, new int[]{black, white}
        );
    }

    public BufferedImage getSourceImage() {
        return sourceImage;
    }

    public void initialize(String path) {
        File input = new File(path);
        try {
            width = ImageIO.read(input).getWidth();
            height = ImageIO.read(input).getHeight();

            sourceImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
            saveImage("raw-image.jpg", sourceImage, ImageIO.read(input));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected static BufferedImage[] generateShares(BufferedImage sourceImage) {
        BufferedImage firstShare = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        BufferedImage secondShare = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        int[] matrix;
        int choice;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                choice = random.nextInt(2);
                matrix = sourceImage.getRGB(j, i) == black ? blackMatrix.get(choice) : whiteMatrix.get(choice);

                firstShare.setRGB(j, i, matrix[0]);
                secondShare.setRGB(j, i, matrix[1]);
            }
        }
        saveImage("share-1.jpg", firstShare);
        saveImage("share-2.jpg", secondShare);
        return new BufferedImage[]{firstShare, secondShare};
    }

    protected static void mergeShares(BufferedImage[] shares) {
        BufferedImage resultBuffer = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        int mergedPixel;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mergedPixel = Math.min(shares[0].getRGB(j, i), shares[1].getRGB(j, i));
                resultBuffer.setRGB(j, i, mergedPixel);
            }
        }
        saveImage("decrypted-image.jpg", resultBuffer);
    }

    private static void saveImage(String path, BufferedImage... bufferedImage) {
        BufferedImage source = bufferedImage.length > 1 ? bufferedImage[1] : bufferedImage[0];
        String format = path.split("\\.")[1];
        bufferedImage[0].createGraphics().drawImage(source, 0, 0, null);

        try {
            ImageIO.write(bufferedImage[0], format, new File(path));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
