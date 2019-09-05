package Oving3;

import java.util.Random;

public class RandomArray {

    public static int[] generateRandomIntArray(int size,int bound) {
        Random rnd = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rnd.nextInt(bound);
        }
        return array;
    }
}