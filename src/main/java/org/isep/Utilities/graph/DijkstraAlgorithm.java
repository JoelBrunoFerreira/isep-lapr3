package org.isep.Utilities.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraAlgorithm {

    // Methods
    // --------------------------------
    public int computePath(Vertex source, double vehicleCapacity) {
        double totalDistance = 0;  // O(1)
        source.setDistance(0);  // O(1)

        // Heap
        PriorityQueue<Vertex> queue = new PriorityQueue<>();  // O(i)
        queue.add(source);  // O(log V)

        while (!queue.isEmpty()) {

            Vertex currentVertex = queue.poll(); // O(log V)

            for (EdgeEI03 edge : currentVertex.getAdjacencyList()) {  // O(n)

                Vertex u = edge.getStartVertex();  // O(1)
                Vertex v = edge.getTargetVertex();  // O(1)

                double d = currentVertex.getDistance() + edge.getweight();  // O(1)

                // Check if the edge leads to a shorter path to the adjacent vertex
                if (d < v.getDistance()) {  // O(log V)  --> because - queue.add(v);
                    v.setDistance(d);
                    v.setPredecessor(currentVertex);
                    queue.add(v);
                }

                // Additional check for undirected graph
                double dReverse = currentVertex.getDistance() + edge.getweight();  // O(1)
                if (dReverse < u.getDistance()) {  // O(log V)  --> because - queue.add(v);
                    u.setDistance(dReverse);
                    u.setPredecessor(currentVertex);
                    queue.add(u);
                }
                totalDistance = u.getDistance();  // O(1)
            }
        }
        // Calculate number of refuel stops
        return (int) Math.ceil(totalDistance / vehicleCapacity) - 1;  // O(1)
    }

    public List<Vertex> getShortestPathTo(Vertex targetVertex) {

        List<Vertex> path = new ArrayList<>();  // 0(1)

        for (Vertex vertex = targetVertex; vertex != null; vertex=vertex.getPredecessor()) {  // 0(n)
            path.add(vertex);  // O(1)
        }
        Collections.reverse(path);  // O(n)

        return path;
    }
}
