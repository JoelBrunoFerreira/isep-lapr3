package org.isep.Utilities.graph;

import java.util.List;

public class Vertex {
    private final String name;
    private boolean isVisited;
    private double latitude;
    private double longitude;
    private double distance; // --> Represents the distance from the starting vertex
    private Vertex predecessor; // --> Represents the previous vertex on the shortest path
    private List<Vertex> adjacencyList;



    // Constructor
    // ------------------------------------
    public Vertex(String name) {
        this.name = name;
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


    // toString
    // -------------------------------------
    @Override
    public String toString() {
        return name;
    }
}
