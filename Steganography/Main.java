package pl.mainpackage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String secret = "secret mess@ge";
        String path = "source-image2.jpg";
        String resultPath = "result.png";

        Steganography steganography = new Steganography();
        steganography.initialize(path,50,50);
        steganography.encode(secret);

        try {
            BufferedImage result = ImageIO.read(new File(resultPath));
            System.out.println(steganography.isEncoded(result));
            System.out.println(steganography.decode(result));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
