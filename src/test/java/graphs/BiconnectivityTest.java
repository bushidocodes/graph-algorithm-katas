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
