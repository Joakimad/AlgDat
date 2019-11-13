package Øving12;

import java.io.IOException;

public class Client {

    public static void main(String[] args) {

        //READ FILE AND COMPRESS IT

        //testfile2 = 11 448 bytes
        ZipZap zipzap = new ZipZap();

        try {

            //zipzap.compress("testfile2.txt");
            zipzap.uncompress("testfile2.txt");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}