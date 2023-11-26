package org.isep.Controllers;

import org.isep.Utilities.graph.Vertex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class GetNStrategicHubsTest {

    static final String distancias_big = "distancias_big.csv";
    static final String distancias_small = "distancias_small.csv";
    static final String locais_big = "locais_big.csv";
    static final String locais_small = "locais_small.csv";

    GetNStrategicHubs getNStrategicHubs = new GetNStrategicHubs(locais_big, distancias_big);

    @Test
    void zeruHubsTest() {

        List<Vertex> strategicHubs = getNStrategicHubs.getNStrategicHubs(0);

        assertEquals(0, strategicHubs.size());
    }

    @Test
    void getNStrategicClustersSizeTest() {

        List<Vertex> strategicHubs = getNStrategicHubs.getNStrategicHubs(3);

        assertEquals(3, strategicHubs.size());

    }

    @Test
    void getNStrategicClustersDegreeTest() {

        List<Vertex> strategicHubs = getNStrategicHubs.getNStrategicHubs(20);

        for (int i = 0; i < strategicHubs.size(); i++) {
            if (i > 0) {
                assertTrue(strategicHubs.get(i).getDegree() <= strategicHubs.get(i - 1).getDegree());
            }
        }
    }


    @Test
    void getNStrategicClustersBetweenessTest() {

        List<Vertex> strategicHubs = getNStrategicHubs.getNStrategicHubs(20);

        for (int i = 0; i < strategicHubs.size(); i++) {
            if (i > 0) {
                System.out.println("Hub: " + strategicHubs.get(i).getName());
                System.out.println("Degree: " + strategicHubs.get(i).getDegree());
                System.out.println("MinPaths: " + strategicHubs.get(i).getNumMinPaths());
                System.out.println();

                if (strategicHubs.get(i).getDegree() == strategicHubs.get(i - 1).getDegree()) {
                    assertTrue(strategicHubs.get(i).getNumMinPaths() <= strategicHubs.get(i - 1).getNumMinPaths());
                }
            }
        }
    }


}