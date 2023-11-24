package org.isep.Utilities.graph;

public class EdgeEI04 implements Comparable<EdgeEI04>{
    private double weight;
    private Vertex startVertex;
    private Vertex targetVertex;


    // Constructor
    // -----------------------------------------
    public EdgeEI04(double weight, Vertex startVertex, Vertex targetVertex) {
        this.weight = weight;
        this.startVertex = startVertex;
        this.targetVertex = targetVertex;
    }


    // Getters
    // -----------------------------------------
    public double getWeight() {
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
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setStartVertex(Vertex startVertex) {
        this.startVertex = startVertex;
    }

    public void setTargetVertex(Vertex targetVertex) {
        this.targetVertex = targetVertex;
    }


    // compareTo
    // --------------------------------------------
    @Override
    public int compareTo(EdgeEI04 otherEdge) {
        // Compare edges based on their edge weight
        // Needed to the sorting
        return Double.compare(this.weight, otherEdge.getWeight());
    }

    // toString
    // -------------------------------------------
    @Override
    public String toString() {
        return String.format("%s -> %s = %.2f Km", startVertex, targetVertex, weight);
    }

    public String toString2() {
        return String.format("%s -> %s = %.2f km", this.startVertex.getName(), this.targetVertex.getName(), this.weight);
    }
}
