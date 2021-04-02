package pl.mainpackage;


import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int n = 15, t = 10;
        int bitLength = 70;

        BigInteger secret = new BigInteger(bitLength, new Random());
        BigInteger prime = new BigInteger(secret.bitLength() + 1, 50, new Random());
        Shamir shamir = new Shamir(n, t, prime);

        System.out.printf("%nSchemat Shamira%n%n");
        System.out.printf("n: %d%nt: %d%n%n", n, t);
        System.out.printf("Sekret:\t\t\t %d%nLiczba pierwsza: %d%n%n", secret, prime);
        Map<Integer, BigInteger> shares = shamir.splitSecret(secret);

        System.out.println("Udziały");
        shares.entrySet().stream()
                .forEach(shareValue -> System.out.printf("s%d: %d%n", shareValue.getKey(), shareValue.getValue()));

        BigInteger result = shamir.mergeShares(shares);
        System.out.printf("%nWynik: %d%n", result);

//        int t2 = 12;
//        Shamir shamir2 = new Shamir(n, t2, prime);
//        BigInteger result2 = shamir2.mergeShares(shares);
//        System.out.printf("Wynik 2: %d%n", result2);
    }
}
