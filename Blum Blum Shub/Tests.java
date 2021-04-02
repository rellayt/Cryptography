package pl.mainpackage;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Tests {
    public List<Integer> bits;

    Tests(List<Integer> bits) {
        this.bits = bits;
    }

    boolean longRunTest() {
        System.out.println("- - - Test długiej serii - - -");

        int length = 0;
        for (int i = 1; i < bits.size(); i++) {

            if (bits.get(i - 1) == bits.get(i))
                length++;
            else
                length = 0;

            if (length >= 26) {
                System.out.println("długość serii to " + length);
                return false;
            }
        }
        System.out.println("w próbie nie ma serii o długości 26 lub więcej");
        return true;
    }

    boolean pokerTest() {
        System.out.println("- - - Test pokerowy - - -");
        DecimalFormat formatter = new DecimalFormat("#.##");
        List<Integer> tempArray, occurrences = new ArrayList<>(5000);
        Map<Integer, Integer> occurrenceCount;
        for (int i = 0; i < bits.size() - 3; i += 4) {
            tempArray = Arrays.asList(bits.get(i), bits.get(i + 1), bits.get(i + 2), bits.get(i + 3));
            occurrences.add(convertToDecimal(tempArray));
        }
        occurrenceCount = occurrences.stream()
                .collect(Collectors.groupingBy(number -> number, Collectors.summingInt(number -> 1)));

        int sum = occurrenceCount.entrySet().stream()
                .map(occ -> occ.getValue() * occ.getValue())
                .reduce(0, (occ, acc) -> occ + acc);

        double result = (16.0 / 5000.0) * sum - 5000;
        System.out.println("X = " + formatter.format(result));

        return result > 2.16 && result < 46.17;
    }

    boolean singleBitsTest() {
        System.out.println("- - - Test pojedynczych bitów - - -");
        long trueBitsCount = bits.stream()
                .filter(bit -> bit == 1)
                .count();
        System.out.println("liczba jedynek wynosi " + trueBitsCount);

        return trueBitsCount > 9725 && trueBitsCount < 10275;
    }

    /*    int seriesTest() {
            System.out.println("- - - Test serii - - -");
            int length = 0, tempLength = 0;
            for (int i = 1; i< bits.size()-1; i++){
                if (bits.get(i - 1) != bits.get(i))
                    tempLength++;
                else {
                    if(length < tempLength) {
                        length = tempLength;
                        tempLength = 0;
                    }
                }
            }
            return length;
        }*/
    void seriesTest() {
        System.out.println("- - - Test serii - - -");
        long series[] = {0, 0, 0, 0, 0, 0};
        for (int i = 0; i < 6; i++) {
            int iterator = i;
            series[i] = IntStream.range(i + 1, bits.size() - 1)
                    .filter(it -> getConditions(iterator, it))
                    .count();
        }
        System.out.println("Długość serii: 1 - " + series[0]);
        System.out.println("Długość serii: 2 - " + series[1]);
        System.out.println("Długość serii: 3 - " + series[2]);
        System.out.println("Długość serii: 4 - " + series[3]);
        System.out.println("Długość serii: 5 - " + series[4]);
        System.out.println("Długość serii: 6 - " + series[5]);
    }

    boolean getConditions(int lengthNumber, int it) {
        boolean condition = false;
        switch (lengthNumber) {
            case 0:
                condition = (bool(bits.get(it - 1)) && !bool(bits.get(it)) && bool(bits.get(it + 1)));
                break;
            case 1:
                condition = (bool(bits.get(it - 2)) && !bool(bits.get(it - 1)) && !bool(bits.get(it)) && bool(bits.get(it + 1)));
                break;
            case 2:
                condition = (bool(bits.get(it - 3)) && !bool(bits.get(it - 2)) && !bool(bits.get(it - 1)) && !bool(bits.get(it)) && bool(bits.get(it + 1)));
                break;
            case 3:
                condition = (bool(bits.get(it - 4)) && !bool(bits.get(it - 3)) && !bool(bits.get(it - 2)) && !bool(bits.get(it - 1)) && !bool(bits.get(it)) && bool(bits.get(it + 1)));
                break;
            case 4:
                condition = (bool(bits.get(it - 5)) && !bool(bits.get(it - 4)) && !bool(bits.get(it - 3)) && !bool(bits.get(it - 2)) && !bool(bits.get(it - 1)) && !bool(bits.get(it)) && bool(bits.get(it + 1)));
                break;
            case 5:
                condition = (bool(bits.get(it - 6)) && !bool(bits.get(it - 5)) && !bool(bits.get(it - 4)) && !bool(bits.get(it - 3)) && !bool(bits.get(it - 2)) && !bool(bits.get(it - 1)) && !bool(bits.get(it)) && bool(bits.get(it + 1)));
                break;
        }
        return condition;
    }

    boolean bool(Integer bit) {
        if (bit == 1)
            return true;

        return false;
    }

    int convertToDecimal(List<Integer> array) {
        int[] values = {8, 4, 2, 1};
        int value = 0;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) == 1)
                value += values[i];
        }
        return value;
    }
}
