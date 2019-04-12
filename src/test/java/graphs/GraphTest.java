package graphs;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class GraphTest {
    @Test
    public void canInstantiateUndirectedGraph() {
        Graph myGraph = new Graph(false);
        assertTrue(myGraph instanceof Graph);
    }

    @Test
    public void canInstantiateDirectedGraph() {
        Graph myGraph = new Graph(true);
        assertTrue(myGraph instanceof Graph);
    }

    @Test
    public void canAddEdge() {
        Graph myGraph = new Graph(true);
        myGraph.addEdge("A", "B");
        assertTrue(myGraph.hasEdge("A", "B") == true);
        myGraph.addEdge("A", "C");
        assertTrue(myGraph.hasEdge("A", "C") == true);
    }

    @Test
    public void automaticallyAddsOpposingEdgeInUndirectedGraphs() {
        Graph myGraph = new Graph(false);
        myGraph.addEdge("A", "B");
        assertTrue(myGraph.hasEdge("A", "B") == true);
        assertTrue(myGraph.hasEdge("B", "A") == true);
        myGraph.addEdge("A", "C");
        assertTrue(myGraph.hasEdge("A", "C") == true);
        assertTrue(myGraph.hasEdge("C", "A") == true);
    }

    @Test
    public void canRemoveEdge() {
        Graph myGraph = new Graph(true);
        myGraph.removeEdge("A", "B");
        assertTrue(myGraph.hasEdge("A", "B") == false);
        myGraph.removeEdge("A", "C");
        assertTrue(myGraph.hasEdge("A", "C") == false);
    }

    @Test
    public void automaticallyRemovesOpposingEdgeInUndirectedGraphs() {
        Graph myGraph = new Graph(false);
        myGraph.removeEdge("A", "B");
        assertTrue(myGraph.hasEdge("A", "B") == false);
        assertTrue(myGraph.hasEdge("B", "A") == false);
        myGraph.removeEdge("A", "C");
        assertTrue(myGraph.hasEdge("A", "C") == false);
        assertTrue(myGraph.hasEdge("C", "A") == false);
    }
}
