package Oving5.Opg2;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class Klient {

    public static void main(String[] args) {
        int size = 6000011;

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
            for (int i = 0; i < randNumSize; i++) {
                hs.addToTable(randomArr[i]);
            }
            slutt = new Date();
            runder++;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double)
                (slutt.getTime() - start.getTime()) / runder;
        System.out.println("---Custom Hash---");
        System.out.println("Millisekund pr. runde:" +  tid);

        int amountElements = hs.countUsed();
        int collisions = hs.getCollisions();
        double avgCollision = (double) collisions / randNumSize;

        System.out.println("\tAntall brukte plasser: " + amountElements);
        System.out.println("\tLastefaktor: " + (double) amountElements / size);
        System.out.println("\tAntall kollisjoner: " + collisions);
        System.out.println("\tGjennomsnittlig antall kollisjoner: " + avgCollision);

        System.out.println("\n---Hashmap---");

        HashMap<Integer,Integer> hmap = new HashMap<Integer,Integer>();

        start = new Date();
        runder = 0;
        do {
            for (int i = 0; i < randNumSize; i++) {
                hmap.put(randomArr[i],randomArr[i]);
            }
            slutt = new Date();
            runder++;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double)
                (slutt.getTime() - start.getTime()) / runder;
        System.out.println("Millisekund pr. runde:" + tid);
    }
}
