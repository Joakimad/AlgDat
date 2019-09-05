package Oving1;

import java.util.Date;
import java.util.Random;

public class ProfitMaker {

    /**
     * Vi har ei tallrekke som angir forandring i kurs for en aksje fra dag til dag. Vi ønsker
     * å finne hvordan vi kunne fått best fortjeneste, dvs. maksimal positiv differanse
     * mellom kjøpspris og salgspris. Kjøp må selvsagt skje før salg.
     * Se tabell 1.1 på motstående side for et eksempel på en slik tallrekke.
     * Det meste vi kan tjene på ett kjøp fulgt av salg, er 5. Det får vi til ved å kjøpe etter
     * kursfallet på dag 3 og selge igjen etter oppgangen på dag 7.
     */

    public static void main(String[] args) {

        Random rd = new Random(); // creating Random object
        int[] randomArray = new int[10000];
        for (int i = 0; i < randomArray.length; i++) {
            randomArray[i] = rd.nextInt(20)-10;
        }

        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            int[] stockChanges = randomArray;

            int highestDiff = 0;
            int lastDiff = 0;

            for (int day = 0; day < stockChanges.length; day++) {

                for (int sDay = day + 1; sDay < stockChanges.length; sDay++) {

                    int diff = stockChanges[sDay] + lastDiff;
                    if (diff > highestDiff) {
                        highestDiff = diff;
                    }
                    lastDiff = diff;
                }
                lastDiff = 0;
            }
            slutt = new Date();
            ++runder;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double)
                (slutt.getTime() - start.getTime()) / runder;
        System.out.println("Millisekund pr. runde:" + tid);
    }

}

