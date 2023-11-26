package org.isep.Utilities.graph;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DijkstraAlgorithmTest {

    @Test
    public void testComputePathAndShortestPathTo() {
        // Create vertices
        Vertex v1 = new Vertex("CT1");
        Vertex v2 = new Vertex("CT2");
        Vertex v3 = new Vertex("CT3");
        Vertex v4 = new Vertex("CT4");

        // Create edges
        EdgeEI03 e1 = new EdgeEI03(5, v1, v2); // A -> B
        EdgeEI03 e2 = new EdgeEI03(9, v1, v3); // A -> C
        EdgeEI03 e3 = new EdgeEI03(2, v2, v4); // B -> D
        EdgeEI03 e4 = new EdgeEI03(6, v3, v4); // C -> D

        // Set adjacency lists
        v1.addNeighbor(e1);
        v1.addNeighbor(e2);
        v2.addNeighbor(e3);
        v3.addNeighbor(e4);

        // Initialize algorithm and compute path
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm();
        dijkstra.computePath(v1, 15); // Vehicle capacity: 10

        // Get shortest path to vertex D
        List<Vertex> shortestPath = dijkstra.getShortestPathTo(v4);

        // Check the shortest path
        assertEquals(3, shortestPath.size()); // Expected path length
        assertEquals(v1, shortestPath.get(0)); // Expected start vertex
        assertEquals(v2, shortestPath.get(1)); // Expected intermediate vertex
        assertEquals(v4, shortestPath.get(2)); // Expected target vertex
    }
}