package Øving12;

import java.io.*;

public class ZipZap {

    private Reader inFile;
    private PrintWriter outFile;
    private int bufferSize = 1024;
    private StringBuffer searchBuffer = new StringBuffer(bufferSize);

    private void trimSearchBuffer() {
        if (searchBuffer.length() > bufferSize) {
            searchBuffer = searchBuffer.delete(0, searchBuffer.length() - bufferSize);
        }
    }

    public void compress(String infile) throws IOException {

        inFile = new BufferedReader(new FileReader("src/Øving12/testfiles/" + infile));
        outFile = new PrintWriter(new BufferedWriter(new FileWriter("src/Øving12/compressed/zipzap-" + infile)));

        int currentChar_int;
        int matchId = 0;
        String match = "";

        // Reads 1 char at the time until end of file.
        while ((currentChar_int = inFile.read()) != -1) {

            char currentChar = (char) currentChar_int;
            int searchResult = searchBuffer.indexOf(match + currentChar);

            // If not match is found in buffer add to buffer.
            if (searchResult != -1) {
                match += currentChar;
                matchId = searchResult;

            } else {
                // Match found in buffer.
                String encoded = "~" + matchId + "~" + match.length() + "~" + currentChar;
                String originalText = match + currentChar;

                //Check if encoded is shorter than original
                if (encoded.length() <= originalText.length()) {
                    outFile.print(encoded);
                    searchBuffer.append(originalText);
                    match = "";
                    matchId = 0;
                } else {
                    match = originalText;
                    matchId = -1;
                    while (match.length() > 1 && matchId == -1) {
                        outFile.print(match.charAt(0));
                        searchBuffer.append(match.charAt(0));
                        match = match.substring(1);
                        matchId = searchBuffer.indexOf(match);
                    }
                }
                trimSearchBuffer();
            }
        }
        if (matchId != -1) {
            String encoded = "~" + matchId + "~" + match.length();
            if (encoded.length() <= match.length()) {
                outFile.print("~" + matchId + "~" + match.length());
            } else {
                outFile.print(match);
            }
        }
        // close files
        inFile.close();
        outFile.flush();
        outFile.close();
    }

    public void uncompress(String infile) throws IOException {

        inFile = new BufferedReader(new FileReader("src/Øving12/compressed/" + infile));
        outFile = new PrintWriter(new BufferedWriter(new FileWriter("src/Øving12/uncompressed/" + infile)));

        StreamTokenizer st = new StreamTokenizer(inFile);

        st.ordinaryChar((int) ' ');
        st.ordinaryChar((int) '.');
        st.ordinaryChar((int) '-');
        st.ordinaryChar((int) '\n');
        st.wordChars((int) '\n', (int) '\n');
        st.wordChars((int) ' ', (int) '}');

        int offset;
        int length;

        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            switch (st.ttype) {
                case StreamTokenizer.TT_WORD:
                    searchBuffer.append(st.sval);
                    outFile.print(st.sval);
                    // Adjust search buffer size if necessary
                    trimSearchBuffer();
                    break;
                case StreamTokenizer.TT_NUMBER:
                    offset = (int) st.nval; // set the offset
                    st.nextToken(); // get the separator (hopefully)
                    if (st.ttype == StreamTokenizer.TT_WORD) {
                        // we got a word instead of the separator,
                        // therefore the first number read was actually part of a word
                        searchBuffer.append(offset + st.sval);
                        outFile.print(offset + st.sval);
                        break; // break out of the switch
                    }
                    // if we got this far then we must be reading a
                    // substitution pointer
                    st.nextToken(); // get the length
                    length = (int) st.nval;
                    // output substring from search buffer

                    if (offset + length < searchBuffer.length()) {
                        String output = searchBuffer.substring(offset, offset + length);
                        outFile.print(output);
                        searchBuffer.append(output);
                    }
                    // Adjust search buffer size if necessary
                    trimSearchBuffer();
                    break;
                default:
            }
        }

        // close files
        inFile.close();
        outFile.flush();
        outFile.close();
    }
}

