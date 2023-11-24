package org.isep.Utilities.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalAlgorithm {

    public void runKruskal(List<Vertex> vertexList, List<EdgeEI04> edgeList) {

        DisjointSet disjointSet = new DisjointSet(vertexList);
        List<EdgeEI04> mst = new ArrayList<>();

        // Merge sort to sort the edges
        Collections.sort(edgeList);

        for (EdgeEI04 edge: edgeList) {

            Vertex u = edge.getStartVertex();
            Vertex v = edge.getTargetVertex();

            // the edge is in the MST if the nodes are in different sets
            if (disjointSet.find(u.getNode()) != disjointSet.find(v.getNode())) {
                mst.add(edge);
            }

            // Merging the disjoint sets
            disjointSet.union(u.getNode(), v.getNode());
        }

        // Display the MST
        double totalDistance = 0;
        for (EdgeEI04 e : mst) {
            System.out.println(e.toString2());
            totalDistance += e.getWeight();
        }
        System.out.printf("Total minimum distance = %.2f Km\n", totalDistance);
    }
}
