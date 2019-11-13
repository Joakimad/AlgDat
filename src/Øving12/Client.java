package Øving12;

import sun.text.resources.cldr.kok.FormatData_kok;

import java.io.IOException;

public class Client {

    public static void main(String[] args) {

        //READ FILE AND COMPRESS IT

        //testfile2 = 11 448 bytes
        ZipZap zipzap = new ZipZap();
        kok k = new kok();

        try {

            //k.compress("testfile2.txt");
            //k.uncompress("kok-testfile2.txt");

            //zipzap.compress("testfile2.txt");
            zipzap.uncompress("zipzap-testfile2.txt");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}