package Øving12;

import java.io.*;

public class Client {

    public static void main(String[] args) {

        //READ FILE AND COMPRESS IT

        String file = "src/Øving12/testfiler/testfil2.txt"
        String input = "";



        String innfil = new DataInputStream(new BufferedInputStream(new FileInputStream(inn_navn)));

        System.out.println(input);

    }

    private static String readFile(String filename) throws IOException {

        //Åpne filer:

        utfil = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(ut_navn)));


//Lese data fra fil inn i byte-array:
// byte []data : arrayet vi leser inn i
// int posisjon : index i byte-array for det vi leser inn
// int mengde : antall byte vi vil lese inn
        innfil.readFully(data, posisjon, mengde);


    }


}
