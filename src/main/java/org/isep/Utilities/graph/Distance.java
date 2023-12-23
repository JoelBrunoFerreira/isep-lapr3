package org.isep.Utilities.graph;

public class Distance {

    private double weight;
    private Vertex startVertex;
    private Vertex targetVertex;
    private int minPaths;


    // Constructors
    // ---------------------------------------
    public Distance(double weight, Vertex startVertex, Vertex targetVertex) {
        this.weight = weight;
        this.startVertex = startVertex;
        this.targetVertex = targetVertex;
    }


    // Getters
    // ------------------------------------------
    public double getweight() {
        return weight;
    }

    public Vertex getStartVertex() {
        return startVertex;
    }

    public Vertex getTargetVertex() {
        return targetVertex;
    }

    public int getMinPaths() { return minPaths; }


    // Setters
    // --------------------------------------------
    public void setweight(double weight) {
        this.weight = weight;
    }

    public void setStartVertex(Vertex startVertex) {
        this.startVertex = startVertex;
    }

    public void setTargetVertex(Vertex targetVertex) {
        this.targetVertex = targetVertex;
    }

    public void incrementMinPaths(){ this.minPaths++;}
}

