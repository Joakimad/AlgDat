package Oving5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Klient {

    public static void main(String[] args) {
        int size = 128;
        String names = "";
        try {
            names = readFile("src/Oving5/navn.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] nameArr = names.split("\n");

        HashTable hs = new HashTable(size);

        for (String s : nameArr) {
            hs.addToTable(s);
        }

        int amountElements = hs.countUsed();
        int collisions = hs.getCollisions();
        int nameCount = nameArr.length;

        double avgCollision = (double) collisions/nameCount;

        System.out.println("---\nAntall elementer: " + amountElements);
        System.out.println("Lastefaktor: " + (double) amountElements / size);
        System.out.println("Gjennomsnittlig antall kollisjoner per person: " + avgCollision);
    }

    private static String readFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }
        return content.toString();
    }
}
