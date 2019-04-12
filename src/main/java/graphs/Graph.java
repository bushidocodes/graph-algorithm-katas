package graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;

// Given a graph G, check if the graph is biconnected or not. If it is not,
// identify all the articulation points. The algorithm should run in linear
// time.
public class Graph {
    private HashMap<String, HashSet<String>> adjacencyMatrix = new HashMap<String, HashSet<String>>();
    boolean isDirected;

    public Graph(boolean newIsDirected) {
        isDirected = newIsDirected;
    }

    private boolean hasSourceHashSet(String source) {
        return this.adjacencyMatrix.containsKey(source);
    }

    private HashSet<String> getSourceHashSet(String source) {
        return this.adjacencyMatrix.get(source);
    }

    public void addEdge(String source, String destination) {
        if (!hasSourceHashSet(source)) {
            this.adjacencyMatrix.put(source, new HashSet<String>());
        }
        getSourceHashSet(source).add(destination);
        if (this.isDirected == false && this.hasEdge(destination, source) == false) {
            this.addEdge(destination, source);
        }
    }

    public void removeEdge(String source, String destination) {
        if (this.hasSourceHashSet(source)) {
            getSourceHashSet(source).remove(destination);
        }
        if (this.isDirected == false && this.hasEdge(destination, source) == true) {
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
        HashSet<String> vertices = new HashSet<String>();
        this.adjacencyMatrix.forEach((source, destinations) -> {
            if (destinations.size() > 0)
                vertices.add(source);
            destinations.forEach(destination -> vertices.add(destination));
        });
        return vertices;
    }

    public HashSet<String> getNeighbors(String source) {
        if (this.hasSourceHashSet(source))
            return this.adjacencyMatrix.get(source);
        else
            return new HashSet<String>();
    }

}
