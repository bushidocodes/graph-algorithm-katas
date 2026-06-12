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
    public void disconnectedGraphWithNonBipartiteComponentIsNotBipartite() {
        Graph myUndirectedGraph = new Graph(false);
        // Bipartite component: A-B
        myUndirectedGraph.addEdge("A", "B");
        // Non-bipartite component: X-Y-Z triangle (odd cycle)
        myUndirectedGraph.addEdge("X", "Y");
        myUndirectedGraph.addEdge("Y", "Z");
        myUndirectedGraph.addEdge("Z", "X");
        GraphBipartite bipartiteChecker = new GraphBipartite(myUndirectedGraph);
        // The triangle makes the whole graph non-bipartite. BFS from a single
        // start vertex would miss it whenever the search began in the A-B
        // component; every component must be examined.
        assertFalse(bipartiteChecker.twoColor());
    }

    @Test
    public void disconnectedGraphOfTwoBipartiteComponentsIsBipartite() {
        Graph myUndirectedGraph = new Graph(false);
        myUndirectedGraph.addEdge("A", "B");
        myUndirectedGraph.addEdge("C", "D");
        GraphBipartite bipartiteChecker = new GraphBipartite(myUndirectedGraph);
        assertTrue(bipartiteChecker.twoColor());
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
