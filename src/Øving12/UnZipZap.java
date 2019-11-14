package Øving12;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UnZipZap {

    private byte[] input;
    private byte[] output;
    private int outputLength;

    public void decompress(String filename) throws IOException {

        readFile(filename);

        for (int i = 0; i < input.length; i++) {
            byte currentByte = input[i];
            if (currentByte > 0) {
                // Compressed data
                byte length = currentByte;
                byte offset = input[i++];

                int startIndex = outputLength - offset;
                if (startIndex < 0) {
                    System.out.println("Error! Negative start index!");
                }

                if (startIndex + length >= output.length) {
                    System.out.println("Error");
                    break;
                }

                for (int j = startIndex; j < startIndex + length; j++) {
                    output[outputLength++] = output[j];
                }

            } else if (currentByte < 0) {
                // Uncompressed data
                int length = -currentByte;
                for (int j = i + 1; j <= i + length; j++) {
                    output[++outputLength] = input[j];

                    //out[++outputLength] = bytesFromFile[i]; // Reserve 1 byte for length

                }
                i += length;
            } else {
                System.out.println("Parse error.");
            }
        }
        writeFile(filename);
    }

    private void readFile(String filename) {
        String path = "src/Øving12/compressed/" + filename;
        try {
            input = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            System.out.println("Error reading file" + e);
        }
        output = new byte[input.length];
    }

    private void writeFile(String filename) throws IOException {
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("src/Øving12/uncompressed/" + filename)));
            dos.write(output, 0, outputLength);
        } catch (IOException e) {
            System.out.println("Error with writing file: " + e);
        } finally {
            dos.close();
        }
    }

}