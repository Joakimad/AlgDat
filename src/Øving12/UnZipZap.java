package Øving12;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UnZipZap {

    private byte[] bytesFromFile;
    private byte[] bytesToFile;
    private int outputLength;

    public void decompress(String filename) {

        // Reads file and populates input array and size of output array
        readFile(filename);

        // Iterate through byte array
        for (int i = 0; i < bytesFromFile.length; i++) {

            byte currentByte = bytesFromFile[i];

            if (currentByte > 0) {
                // Compressed data
                byte length = currentByte;
                byte offset = bytesFromFile[++i];
                int startIndex = outputLength - offset;
                if (startIndex < 0) {
                    System.out.println("Error! Negative start index!");
                }

                if (startIndex + length >= bytesToFile.length) {
                    System.out.println("Error");
                    break;
                }

                for (int j = startIndex; j < startIndex + length; j++) {
                    bytesToFile[outputLength++] = bytesToFile[j];
                }

            } else if (currentByte < 0) {
                // Uncompressed data
                int length = -currentByte;
                for (int j = i + 1; j <= i + length; j++) {
                    bytesToFile[outputLength++] = bytesFromFile[j];
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
            bytesFromFile = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytesToFile = new byte[bytesFromFile.length * 5];
    }

    private void writeFile() {
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("src/Øving12/uncompressed/testfile.txt")))) {
            dos.write(bytesToFile, 0, outputLength);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}