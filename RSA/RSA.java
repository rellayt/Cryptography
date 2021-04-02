package pl.mainpackage;

import java.util.Arrays;
import java.util.List;

public class RSA {
    Utility utils = new Utility();

    int n, phi, e, d;

    RSA(int p, int q) {
        this.n = p * q;
        this.phi = (p - 1) * (q - 1);
        generateE();
        generateD();
    }

    void generateE() {
        for (int i = 0; i < this.phi; i++) {
            if (utils.isPrime(i) && utils.getGCD(this.phi, i) == 1) {
                this.e = i;
                break;
            }
        }
    }

    void generateD() {
        for (int i = 0; i < this.phi; i++) {
            this.d = i;
            if (utils.isDivisible(this.e * this.d - 1, this.phi))
                break;
        }
    }

    String encrypt(String plainText) {
        List<Integer> segments = utils.convertList(utils.stringToCharList(plainText), s -> (int)s);

        StringBuilder encryptedText = new StringBuilder(segments.size());

        segments.stream()
                .map(ascii -> utils.powMod(ascii, this.e, this.n) + " ")
                .forEach(encryptedText::append);

//        for (int i = 0; i < plainText.length(); i++) {
//            int asciiChar = (int) plainText.charAt(i);
//            double encryptedChar = Math.pow(asciiChar, this.e) % this.n;
//            if (i != 0)
//                encryptedText.append(".");
//            encryptedText.append((int) encryptedChar);
//        }

        return encryptedText.toString();
    }

    String decrypt(String encryptedText) {
        List<Integer> segments = Utility.convertList(Arrays.asList(encryptedText.split(" ")), Integer::parseInt);
        StringBuilder decryptedText = new StringBuilder(segments.size());

        segments.stream()
                .map(segment -> (char) utils.powMod(segment, this.d, this.n))
                .forEach(decryptedText::append);

        return decryptedText.toString();
    }
}
