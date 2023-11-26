package org.isep.Utilities.Distance;

import org.isep.Utilities.graph.Vertex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FurthestVerticesTest {

    @Test
    public void testFindFurthestVertices() {
        // Create sample vertices
        Vertex v1 = new Vertex("CT1");
        v1.setLatitude(0);
        v1.setLongitude(0);
        Vertex v2 = new Vertex("CT2");
        v2.setLatitude(90);
        v2.setLongitude(0);
        Vertex v3 = new Vertex("CT3");
        v3.setLatitude(45);
        v3.setLongitude(90);
        Vertex v4 = new Vertex("CT4");
        v4.setLatitude(-45);
        v4.setLongitude(-90);
        List<Vertex> vertices = new ArrayList<>(Arrays.asList(v1, v2, v3, v4));

        // Invoke method
        List<Vertex> result = FurthestVertices.findFurthestVertices(vertices);

        // Test the result
        assertEquals(2, result.size()); // Expected result size FurthestVertices
    }
}