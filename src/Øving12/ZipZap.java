package Øving12;

import java.io.*;

public class ZipZap {

    private Reader inFile;
    private PrintWriter outFile;
    private int bufferSize = 65536;
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
        int currentIndex = 0;

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
            currentIndex++;
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
        StringBuilder text = new StringBuilder();
        int encodedIndex;
        int encodedLength;
        int currentIndex = 0;

        // Parse through file char by char.
        while ((currentChar_int = inFile.read()) != -1) {

            text.append((char) currentChar_int);
            searchBuffer.append((char) currentChar_int);

            StringBuilder tempString = new StringBuilder();

            // Found encoded piece. Finding lookback and length of string.
            if (currentChar_int == '~') {

                while ((currentChar_int = inFile.read()) != '-') {
                    text.append((char) currentChar_int);
                    tempString.append((char) currentChar_int);
                }
                text.append((char) currentChar_int);

                encodedIndex = Integer.parseInt(tempString.toString());
                tempString = new StringBuilder();

                while ((currentChar_int = inFile.read()) != '~') {
                    text.append((char) currentChar_int);
                    tempString.append((char) currentChar_int);
                }
                text.append((char) currentChar_int);

                encodedLength = Integer.parseInt(tempString.toString());

                System.out.println(encodedIndex + " - " + encodedLength);

                int start = encodedIndex;

                System.out.println("Start: " + start);

                //Replaces the encoded text with the actual text.
                StringBuilder uncompressed = new StringBuilder();
                for (int i = start; i < encodedLength; i++) {
                    uncompressed.append(text.charAt(i));
                }

                System.out.println("Replaced: " + uncompressed);
                outFile.print(uncompressed);

            } else {
                outFile.print((char) currentChar_int);
            }
            currentIndex++;
        }

        //System.out.println(text);

        // close files
        inFile.close();
        outFile.flush();
        outFile.close();
    }
}

