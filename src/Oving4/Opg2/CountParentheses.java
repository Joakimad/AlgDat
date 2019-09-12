package Oving4.Opg2;

import java.io.*;
import java.util.Stack;

public class CountParentheses {

    public static void main(String[] args) {

        Stack<Integer> stack = new Stack<Integer>();

        String codeBlock = "";
        try {
            codeBlock = readFile("codeExample.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < codeBlock.length(); i++) {

            int openCurlyBrackets = 123;
            int closedCurlyBrackets = 125;

            int openBrackets = 133;
            int closedBrackets = 135;

            int openParenthesis = 40;
            int closedParenthesis = 41;

            if (codeBlock.charAt(i) == openBrackets ||
                    codeBlock.charAt(i) == openCurlyBrackets  ||
                    codeBlock.charAt(i) == openParenthesis) {
                stack.push((int) codeBlock.charAt(i));
            }

            if (codeBlock.charAt(i) == closedBrackets ||
                    codeBlock.charAt(i) == closedCurlyBrackets  ||
                    codeBlock.charAt(i) == closedParenthesis) {
                stack.pop();
            }
        }
        if (stack.empty()) {
            System.out.println("Programmet kompilerer uten feil");
        } else {
            System.out.println("Programmet har kompileringsfeil");
        }
    }

    private static String readFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/Oving4/Opg2/codeExample.txt"));

        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }
        return content.toString();
    }
}
