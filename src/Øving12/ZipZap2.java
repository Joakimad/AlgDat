package Øving12;

import java.io.*;

public class ZipZap2 {

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

        int offset;
        int length;
        char currentChar;

        // Reads 1 char at the time until end of file.
        while ((currentChar_int = inFile.read()) != -1) {

            currentChar = (char) currentChar_int;

            //check if pattern is in searchbuffer

            //String output = "(" + offset + "," + length "," + currentChar + ")";
            //outFile.print(output);

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

        // Parse through file char by char.
        while ((currentChar_int = inFile.read()) != -1) {

            text.append((char) currentChar_int);

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
            //System.out.println((char) currentChar_int);
        }


        System.out.println(text.substring(133, 133 + 8));

        //System.out.println(text);

        // close files
        inFile.close();
        outFile.flush();
        outFile.close();
    }
}

