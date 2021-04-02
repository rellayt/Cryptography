package pl.mainpackage;

import java.math.BigInteger;
import java.util.Random;

public class Client {
    private Random random = new Random();
    private Utility utility = new Utility();

    int n, g, privateKey, XY, sessionKey;

    Client(int n, int g, int size) {
        this.n = n;
        this.g = g;
        this.privateKey = random.nextInt(size - 1) + 1;

        BigInteger calculation = new BigInteger(Integer.toString(this.g)).pow(this.privateKey);
        this.XY = (int)calculation.mod(new BigInteger(Integer.toString(this.n))).longValue();
    }

    void generateSessionKey(int number){
        BigInteger calculation = new BigInteger(Integer.toString(number)).pow(this.privateKey);
        this.sessionKey = (int)calculation.mod(new BigInteger(Integer.toString(this.n))).longValue();
    }


}
