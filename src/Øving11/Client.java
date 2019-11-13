package Øving11;

public class Client {

    public static void main(String[] args) {

        char[] inputAlphabetA = {'0', '1'};
        char[] inputAlphabetB = {'a', 'b'};

        //start på 0 nøyaktig en 1
        int[][] nextStateA = {{1, 3}, {1, 2}, {2, 3}, {3, 3}};
        Automat a = new Automat(inputAlphabetA, 2, nextStateA);

        // start på ab eller ba
        int[][] nextStateB = {{1, 2}, {3, 4}, {4, 3}, {3, 3}, {4, 4}};
        Automat b = new Automat(inputAlphabetB, 4, nextStateB);

        //false
        System.out.println("Sjekker tom string - " + a.sjekkInput(""));

        //true
        System.out.println("Sjekker 010 - " + a.sjekkInput("010"));

        //false
        System.out.println("Sjekker 111 - " + a.sjekkInput("111"));

        //false
        System.out.println("Sjekker 010110 - " + a.sjekkInput("010110"));

        //true
        System.out.println("Sjekker 001000 - " + a.sjekkInput("001000"));

        //true
        System.out.println("Sjekker abbb - " + b.sjekkInput("abbb"));

        //false
        System.out.println("Sjekker aaab - " + b.sjekkInput("aaab"));

        //true
        System.out.println("Sjekker babab - " + b.sjekkInput("babab"));

    }
}
