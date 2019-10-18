package Oving8;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class WeightedGraph {

    private int[][] flow;
    private int[][] capacity;
    private int[] parent;
    private boolean[] visited;
    private int vertices, edges;

    private String findPath() {
        String res = "";



        return res;
    }

    /**
     *     public ArrayList<Integer> finnVeiTilbake(int start, int sluk) {
     *         ArrayList<Integer> nodes = new ArrayList<>();
     *         Node current = node[sluk];
     *         Node stopp = node[start];
     *
     *         while (current != null) {
     *             for (int i = 0; i < node.length; i++) {
     *                 if (current == node[i]) {
     *                     nodes.add(i);
     *                 }
     *             }
     *             current = ((Forgj) current.d).finn_forgj();
     *         }
     *         return nodes;
     *     }
     */

    public WeightedGraph(int vertices, int edges) {
        this.vertices = vertices;
        this.edges = edges;
        this.flow = new int[vertices][vertices];
        this.capacity = new int[vertices][vertices];
        this.parent = new int[vertices];
        this.visited = new boolean[vertices];
    }

    public void addEdge(int from, int to, long capacity) {
        assert capacity >= 0;
        this.capacity[from][to] += capacity;
    }

    public void getMaxFlow(int start, int end) {

        System.out.println("Maksimum flyt fra " + start + " til " + end + " med Edmond-Karp");
        System.out.println("Økning : Flytøkende vei");

        int bottleneck = -1;

        while (true) {
            Queue<Integer> Q = new LinkedList<>();
            Q.add(start);

            for (int i = 0; i < this.vertices; ++i) {
                visited[i] = false;
            }

            visited[start] = true;
            boolean foundPath = false;
            int current;

            while (!Q.isEmpty()) {
                current = Q.peek();
                if (current == end) {
                    foundPath = true;
                    break;
                }
                Q.remove();

                for (int i = 0; i < vertices; ++i) {
                    // Checks if there are any available unvisited nodes with enough capacity
                    if (!visited[i] && capacity[current][i] > flow[current][i]) {
                        visited[i] = true;
                        Q.add(i);
                        parent[i] = current;
                    }
                }
            }

            // Break the loop if no path is found.
            if (!foundPath) {
                break;
            }

            //Finds the path taken
            ArrayList<Integer> path = new ArrayList<>();
            path.add(end);

            bottleneck = capacity[parent[end]][end] - flow[parent[end]][end];
            for (int i = end; i != start; i = parent[i]) {
                bottleneck = Math.min(bottleneck, (capacity[parent[i]][i] - flow[parent[i]][i]));
                path.add(parent[i]);
            }

            for (int i = end; i != start; i = parent[i]) {
                // Sets flow and reverse flow.
                flow[parent[i]][i] += bottleneck;
                flow[i][parent[i]] -= bottleneck;
            }
            System.out.print(bottleneck + "\t\t");
            for (int i = path.size()-1; i >= 0; i--) {
                System.out.print(path.get(i) + " ");
            }

            System.out.println();
        }

        // No more paths, totals up the flow in the system from the start vertice
        int result = 0;
        for (int i = 0; i < vertices; ++i) {
            result += flow[start][i];
        }

        // Prints max flow
        System.out.println("Maksimal flyt ble " + result);
    }
}