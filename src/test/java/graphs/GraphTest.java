package graphs;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

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
        assertTrue(myGraph.hasEdge("A", "B"));
        myGraph.addEdge("A", "C");
        assertTrue(myGraph.hasEdge("A", "C"));
    }

    @Test
    public void canAddEdgeList() {
        Graph myGraph = new Graph(true);
        String[][] edgeList = { { "A", "B" }, { "A", "C" } };
        myGraph.addEdgeList(edgeList);
        assertTrue(myGraph.hasEdge("A", "B"));
        assertTrue(myGraph.hasEdge("A", "C"));
        Graph myUndirectedGraph = new Graph(false);
        myUndirectedGraph.addEdgeList(edgeList);
        assertTrue(myUndirectedGraph.hasEdge("A", "B"));
        assertTrue(myUndirectedGraph.hasEdge("B", "A"));
        assertTrue(myUndirectedGraph.hasEdge("A", "C"));
        assertTrue(myUndirectedGraph.hasEdge("C", "A"));
    }

    @Test
    public void automaticallyAddsOpposingEdgeInUndirectedGraphs() {
        Graph myGraph = new Graph(false);
        myGraph.addEdge("A", "B");
        assertTrue(myGraph.hasEdge("A", "B"));
        assertTrue(myGraph.hasEdge("B", "A"));
        myGraph.addEdge("A", "C");
        assertTrue(myGraph.hasEdge("A", "C"));
        assertTrue(myGraph.hasEdge("C", "A"));
    }

    @Test
    public void canRemoveEdge() {
        Graph myGraph = new Graph(true);
        myGraph.removeEdge("A", "B");
        assertFalse(myGraph.hasEdge("A", "B"));
        myGraph.removeEdge("A", "C");
        assertFalse(myGraph.hasEdge("A", "C"));
    }

    @Test
    public void automaticallyRemovesOpposingEdgeInUndirectedGraphs() {
        Graph myGraph = new Graph(false);
        myGraph.removeEdge("A", "B");
        assertFalse(myGraph.hasEdge("A", "B"));
        assertFalse(myGraph.hasEdge("B", "A"));
        myGraph.removeEdge("A", "C");
        assertFalse(myGraph.hasEdge("A", "C"));
        assertFalse(myGraph.hasEdge("C", "A"));
    }

    @Test
    public void canGetSetOfVertices() {
        Graph myGraph = new Graph(false);
        myGraph.addEdge("A", "B");
        assertTrue(myGraph.getVertices().contains("A"));
        assertTrue(myGraph.getVertices().contains("B"));
        assertTrue(myGraph.getVertices().size() == 2);
        myGraph.addEdge("A", "C");
        assertTrue(myGraph.getVertices().contains("C"));
        assertTrue(myGraph.getVertices().size() == 3);
        myGraph.removeEdge("A", "C");
        // Removing C's only edge makes it isolated (degree 0), but C is still a
        // vertex in the graph and must remain in the vertex set.
        assertTrue(myGraph.getVertices().contains("A"));
        assertTrue(myGraph.getVertices().contains("B"));
        assertTrue(myGraph.getVertices().contains("C"));
        assertTrue(myGraph.getVertices().size() == 3);
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

    @Test
    public void directedEdgeIsOneWay() {
        Graph myDirectedGraph = new Graph(true);
        myDirectedGraph.addEdge("A", "B");
        assertTrue(myDirectedGraph.hasEdge("A", "B"));
        // In a directed graph the reverse edge is NOT created automatically.
        assertFalse(myDirectedGraph.hasEdge("B", "A"));
    }

    @Test
    public void removingDirectedEdgeLeavesReverseEdgeIntact() {
        Graph myDirectedGraph = new Graph(true);
        myDirectedGraph.addEdge("A", "B");
        myDirectedGraph.addEdge("B", "A");
        myDirectedGraph.removeEdge("A", "B");
        // Only the given direction is removed; the opposing edge survives.
        assertFalse(myDirectedGraph.hasEdge("A", "B"));
        assertTrue(myDirectedGraph.hasEdge("B", "A"));
    }

    @Test
    public void getVerticesIncludesDestinationOnlyVertices() {
        Graph myDirectedGraph = new Graph(true);
        myDirectedGraph.addEdge("A", "B");
        // B only ever appears as a destination, never as a source key, but it
        // is still a vertex of the graph.
        assertTrue(myDirectedGraph.getVertices().contains("A"));
        assertTrue(myDirectedGraph.getVertices().contains("B"));
        assertTrue(myDirectedGraph.getVertices().size() == 2);
    }

    @Test
    public void getNeighborsReturnsEmptySetForUnknownVertex() {
        Graph myGraph = new Graph(false);
        myGraph.addEdge("A", "B");
        assertTrue(myGraph.getNeighbors("Z").isEmpty());
    }

    @Test
    public void duplicateEdgeIsStoredOnce() {
        Graph myDirectedGraph = new Graph(true);
        myDirectedGraph.addEdge("A", "B");
        myDirectedGraph.addEdge("A", "B");
        assertTrue(myDirectedGraph.hasEdge("A", "B"));
        assertTrue(myDirectedGraph.getNeighbors("A").size() == 1);
    }
}
