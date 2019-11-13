package Øving11;

public class Automat {

    private char[] inputAlphabet;
    private int acceptState;
    private int[][] nextState;

    public Automat(char[] inputAlphabet, int acceptState, int[][] nextState) {
        this.inputAlphabet = inputAlphabet;
        this.acceptState = acceptState;
        this.nextState = nextState;
    }

    public boolean sjekkInput(String inputString) {

        boolean result = false;
        char[] input = inputString.toCharArray();
        if (!sjekkInputFormat(input)) {
            System.out.println("Wrong format");
            return false;
        }

        int current = 0;

        for (int i = 0; i < input.length; i++) {

            int next = 0;

            if (input[i] == 48 || input[i] == 97) {
                next = 0;

            } else if (input[i] == 49 || input[i] == 98) {
                next = 1;
            }

            current = nextState[current][next];

        }
        if (current == acceptState) {
            result = true;
        }
        return result;
    }

    public boolean sjekkInputFormat(char[] input) {
        boolean result = true;
        boolean charInAlphabet = false;
        for (char inputChar : input) {
            for (char alphabetChar : inputAlphabet) {
                if (inputChar == alphabetChar) {
                    charInAlphabet = true;
                    break;
                }
            }
            if (!charInAlphabet) {
                result = false;
            }
            charInAlphabet = false;
        }
        return result;
    }

    public void printNextStateArr() {
        System.out.print("The Array is :\n");
        for (int i = 0; i < nextState.length; i++) {
            for (int j = 0; j < nextState[i].length; j++) {
                System.out.print(nextState[i][j] + "  ");
            }
            System.out.println();
        }
    }
}
