package Øving12.kok;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Arrays;

public class LZ77 {
    private static final byte MATCH_LENGTH = 5;
    private String fileName;

    private byte[] input;
    private byte[] output;
    private int outputLength;

    public static void main(String[] args) throws IOException {
        new LZ77().run();
    }

    public void run() throws IOException {
        String path = "src/Øving12/testfiles/testfile4.txt";
        readFile(path);
        compress();
        writeFile();

        //test methods
        listArrays();
    }

    public void readFile(String fileName) throws IOException {
        this.fileName = fileName;
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)));
            input = IOUtils.toByteArray(dis);
            System.out.println("Byte array size: " + input.length);
        } catch(IOException ioe) {
            System.out.println("Error with reading file: " + ioe);
        } finally {
            dis.close();
            output = new byte[input.length];
        }
    }

    public void compress() {
        byte uncompressedCount = 0;
        int uncompressedStartIndex = -1;
        for(int i = 0; i < input.length; i++) {
            byte matchLength = 0;
            int matchIndex = -1;
            int newOutputLength = outputLength + 2; // Reserve 2 bytes for offset and length


            // For the current position:
            // Search for a similar pattern up to 127 bytes behind self
            final int searchStartIndex = Math.max(0, i - 127);
            for(int j = searchStartIndex; j < i; j++) {
                if(i + matchLength >= input.length)
                    break;

                if(input[j] == input[i + matchLength]) {
                    if(matchIndex == -1) matchIndex = j;
                    matchLength++;
                    if(newOutputLength >= output.length) {
                        System.out.println("Error");
                    }
                    if(j >= input.length) {
                        System.out.println("Error");
                    }
                    output[newOutputLength++] = input[j];
                } else if(matchIndex != -1) {
                    if(matchLength >= MATCH_LENGTH) {
                        // Long enough, return
                        break;
                    }
                    // Reset
                    matchIndex = -1;
                    matchLength = 0;
                }
            }

            // Any matches?
            if(matchIndex != -1 && matchLength >= MATCH_LENGTH) {
                // Check if we have stared making an uncompressed block
                if (uncompressedCount > 0) {
                    // Finish block and reset counters
                    output[uncompressedStartIndex] = (byte)-uncompressedCount;
                    uncompressedStartIndex = -1;
                    uncompressedCount = 0;
                    outputLength++; // 1 byte for length
                }

                // Add reference to match and length of match to output
                output[outputLength++] = matchLength; // length
                output[outputLength++] = (byte)(i - matchIndex); // offset behind

                System.out.println("Found match! \"" + convertToString(input, i, matchLength) + "\" (" + i + ")"
                        + " with \"" + convertToString(input, matchIndex, matchLength) + "\" (" + matchIndex + ")");

                i += matchLength - 1; // -1 because i++ in for-loop
            } else {
                // Didn't find any matches, uncompressed
                // Set uncompressed start
                if(uncompressedStartIndex == -1) {
                    uncompressedStartIndex = outputLength;
                }
                uncompressedCount++;
                output[++outputLength] = input[i]; // Reserve 1 byte for length
            }

            // Check if uncompressed block is full
            if (uncompressedCount == 127) {
                // Finish block and reset counters
                output[uncompressedStartIndex] = -127;
                uncompressedStartIndex = -1;
                uncompressedCount = 0;
                outputLength++; // 1 byte for length
            }
        }

        // Check if any leftover uncompressed data
        if (uncompressedCount > 0) {
            // Finish block
            output[uncompressedStartIndex] = (byte)-uncompressedCount;
            outputLength++; // 1 byte for length
        }
    }

    public void writeFile() throws IOException {
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("src/Øving12/compressed/kok-testfile.zipzap")));
            dos.write(output, 0, outputLength);
        } catch(IOException ioe) {
            System.out.println("Error with writing file: " + ioe);
        } finally {
            dos.close();
        }
    }

    //help methods
    private void listArrays() {
        System.out.println("Input: " + Arrays.toString(input));
        System.out.println("Output: " + Arrays.toString(output));
    }

    private static String convertToString(byte[] buffer, int startIndex, int count) {
        String s = "";
        for (int i = startIndex; i < startIndex + count; i++) {
            s += (char)(buffer[i]);
        }
        return s;
    }
}