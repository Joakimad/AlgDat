package Oving8;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class WeightedGraph {

    // Array for keeping track of flow in system
    private int[][] flow;
    // Array for keeping track of capacity per edge
    private int[][] capacity;
    // Array that keeps track of path
    private int[] parent;
    // Boolean array that keeps track of previous visited vertices
    private boolean[] visited;

    private int vertices;

    public WeightedGraph(int vertices) {
        this.vertices = vertices;
        this.flow = new int[vertices][vertices];
        this.capacity = new int[vertices][vertices];
        this.parent = new int[vertices];
        this.visited = new boolean[vertices];
    }

    // Adds edge to graph by adding it into the capacity array
    public void addEdge(int from, int to, long capacity) {
        this.capacity[from][to] += capacity;
    }

    // Finds max flow from startpoint to endpoint
    public void getMaxFlow(int start, int end) {

        System.out.println("Maksimum flyt fra " + start + " til " + end + " med Edmond-Karp");
        System.out.println("Økning : Flytøkende vei");


        // Loops as long as it finds viable paths
        while (true) {

            // Uses queue for traversal, adds startpoint.
            Queue<Integer> queue = new LinkedList<>();
            queue.add(start);

            // Stores the path taken for printing
            ArrayList<Integer> path = new ArrayList<>();
            path.add(end);

            // Sets all vertices to false
            for (int i = 0; i < this.vertices; ++i) {
                visited[i] = false;
            }

            // Sets startpoint to be visited
            visited[start] = true;

            boolean foundPath = false;
            int current;

            // Uses a queue to keep track of traversal
            while (!queue.isEmpty()) {
                current = queue.peek();

                // Breaks when current has reached the endpoint
                if (current == end) {
                    foundPath = true;
                    break;
                }
                // Removes queue element
                queue.remove();

                // Checks if there are any available unvisited vertices with enough capacity.
                // Adds the result into the queue.
                for (int i = 0; i < vertices; ++i) {
                    if (!visited[i] && capacity[current][i] > flow[current][i]) {
                        visited[i] = true;
                        queue.add(i);
                        parent[i] = current;
                    }
                }
            }

            // Break the loop if no path is found and begin printing result
            if (!foundPath) {
                break;
            }

            // bottleneck - edge with lowest capacity in the path
            // Finds bottleneck value for the last point of path
            int bottleneck = capacity[parent[end]][end] - flow[parent[end]][end];

            // Iterates through the path and finds minimum bottleneck. Also stores path.
            for (int i = end; i != start; i = parent[i]) {
                bottleneck = Math.min(bottleneck, (capacity[parent[i]][i] - flow[parent[i]][i]));
                path.add(parent[i]);
            }

            // Updates the flow through the path and reverse flow.
            for (int i = end; i != start; i = parent[i]) {
                flow[parent[i]][i] += bottleneck;
                flow[i][parent[i]] -= bottleneck;
            }

            // Print out bottleneck and path
            System.out.print(bottleneck + "\t\t");
            for (int i = path.size() - 1; i >= 0; i--) {
                System.out.print(path.get(i) + " ");
            }
            System.out.println();
        }

        // No more paths, totals up the flow in the system
        int result = 0;
        for (int i = 0; i < vertices; ++i) {
            result += flow[start][i];
        }

        // Prints max flow
        System.out.println("Maksimal flyt ble " + result);
    }
}