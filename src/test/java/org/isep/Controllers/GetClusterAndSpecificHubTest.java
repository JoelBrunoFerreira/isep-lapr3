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
    void assertZeroClusters() {
        Map<Vertex, ArrayList<Vertex>> clustersAndSpecificHub = getClusterAndSpecificHub.getClustersAndSpecificHub(0);
        assertEquals(clustersAndSpecificHub, null);
    }

    @Test
    void assertNumberOfClustersBiggerThanVertices() {
        Map<Vertex, ArrayList<Vertex>> clustersAndSpecificHub = getClusterAndSpecificHub.getClustersAndSpecificHub(18);
        assertEquals(clustersAndSpecificHub, null);
    }

    @Test
    void assertNumberOfVertices() {
        Map<Vertex, ArrayList<Vertex>> clustersAndSpecificHub = getClusterAndSpecificHub.getClustersAndSpecificHub(getClusterAndSpecificHub.getMatrixGraph().vertices().size());
        assertEquals(clustersAndSpecificHub.size(), getClusterAndSpecificHub.getMatrixGraph().vertices().size());
    }

    @Test
    void assertOneCluster() {
        Map<Vertex, ArrayList<Vertex>> clustersAndSpecificHub = getClusterAndSpecificHub.getClustersAndSpecificHub(1);

        System.out.println("OneClusterTest: ");
        for (Map.Entry<Vertex, ArrayList<Vertex>> entry: clustersAndSpecificHub.entrySet()){
            System.out.print("Hub: ");
            System.out.println(entry.getKey().getName());
            System.out.println("Cluster: ");
            ArrayList<Vertex> cluster = entry.getValue();
            for (Vertex local: cluster){
                System.out.print(local.getName() + " | ");
            }
            System.out.println();
        }
        System.out.println();
        assertEquals(clustersAndSpecificHub.size(), 1);
    }



    @Test
    void assertNClusters() {
        int numberOfClusters = 4;
        Map<Vertex, ArrayList<Vertex>> clustersAndSpecificHub = getClusterAndSpecificHub.getClustersAndSpecificHub(numberOfClusters);

        int totalLocals = 0;
        System.out.println("NClustersTest");
        System.out.println("n = " + numberOfClusters);
        int i = 1;
        for (Map.Entry<Vertex, ArrayList<Vertex>> entry: clustersAndSpecificHub.entrySet()){
            System.out.print("Hub [" + i + "] - ");
            System.out.println(entry.getKey().getName());
            System.out.println("Cluster: ");
            ArrayList<Vertex> cluster = entry.getValue();
            for (Vertex local: cluster){
                System.out.print(local.getName() + " | ");
            }
            System.out.println();
            totalLocals += entry.getValue().size();
            i++;
        }
        System.out.println();

        assertEquals(clustersAndSpecificHub.size(), numberOfClusters);
        assertEquals(totalLocals, getClusterAndSpecificHub.getMatrixGraph().vertices().size());
    }






}