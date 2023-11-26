package org.isep.Controllers;

import org.isep.Utilities.graph.Vertex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClustersAndSilhouetteTest {

    static final String distancias_big = "distancias_big.csv";
    static final String distancias_small = "distancias_small.csv";
    static final String locais_big = "locais_big.csv";
    static final String locais_small = "locais_small.csv";

    GetNStategicClusters getNStategicClusters = new GetNStategicClusters(locais_big, distancias_big);

    @Test
    void calculateSilhouetteCoefficients() {
        List<String> ids = new ArrayList<>();
        ids.add("CT10");
        ids.add("CT11");
        ids.add("CT12");

        Map<ArrayList<Vertex>, Double> clustersAndSilhouette = getNStategicClusters.getClustersAndSilhouette(ids);

        int i = 0;
        for (Map.Entry<ArrayList<Vertex>, Double> entry : clustersAndSilhouette.entrySet()) {
            ArrayList<Vertex> cluster = entry.getKey();
            Double silhouetteCoefficient = entry.getValue();

            System.out.println("Cluster: " + ids.get(i));
            System.out.println("Number of vertices: " + cluster.size());
            System.out.println("Silhouette Coefficient: " + silhouetteCoefficient);
            System.out.println();

            assertTrue(silhouetteCoefficient > -1);
            assertTrue(silhouetteCoefficient < 1);

            i++;
        }

        assertEquals(3, clustersAndSilhouette.size());

    }
}