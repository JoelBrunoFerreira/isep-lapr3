package org.isep.Utilities.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DisjointSetTest {

    @Test
    public void testUnionAndFind() {
        // Create vertices
        Vertex v1 = new Vertex("CT1");
        Vertex v2 = new Vertex("CT2");
        Vertex v3 = new Vertex("CT3");

        // Create nodes for vertices
        Node n1 = new Node(1, null);
        Node n2 = new Node(2, null);
        Node n3 = new Node(3, null);

        // Set nodes for vertices
        v1.setNode(n1);
        v2.setNode(n2);
        v3.setNode(n3);

        // Create DisjointSet
        List<Vertex> vertexList = new ArrayList<>();
        vertexList.add(v1);
        vertexList.add(v2);
        vertexList.add(v3);

        DisjointSet disjointSet = new DisjointSet(vertexList);

        // Test initial find operation
        assertEquals(n1, disjointSet.find(n1)); // Check if find returns the same node

        // Test union operation
        disjointSet.union(n1, n2);
        assertEquals(disjointSet.find(n1), disjointSet.find(n2)); // Check if nodes are in the same set after union

        disjointSet.union(n2, n3);
        assertEquals(disjointSet.find(n1), disjointSet.find(n3)); // Check if nodes are in the same set after union

        // NOTA:
        // São criados três vértices, cada um com seu próprio nó associado. Em seguida, são realizadas operações de união
        // entre esses nós e verificamos se a operação de find retorna o nó correto após as uniões.
    }
}