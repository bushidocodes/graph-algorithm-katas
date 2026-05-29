package graphs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
