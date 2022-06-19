package com.tia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameUtils {

    /**
     * Get all possible pairs of items in array.
     * Resources: https://www.geeksforgeeks.org/find-all-pairs-possible-from-the-given-array/
     *
     * @param arr
     * @return list of int pairs
     */
    public static List<int[]> getAllPossiblePairs(int arr[]) {
        List<int[]> pairs = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                int[] pair = {arr[i], arr[j]};
                pairs.add(pair);
            }
        }

        return pairs;
    }

    /**
     * Resources: https://www.baeldung.com/java-random-list-element
     *
     * @param list
     * @return a pair of int.
     */
    public static int[] getRandomItemFromList(List<int[]> list) {
        Random rand = new Random();
        int[] randomElement = list.get(rand.nextInt(list.size()));
        return randomElement;
    }

    public static void print(Object name, Object obj) {
        System.out.println(name + ": " + obj);
    }

    public static Long convertToLong(Object o) {
        String stringToConvert = String.valueOf(o);
        Long convertedLong = Long.parseLong(stringToConvert);
        return convertedLong;
    }

}
