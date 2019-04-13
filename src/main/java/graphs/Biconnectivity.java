package graphs;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

// Given a graph G, check if the graph is biconnected or not. If it is not,
// identify all the articulation points. The algorithm should run in linear
// time.                         
public class Biconnectivity {
    Graph g;
    HashSet<String> articulationPoints = new HashSet<String>();
    HashSet<String> vertices;
    HashMap<String, Boolean> isDiscovered = new HashMap<String, Boolean>();
    HashMap<String, String> parents = new HashMap<String, String>();
    HashMap<String, Integer> orderVisited = new HashMap<String, Integer>();
    HashMap<String, Integer> orderReachableByFirstBackedge = new HashMap<String, Integer>();
    int step = 0;
    HashMap<String, HashSet<String>> spanningTree = new HashMap<String, HashSet<String>>();
    String spanningTreeRoot;
    HashMap<String, HashSet<String>> backedges = new HashMap<String, HashSet<String>>();

    public Biconnectivity(Graph newGraph) {
        this.g = newGraph;
        this.vertices = g.getVertices();
        this.vertices.forEach(vertex -> {
            this.isDiscovered.put(vertex, false);
            this.orderVisited.put(vertex, -1);
        });
    }

    public HashSet<String> findArticulationPoints() {
        if (this.vertices.size() > 0) {
            this.initializeStart();
            this.buildSpanningTreeAndBackEdges(this.spanningTreeRoot);
            this.vertices.forEach(v -> {
                this.orderReachableByFirstBackedge.put(v, this.findOrderReachableByFirstBackedge(v));
            });
            this.collectAriculationPoints(this.spanningTreeRoot);
        }

        return this.articulationPoints;
    }

    private void initializeStart() {
        this.spanningTreeRoot = this.vertices.toArray(new String[0])[0];
        this.isDiscovered.put(this.spanningTreeRoot, true);
    }

    public void buildSpanningTreeAndBackEdges(String currentVertex) {
        this.buildSpanningTreeAndBackEdgesProcessVertex(currentVertex);
        this.g.getNeighbors(currentVertex).forEach(neighbor -> {
            this.buildSpanningTreeAndBackEdgesProcessEdge(currentVertex, neighbor);
            if (!this.isDiscovered.get(neighbor)) {
                this.isDiscovered.put(neighbor, true);
                this.buildSpanningTreeAndBackEdges(neighbor);
            }
        });
    }

    private void buildSpanningTreeAndBackEdgesProcessVertex(String currentVertex) {
        this.orderVisited.put(currentVertex, this.step);
        this.step++;
    }

    private void buildSpanningTreeAndBackEdgesProcessEdge(String currentVertex, String neighbor) {
        if (notOppositeEdgeInUndirectedGraph(currentVertex, neighbor)) {
            // If the edge has already been visited, but it isn't the opposite edge in an
            // undirected graph, add it as a backedge
            if (this.isDiscovered.get(neighbor)) {
                if (!this.backedges.containsKey(currentVertex)) {
                    this.backedges.put(currentVertex, new HashSet<String>());
                }
                this.backedges.get(currentVertex).add(neighbor);
            } else {
                if (!this.spanningTree.containsKey(currentVertex)) {
                    this.spanningTree.put(currentVertex, new HashSet<String>());
                }
                this.spanningTree.get(currentVertex).add(neighbor);
            }
        }
    }

    private boolean notOppositeEdgeInUndirectedGraph(String currentVertex, String neighbor) {
        return (!this.spanningTree.containsKey(neighbor) || !this.spanningTree.get(neighbor).contains(currentVertex))
                && (!this.backedges.containsKey(neighbor) || !this.backedges.get(neighbor).contains(currentVertex));
    }

    // Uses BFS to traverse the spanning tree, resolving to the earliest order
    // in the first level with a backedge or Int.MAX_VALUE if no backedges are
    // found.
    private int findOrderReachableByFirstBackedge(String sourceVertex) {
        HashSet<String> currentLevel = new HashSet<String>();
        int result = Integer.MAX_VALUE;
        currentLevel.add(sourceVertex);
        while (currentLevel.size() > 0 && result == Integer.MAX_VALUE) {
            List<Integer> earliestBackEdgesInCurrent = currentLevel.stream()

                    .filter(v -> this.backedges.containsKey(v))

                    .map(v -> this.getEarliestBackedgeFromVertex(v))

                    .collect(Collectors.toList());

            if (earliestBackEdgesInCurrent.size() > 0) {
                result = Collections.min(earliestBackEdgesInCurrent);
            } else {
                currentLevel = currentLevel.stream()

                        .filter(v -> this.spanningTree.containsKey(v))

                        .map(v -> this.spanningTree.get(v))

                        .reduce(new HashSet<String>(), (setA, setB) -> {
                            setA.addAll(setB);
                            return setA;
                        });
            }
        }
        return result;
    }

    // Assumes that the source vertex has a backedge
    int getEarliestBackedgeFromVertex(String sourceVertex) {
        return Collections.min(

                this.backedges.get(sourceVertex).stream()

                        .map(dest -> this.orderVisited.get(dest))

                        .collect(Collectors.toList())

        );
    }

    public void collectAriculationPoints(String parent) {
        if (this.spanningTree.containsKey(parent)) {
            this.spanningTree.get(parent).forEach(child -> {
                // If the child is not a leaf and L[v] >= d[u], parent is an articulation point
                // See https://www.youtube.com/watch?v=jFZsDDB0-vo to understand why
                if (notRoot(parent) && notLeaf(child)
                        && this.orderReachableByFirstBackedge.get(child) >= this.orderVisited.get(parent)) {
                    this.articulationPoints.add(parent);
                }

                this.collectAriculationPoints(child);
            });
        }
    }

    private boolean notRoot(String currentVertex) {
        return currentVertex.equals(this.spanningTreeRoot) == false;
    }

    private boolean notLeaf(String neighbor) {
        return this.spanningTree.containsKey(neighbor);
    }

}
