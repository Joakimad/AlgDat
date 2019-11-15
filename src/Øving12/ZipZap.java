package Øving12;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ZipZap {

    private static final byte MATCH_LENGTH = 5;

    private byte[] bytesFromFile = new byte[0];
    private byte[] bytesToFile = new byte[0];
    private int outputLength;

    public void compress(String filename) throws IOException {

        readFile(filename);

        byte uncompressedCount = 0;
        int uncompressedStartIndex = -1;

        for (int i = 0; i < bytesFromFile.length; i++) {

            byte matchLength = 0;
            int matchIndex = -1;
            int encodedLength = outputLength + 2; // Reserve 2 bytes for offset and length

            // Searches for similar pattern 127 bytes behind itself.
            final int searchStart = Math.max(0, i - 127);


            for (int j = searchStart; j < i; j++) {

                if (i + matchLength >= bytesFromFile.length) {
                    break;
                }

                if (bytesFromFile[j] == bytesFromFile[i + matchLength]) {
                    if (matchIndex == -1) {
                        matchIndex = j;
                    }

                    matchLength++;
                    bytesToFile[encodedLength++] = bytesFromFile[j];

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

                    bytesToFile[uncompressedStartIndex] = (byte) -uncompressedCount;
                    uncompressedStartIndex = -1;
                    uncompressedCount = 0;
                    outputLength++;
                }

                bytesToFile[outputLength++] = matchLength;
                bytesToFile[outputLength++] = (byte) (i - matchIndex);

                i += matchLength - 1; // -1 because i++ in for-loop
            } else {
                // Didn't find any matches, uncompressed
                // Set uncompressed start
                if (uncompressedStartIndex == -1) {
                    uncompressedStartIndex = outputLength;
                }
                uncompressedCount++;
                bytesToFile[++outputLength] = bytesFromFile[i]; // Reserve 1 byte for length
            }

            // Check if uncompressed block is full
            if (uncompressedCount == 127) {
                // Finish block and reset counters
                bytesToFile[uncompressedStartIndex] = -127;
                uncompressedStartIndex = -1;
                uncompressedCount = 0;
                outputLength++; // 1 byte for length
            }
        }

        // Check if any leftover uncompressed data
        if (uncompressedCount > 0) {
            // Finish block
            bytesToFile[uncompressedStartIndex] = (byte) -uncompressedCount;
            outputLength++; // 1 byte for length
        }

        // Write to new compressed file.
        writeFile();
    }

    private void readFile(String infile) {
        String path = "src/Øving12/testfiles/" + infile;
        try {
            bytesFromFile = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytesToFile = new byte[bytesFromFile.length];
    }

    private void writeFile() {
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("src/Øving12/compressed/testfile.zipzap")))) {
            dos.write(bytesToFile, 0, outputLength);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

