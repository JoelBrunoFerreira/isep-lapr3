package org.isep.Utilities.graph;

public class EdgeEI03 {

    private double weight;
    private Vertex startVertex;
    private Vertex targetVertex;


    // Constructors
    // ---------------------------------------
    public EdgeEI03(double weight, Vertex startVertex, Vertex targetVertex) {
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
}
