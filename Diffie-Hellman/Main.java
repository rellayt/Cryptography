package pl.mainpackage;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        Utility utility = new Utility();
        int size = 100000;
        int n = utility.getPrime(size);
        int g = utility.getPrimitive(n);

        Client A = new Client(n, g, size);
        Client B = new Client(n, g, size);

        A.generateSessionKey(B.XY);
        B.generateSessionKey(A.XY);

        System.out.println("Algorytm DH\n");

        System.out.println("n: " + n + "\ng: " + g + "\n");

        System.out.println("A - client");
        System.out.println("PrivateKey: " + A.privateKey);
        System.out.println("X: " + A.XY);
        System.out.println("Session key: " + A.sessionKey + "\n");

        System.out.println("B - client");
        System.out.println("PrivateKey: " + B.privateKey);
        System.out.println("Y: " + B.XY);
        System.out.println("Session key: " + B.sessionKey);

    }
}
