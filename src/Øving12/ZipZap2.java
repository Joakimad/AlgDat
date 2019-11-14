package Øving12;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ZipZap2 {

    private byte[] bytesFromFile = new byte[0];
    private byte[] out = new byte[0];
    private int outputLength;

    private void readFile(String infile) {
        String path = "src/Øving12/testfiles/" + infile;
        try {
            bytesFromFile = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            System.out.println("Error reading file" + e);
        }
        out = new byte[bytesFromFile.length];
    }

    private void writeFile(String infile) throws IOException {
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("src/Øving12/compressed/" + infile)));
            dos.write(out, 0, outputLength);
        } catch (IOException e) {
            System.out.println("Error with writing file: " + e);
        } finally {
            dos.close();
        }
    }

    public void compress(String filename) throws IOException {

        readFile(filename);

        if (bytesFromFile.length == 0) {
            System.out.println("Error reading file");
        }

        byte uncompressedCount = 0;
        int uncompressedStartIndex = -1;

        for (int i = 0; i < bytesFromFile.length; i++) {

            byte matchLength = 0;
            int matchIndex = -1;
            int encodedLength = outputLength + 2; // Reserve 2 bytes for offset and length

            // Searches for similar pattern 127 bytes behind itself.
            int SEARCH_LENGTH = 127;
            int searchStart = i - SEARCH_LENGTH;
            if (searchStart < 0) {
                searchStart = 0;
            }

            byte MATCH_LENGTH = 5;
            for (int j = searchStart; j < i; j++) {

                if (i + matchLength >= bytesFromFile.length) {
                    break;
                }

                if (bytesFromFile[j] == bytesFromFile[i + matchLength]) {
                    if (matchIndex == -1) {
                        matchIndex = j;
                    }

                    matchLength++;
                    out[encodedLength++] = bytesFromFile[j];

                } else if (matchIndex != -1) {
                    if (matchLength >= MATCH_LENGTH) {
                        break;
                    }
                    // Reset
                    matchIndex = -1;
                    matchLength = 0;
                }
            }

            // Any matches?
            if (matchIndex != -1 && matchLength >= MATCH_LENGTH) {
                if (uncompressedCount > 0) {

                    out[uncompressedStartIndex] = (byte) -uncompressedCount;
                    uncompressedStartIndex = -1;
                    uncompressedCount = 0;
                    outputLength++;
                }

                out[outputLength++] = matchLength;
                out[outputLength++] = (byte) (i - matchIndex);

                i += matchLength - 1; // -1 because i++ in for-loop
            } else {
                // Didn't find any matches, uncompressed
                // Set uncompressed start
                if (uncompressedStartIndex == -1) {
                    uncompressedStartIndex = outputLength;
                }
                uncompressedCount++;
                out[++outputLength] = bytesFromFile[i]; // Reserve 1 byte for length
            }

            // Check if uncompressed block is full
            if (uncompressedCount == 127) {
                // Finish block and reset counters
                out[uncompressedStartIndex] = -127;
                uncompressedStartIndex = -1;
                uncompressedCount = 0;
                outputLength++; // 1 byte for length
            }
        }

        // Check if any leftover uncompressed data
        if (uncompressedCount > 0) {
            // Finish block
            out[uncompressedStartIndex] = (byte) -uncompressedCount;
            outputLength++; // 1 byte for length
        }
        writeFile(filename);
    }
}

