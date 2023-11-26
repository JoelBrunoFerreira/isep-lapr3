package org.isep.Utilities.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalAlgorithm {

    public void runKruskal(List<Vertex> vertexList, List<EdgeEI04> edgeList) {

        DisjointSet disjointSet = new DisjointSet(vertexList);  // O(V)
        List<EdgeEI04> mst = new ArrayList<>();  // O(1)

        // Merge sort to sort the edges
        Collections.sort(edgeList);  // O(E log E)

        for (EdgeEI04 edge: edgeList) {  // O(E)

            Vertex u = edge.getStartVertex();
            Vertex v = edge.getTargetVertex();

            // the edge is in the MST if the nodes are in different sets
            if (disjointSet.find(u.getNode()) != disjointSet.find(v.getNode())) {  // O(a.V) <=> O(1)
                mst.add(edge);  // O(1)
            }

            // Merging the disjoint sets
            disjointSet.union(u.getNode(), v.getNode());  // O(a.V) <=> O(1)
        }

        // Display the MST
        double totalDistance = 0;  // O(1)
        System.out.println();
        System.out.println();
        System.out.println("Rede de ligação minima:");
        for (EdgeEI04 e : mst) {  // O(E)
            System.out.println(e.toString2());
            totalDistance += e.getWeight();
        }
        System.out.printf("Distância total da rede = %.2f Km\n", totalDistance);
    }
}
