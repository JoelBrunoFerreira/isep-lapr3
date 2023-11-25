package org.isep.Controllers;


import org.isep.Utilities.graph.Vertex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


class GetNStategicClustersTest {

    static final String distancias_big = "distancias_big.csv";
    static final String distancias_small = "distancias_small.csv";
    static final String locais_big = "locais_big.csv";
    static final String locais_small = "locais_small.csv";

    GetNStategicClusters getNStategicClusters = new GetNStategicClusters(locais_big, distancias_big);

    @Test
    void InitializeClustersTest(){

        Vertex v1 = new Vertex("C10");
        Vertex v2 = new Vertex("C11");
        Vertex v3 = new Vertex("C12");

        List<Vertex> centroids = new ArrayList<>();
        centroids.add(v1);
        centroids.add(v2);
        centroids.add(v3);

        List<ArrayList<Vertex>> clusters = new ArrayList<>();

        getNStategicClusters.InitializeClusters(clusters, centroids);

        assertEquals(3, clusters.size());
    }

    @Test
    void InitializeCentroids(){

        List<String> ids = new ArrayList<>();
        ids.add("CT10");
        ids.add("CT11");
        ids.add("CT12");

        List<Vertex> centroids = new ArrayList<>();

        getNStategicClusters.InitializeCentroids(ids, centroids);

        assertEquals(3, centroids.size());
    }


    @Test
    void getNStrategicClusters(){

        List<String> ids = new ArrayList<>();
        ids.add("CT10");
        ids.add("CT11");
        ids.add("CT12");


        List<ArrayList<Vertex>> clusters = getNStategicClusters.getNStrategicClusters(ids);

        assertEquals(3, clusters.size());
    }
}