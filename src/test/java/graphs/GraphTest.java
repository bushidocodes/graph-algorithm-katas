package graphs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

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
    public void canAddEdgeList() {
        Graph myGraph = new Graph(true);
        String[][] edgeList = { { "A", "B" }, { "A", "C" } };
        myGraph.addEdgeList(edgeList);
        assertTrue(myGraph.hasEdge("A", "B") == true);
        assertTrue(myGraph.hasEdge("A", "C") == true);
        Graph myUndirectedGraph = new Graph(false);
        myUndirectedGraph.addEdgeList(edgeList);
        assertTrue(myUndirectedGraph.hasEdge("A", "B") == true);
        assertTrue(myUndirectedGraph.hasEdge("B", "A") == true);
        assertTrue(myUndirectedGraph.hasEdge("A", "C") == true);
        assertTrue(myUndirectedGraph.hasEdge("C", "A") == true);
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

    @Test
    public void canGetSetOfVertices() {
        Graph myGraph = new Graph(false);
        myGraph.addEdge("A", "B");
        System.out.println(myGraph.getVertices());
        assertTrue(myGraph.getVertices().contains("A"));
        assertTrue(myGraph.getVertices().contains("B"));
        assertTrue(myGraph.getVertices().size() == 2);
        myGraph.addEdge("A", "C");
        assertTrue(myGraph.getVertices().contains("C"));
        assertTrue(myGraph.getVertices().size() == 3);
        myGraph.removeEdge("A", "C");
        assertTrue(myGraph.getVertices().contains("A"));
        assertTrue(myGraph.getVertices().contains("B"));
        assertTrue(myGraph.getVertices().size() == 2);
    }

    @Test
    public void canGetNeighbors() {
        Graph myUndirectedGraph = new Graph(false);
        myUndirectedGraph.addEdge("A", "B");
        myUndirectedGraph.addEdge("A", "C");
        myUndirectedGraph.addEdge("A", "D");
        HashSet<String> neighbors = myUndirectedGraph.getNeighbors("A");
        assertTrue(neighbors.contains("B"));
        assertTrue(neighbors.contains("C"));
        assertTrue(neighbors.contains("D"));
        assertFalse(neighbors.contains("E"));
        assertTrue(neighbors.size() == 3);
    }
}
