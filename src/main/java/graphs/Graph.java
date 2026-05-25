package graphs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

// Adjacency-list graph supporting both directed and undirected edges.
public class Graph {
    private HashMap<String, HashSet<String>> adjacencyList = new HashMap<>();
    boolean isDirected;

    public Graph(boolean newIsDirected) {
        isDirected = newIsDirected;
    }

    private boolean hasSourceHashSet(String source) {
        return this.adjacencyList.containsKey(source);
    }

    private HashSet<String> getSourceHashSet(String source) {
        return this.adjacencyList.get(source);
    }

    public void addEdge(String source, String destination) {
        if (!hasSourceHashSet(source)) {
            this.adjacencyList.put(source, new HashSet<>());
        }
        getSourceHashSet(source).add(destination);
        if (!this.isDirected && !this.hasEdge(destination, source)) {
            this.addEdge(destination, source);
        }
    }

    public void removeEdge(String source, String destination) {
        if (this.hasSourceHashSet(source)) {
            getSourceHashSet(source).remove(destination);
        }
        if (!this.isDirected && this.hasEdge(destination, source)) {
            this.removeEdge(destination, source);
        }
    }

    public boolean hasEdge(String source, String destination) {
        if (!hasSourceHashSet(source)) {
            return false;
        }
        return getSourceHashSet(source).contains(destination);
    }

    public void addEdgeList(String[][] edgelist) {
        Arrays.asList(edgelist).forEach((tuple) -> this.addEdge(tuple[0], tuple[1]));
    }

    public HashSet<String> getVertices() {
        HashSet<String> vertices = new HashSet<>();
        this.adjacencyList.forEach((source, destinations) -> {
            if (destinations.size() > 0)
                vertices.add(source);
            destinations.forEach(destination -> vertices.add(destination));
        });
        return vertices;
    }

    public HashSet<String> getNeighbors(String source) {
        if (this.hasSourceHashSet(source))
            return this.adjacencyList.get(source);
        else
            return new HashSet<>();
    }
}
