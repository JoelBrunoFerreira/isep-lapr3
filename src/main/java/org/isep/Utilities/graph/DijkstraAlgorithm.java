package org.isep.Utilities.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraAlgorithm {

    // Methods
    // --------------------------------
    public int computePath(Vertex source, double vehicleCapacity) {
        double totalDistance = 0;
        source.setDistance(0);

        // Heap
        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        queue.add(source);

        while (!queue.isEmpty()) {

            Vertex currentVertex = queue.poll();

            for (EdgeEI03 edge : currentVertex.getAdjacencyList()) {

                Vertex u = edge.getStartVertex();
                Vertex v = edge.getTargetVertex();

                double d = currentVertex.getDistance() + edge.getweight();

                // Check if the edge leads to a shorter path to the adjacent vertex
                if (d < v.getDistance()) {
                    v.setDistance(d);
                    v.setPredecessor(currentVertex);
                    queue.add(v);
                }

                // Additional check for undirected graph
                double dReverse = currentVertex.getDistance() + edge.getweight();
                if (dReverse < u.getDistance()) {
                    u.setDistance(dReverse);
                    u.setPredecessor(currentVertex);
                    queue.add(u);
                }
                totalDistance = u.getDistance();
            }
        }
        // Calculate number of refuel stops
        return (int) Math.ceil(totalDistance / vehicleCapacity) - 1;
    }

    public List<Vertex> getShortestPathTo(Vertex targetVertex) {

        List<Vertex> path = new ArrayList<>();

        for (Vertex vertex = targetVertex; vertex != null; vertex=vertex.getPredecessor()) {
            path.add(vertex);
        }
        Collections.reverse(path);

        return path;
    }
}
