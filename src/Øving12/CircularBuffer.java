package Øving12;

/**
 ** Java Program to implement Circular Buffer
 **/

import java.util.ArrayList;

/** Class Circular Buffer **/
class CircularBuffer
{
    private int maxSize;
    private int front = 0;
    private int rear = 0;
    private int bufLen = 0;
    private byte[] buf;

    /** constructor **/
    public CircularBuffer(int size)
    {
        maxSize = size;
        front = rear = -1;
        rear = -1;
        bufLen = 0;
        buf = new byte[maxSize];
    }
    /** function to get size of buffer **/
    public int getSize()
    {
        return bufLen;
    }
    /** function to clear buffer **/
    public void clear()
    {
        front = rear = 0;
        rear = 0;
        bufLen = 0;
        buf = new byte[maxSize];
    }
    /** function to check if buffer is empty **/
    public boolean isEmpty()
    {
        return bufLen == 0;
    }
    /** function to check if buffer is full **/
    public boolean isFull()
    {
        return bufLen == maxSize;
    }
    /** insert an element **/
    public void insert(byte c)
    {
        if (isFull()){
            delete();
        }
        bufLen++;
        rear = (rear + 1) % maxSize;
        buf[rear] = c;
    }
    /** delete an element **/
    public byte delete()
    {
        if (!isEmpty() )
        {
            bufLen--;
            front = (front + 1) % maxSize;
            return buf[front];
        }
        else
        {
            System.out.println("Error : Underflow Exception");
            return ' ';
        }
    }

    public ArrayList<Integer> findLetterAll(byte search){
        ArrayList<Integer> posisjoner = new ArrayList<Integer>();
        if(bufLen==maxSize) {
            for (int i = 0; i < maxSize; i++) {
                if (buf[i] == search) posisjoner.add(i);
            }
        }
        else{
            for (int i = 0; i < bufLen; i++){
                if (buf[i] == search) posisjoner.add(i);
            }
        }

        return posisjoner;
    }
    public ArrayList<Integer> findLetter(byte search, ArrayList<Integer> positions){
        ArrayList<Integer> posNew = new ArrayList<>();
        if(bufLen==maxSize) {
            for (int i = 0; i < positions.size(); i++) {
                if (buf[(positions.get(i) + 1) % maxSize] == search && positions.get(i) + 1 != rear + 1) posNew.add(positions.get(i) + 1);
            }
        }
        else {
            for (int i = 0; i < positions.size(); i++) {
                if (buf[(positions.get(i) + 1) % maxSize] == search && positions.get(i) + 1 < bufLen) posNew.add(positions.get(i) + 1);
            }
        }
        return  posNew;
    }

    public int returnPosition(int length, ArrayList<Integer> positions) {
        int pos = positions.get(0);
        if (pos-length > 0 ){
            pos = pos-length;
        }
        else {
            pos = maxSize + pos - length;
        }
        if( rear - pos > 0) {
            return rear - pos;
        }


        else {
            return rear + maxSize - pos;
        }
    }
}

