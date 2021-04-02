package pl.mainpackage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Steganography {
    BufferedImage sourceImage;
    private int width, height;
    private int startRow, startColumn;

    public void initialize(String path, int startRowPercentage, int startColumnPercentage) {
        File input = new File(path);
        try {
            sourceImage = ImageIO.read(input);
            width = sourceImage.getWidth();
            height = sourceImage.getHeight();
            startRow = height * startRowPercentage / 100;
            startColumn = width * startColumnPercentage / 100;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void encode(String message) {
        BufferedImage resultBuffer = sourceImage;
        Map<Integer, int[]> bitMessage = new HashMap<>();
        int data, key = 0;

        message = "%len:" + message.length() + "%" + message;

        for (char c : message.toCharArray())
            bitMessage.put(key++, new int[]{c >> 6, c >> 4, c >> 2, c});

        key = 0;
        loop:
        for (int y = startRow; y < height; y++) {
            for (int x = startColumn; x < width; x += 4) {
                if (key == message.length()) break loop;

                for (int i = 0; i < 4; i++) {
                    data = sourceImage.getRGB(x + i, y) & 0xFFFFFC | bitMessage.get(key)[i];
                    resultBuffer.setRGB(x + i, y, data);
                }
                key++;
            }
        }
        try {
            ImageIO.write(resultBuffer, "png", new File("result.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String decode(BufferedImage inputImage) {
        int messageLength = getMessageLength(inputImage), prefixLength = 24 + String.valueOf(messageLength).length() * 4;
        Deque<Integer> bitMessage = new ArrayDeque<>();
        char singleChar;
        StringBuilder decodedMessage = new StringBuilder();

        for (int y = startRow; y < height; y++) {
            for (int x = startColumn + prefixLength; x < width; x += 4) {
                if (x - startColumn - prefixLength == messageLength * 4) return new String(decodedMessage);
                singleChar = getSingleChar(inputImage, bitMessage, x, y);
                decodedMessage.append(singleChar);
            }
        }
        return null;
    }

    public Boolean isEncoded(BufferedImage inputImage) {
        Deque<Integer> bitMessage = new ArrayDeque<>();
        char singleChar;
        StringBuilder decodedMessage = new StringBuilder();

        for (int y = startRow; y < height; y++) {
            for (int x = startColumn; x < width; x += 4) {
                if (x - startColumn == 20) return new String(decodedMessage).equals("%len:");
                singleChar = getSingleChar(inputImage, bitMessage, x, y);
                decodedMessage.append(singleChar);
            }
        }
        return false;
    }

    private int getMessageLength(BufferedImage inputImage) {
        Deque<Integer> bitMessage = new ArrayDeque<>();
        char singleChar;
        StringBuilder decodedMessage = new StringBuilder();

        for (int y = startRow; y < height; y++) {
            for (int x = startColumn + 20; x < width; x += 4) {
                singleChar = getSingleChar(inputImage, bitMessage, x, y);

                if (singleChar != '%') {
                    decodedMessage.append(singleChar);
                } else {
                    return Integer.parseInt(decodedMessage.toString());
                }
            }
        }
        return 0;
    }

    private static char getSingleChar(BufferedImage inputImage, Deque<Integer> bitMessage, int x, int y) {
        int data;
        for (int i = 0; i < 4; i++) {
            //Eksport dwóch LSB z wiadomości
            data = inputImage.getRGB(x + i, y) & 0x03;
            bitMessage.add(data);
        }
        return (char) (bitMessage.pop() << 6 | bitMessage.pop() << 4 | bitMessage.pop() << 2 | bitMessage.pop());
    }
}
