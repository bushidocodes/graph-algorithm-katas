package graphs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;

// Given an undirected graph, return true if and only if it is bipartite.
// The graph is given in the following form: graph[i] is a list of indexes j for
// which the edge between nodes i and j exists. Each node is an integer between
// 0 and graph.length 1. There are no self edges or parallel edges: graph[i]
// does not contain i, and it doesn't contain any element twice.
// Input: [[1,3], [0,2], [1,3], [0,2]]
// Output: true
public class GraphBipartite {
    boolean isBipartite = true;
    Graph g;
    HashSet<String> vertices;
    HashMap<String, Boolean> isDiscovered = new HashMap<>();
    HashMap<String, String> vertexColors = new HashMap<>();
    Deque<String> frontier = new ArrayDeque<>();

    public GraphBipartite(Graph newGraph) {
        g = newGraph;
        vertices = g.getVertices();
        this.vertices.forEach(vertex -> {
            this.isDiscovered.put(vertex, false);
            this.vertexColors.put(vertex, "UNDEFINED");
        });
    }

    public String complement(String color) {
        return "WHITE".equals(color) ? "BLACK" : "WHITE";
    }

    public boolean twoColor() {
        // A graph is bipartite only if EVERY connected component is. BFS from a
        // single start vertex never reaches disconnected components, so an odd
        // cycle living in a separate component would be missed. Restart BFS from
        // each still-undiscovered vertex to cover all components.
        for (String vertex : this.vertices) {
            if (this.isBipartite && !this.isDiscovered.get(vertex)) {
                initializeComponent(vertex);
                traverseComponent();
            }
        }
        return this.isBipartite;
    }

    private void initializeComponent(String start) {
        this.isDiscovered.put(start, true);
        this.frontier.add(start);
        this.vertexColors.put(start, "WHITE");
    }

    private void traverseComponent() {
        while (this.frontier.size() > 0 && this.isBipartite) {
            String currentVertex = this.frontier.poll();
            this.g.getNeighbors(currentVertex).forEach(neighbor -> {
                processEdge(currentVertex, neighbor);

                if (!this.isDiscovered.get(neighbor)) {
                    this.isDiscovered.put(neighbor, true);
                    this.frontier.add(neighbor);
                }
            });
        }
    }

    private void processEdge(String currentVertex, String neighbor) {
        if (this.vertexColors.get(currentVertex).equals(this.vertexColors.get(neighbor))) {
            this.isBipartite = false;
        } else {
            this.vertexColors.put(neighbor, this.complement(this.vertexColors.get(currentVertex)));
        }
    }
}
