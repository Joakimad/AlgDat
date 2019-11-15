package Øving12;

import java.io.IOException;

public class Client {

    public static void main(String[] args) {

        //testfile2 = 11 448 bytes
        ZipZap zipzap = new ZipZap();
        UnZipZap unzipzap = new UnZipZap();

        try {
            zipzap.compress("testfile4.txt");
            unzipzap.decompress("testfile.zipzap");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}