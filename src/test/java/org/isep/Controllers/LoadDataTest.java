package org.isep.Controllers;

import org.isep.Utilities.graph.Edge;
import org.isep.Utilities.graph.Vertex;
import org.isep.Utilities.graph.matrix.MatrixGraph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoadDataTest {

    static final String distancias_big = "distancias_big.csv";
    static final String distancias_small = "distancias_small.csv";
    static final String locais_big = "locais_big.csv";
    static final String locais_small = "locais_small.csv";


    @Test
    void readCSV1() {
        List<Edge> edges = LoadData.readCSV1(distancias_big);

        assertNotNull(edges);
        assertEquals(783, edges.size());
    }


    @Test
    void fillMatrixGraph() {
        //Test readCSV2 method
        List<Vertex> locals = LoadData.readCSV2(locais_big);

        MatrixGraph<Vertex, Double> graph = LoadData.fillMatrixGraph(distancias_big, locals);
        assertNotNull(locals);
        assertEquals(323, graph.vertices().size());
    }


}