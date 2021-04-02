package pl.mainpackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int P = 7, Q = 11, x = 5;
        BBS generator = new BBS(P,Q,x);
        generator.generateRandomValues(20000);
        Tests test = new Tests(generator.generatedBitsArray);

        System.out.println("Wygenerowany ciąg bitów: ");
        for (int i = 0; i < 20000; i++){
            System.out.print(generator.generatedBitsArray.get(i) + " ");
            if(i % 80 == 0)
                System.out.println();
        }

        System.out.println("\n");
        System.out.println("wynik testu: " + test.singleBitsTest());
        System.out.println();
        System.out.println("wynik testu: " + test.longRunTest());
        System.out.println();
        System.out.println("wynik testu: " + test.pokerTest());
        System.out.println();
        test.seriesTest();
    }
/*    public static boolean isPrimeNumber(int number)
    {
        if(number<2) {
            return false;
        }
        for(int i=2; i<=number/2; i++) {
            if(number%i==0) {
                return false;
            }
        }
        return true;
    }*/
}
