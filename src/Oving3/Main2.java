package Oving3;

import java.util.Date;

public class Main2 {


    private static void quicksort(int t[], int v, int h, int deletall) {
        if (h - v > deletall) {
            int delepos = splitt(t, v, h);
            quicksort(t, v, delepos - 1, deletall);
            quicksort(t, delepos + 1, h, deletall);
        } else insertionSort(t, v, h);
    }

    private static void bytt(int[] t, int i, int j) {
        int k = t[j];
        t[j] = t[i];
        t[i] = k;
    }

//    private static int median3sort(int[] t, int v, int h) {
//        int m = (v + h) / 2;
//        if (t[v] > t[m]) bytt(t, v, m);
//        if (t[m] > t[h]) {
//            bytt(t, m, h);
//            if (t[v] > t[m]) bytt(t, v, m);
//        }
//        return m;
//    }

    private static int splitt(int[] t, int v, int h) {
        int iv, ih;
        int m = insertionSort(t, v, h);
        int dv = t[m];
        bytt(t, m, h - 1);
        for (iv = v, ih = h - 1; ; ) {
            while (t[++iv] < dv) ;
            while (t[--ih] > dv) ;
            if (iv >= ih) break;
            bytt(t, iv, ih);
        }
        bytt(t, iv, h - 1);
        return iv;
    }

    private static int insertionSort(int[] t, int v, int h) {
        for (int j = v; j <= h; j++) {
            int swap = t[j];
            int i = j - 1;
            while (i >= v && t[i] > swap) {
                t[i + 1] = t[i];
                i--;
            }
            t[i + 1] = swap;
        }
        return (v + h) / 2;
    }

    public static void main(String[] args) {

        int n = 100000;
        int[] Orgarray = RandomArray.generateRandomIntArray(n, 100);

        int deletall;
        double kortestTid = 10000;
        int kortestTidDeletall = 1;

        for (deletall = 2; deletall < 1000; deletall += 50) {
            int[] array = Orgarray;
            Date start = new Date();
            int runder = 0;
            double tid;
            Date slutt;

            do {
                quicksort(array, 0, n - 1, deletall);
                slutt = new Date();
                ++runder;
            } while (slutt.getTime() - start.getTime() < 2000);

            tid = (double)
                    (slutt.getTime() - start.getTime()) / runder;

            if (tid < kortestTid) {
                kortestTid = tid;
                kortestTidDeletall = deletall;
            }

            System.out.println(deletall + " - Millisekund pr. runde: " + tid);

            for (int i = 0; i < array.length - 1; i++) {
                if (!(array[i + 1] >= array[i])) {
                    System.out.println("feil");
                }
            }
        }
        System.out.println("Kortest tid - Deletall:" + kortestTidDeletall + " Tid: " + kortestTid);
    }
}