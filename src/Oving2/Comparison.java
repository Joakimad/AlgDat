package Oving2;

import java.util.Date;

public class Comparison {

    public static void main(String[] args) {

        double x = 1.001;
        int n = 5000 ;

        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            power1(x, n);
            slutt = new Date();
            runder++;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double)
                (slutt.getTime() - start.getTime()) / runder;
        System.out.println("Opg1 - Millisekund pr. runde:" + tid + " - runder: " + runder);

        start = new Date();
        runder = 0;
        do {
            power2(x, n);
            slutt = new Date();
            runder++;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double)
                (slutt.getTime() - start.getTime()) / runder;
        System.out.println("Opg2 - Millisekund pr. runde:" + tid + " - runder: " + runder);

        start = new Date();
        runder = 0;
        do {
            Math.pow(x, n);
            slutt = new Date();
            runder++;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double)
                (slutt.getTime() - start.getTime()) / runder;
        System.out.println("Opg3 - Millisekund pr. runde:" + tid + " - runder: " + runder);
    }

    private static double power1(double x, int n) {
        if (n != 0)
            return (x * power1(x, n - 1));
        else
            return 1;
    }

    private static double power2(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (n % 2 == 0) {
            return x * power2(x * x, n / 2);
        } else {
            return x * power2(x * x, (n - 1) / 2);
        }
    }
}
