package Oving3;

import java.util.Date;

@SuppressWarnings("ALL")

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

    private static int splitt(int[] t, int v, int h) {
        int iv, ih;
        int m = Median3Sort(t, v, h);
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

    private static void insertionSort(int[] t, int v, int h) {
        for (int j = v+1; j <= h; j++) {
            int swap = t[j];
            int i = j - 1;
            while (i >= v && t[i] > swap) {
                t[i + 1] = t[i];
                i--;
            }
            t[i + 1] = swap;
        }
    }

    private static int Median3Sort(int[] t, int v, int h){
        int m = (v+h)/2;
        if(t[v]>t[m]) bytt(t,v,m);
        if(t[m]>t[h]){
            bytt(t,m,h);
            if(t[v]>t[m]) bytt(t,v,m);
        }
        return m;
    }

    private static boolean checkIfSorted(int[] array) {
        for (int i = 0; i < array.length - 2; i++) {
            if (!(array[i + 1] >= array[i])) {
                return false;
            }
        }
        return true;
    }

    private static int[] copyTable(int[] tabell){
        int[] copy = new int[tabell.length];
        for(int i = 0; i<tabell.length; i++){
            copy[i] = tabell[i];
        }
        return copy;
    }


    public static void main(String[] args) {

        int n = 100000;
        int[] Orgarray = Oving3.RandomArray.generateRandomIntArray(n, 100000);

        int kortestTidDeletall = 0;
        double kortestTid = 10000;

        int[] array;

        //Best deletall = 71?
        for (int deletall = 65; deletall < 85; deletall +=1) {
            Date start = new Date();
            int runder = 0;
            double tid;
            Date slutt;

            do {
                array = copyTable(Orgarray);
                quicksort(array, 0, n - 1, deletall);
                slutt = new Date();
                ++runder;
            } while (slutt.getTime() - start.getTime() < 5000);
            tid = (double)
                    (slutt.getTime() - start.getTime()) / runder;

            if (tid < kortestTid) {
                kortestTid = tid;
                kortestTidDeletall = deletall;
            }

            System.out.println(deletall + " - Millisekund pr. runde: " + tid);

            for (int i = 0; i < array.length - 2; i++) {
                if (!(array[i + 1] >= array[i])) {
                    System.out.println("feil");
                }
            }
        }
        System.out.println("Kortest tid - Deletall:" + kortestTidDeletall + " Tid: " + kortestTid);
    }
}