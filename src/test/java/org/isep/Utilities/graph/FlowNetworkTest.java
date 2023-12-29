package org.isep.Utilities.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlowNetworkTest {

    private FlowNetwork flowNetwork;

    @BeforeEach
    public void setUp() {
        // Initialize a FlowNetwork with a specific number of vertices for testing
        int numOfVertices = 5; // Replace with the desired number of vertices
        flowNetwork = new FlowNetwork(numOfVertices);
    }

    @Test
    public void testGetNumOfVertices() {
        int expectedVertices = 5; // Replace with the expected number of vertices
        assertEquals(expectedVertices, flowNetwork.getNumOfVertices());
    }

    @Test
    public void testAddEdgeAndGetAdjacenciesList() {
        // Create vertices and an EdgeEI10 object for testing
        Vertex vertex1 = new Vertex("CT1");
        Vertex vertex2 = new Vertex("CT6");
        EdgeEI10 edge = new EdgeEI10(vertex1, vertex2, 8);

        // Add the edge to the flow network
        flowNetwork.addEdge(edge);

        // Test whether the edge was properly added
        List<EdgeEI10> adjacenciesListV1 = flowNetwork.getAdjacenciesList(vertex1);
        List<EdgeEI10> adjacenciesListV2 = flowNetwork.getAdjacenciesList(vertex2);

        assertTrue(adjacenciesListV1.contains(edge));
        assertTrue(adjacenciesListV2.contains(edge));
    }
}