package org.isep.Utilities.graph;


import org.isep.Utilities.graph.matrix.MatrixGraph;

import java.time.LocalTime;
import java.util.List;

public class Vertex {
    private final String name;
    private boolean isVisited;
    private double latitude;
    private double longitude;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private int numerOfEmployees;
    private int degree;
    private double averageDistance;  // --> Represents the avg distance to all vertices
    private int numMinPaths; // ---> number of minPanths
    private double distance; // --> Represents the distance from the starting vertex
    private Vertex predecessor; // --> Represents the previous vertex on the shortest path
    private List<Vertex> adjacencyList;

    MatrixGraph<String, Integer> instance = null;


    // Constructor
    // ------------------------------------
    public Vertex(String name) {
        this.name = name;
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

    public double getAverageDistance() {
        return averageDistance;
    }
    public int getNumMinPaths() {
        return numMinPaths;
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

    public void setAverageDistance(double averageDistance) {
        this.averageDistance = averageDistance;
    }
    public void incrementMinPaths(){
        this.numMinPaths ++;
    }

    // toString
    // -------------------------------------
    @Override
    public String toString() {
        return name;
    }

}
