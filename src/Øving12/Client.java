package Øving12;

import java.io.IOException;

public class Client {

    public static void main(String[] args) {

        //READ FILE AND COMPRESS IT

        //testfile2 = 11 448 bytes
        ZipZap zipzap = new ZipZap();
        ZipZap2 zipzap2 = new ZipZap2();
        UnZipZap unzipzap = new UnZipZap();

        LZ77 lz = new LZ77();
        LZ77Decompress unlz = new LZ77Decompress();



        //zipzap.compress("testfile2.txt");
        //zipzap.uncompress("zipzap-testfile2.txt");

        //zipzap2.compress("testfile2.txt");
        //unzipzap.decompress("testfile2.txt");




    }
}