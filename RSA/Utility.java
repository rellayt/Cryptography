package pl.mainpackage;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Utility {
    private Random random = new Random();

    Utility() {
    }

    int getPrime(int min, int max) {
        int number;
        while (true) {
            number = random.nextInt(max - min) + min;
            if (isPrime(number))
                break;
        }
        return number;
    }

    int getGCD(int firstNumber, int secondNumber) {
        while (firstNumber != secondNumber) {
            if (firstNumber > secondNumber)
                firstNumber -= secondNumber;
            else
                secondNumber -= firstNumber;
        }
        return secondNumber;
    }

    int powMod(int a, int b, int c) {
        long x = 1;
        long y = a;
        while (b > 0) {
            if (b % 2 == 1) {
                x = (x * y) % c;
            }
            y = (y * y) % c;
            b /= 2;
        }
        return (int) x % c;
    }

    boolean isPrime(int number) {
        if (number <= 1)
            return false;

        for (int i = 2; i < number; i++)
            if (number % i == 0)
                return false;

        return true;
    }

    boolean isDivisible(int firstNumber, int secondNumber) {
        if (Math.floorMod(firstNumber, secondNumber) == 0)
            return true;
        else
            return false;

    }

    public static <T, U> List<U> convertList(List<T> fromList, Function<T, U> function) {
        return fromList.stream().map(function).collect(Collectors.toList());
    }

    public static List<Character> stringToCharList(String str)
    {
        List<Character> chars = new ArrayList<>();
        for (char ch : str.toCharArray()) {
            chars.add(ch);
        }
        return chars;
    }

}
