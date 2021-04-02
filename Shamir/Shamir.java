package pl.mainpackage;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Shamir {
    private final int n;
    private final int t;
    private final BigInteger p;

    protected Shamir(int n, int t, BigInteger prime) {
        this.n = n;
        this.t = t;
        this.p = prime;
    }

    protected Map<Integer, BigInteger> splitSecret(final BigInteger secret) {
        BigInteger[] tArray = new BigInteger[t - 1];
        Map<Integer, BigInteger> shares = new HashMap<>();

        for (int i = 0; i < t - 1; i++)
            tArray[i] = new BigInteger(p.bitLength(), new Random());

        for (int i = 1; i <= n; i++) {
            BigInteger shareValue = secret;
            for (int j = 1; j < t; j++) {
                BigInteger x = BigInteger.valueOf(i).pow(j);
                BigInteger a = tArray[j - 1].multiply(x);
                shareValue = shareValue.add(a).mod(p);
            }
            shares.put(i, shareValue);
        }
        return shares;
    }

    protected BigInteger mergeShares(Map<Integer, BigInteger> shares) {
        BigInteger secret = BigInteger.ZERO;
        for (int i = 1; i <= t; i++) {
            BigInteger numerator = BigInteger.ONE, denominator = BigInteger.ONE;
            for (int j = 1; j <= t; j++) {
                if(i != j){
                    numerator = numerator.multiply(BigInteger.valueOf(-j));
                    denominator = denominator.multiply(BigInteger.valueOf(i - j));
                }
            }
            BigInteger multiplication = shares.get(i).multiply(numerator).multiply(denominator.modInverse(p));
            secret = secret.add(p).add(multiplication).mod(p);
        }
        return secret;
    }
}
