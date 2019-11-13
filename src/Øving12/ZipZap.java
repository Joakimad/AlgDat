package Øving12;

import java.io.*;

public class ZipZap {

    private Reader in;
    private PrintWriter out;
    private int bufferSize = 1024;
    private StringBuffer searchBuffer = new StringBuffer(bufferSize);

    private void trimSearchBuffer() {
        if (searchBuffer.length() > bufferSize) {
            searchBuffer = searchBuffer.delete(0, searchBuffer.length() - bufferSize);
        }
    }

    public void compress(String infile) throws IOException {

        in = new BufferedReader(new FileReader("src/Øving12/testfiles/" + infile));
        out = new PrintWriter(new BufferedWriter(new FileWriter("src/Øving12/compressed/" + infile)));

        int currentChar_int;
        currentChar_int = in.read();
        int matchId = 0;

        // Reads 1 char at the time until end of file.
        while (currentChar_int != -1) {
            char currentChar = (char) currentChar_int;

            String match = "";

            int searchResult = searchBuffer.indexOf(match + currentChar);

            // If not match is found in buffer add to buffer.
            if (searchResult != -1) {
                match += currentChar;
                matchId = searchResult;

            } else {
                // Match found in buffer.
                String encoded = "-" + matchId + "-" + match.length() + "-" + currentChar;
                String original = match + currentChar;

                //Check if encoded is shorter than original
                if (encoded.length() <= original.length()) {
                    out.print(encoded);
                    searchBuffer.append(original);
                } else {

                    while (match.length() > 1) {

                        out.print(match.charAt(0));
                        searchBuffer.append(match.charAt(0));
                        match = match.substring(1, match.length());
                        matchId = searchBuffer.indexOf(match);
                    }
                }
                trimSearchBuffer();
            }
            currentChar_int = in.read();
        }


    }

    public void uncompress(String infile) throws IOException {

        in = new BufferedReader(new FileReader("src/Øving12/compressed/" + infile));
        out = new PrintWriter(new BufferedWriter(new FileWriter("src/Øving12/uncompressed/" + infile)));
    }
}

