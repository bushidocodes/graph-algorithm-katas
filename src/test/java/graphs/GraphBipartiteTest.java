package graphs;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GraphBipartiteTest {
    @Test
    public void emptyGraphIsBipartite() {
        Graph myUndirectedGraph = new Graph(false);
        GraphBipartite bipartiteChecker = new GraphBipartite(myUndirectedGraph);
        assertTrue(bipartiteChecker.twoColor());
    }

    @Test
    public void graphOfOneEdgeIsBipartite() {
        Graph myUndirectedGraph = new Graph(false);
        myUndirectedGraph.addEdge("A", "B");
        GraphBipartite bipartiteChecker = new GraphBipartite(myUndirectedGraph);
        assertTrue(bipartiteChecker.twoColor());
    }

    @Test
    public void trianlgeIsNotBipartite() {
        Graph myUndirectedGraph = new Graph(false);
        myUndirectedGraph.addEdge("A", "B");
        myUndirectedGraph.addEdge("A", "C");
        myUndirectedGraph.addEdge("B", "C");
        GraphBipartite bipartiteChecker = new GraphBipartite(myUndirectedGraph);
        assertFalse(bipartiteChecker.twoColor());
    }

    @Test
    public void testcaseFromDescription() {
        Graph myUndirectedGraph = new Graph(false);
        String edgelist[][] = { { "1", "3" }, { "0", "2" }, { "1", "3" }, { "0", "2" } };
        myUndirectedGraph.addEdgeList(edgelist);
        GraphBipartite bipartiteChecker = new GraphBipartite(myUndirectedGraph);
        assertTrue(bipartiteChecker.twoColor());
    }
}
