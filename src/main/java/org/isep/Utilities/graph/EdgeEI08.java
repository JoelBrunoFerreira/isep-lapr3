package org.isep.Utilities.graph;

public class EdgeEI08 {
    private final Vertex source;
    private final Vertex destination;
    private final double length;


    // Constructor
    // -----------------------------------
    public EdgeEI08(Vertex source, Vertex destination, double length) {
        this.source = source;
        this.destination = destination;
        this.length = length;
    }


    // Getters
    // ------------------------------------
    public Vertex getSource() {
        return source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public double getLength() {
        return length;
    }


    // toString
    // ----------------------------------------
    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source +
                ", destination=" + destination +
                ", length=" + length +
                '}';
    }
}
