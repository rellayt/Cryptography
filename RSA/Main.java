package pl.mainpackage;

public class Main {

    public static void main(String[] args) {
        Utility utils = new Utility();
        // int p = 31, q = 19;
        int p = utils.getPrime(1000, 9999), q = utils.getPrime(1000, 9999);
        RSA rsa = new RSA(p, q);

        String plainText = "viverra vitae congue eu consequat ac felis donec et odio pellentesque diam volutpat commodo sed egestas egestas fringilla phasellus faucibus" +
                " scelerisque eleifend donec pretium vulputate sapien nec sagittis aliquam malesuada bibendum arcu vitae elementum curabitur vitae nunc sed velit dignissim " +
                "sodales ut eu sem integer vitae justo eget magna fermentum";

        String encryptedText = "";
        String decryptedText = "";

        encryptedText = rsa.encrypt(plainText);
        decryptedText = rsa.decrypt(encryptedText);

        System.out.println("\n--- RSA ---\n");
        System.out.println("p: " + p + ", q: " + q);
        System.out.println("n: " + rsa.n);
        System.out.println("phi: " + rsa.phi);
        System.out.println("e: " + rsa.e);
        System.out.println("d: " + rsa.d);
        System.out.println("\nPlain text: " + plainText);
        System.out.println("Encrypted text: " + encryptedText);
        System.out.println("Decrypted text: " + decryptedText);
    }
}