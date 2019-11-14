package Øving12;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.CharBuffer;
import java.util.ArrayList;


public class ZipZap {

    private BufferedReader in;
    private PrintWriter out;
    private int bufferSize = 32767;
    private Øving12.CircularBuffer searchBuffer = new Øving12.CircularBuffer(bufferSize);
    private ArrayList<Short> Order = new ArrayList<Short>();
    private ArrayList<Short> Length = new ArrayList<Short>();
    int currentElement = -1;
    char[] Characters;
    String Output = "";
    int divisionNumber = 8;

    public void compress(String infile) throws IOException {

       in = new BufferedReader(new FileReader("C:\\Users\\Jon\\IdeaProjects\\Øving12\\src\\Øving12\\testfiles\\" + infile));
        out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\Jon\\IdeaProjects\\Øving12\\src\\Øving12\\compressed\\" + infile)));

        String inputText = "";
        String line = "";
        while ((line = in.readLine()) != null){
            inputText += line + '\n';
        }


        Characters = inputText.toCharArray();
      //  for(int k = 0; k<Characters.length; k++) System.out.println(Characters[k]);
        int[] value = {-1, -1};
        short counter = 0;
        for (currentElement = 0; currentElement< Characters.length; currentElement++){
           ArrayList<Integer> usages = searchBuffer.findLetterAll(Characters[currentElement]);
           if (usages.size()>0 && currentElement<=(Characters.length-2)){
               value = findLongestRepetableString(currentElement+1, usages);
               if(value[0] > divisionNumber){
                   Order.add(counter);
                   counter = 0;
                   //System.out.println("her repeteres noe fra index: " + currentElement + " med lengde " + value[0] + " med " + value[1] +" steg bakover");
                   Order.add((short)value[1]);
                   Length.add((short) value[0]);
               }
               else{
                   counter =(short) (counter -1);
               }
           }
           else{
               counter = (short) (counter -1);
           }
           searchBuffer.insert(Characters[currentElement]);
            if(value[0] > divisionNumber && usages.size()>0 && currentElement<=(Characters.length-2)) {
                int recentCurrentElement = currentElement +1;
                for (currentElement = currentElement; currentElement < (value[0] + recentCurrentElement-1); currentElement++){
                    searchBuffer.insert(Characters[currentElement]);
                }
                currentElement--;
            }
        }
        Order.add(counter);
        currentElement = 0;
        System.out.println(Order.toString());
        for (int i = 0; i<Order.size(); i++){
            int LengthPos = 0;
            if(Order.get(i)<(short)0) {
                Output += Order.get(i) + "~";
                int recentElement = currentElement;
                for (currentElement = currentElement; currentElement< recentElement-Order.get(i); currentElement++){
                    if(currentElement<Characters.length - 1) {
                        Output += inputText.charAt(currentElement);
                    }
                }
            }
            else {
                Output+=Order.get(i) + "~";
                Output+=Length.get(LengthPos) + "~";
                currentElement = currentElement + Length.get(LengthPos);
                LengthPos++;
            }
        }
        System.out.println(Output);
        System.out.println(inputText);
        out.print(Output);
        out.close();

    }

    public void uncompress(String infile) throws IOException {

        in = new BufferedReader(new FileReader("src/Øving12/compressed/" + infile));
        out = new PrintWriter(new BufferedWriter(new FileWriter("src/Øving12/uncompressed/" + infile)));
    }
    //finds how long we can go back and how far we can go back to find it, int[] = {the position of the last found letter in the chain, how far back we have to go}
    public int[] findLongestRepetableString(int pos, ArrayList<Integer> usages){
        ArrayList<Integer> newPos = searchBuffer.findLetter(Characters[pos], usages);
        int[] info = new int[2];
        if( newPos.size()>0) {
            pos++;
            if(pos<Characters.length) {
                info = findLongestRepetableString(pos, newPos);
            }
        }
        else{
            info[0] = pos - currentElement;
            info[1] = searchBuffer.returnPosition(pos - currentElement, usages);
        }
        return info;

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
