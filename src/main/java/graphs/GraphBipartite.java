package graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

// Given an undirected graph, return true if and only if it is bipartite.
// The graph is given in the following form: graph[i] is a list of indexes j for
// which the edge between nodes i and j exists. Each node is an integer between
// 0 and graph.length 1. There are no self edges or parallel edges: graph[i]
// does not contain i, and it doesn’t contain any element twice.
// Input: [[1,3], [0,2], [1,3], [0,2]]
// Output: true
public class GraphBipartite {
    boolean isBipartite = true;
    Graph g;
    HashSet<String> vertices;
    HashMap<String, Boolean> isDiscovered = new HashMap<String, Boolean>();
    HashMap<String, String> vertexColors = new HashMap<String, String>();
    PriorityQueue<String> frontier = new PriorityQueue<String>();

    public GraphBipartite(Graph newGraph) {
        g = newGraph;
        vertices = g.getVertices();
        this.vertices.forEach(vertex -> {
            this.isDiscovered.put(vertex, false);
            this.vertexColors.put(vertex, "UNDEFINED");
        });
    }

    public String complement(String color) {
        return color == "WHITE" ? "BLACK" : "WHITE";
    }

    public boolean twoColor() {
        if (this.vertices.size() > 0) {
            initializeStart();
            while (this.frontier.size() > 0 && this.isBipartite) {
                String currentVertex = this.frontier.poll();
                // Process vertex
                this.g.getNeighbors(currentVertex).forEach(neighbor -> {
                    // process edge
                    processEdge(currentVertex, neighbor);

                    if (!this.isDiscovered.get(neighbor)) {
                        this.isDiscovered.put(neighbor, true);
                        this.frontier.add(neighbor);
                    }
                });
            }
        }
        return this.isBipartite;
    }

    private void initializeStart() {
        String start = this.vertices.toArray(new String[0])[0];
        this.isDiscovered.put(start, true);
        this.frontier.add(start);
        this.vertexColors.put(start, "WHITE");
    }

    private void processEdge(String currentVertex, String neighbor) {
        if (this.vertexColors.get(currentVertex) == this.vertexColors.get(neighbor)) {
            this.isBipartite = false;
        } else {
            this.vertexColors.put(neighbor, this.complement(this.vertexColors.get(currentVertex)));
        }
    }
}
