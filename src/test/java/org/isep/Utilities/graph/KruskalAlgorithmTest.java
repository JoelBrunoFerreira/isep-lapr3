package org.isep.Utilities.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class KruskalAlgorithmTest {

    @Test
    public void testRunKruskal() {
        // Create vertices
        Vertex v1 = new Vertex("CT1");
        Vertex v2 = new Vertex("CT2");
        Vertex v3 = new Vertex("CT3");
        Vertex v4 = new Vertex("CT4");

        // Create edges
        EdgeEI04 e1 = new EdgeEI04(5, v1, v2); // A -> B
        EdgeEI04 e2 = new EdgeEI04(9, v1, v3); // A -> C
        EdgeEI04 e3 = new EdgeEI04(2, v2, v4); // B -> D
        EdgeEI04 e4 = new EdgeEI04(6, v3, v4); // C -> D

        // Create lists
        List<Vertex> vertexList = new ArrayList<>();
        vertexList.add(v1);
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);

        List<EdgeEI04> edgeList = new ArrayList<>();
        edgeList.add(e1);
        edgeList.add(e2);
        edgeList.add(e3);
        edgeList.add(e4);

        // Run Kruskal's algorithm
        KruskalAlgorithm kruskal = new KruskalAlgorithm();
        kruskal.runKruskal(vertexList, edgeList);

    }
}