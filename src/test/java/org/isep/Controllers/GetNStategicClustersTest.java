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

        ArrayList<Vertex> cluster1 = clusters.get(0);
        assertEquals(1, cluster1.size());
        assertEquals(true, cluster1.contains(v1));

        ArrayList<Vertex> cluster2 = clusters.get(1);
        assertEquals(1, cluster2.size());
        assertEquals(true, cluster2.contains(v2));

        ArrayList<Vertex> cluster3 = clusters.get(2);
        assertEquals(1, cluster3.size());
        assertEquals(true, cluster3.contains(v3));

    }

    @Test
    void InitializeCentroidsTest(){

        List<String> ids = new ArrayList<>();
        ids.add("CT10");
        ids.add("CT11");
        ids.add("CT12");

        List<Vertex> centroids = new ArrayList<>();

        getNStategicClusters.InitializeCentroids(ids, centroids);

        assertEquals(3, centroids.size());
        assertEquals("CT10", centroids.get(0).getName());
        assertEquals("CT11", centroids.get(1).getName());
        assertEquals("CT12", centroids.get(2).getName());


    }


    @Test
    void getNStrategicClustersSizeTest(){

        List<String> ids = new ArrayList<>();
        ids.add("CT10");
        ids.add("CT11");
        ids.add("CT12");

        List<ArrayList<Vertex>> clusters = getNStategicClusters.getNStrategicClusters(ids);

        assertEquals(3, clusters.size());

        ArrayList<Vertex> cluster1 = clusters.get(0);

        ArrayList<Vertex> cluster2 = clusters.get(1);;

        ArrayList<Vertex> cluster3 = clusters.get(2);

        assertEquals(323, cluster1.size() + cluster2.size() + cluster3.size());

    }

    @Test
    void getNStrategicClustersCentroidTest(){

        List<String> ids = new ArrayList<>();
        ids.add("CT10");
        ids.add("CT11");
        ids.add("CT12");

        List<ArrayList<Vertex>> clusters = getNStategicClusters.getNStrategicClusters(ids);


        ArrayList<Vertex> cluster1 = clusters.get(0);

        boolean cluster1CentroidFlag = false;
        boolean cluster1WrongCentroidFound = false;

        for(Vertex v: cluster1){
            if(v.getName().equals("CT10")){
                cluster1CentroidFlag = true;
            }
            if(v.getName().equals("CT11") || v.getName().equals("CT12")){
                cluster1WrongCentroidFound = true;
            }
        }


        ArrayList<Vertex> cluster2 = clusters.get(1);;

        boolean cluster2CentroidFlag = false;
        boolean cluster2WrongCentroidFound = false;

        for(Vertex v: cluster2){
            if(v.getName().equals("CT11")){
                cluster2CentroidFlag = true;
            }
            if(v.getName().equals("CT10") || v.getName().equals("CT12")){
                cluster2WrongCentroidFound = true;
            }
        }


        ArrayList<Vertex> cluster3 = clusters.get(2);

        boolean cluster3CentroidFlag = false;
        boolean cluster3WrongCentroidFound = false;

        for(Vertex v: cluster3){
            if(v.getName().equals("CT12")){
                cluster3CentroidFlag = true;
            }
            if(v.getName().equals("CT10") || v.getName().equals("CT11")){
                cluster3WrongCentroidFound = true;
            }
        }



        assertEquals(true, cluster1CentroidFlag);
        assertEquals(false, cluster1WrongCentroidFound);

        assertEquals(true, cluster2CentroidFlag);
        assertEquals(false, cluster2WrongCentroidFound);

        assertEquals(true, cluster3CentroidFlag);
        assertEquals(false, cluster3WrongCentroidFound);

    }

    @Test
    void getNStrategicClustersSpecificVertexTest(){

        List<String> ids = new ArrayList<>();
        ids.add("CT10");
        ids.add("CT11");
        ids.add("CT12");

        List<ArrayList<Vertex>> clusters = getNStategicClusters.getNStrategicClusters(ids);


        ArrayList<Vertex> cluster1 = clusters.get(0);

        boolean foundCT6InCorrectCluster = false;
        boolean foundCT13InCorrectCluster = false;

        for(Vertex v: cluster1){
            if(v.getName().equals("CT6")){
                foundCT6InCorrectCluster = true;
            }
            if(v.getName().equals("CT13")){
                foundCT13InCorrectCluster = true;
            }
        }


        ArrayList<Vertex> cluster2 = clusters.get(2);

        boolean foundCT6InWrongCluster = false;
        boolean foundCT13InWrongCluster = false;

        for(Vertex v: cluster2){
            if(v.getName().equals("CT6")){
                foundCT6InWrongCluster = true;
            }
            if(v.getName().equals("CT13")){
                foundCT6InWrongCluster = true;
            }
        }




        assertEquals(true, foundCT6InCorrectCluster);
        assertEquals(true, foundCT13InCorrectCluster);
        assertEquals(false, foundCT6InWrongCluster);
        assertEquals(false, foundCT13InWrongCluster);

    }
}