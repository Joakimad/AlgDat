package Oving7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {

    private int size;
    private LinkedList[] adj;
    private Vector[] edges;

    Graph(int size) {
        this.size = size;
        adj = new LinkedList[size];
        edges = new Vector[size];
        Arrays.fill(edges, new Vector<>());
        for (int i = 0; i < size; ++i)
            adj[i] = new LinkedList();
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        edges[v].add(w);
        edges[w].add(v);
    }

    void topologicalSortUtil(int v, boolean[] visited, Stack stack) {
        visited[v] = true;
        Integer i;

        Iterator<Integer> iterator = adj[v].iterator();
        while (iterator.hasNext()) {
            i = iterator.next();
            if (!visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }
        stack.push(new Integer(v));
    }

    void topologicalSort() {
        Stack stack = new Stack();

        boolean[] visited = new boolean[size];
        for (int i = 0; i < size; i++) {
            visited[i] = false;
        }
        for (int i = 0; i < size; i++) {
            if (!visited[i]) {
                //Hjelpemetode
                topologicalSortUtil(i, visited, stack);
            }
        }

        while (!stack.empty()) {
            System.out.print(stack.pop() + " ");
        }
    }

    String BFS(int n) {
        StringBuilder res = new StringBuilder();
        res.append("fra " + n + "\nNode\tDist\n");

        for (int i = 0; i < size; i++) {
            res.append("\t").append(i).append("\t");
            Vector<Boolean> visited = new Vector<Boolean>(size);

            for (int j = 0; j < size; j++) {
                visited.addElement(false);
            }

            Vector<Integer> distance = new Vector<Integer>(size);

            for (int j = 0; j < size; j++) {
                distance.addElement(0);
            }

            Queue<Integer> queue = new LinkedList<>();
            distance.setElementAt(0, n);

            queue.add(n);
            visited.setElementAt(true, n);
            while (!queue.isEmpty()) {
                int x = queue.peek();
                queue.poll();

                for (int j = 0; j < edges[x].size(); j++) {
                    if (visited.elementAt((Integer) edges[x].get(j)))
                        continue;

                    distance.setElementAt(distance.get(x) + 1, (Integer) edges[x].get(j));
                    queue.add((Integer) edges[x].get(j));
                    visited.setElementAt(true, (Integer) edges[x].get(j));
                }
            }
            res.append("\t").append(distance.get(i)).append("\n");
        }
        return String.valueOf(res);
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

    public static void main(String args[]) {

        String file = "";
        try {
            file = readFile("src/Oving7/L7g5.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] fileArr = file.split("\n");

        Graph graph = null;
        for (int i = 0; i < fileArr.length; i++) {
            String[] line = fileArr[i].split(" ");
            int start = Integer.parseInt(line[0]);
            int end = Integer.parseInt(line[1]);
            if (i == 0) {
                graph = new Graph(start);
            } else {
                graph.addEdge(start, end);
            }
        }

        System.out.println("Avstand til hver node " + graph.BFS(5));
        System.out.println("Topografisk");
        graph.topologicalSort();
    }
}

