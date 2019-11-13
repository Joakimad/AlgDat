package Øving12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UnZipZapIt {

    public static void main(String[] args) {


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

