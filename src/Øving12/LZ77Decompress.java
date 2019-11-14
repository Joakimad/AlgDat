package Øving12;

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
        String path = "src/Øving12/compressed/testfile2.txt";
        readFile(path);
        decompress();
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
        } catch(IOException ioe) {
            System.out.println("Error with reading file: " + ioe);
        } finally {
            dis.close();
            output = new byte[input.length*5];
        }
    }

    public void decompress() {
        for(int i = 0; i < input.length; i++) {
            byte currentByte = input[i];
            if (currentByte > 0) {
                // Compressed data
                byte length = currentByte;
                byte offset = input[++i];
                int startIndex = outputLength - offset;
                if (startIndex < 0) {
                    System.out.println("Error! Negative start index!");
                }

                if(startIndex + length >= output.length) {
                    System.out.println("Error");
                    break;
                }

                for (int j = startIndex; j < startIndex + length; j++) {
                    output[outputLength++] = output[j];
                }
                System.out.println(i + ": " + convertToString(output, outputLength - length, length));

            } else if(currentByte < 0) {
                // Uncompressed data
                int length =- currentByte;
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

    public void writeFile() throws IOException {
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName.replace(".compressed", ".decompressed"))));
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