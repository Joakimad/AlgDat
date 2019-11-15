package Øving12;

import java.io.IOException;

public class Client {

    public static void main(String[] args) throws IOException {

        //testfile2 = 11 448 bytes
        ZipZap zipzap = new ZipZap();
        UnZipZap unzipzap = new UnZipZap();

        //zipzap.compress("testfile2.txt");
        unzipzap.decompress("testfile.zipzap");
    }
}