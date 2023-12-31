package org.isep.Utilities.graph;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HamiltonianCycleTest {

    @Test
    public void testFindHamiltonianCycle() {
        List<Vertex> vertexList = new ArrayList<>();
        Vertex CT1 = new Vertex("CT1");
        Vertex CT2 = new Vertex("CT2");
        Vertex CT3 = new Vertex("CT3");
        Vertex CT4 = new Vertex("CT4");
        Vertex CT5 = new Vertex("CT5");
        Vertex CT6 = new Vertex("CT6");
        Vertex CT7 = new Vertex("CT7");

        vertexList.add(CT1);
        vertexList.add(CT2);
        vertexList.add(CT3);
        vertexList.add(CT4);
        vertexList.add(CT5);
        vertexList.add(CT6);
        vertexList.add(CT7);

        List<EdgeEI08> edgeList = new ArrayList<>();
        edgeList.add(new EdgeEI08(CT1, CT2, 230.55));
        edgeList.add(new EdgeEI08(CT1, CT3, 100.80));
        edgeList.add(new EdgeEI08(CT1, CT6, 380.38));

        edgeList.add(new EdgeEI08(CT2, CT1, 230.55));
        edgeList.add(new EdgeEI08(CT2, CT6, 260.14));
        edgeList.add(new EdgeEI08(CT2, CT4, 289.78));

        edgeList.add(new EdgeEI08(CT3, CT1, 100.80));
        edgeList.add(new EdgeEI08(CT3, CT5, 420.21));

        edgeList.add(new EdgeEI08(CT4, CT2, 289.78));
        edgeList.add(new EdgeEI08(CT4, CT5, 512.19));
        edgeList.add(new EdgeEI08(CT4, CT6, 120.55));
        edgeList.add(new EdgeEI08(CT4, CT7, 82.15));

        edgeList.add(new EdgeEI08(CT5, CT3, 420.21));
        edgeList.add(new EdgeEI08(CT5, CT4, 512.19));
        edgeList.add(new EdgeEI08(CT5, CT7, 50.68));

        edgeList.add(new EdgeEI08(CT6, CT1, 380.38));
        edgeList.add(new EdgeEI08(CT6, CT2, 260.14));
        edgeList.add(new EdgeEI08(CT6, CT4, 120.55));

        edgeList.add(new EdgeEI08(CT7, CT5, 50.68));
        edgeList.add(new EdgeEI08(CT7, CT4, 82.15));


        HamiltonianCycle hamiltonianCycle = new HamiltonianCycle(vertexList, edgeList);

        int vehicleCapacity = 300; // Define a value
        int vehicleSpeed = 100; // Define a value

        assertDoesNotThrow(() -> hamiltonianCycle.findHamiltonianCycle(vehicleCapacity, vehicleSpeed));
    }
}


