package Øving12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ZipZapIt {

    public static void main(String[] args) {

        //READ FILE AND COMPRESS IT

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
