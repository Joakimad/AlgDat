package Oving2;

import java.util.Date;

public class Opg3 {

    public static void main(String[] args) {

        double x = 1.001;
        int n = 5000;

        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            Math.pow(x, n);
            slutt = new Date();
            runder++;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double)
                (slutt.getTime() - start.getTime()) / runder;
        System.out.println("Millisekund pr. runde:" + tid);
    }
}

