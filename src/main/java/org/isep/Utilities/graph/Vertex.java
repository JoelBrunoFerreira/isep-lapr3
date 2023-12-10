package org.isep.Utilities.graph;


import org.isep.Utilities.graph.matrix.MatrixGraph;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Vertex implements Comparable<Vertex> {
    private final String name;
    private boolean isVisited;
    private double distance;  // --> Represents the distance from the starting vertex USEI03
    private double latitude;
    private double longitude;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private final int numerOfEmployees;
    private int degree;
    private double averageDistance;  // --> Represents the avg distance to all vertices
    private int numMinPaths; // ---> number of minPanths
    private Vertex predecessor; // --> Represents the previous vertex on the shortest path
    private final List<EdgeEI03> adjacencyList;
    private Node node;  // --> Will represent a node in the disjoint set

    MatrixGraph<String, Integer> instance = null;


    // Constructor
    // ------------------------------------
    public Vertex(String name) {
        this.name = name;
        this.adjacencyList = new ArrayList<>();
        this.distance = Double.MAX_VALUE; // --> Represents the Infinity
        this.numerOfEmployees = Integer.parseInt(name.substring(2));

        if (numerOfEmployees >= 1 && numerOfEmployees <= 105) {
            this.openingTime = LocalTime.of(9, 0, 0);
            this.closingTime = LocalTime.of(14, 0, 0);
        } else if (numerOfEmployees >= 106 && numerOfEmployees <= 215) {
            this.openingTime = LocalTime.of(11, 0, 0);
            this.closingTime = LocalTime.of(16, 0, 0);
        } else if (numerOfEmployees >= 216 && numerOfEmployees <= 323) {
            this.openingTime = LocalTime.of(12, 0, 0);
            this.closingTime = LocalTime.of(17, 0, 0);
        }
    }

    // Getters
    // -------------------------------------
    public String getName() {
        return name;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public int getNumerOfEmployees() {
        return numerOfEmployees;
    }

    public int getDegree() {
        return degree;
    }

    public List<EdgeEI03> getAdjacencyList() {
        return adjacencyList;
    }

    public double getAverageDistance() {
        return averageDistance;
    }

    public int getNumMinPaths() {
        return numMinPaths;
    }

    public double getDistance() {
        return distance;
    }

    public Vertex getPredecessor() {
        return predecessor;
    }

    public Node getNode() {
        return node;
    }


    // Setters
    // -------------------------------------
    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public void setAverageDistance(double averageDistance) {
        this.averageDistance = averageDistance;
    }

    public void incrementMinPaths(){
        this.numMinPaths ++;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setPredecessor(Vertex predecessor) {
        this.predecessor = predecessor;
    }

    public void setNode(Node node) {
        this.node = node;
    }



    // Methods
    // -----------------------------------------
    public void addNeighbor(EdgeEI03 edge) {
        this.adjacencyList.add(edge);

        // For undirected graphs
        Vertex otherVertex = edge.getTargetVertex();
        EdgeEI03 reverseEdge = new EdgeEI03(edge.getweight(), otherVertex, this);
        otherVertex.getAdjacencyList().add(reverseEdge);
    }

    // Compare To Method
    // -----------------------------------------
    @Override
    public int compareTo(Vertex otherVertex) {
        // Comparing objects
        // Compare the distance of this vertex with the distance of another vertex distance
        // Example:
        // v1 < v2 if v1.distance < v2.distance
        // OR
        // v2 < v1 if v2.distance < v1.distance
        return Double.compare(this.distance, otherVertex.getDistance());
    }

    // toString
    // ----------------------------------------
    @Override
    public String toString() {
        return String.format("%s = %.2f km", name, distance);
    }

    public String toString2() {
        return name;
    }
}
