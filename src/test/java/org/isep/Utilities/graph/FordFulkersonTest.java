package org.isep.Utilities.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FordFulkersonTest {

    @Test
    public void testMaxFlow() {
        // Create a flow network for testing
        FlowNetwork flowNetwork = new FlowNetwork(9);

        // Define source and target vertices
        Vertex CT1 = new Vertex(0, "CT1");
        Vertex CT2 = new Vertex(1, "CT2");
        Vertex CT3 = new Vertex(2, "CT3");
        Vertex CT4 = new Vertex(3, "CT4");
        Vertex CT5 = new Vertex(4, "CT5");
        Vertex CT6 = new Vertex(5, "CT6");

        List<Vertex> vertexList = new ArrayList<>();
        vertexList.add(CT1);
        vertexList.add(CT2);
        vertexList.add(CT3);
        vertexList.add(CT4);
        vertexList.add(CT5);
        vertexList.add(CT6);

        // Flow Network == 9 edge instances == numberOfVertices 'connections'
        flowNetwork.addEdge(new EdgeEI10(CT1, CT2, 4));
        flowNetwork.addEdge(new EdgeEI10(CT1, CT3, 5));

        flowNetwork.addEdge(new EdgeEI10(CT2, CT4, 7));

        flowNetwork.addEdge(new EdgeEI10(CT3, CT2, 4));
        flowNetwork.addEdge(new EdgeEI10(CT3, CT4, 1));
        flowNetwork.addEdge(new EdgeEI10(CT3, CT5, 3));

        flowNetwork.addEdge(new EdgeEI10(CT4, CT5, 3));
        flowNetwork.addEdge(new EdgeEI10(CT4, CT6, 6));

        flowNetwork.addEdge(new EdgeEI10(CT5, CT6, 2));

        // Create the FordFulkerson object
        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, CT1, CT6);

        // Test the getMaxFlow method
        double maxFlow = fordFulkerson.getMaxFlow();

        // Assert that the max flow is as expected
        assertEquals(8, maxFlow, 0.001);
    }

    @Test
    public void testIsInCut() {
        // Create a flow network for testing
        FlowNetwork flowNetwork = new FlowNetwork(9);

        // Define source and target vertices
        Vertex CT1 = new Vertex(0, "CT1");
        Vertex CT2 = new Vertex(1, "CT2");
        Vertex CT3 = new Vertex(2, "CT3");
        Vertex CT4 = new Vertex(3, "CT4");
        Vertex CT5 = new Vertex(4, "CT5");
        Vertex CT6 = new Vertex(5, "CT6");

        List<Vertex> vertexList = new ArrayList<>();
        vertexList.add(CT1);
        vertexList.add(CT2);
        vertexList.add(CT3);
        vertexList.add(CT4);
        vertexList.add(CT5);
        vertexList.add(CT6);

        // Flow Network == 9 edge instances == numberOfVertices 'connections'
        flowNetwork.addEdge(new EdgeEI10(CT1, CT2, 4));
        flowNetwork.addEdge(new EdgeEI10(CT1, CT3, 5));

        flowNetwork.addEdge(new EdgeEI10(CT2, CT4, 7));

        flowNetwork.addEdge(new EdgeEI10(CT3, CT2, 4));
        flowNetwork.addEdge(new EdgeEI10(CT3, CT4, 1));
        flowNetwork.addEdge(new EdgeEI10(CT3, CT5, 3));

        flowNetwork.addEdge(new EdgeEI10(CT4, CT5, 3));
        flowNetwork.addEdge(new EdgeEI10(CT4, CT6, 6));

        flowNetwork.addEdge(new EdgeEI10(CT5, CT6, 2));

        // Define source and target vertices
        Vertex source = new Vertex(0,"CT1");
        Vertex target = new Vertex(5,"CT6");

        // Create the FordFulkerson object
        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, source, target);

        // Test the isInCut method for specific vertices
        assertTrue(fordFulkerson.isInCut(0));
        assertFalse(fordFulkerson.isInCut(5));
    }
}