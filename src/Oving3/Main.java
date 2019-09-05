package Oving3;

import java.util.Date;
import java.util.Random;

public class Main {


    public static void main(String[] args) {

        Random rd = new Random(); // creating Random object
        int[] testArr = new int[1000];
        for (int i = 0; i < testArr.length; i++) {
            testArr[i] = rd.nextInt(20)-10;
        }


        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            quickSort(testArr, 0, testArr.length - 1);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double)
                (slutt.getTime() - start.getTime()) / runder;
        System.out.println("Millisekund pr. runde:" + tid);

        for (int element : testArr) {
            System.out.print(element + " ");
        }
    }

    private static void quickSort(int[] arr, int begin, int end) {
        //Hvis deltabellene kan deles opp mer
        if (end - begin > 2) {
            int delepos = partition(arr, begin, end);
            quickSort(arr, begin, delepos - 1);
            quickSort(arr, delepos + 1, end);
        } else {
            insertionSort(arr, begin, end);
        }
    }

    private static void insertionSort(int[] arr, int begin, int end) {
        for (int j = begin; j < end; j++) {
            int swap = arr[j];
            int i = j - 1;
            while (i >= 0 && arr[i] > swap) {
                arr[i + 1] = arr[i];
                i--;
            }
            arr[i + 1] = swap;
        }
    }

    //Finner delingstall
    private static int partition(int arr[], int begin, int end) {

        int pivot = arr[(end - begin) / 2];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                int swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }

        int swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }
}
