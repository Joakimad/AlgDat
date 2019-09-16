package Oving4.Opg2;

import java.io.*;
import java.util.Stack;

public class CountParentheses {

    public static void main(String[] args) {

        final int openCurlyBrackets = 123;
        final int closedCurlyBrackets = 125;

        final int openBrackets = 133;
        final int closedBrackets = 135;

        final int openParenthesis = 40;
        final int closedParenthesis = 41;

        Stack<Integer> stack = new Stack<Integer>();

        String codeBlock = "";
        try {
            codeBlock = readFile("src/Oving4/Opg2/codeExample.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < codeBlock.length(); i++) {

            int current = (int) codeBlock.charAt(i);

            switch (current) {
                case openBrackets:
                case openCurlyBrackets:
                case openParenthesis:
                    stack.push(current);
                    break;
                case closedBrackets:
                    if (stack.peek() == openBrackets) {
                        if (!(stack.empty())) {
                            stack.pop();
                        }
                    }
                    break;
                case closedCurlyBrackets:
                    if (stack.peek() == openCurlyBrackets) {
                        if (!(stack.empty())) {
                            stack.pop();
                        }
                    }
                    break;
                case closedParenthesis:
                    if (stack.peek() == openParenthesis) {
                        if (!(stack.empty())) {
                            stack.pop();
                        }
                    }
                    break;
                default:
            }
        }
        if (stack.empty()) {
            System.out.println("Programmet kompilerer uten feil");
        } else {
            System.out.println("Programmet har kompileringsfeil");
        }
    }

    private static String readFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }
        return content.toString();
    }
}

