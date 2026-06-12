package graphs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class BiconnectivityTest {
    @Test
    public void shouldReturnArticulationPointsWhenNotBiconnected() {
        Graph myUndirectedGraph = new Graph(false);
        myUndirectedGraph.addEdge("A", "B");
        myUndirectedGraph.addEdge("A", "C");
        myUndirectedGraph.addEdge("B", "D");
        myUndirectedGraph.addEdge("C", "D");
        myUndirectedGraph.addEdge("D", "E");
        myUndirectedGraph.addEdge("D", "F");
        myUndirectedGraph.addEdge("E", "F");
        Biconnectivity biconnectivityChecker = new Biconnectivity(myUndirectedGraph);
        HashSet<String> articulationPoints = biconnectivityChecker.findArticulationPoints();
        assertTrue(articulationPoints.contains("D"));
        assertEquals(1, articulationPoints.size());
    }

    @Test
    public void shouldReportStarCenterAsArticulationPoint() {
        // Star X-{A,B,C}: removing X disconnects the leaves
        Graph star = new Graph(false);
        star.addEdge("X", "A");
        star.addEdge("X", "B");
        star.addEdge("X", "C");
        Biconnectivity checker = new Biconnectivity(star);
        HashSet<String> aps = checker.findArticulationPoints();
        assertTrue(aps.contains("X"));
        assertEquals(1, aps.size());
    }

    @Test
    public void shouldReportInternalPathNodesAsArticulationPoints() {
        // Path A-B-C-D-E: B, C, D are all articulation points
        Graph path = new Graph(false);
        path.addEdge("A", "B");
        path.addEdge("B", "C");
        path.addEdge("C", "D");
        path.addEdge("D", "E");
        Biconnectivity checker = new Biconnectivity(path);
        HashSet<String> aps = checker.findArticulationPoints();
        assertTrue(aps.contains("B"));
        assertTrue(aps.contains("C"));
        assertTrue(aps.contains("D"));
        assertEquals(3, aps.size());
    }

    @Test
    public void shouldFindArticulationPointsInEveryComponentOfDisconnectedGraph() {
        // Two disjoint paths; each has one internal articulation point.
        Graph g = new Graph(false);
        g.addEdge("A", "B");
        g.addEdge("B", "C"); // path A-B-C, B is an articulation point
        g.addEdge("D", "E");
        g.addEdge("E", "F"); // path D-E-F, E is an articulation point
        Biconnectivity checker = new Biconnectivity(g);
        HashSet<String> aps = checker.findArticulationPoints();
        // A single-component search would return only B or only E depending on
        // which component it started in; both must be found.
        assertTrue(aps.contains("B"));
        assertTrue(aps.contains("E"));
        assertEquals(2, aps.size());
    }

    @Test
    public void shouldIgnoreBiconnectedComponentWhenSearchingDisconnectedGraph() {
        Graph g = new Graph(false);
        // Biconnected triangle X-Y-Z: no articulation points
        g.addEdge("X", "Y");
        g.addEdge("Y", "Z");
        g.addEdge("Z", "X");
        // Path P-Q-R in a separate component: Q is an articulation point
        g.addEdge("P", "Q");
        g.addEdge("Q", "R");
        Biconnectivity checker = new Biconnectivity(g);
        HashSet<String> aps = checker.findArticulationPoints();
        assertTrue(aps.contains("Q"));
        assertEquals(1, aps.size());
    }

    @Test
    public void shouldHandleIsolatedVerticesWithoutCrashing() {
        Graph g = new Graph(false);
        g.addEdge("A", "B");
        g.addEdge("B", "C"); // path A-B-C, B is an articulation point
        // Create isolated vertices (degree 0) by adding then removing an edge.
        g.addEdge("ISO", "TEMP");
        g.removeEdge("ISO", "TEMP");
        Biconnectivity checker = new Biconnectivity(g);
        HashSet<String> aps = checker.findArticulationPoints();
        // Isolated vertices are their own single-vertex components and are never
        // articulation points; the path's articulation point is still found.
        assertTrue(aps.contains("B"));
        assertFalse(aps.contains("ISO"));
        assertFalse(aps.contains("TEMP"));
        assertEquals(1, aps.size());
    }

    @Test
    public void shouldEmptySetWhenBiconnected() {
        Graph myUndirectedGraph = new Graph(false);
        myUndirectedGraph.addEdge("A", "B");
        myUndirectedGraph.addEdge("A", "C");
        myUndirectedGraph.addEdge("B", "E");
        myUndirectedGraph.addEdge("C", "F");
        myUndirectedGraph.addEdge("E", "F");
        Biconnectivity biconnectivityChecker = new Biconnectivity(myUndirectedGraph);
        assertTrue(biconnectivityChecker.findArticulationPoints().isEmpty());
    }

    @Test
    public void emptyGraphHasNoArticulationPoints() {
        Biconnectivity checker = new Biconnectivity(new Graph(false));
        assertTrue(checker.findArticulationPoints().isEmpty());
    }

    @Test
    public void singleEdgeHasNoArticulationPoints() {
        Graph g = new Graph(false);
        g.addEdge("A", "B");
        // Removing either endpoint of a lone edge leaves a single vertex, which
        // is still connected, so neither endpoint is an articulation point.
        Biconnectivity checker = new Biconnectivity(g);
        assertTrue(checker.findArticulationPoints().isEmpty());
    }

    @Test
    public void nextBfsLevelUnionsSpanningTreeChildrenFromEveryVertexInLevel() {
        Biconnectivity checker = new Biconnectivity(new Graph(false));
        // Hand-wire a spanning tree:  R -> {A, B};  A -> {C};  B -> {D}
        checker.spanningTree.put("R", new HashSet<>(Arrays.asList("A", "B")));
        checker.spanningTree.put("A", new HashSet<>(Arrays.asList("C")));
        checker.spanningTree.put("B", new HashSet<>(Arrays.asList("D")));

        // A single-vertex frontier expands to that vertex's children.
        assertEquals(new HashSet<>(Arrays.asList("A", "B")),
                checker.nextBfsLevel(new HashSet<>(Arrays.asList("R"))));

        // A frontier with MULTIPLE vertices must union ALL of their children.
        // This multi-source union is exactly what the previous mutable-identity
        // Stream.reduce could corrupt under parallel evaluation.
        assertEquals(new HashSet<>(Arrays.asList("C", "D")),
                checker.nextBfsLevel(new HashSet<>(Arrays.asList("A", "B"))));

        // Leaves (and vertices absent from the spanning tree) expand to nothing.
        assertTrue(checker.nextBfsLevel(new HashSet<>(Arrays.asList("C", "D"))).isEmpty());
    }
}
