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
        String match = "";

        // Reads 1 char at the time until end of file.
        while (currentChar_int != -1) {
            char currentChar = (char) currentChar_int;

            int searchResult = searchBuffer.indexOf(match + currentChar);

            // If not match is found in buffer add to buffer.
            if (searchResult != -1) {
                match += currentChar;
                matchId = searchResult;

            } else {
                // Match found in buffer.
                String encoded = "-" + matchId + "-" + match.length() + "-" + currentChar;
                String originalText = match + currentChar;

                //Check if encoded is shorter than original
                if (encoded.length() <= originalText.length()) {
                    out.print(encoded);
                    searchBuffer.append(originalText);
                } else {
                    match = originalText;
                    matchId = -1;
                    while (match.length() > 1) {
                        out.print(match.charAt(0));
                        searchBuffer.append(match.charAt(0));
                        match = match.substring(1);
                        matchId = searchBuffer.indexOf(match);
                    }
                }
                trimSearchBuffer();
            }
            currentChar_int = in.read();
        }
        if (matchId != -1) {
            String codedString =
                    "~" + matchId + "~" + match.length();
            if (codedString.length() <= match.length()) {
                out.print("~" + matchId + "~" + match.length());
            } else {
                out.print(match);
            }
        }
        // close files
        in.close();
        out.flush();
        out.close();
    }

    public void uncompress(String infile) throws IOException {

        in = new BufferedReader(new FileReader("src/Øving12/compressed/" + infile));
        out = new PrintWriter(new BufferedWriter(new FileWriter("src/Øving12/uncompressed/" + infile)));
    }
}

