package org.isep.Controllers;

import org.isep.Utilities.graph.Vertex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GetClusterAndSpecificHubTest {

    static final String distancias_big = "distancias_big.csv";
    static final String distancias_small = "distancias_small.csv";
    static final String locais_big = "locais_big.csv";
    static final String locais_small = "locais_small.csv";

    GetClusterAndSpecificHub getClusterAndSpecificHub = new GetClusterAndSpecificHub(locais_small, distancias_small);

    @Test
    void getClustersAndSpecificHub() {
        Map<ArrayList<Vertex>, Vertex> clustersAndSpecificHub = getClusterAndSpecificHub.getClustersAndSpecificHub(3);



        assertEquals(clustersAndSpecificHub.size(), 3);
    }
}