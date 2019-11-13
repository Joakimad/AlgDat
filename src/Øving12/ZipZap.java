package Øving12;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.CharBuffer;
import java.util.ArrayList;

public class ZipZap {

    private Reader in;
    private PrintWriter out;
    private int bufferSize = 32767;
    private CircularBuffer searchBuffer = new CircularBuffer(bufferSize);
   // private ArrayList<String> currentChars = new ArrayList<String>();
    char[] Characters;

    public void compress(String infile) throws IOException {

       BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\jon-stasjonær\\IdeaProjects\\Joakim sin git 2 år\\src\\Øving12\\testfiles\\" + infile));
        //out = new PrintWriter(new BufferedWriter(new FileWriter("src/Øving12/compressed/" + infile)));

        String inputText = "";
        String line = "";
        while ((line = in.readLine()) != null){
            inputText += line;
        }


        Characters = inputText.toCharArray();
      //  for(int k = 0; k<Characters.length; k++) System.out.println(Characters[k]);
        int lengthRepeat = -1;
        for (int i = 0; i< Characters.length; i++){
           ArrayList<Integer> usages = searchBuffer.findLetterAll(Characters[i]);
           if (usages.size()>0 && i<=(Characters.length-2)){
               lengthRepeat = findLongestRepetableString(i+1, usages);
              if(lengthRepeat - i > 3) System.out.println("her repeteres noe fra index: " + i + " med lengde " + (lengthRepeat-i));
           }
           searchBuffer.insert(Characters[i]);
        }


    }

    public void uncompress(String infile) throws IOException {

        in = new BufferedReader(new FileReader("src/Øving12/compressed/" + infile));
        out = new PrintWriter(new BufferedWriter(new FileWriter("src/Øving12/uncompressed/" + infile)));
    }

    public int findLongestRepetableString(int pos, ArrayList<Integer> usages){
        ArrayList<Integer> newPos = searchBuffer.findLetter(Characters[pos], usages);
        int bufferPos = 0;
        if( newPos.size()>0) {
            pos++;
            bufferPos++;
            if(pos<Characters.length) {
                pos = findLongestRepetableString(pos, newPos);
            }
        }
        else{}
        return pos;

    }

    public int findHowFarBackToGo() {return 0;};

    public void addToBuffer(int start, int stop){

    }
}


// Reads 1 char at the time until end of file.
   /*     while (currentChar_int != -1) {
                char currentChar = (char) currentChar_int;

                String match = "";

                int searchResult = searchBuffer.indexOf(match + currentChar);

                // If not match is found in buffer add to buffer.
                if (searchResult != -1) {
                match += currentChar;
                matchId = searchResult;

                } else {
                // Match found in buffer.
                String encoded = "-" + matchId + "-" + match.length() + "-" + currentChar;
                String original = match + currentChar;

                //Check if encoded is shorter than original
                if (encoded.length() <= original.length()) {
                out.print(encoded);
                searchBuffer.append(original);
                } else {

                while (match.length() > 1) {

                out.print(match.charAt(0));
                searchBuffer.append(match.charAt(0));
                match = match.substring(1, match.length());
                matchId = searchBuffer.indexOf(match);
                }
                }
                trimSearchBuffer();
                }
                currentChar_int = in.read();
                }
*/
