package pl.mainpackage;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {
        String path = "source-image2.jpg";

        VisualCryptography visualCryptography = new VisualCryptography();
        visualCryptography.initialize(path);

        BufferedImage sourceImage = visualCryptography.getSourceImage();
        BufferedImage[] shares = VisualCryptography.generateShares(sourceImage);

        VisualCryptography.mergeShares(shares);
    }
}
