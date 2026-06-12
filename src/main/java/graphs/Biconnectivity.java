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
    HashSet<String> articulationPoints = new HashSet<>();
    HashSet<String> vertices;
    HashMap<String, Boolean> isDiscovered = new HashMap<>();
    HashMap<String, Integer> orderVisited = new HashMap<>();
    HashMap<String, Integer> orderReachableByFirstBackedge = new HashMap<>();
    int step = 0;
    HashMap<String, HashSet<String>> spanningTree = new HashMap<>();
    HashSet<String> spanningTreeRoots = new HashSet<>();
    HashMap<String, HashSet<String>> backedges = new HashMap<>();

    public Biconnectivity(Graph newGraph) {
        this.g = newGraph;
        this.vertices = g.getVertices();
        this.vertices.forEach(vertex -> {
            this.isDiscovered.put(vertex, false);
            this.orderVisited.put(vertex, -1);
        });
    }

    public HashSet<String> findArticulationPoints() {
        // Build a DFS spanning tree for every connected component. A disconnected
        // graph's articulation points are the union of each component's, so we
        // start a fresh DFS from every still-undiscovered vertex; starting from a
        // single root would ignore all other components.
        for (String vertex : this.vertices) {
            if (!this.isDiscovered.get(vertex)) {
                this.spanningTreeRoots.add(vertex);
                this.isDiscovered.put(vertex, true);
                this.buildSpanningTreeAndBackEdges(vertex);
            }
        }
        this.vertices.forEach(v -> {
            this.orderReachableByFirstBackedge.put(v, this.findOrderReachableByFirstBackedge(v));
        });
        this.spanningTreeRoots.forEach(this::collectArticulationPoints);

        return this.articulationPoints;
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
            if (this.isDiscovered.get(neighbor)) {
                if (!this.backedges.containsKey(currentVertex)) {
                    this.backedges.put(currentVertex, new HashSet<>());
                }
                this.backedges.get(currentVertex).add(neighbor);
            } else {
                if (!this.spanningTree.containsKey(currentVertex)) {
                    this.spanningTree.put(currentVertex, new HashSet<>());
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
        HashSet<String> currentLevel = new HashSet<>();
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
                currentLevel = nextBfsLevel(currentLevel);
            }
        }
        return result;
    }

    // Expands a BFS frontier to the next level: the union of every current
    // vertex's spanning-tree children. Built with flatMap/collect rather than a
    // mutable-identity Stream.reduce so it stays correct under any stream
    // evaluation (including parallel). Package-private for direct unit testing.
    HashSet<String> nextBfsLevel(HashSet<String> currentLevel) {
        return currentLevel.stream()
                .filter(v -> this.spanningTree.containsKey(v))
                .flatMap(v -> this.spanningTree.get(v).stream())
                .collect(Collectors.toCollection(HashSet::new));
    }

    // Assumes that the source vertex has a backedge
    int getEarliestBackedgeFromVertex(String sourceVertex) {
        return Collections.min(
                this.backedges.get(sourceVertex).stream()
                        .map(dest -> this.orderVisited.get(dest))
                        .collect(Collectors.toList()));
    }

    public void collectArticulationPoints(String parent) {
        if (this.spanningTree.containsKey(parent)) {
            // Root is an articulation point iff it has >= 2 spanning-tree children
            if (!notRoot(parent) && this.spanningTree.get(parent).size() >= 2) {
                this.articulationPoints.add(parent);
            }

            this.spanningTree.get(parent).forEach(child -> {
                // Non-root parent is an articulation point if low[child] >= disc[parent]
                // See https://www.youtube.com/watch?v=jFZsDDB0-vo to understand why
                if (notRoot(parent)
                        && this.orderReachableByFirstBackedge.get(child) >= this.orderVisited.get(parent)) {
                    this.articulationPoints.add(parent);
                }

                this.collectArticulationPoints(child);
            });
        }
    }

    private boolean notRoot(String currentVertex) {
        return !this.spanningTreeRoots.contains(currentVertex);
    }

    private boolean notLeaf(String neighbor) {
        return this.spanningTree.containsKey(neighbor);
    }
}
