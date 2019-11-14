package Øving12;

import java.io.IOException;

public class Client {

    public static void main(String[] args) {

        //READ FILE AND COMPRESS IT

        //testfile2 = 11 448 bytes
        Øving12.ZipZap zipzap = new Øving12.ZipZap();

        try {

            zipzap.compress("testfile3.pdf");
            //zipzap.uncompress("Test.txt");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}