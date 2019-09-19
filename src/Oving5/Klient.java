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

        for (int i = 0; i < nameArr.length; i++) {
            hs.addToTable(nameArr[i]);
        }

        System.out.println(hs.countAll());
        System.out.println(hs.countUsed(size) + " hallooooooooo");
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
