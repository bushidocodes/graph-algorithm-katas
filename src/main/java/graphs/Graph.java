package graphs;

import java.util.HashMap;
import java.util.ArrayList;

// Given a graph G, check if the graph is biconnected or not. If it is not,
// identify all the articulation points. The algorithm should run in linear
// time.
public class Graph {
    private HashMap<String, ArrayList<String>> adjacencyMatrix = new HashMap<String, ArrayList<String>>();
    boolean isDirected;

    public Graph(boolean newIsDirected) {
        isDirected = newIsDirected;
    }

    private boolean hasSourceArrayList(String source) {
        return this.adjacencyMatrix.containsKey(source);
    }

    private ArrayList<String> getSourceArrayList(String source) {
        return this.adjacencyMatrix.get(source);
    }

    public void addEdge(String source, String destination) {
        if (!hasSourceArrayList(source)) {
            this.adjacencyMatrix.put(source, new ArrayList<String>());
        }
        getSourceArrayList(source).add(destination);
        if (this.isDirected == false && this.hasEdge(destination, source) == false) {
            this.addEdge(destination, source);
        }
    }

    public void removeEdge(String source, String destination) {
        if (this.hasSourceArrayList(source)) {
            getSourceArrayList(source).remove(destination);
        }
        if (this.isDirected == false && this.hasEdge(destination, source) == true) {
            this.removeEdge(destination, source);
        }
    }

    public boolean hasEdge(String source, String destination) {
        if (!hasSourceArrayList(source)) {
            return false;
        }
        return getSourceArrayList(source).contains(destination);
    }
}
