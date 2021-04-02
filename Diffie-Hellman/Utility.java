package pl.mainpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utility {
    private Random random = new Random();

    Utility(){}

    static int power(int x, int y, int p) {
        int result = 1;
        x = x % p;
        while (y > 0) {
            if (y % 2 == 1) {
                result = (result * x) % p;
            }
            y = y >> 1;
            x = (x * x) % p;
        }
        return result;
    }

    int getPrimitive(int number) {
        int phi = number - 1;
        List<Integer> factors = findPrimeFactors(phi);

        for (int i = 2; i <= phi; i++) {
            boolean flag = false;
            for (Integer a : factors) {
                if (power(i,phi / a, number) == 1){
                    flag = true;
                    break;
                }
            }
            if (!flag)
                return i;
        }
        return -1;
    }

    List<Integer> findPrimeFactors(int number) {
        List<Integer> factors = new ArrayList<Integer>();

        while (number % 2 == 0) {
            factors.add(2);
            number /= 2;
        }

        for (int i = 3; i <= Math.sqrt(number); i = i + 2) {
            while (number % i == 0) {
                factors.add(i);
                number /= i;
            }
        }

        if (number > 2) {
            factors.add(number);
        }
        return factors;
    }

    int getPrime(int size) {
        int number;
        while (true) {
            number = random.nextInt(size - 1) + 1;
            if (isPrime(number))
                break;
        }
        return number;
    }

    boolean isPrime(int number) {
        if (number <= 1)
            return false;

        for (int i = 2; i < number; i++)
            if (number % i == 0)
                return false;

        return true;
    }
}
