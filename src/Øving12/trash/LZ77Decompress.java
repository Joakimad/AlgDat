package Øving12.trash;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Arrays;

public class LZ77Decompress {
    private String fileName;

    private byte[] input;
    private byte[] output;
    private int outputLength;

    public static void main(String[] args) throws IOException {
        new LZ77Decompress().run();
    }

    public void run() throws IOException {
        String path = "src/Øving12/compressed/kok-testfile.zipzap";
        readFile(path);
        decompress();
        writeFile();

        //test methods
        listArrays();
    }

    public void decompress() {

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
                System.out.println(i + ": " + convertToString(output, outputLength - length, length));

            } else if (currentByte < 0) {
                // Uncompressed data
                int length = -currentByte;
                for (int j = i + 1; j <= i + length; j++) {
                    output[outputLength++] = input[j];
                }
                System.out.println(i + ": " + convertToString(output, outputLength - length, length));
                i += length;
            } else {
                System.out.println("Parse error.");
            }
        }
    }

    private void readFile(String fileName) throws IOException {
        this.fileName = fileName;
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
            input = IOUtils.toByteArray(dis);
        } catch (IOException ioe) {
            System.out.println("Error with reading file: " + ioe);
        } finally {
            output = new byte[input.length * 5];
        }
    }

    private void writeFile() throws IOException {
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("src/Øving12/uncompressed/kok-testfile.txt")));
            dos.write(output, 0, outputLength);
        } catch (IOException ioe) {
            System.out.println("Error with writing file: " + ioe);
        } finally {
            assert dos != null;
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
            s += (char) (buffer[i]);
        }
        return s;
    }
}