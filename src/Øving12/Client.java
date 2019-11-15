package Øving12;

import java.awt.image.ShortLookupTable;
import java.io.IOException;
import java.util.ArrayList;

public class Client {

    public static void main(String[] args) {

        //READ FILE AND COMPRESS IT

        //testfile2 = 11 448 bytes
        Øving12.ZipZap zipzap = new Øving12.ZipZap();

        try {

            zipzap.compress("testfile4.txt");
            //zipzap.uncompress("testfile3.pdf");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}