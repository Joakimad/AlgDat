package Øving12;

import java.io.*;

public class ZipZap {

    private Reader inFile;
    private PrintWriter outFile;
    private int bufferSize = 1024;
    private StringBuffer searchBuffer = new StringBuffer(bufferSize);

    private void trimSearchBuffer() {
        if (searchBuffer.length() > bufferSize) {
            searchBuffer = searchBuffer.delete(0, searchBuffer.length() - bufferSize);
        }
    }

    public void compress(String infile) throws IOException {

        inFile = new BufferedReader(new FileReader("src/Øving12/testfiles/" + infile));
        outFile = new PrintWriter(new BufferedWriter(new FileWriter("src/Øving12/compressed/zipzap-" + infile)));

        int currentChar_int;
        int matchIndex = 0;
        String match = "";

        // Reads 1 char at the time until end of file.
        while ((currentChar_int = inFile.read()) != -1) {

            char currentChar = (char) currentChar_int;
            int searchResult = searchBuffer.indexOf(match + currentChar);

            // If match replace text and update where to find textpiece
            if (searchResult != -1) {
                match += currentChar;
                matchIndex = searchResult;

            } else {
                // Match not found in buffer. Encode
                String encoded = "~" + matchIndex + "-" + match.length() + "~" + currentChar;
                String originalText = match + currentChar;

                //Check if encoded is shorter than original
                if (encoded.length() <= originalText.length()) {
                    outFile.print(encoded);
                    searchBuffer.append(originalText);
                    match = "";
                    matchIndex = 0;
                } else {
                    // output one char at the time until new match.
                    match = originalText;
                    matchIndex = -1;
                    while (match.length() > 1 && matchIndex == -1) {
                        outFile.print(match.charAt(0));
                        searchBuffer.append(match.charAt(0));
                        match = match.substring(1);
                        matchIndex = searchBuffer.indexOf(match);
                    }
                }
                trimSearchBuffer();
            }
        }
        if (matchIndex != -1) {
            String encoded = "~" + matchIndex + "~" + match.length();
            if (encoded.length() <= match.length()) {
                outFile.print("~" + matchIndex + "~" + match.length());
            } else {
                outFile.print(match);
            }
        }
        // close files
        inFile.close();
        outFile.flush();
        outFile.close();
    }

    public void uncompress(String infile) throws IOException {

        inFile = new BufferedReader(new FileReader("src/Øving12/compressed/" + infile));
        outFile = new PrintWriter(new BufferedWriter(new FileWriter("src/Øving12/uncompressed/" + infile)));

        int currentChar_int;

        while ((currentChar_int = inFile.read()) != -1) {

            String tempString = "";
            int encodedLookback = 0;
            int encodedLength = 0;

            // Found encoded piece. Start decompressing.
            if (currentChar_int == '~') {
                while ((currentChar_int = inFile.read()) != '-') {
                    tempString += (char) currentChar_int;
                }

                encodedLookback = Integer.parseInt(tempString);
                tempString = "";

                while ((currentChar_int = inFile.read()) != '~') {
                    tempString += (char) currentChar_int;
                }
                encodedLength = Integer.parseInt(tempString);
                System.out.println(encodedLookback + " - " + encodedLength);

                //inFile.read();
            } else {
                outFile.print((char) currentChar_int);
            }
        }
        // close files
        inFile.close();
        outFile.flush();
        outFile.close();
    }
}

