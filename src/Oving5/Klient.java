package Oving5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Klient {

    public static void main(String[] args) {

        String names = "";
        try {
            names = readFile("src/Oving5/navn.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] nameArr = names.split("\n");
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
