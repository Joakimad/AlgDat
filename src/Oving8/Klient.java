package Oving8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Klient {

    public static void main(String[] args) throws IOException {

        // READING FROM FILE AND INITIALIZING CLASS
        String filename = "src/Oving8/flytgraf1";

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String init = reader.readLine();
        String[] initVal = init.split(" ");

        int vertices = Integer.parseInt(initVal[0]);

        WeightedGraph ek = new WeightedGraph(vertices);

        String line;
        while ((line = reader.readLine()) != null) {
            String[] lineVal = line.split(" ");
            int from = Integer.parseInt(lineVal[0]);
            int to = Integer.parseInt(lineVal[1]);
            int weight = Integer.parseInt(lineVal[2]);
            ek.addEdge(from, to, weight);
        }

        // WHERE THE MAGIC HAPPENS
        ek.getMaxFlow(0, vertices-1);
    }
}
