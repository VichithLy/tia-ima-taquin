package com.tia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameUtils {

    /**
     * https://www.geeksforgeeks.org/find-all-pairs-possible-from-the-given-array/
     * @param arr
     * @return all possible pairs of items in array
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
     * https://www.baeldung.com/java-random-list-element
     * @param list
     * @return
     */
    public static int[] getRandomItemFromList(List<int[]> list) {
        Random rand = new Random();
        int[] randomElement = list.get(rand.nextInt(list.size()));
        return randomElement;
    }

}