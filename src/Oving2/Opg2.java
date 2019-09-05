package Oving2;

import java.util.Date;

public class Opg2 {

    public static void main(String[] args) {

        double x = 1.001;
        int n = 5000;

        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            power(x,n);
            slutt = new Date();
            runder++;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double)
                (slutt.getTime() - start.getTime()) / runder;
        System.out.println("Millisekund pr. runde:" + tid);
    }

    private static double power(double x, int n) {

        if (n == 0) {
            return 1;
        }
        if (n % 2 == 0) {
            return x * power(x*x,n/2);
        }
        else {
            return x * power(x*x,(n-1)/2);
        }

    }

}

