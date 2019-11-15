package Øving12;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UnZipZap {

    private byte[] input;
    private byte[] output;
    private int outputLength;

    public void decompress(String filename) {

        // Reads file and populates input array and size of output array
        readFile(filename);

        System.out.println("INPUT SIZE: " + input.length);
        System.out.println("OUTPUT SIZE: " + output.length);

        for (int i = 0; i < input.length; i++) {
            byte currentByte = input[i];
            if (currentByte > 0) {
                // Compressed data
                byte length = currentByte;
                byte offset = input[++i];
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
                    output[outputLength++] = input[j];
                }
                i += length;
            } else {
                System.out.println("Parse error.");
            }
        }
        writeFile();
    }

    private void readFile(String filename) {
        String path = "src/Øving12/compressed/" + filename;
        try {
            input = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        output = new byte[input.length * 5];
    }

    private void writeFile() {
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("src/Øving12/uncompressed/testfile.txt")))) {
            dos.write(output, 0, outputLength);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}