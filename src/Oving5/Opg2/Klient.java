package Oving5.Opg2;

import java.util.Date;
import java.util.Random;

public class Klient {

    public static void main(String[] args) {
        int size = 625087;

        HashTable hs = new HashTable(size);

        Random rnd = new Random();
        int randNumSize = 5000000;
        int[] randomArr = new int[randNumSize];
        for (int i = 0; i < randomArr.length; i++) {
            randomArr[i] = rnd.nextInt(size * 100);
        }

        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            //Stuff here
            for (int i = 0; i < randNumSize; i++) {
                hs.addToTable(randomArr[i]);
                if (i % 1000000 == 0) {
                    System.out.println("Going strong");
                }
            }
            slutt = new Date();
            runder++;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double)
                (slutt.getTime() - start.getTime()) / runder;
        System.out.println("Millisekund pr. runde:" + tid);

        int amountElements = hs.countUsed();
        int collisions = hs.getCollisions();
        double avgCollision = (double) collisions / randNumSize;

        System.out.println("---\nAntall brukte plasser: " + amountElements);
        System.out.println("Lastefaktor: " + (double) amountElements / size);
        System.out.println("Antall kollisjoner: " + collisions);
        System.out.println("Gjennomsnittlig antall kollisjoner per person: " + avgCollision);
    }
}
