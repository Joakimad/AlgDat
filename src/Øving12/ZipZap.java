package Øving12;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class ZipZap {

    private BufferedReader in;
    private PrintWriter out;
    private int bufferSize = 32767;
    private Øving12.CircularBuffer searchBuffer = new Øving12.CircularBuffer(bufferSize);
    private ArrayList<Short> Order = new ArrayList<Short>();
    private ArrayList<Short> Length = new ArrayList<Short>();
    int currentElement = -1;
    ArrayList<Byte> output = new ArrayList<Byte>();
    int divisionNumber = 4;
    byte[] inputBytes;
    byte[] inputBytes2;
    int orderCounter = -1;
    int lengthCounter = -1;
    int inputCounter = 0;

    public void compress(String infile) throws IOException {

       Path path = Paths.get("C:\\Users\\Jon\\IdeaProjects\\Øving12\\src\\Øving12\\testfiles\\" + infile);

        inputBytes = Files.readAllBytes(path);
        inputBytes2 = new byte[inputBytes.length];
        for(int i = 0; i<inputBytes.length; i++){
            inputBytes2[i] = inputBytes[i];
        }
        int[] value = {-1, -1};
        short counter = 0;
        for (currentElement = 0; currentElement< inputBytes.length; currentElement++){
            if(currentElement>291628){
                //System.out.println("here maybe");
            }

            if(counter> 32766 || counter<-32760){
                Order.add(counter);
                counter = 0;
            }
           ArrayList<Integer> usages = searchBuffer.findLetterAll(inputBytes[currentElement]);
           if (usages.size()>0 && currentElement<=(inputBytes.length-2)){
               value = findLongestRepetableString(currentElement+1, usages);
               if(value[0] > divisionNumber){
                   if(counter<0) {
                       Order.add(counter);
                   }
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
            if(value[0] > divisionNumber && usages.size()>0 && currentElement<=(inputBytes.length-2)) {
                int recentCurrentElement = currentElement +1;
                for (currentElement = currentElement; currentElement < (value[0] + recentCurrentElement-1); currentElement++){
                    searchBuffer.insert(inputBytes[currentElement]);
                }
                currentElement--;
            }
            else{
                searchBuffer.insert(inputBytes[currentElement]);
            }
        }
        Order.add(counter);
        currentElement = 0;
        byte[] order = shortToByte(Order);
        System.out.println(order.length);
        byte[] length = shortToByte(Length);
        System.out.println(length.length);
        System.out.println(order.toString());
        int LengthPos = 0;
        for (int i = 0; i<order.length/2; i++){
            if(Order.get(i) < 0) {
                output.add(order[i*2]);
                output.add(order[(i*2)+1]);
                int recentElement = currentElement;
                for (currentElement = currentElement; currentElement< recentElement-Order.get(i); currentElement++){
                    if(currentElement<inputBytes.length) {
                        output.add(inputBytes[currentElement]);
                    }
                }
            }
            else {
                output.add(order[2*i]);
                output.add(order[(2*i)+1]);
                output.add(length[LengthPos*2]);
                output.add(length[(LengthPos*2) +1]);
                currentElement = currentElement + bytesToShort(length, LengthPos*2);;
                LengthPos = LengthPos+1;
            }
        }
        //System.out.println(java.util.Arrays.toString(inputBytes));
        //System.out.println('\n');
        //System.out.println(output.toString());
        System.out.println(Order.toString());
        //System.out.println(java.util.Arrays.toString(order));
        System.out.println(Length.toString());
        //System.out.println(java.util.Arrays.toString(length));
        byte[] outputArray = new byte[output.size()];
        for(int i = 0; i<output.size(); i++){
            outputArray[i] = output.get(i);
        }
        try (FileOutputStream fos = new FileOutputStream("C:\\Users\\Jon\\IdeaProjects\\Øving12\\src\\Øving12\\compressed\\" + infile)) {
            fos.write(outputArray);
            //fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
        }

    }

    //finds how long we can go back and how far we can go back to find it, int[] = {the position of the last found letter in the chain, how far back we have to go}
    public int[] findLongestRepetableString(int pos, ArrayList<Integer> usages){
        ArrayList<Integer> newPos = searchBuffer.findLetter(inputBytes[pos], usages);
        int[] info = new int[2];
        if( newPos.size()>0) {
            pos++;
            if(pos<inputBytes.length) {
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

    public byte[] shortToByte(ArrayList<Short> s){
        byte[] converted = new byte[s.size()*2];
        for(int i = 0; i<s.size(); i++ ){
            converted[i*2] = (byte) (s.get(i) & 0x00FF);
            converted[(i*2)+1] =(byte) ((s.get(i) & 0xFF00) >> 8);
        }
        return converted;
    }

    public short bytesToShort(byte[] bytes, int startpos){
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.put(bytes[startpos]);
        bb.put(bytes[startpos+1]);
        return bb.getShort(0);
    }

    public void uncompress(String infile) throws IOException {
        output = new ArrayList<>();
        currentElement = -1;
        Path path = Paths.get("C:\\Users\\Jon\\IdeaProjects\\Øving12\\src\\Øving12\\compressed\\" + infile);

        inputBytes = Files.readAllBytes(path);
        short convertedBytes = 0;
        short convertedLength = 0;
        for (currentElement = 0; currentElement<inputBytes.length-1; currentElement++){
            convertedBytes = bytesToShort(inputBytes, currentElement);
            orderCounter++;
            if(convertedBytes<0){
                currentElement = currentElement + 2;
                int recentElement = currentElement;
                for(currentElement = currentElement; currentElement < recentElement - convertedBytes; currentElement++) {
                    output.add(inputBytes[currentElement]);
                    //testDecompression(convertedBytes, convertedLength);
                    inputCounter++;
                }
                currentElement--;
            }
            else {
                int sizeBeforeAdd = output.size();
                convertedLength = bytesToShort(inputBytes, currentElement+2);
                lengthCounter++;
                currentElement = currentElement + 3;
                for(int i = 0; i< convertedLength; i++){
                    output.add(output.get(sizeBeforeAdd-convertedBytes+i));
                    //testDecompression(convertedBytes, convertedLength);
                    inputCounter++;
                }
            }
        }
        out = new PrintWriter(new BufferedWriter(new FileWriter("src/Øving12/decompressed/" + infile)));
        byte[] outputArray = new byte[output.size()];
        for(int i = 0; i<output.size(); i++){
            outputArray[i] = output.get(i);
        }
        for (int i = 0; i<outputArray.length; i++){

        }
        try (FileOutputStream fos = new FileOutputStream("C:\\Users\\Jon\\IdeaProjects\\Øving12\\src\\Øving12\\decompressed\\" + infile)) {
            fos.write(outputArray);
            //fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
        }
    }

   /* public void testDecompression(short orderElement, short lengthElement){
        if(!(output.get(output.size()-1) == inputBytes2[inputCounter])) System.out.println("feil input i posisjon " + inputCounter + " input " + inputBytes2[inputCounter] + " output " + output.get(output.size() -1) + " the order is " + orderElement + " the length is " + lengthElement + " the posistion of order is " + orderCounter);
        if(orderElement != Order.get(orderCounter)) System.out.println("feil order motatt i " + orderCounter + " orderElement " + orderElement + " Order " + Order.get(orderCounter));
        if(lengthCounter > -1) {
            if (lengthElement != Length.get(lengthCounter)) System.out.println("feil Length motatt i " + lengthCounter + " orderElement " + lengthElement + " Order " + Length.get(lengthCounter));
            }
        if (orderElement<lengthElement && orderElement>-1) System.out.println("lengthelement høyere enn orderlement" + lengthElement + " " + orderElement + " orderelement posisjon " + orderCounter +" element og forrige elementer " + Order.get(orderCounter) + Order.get(orderCounter-1) + Order.get(orderElement-2));
        }*/
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
