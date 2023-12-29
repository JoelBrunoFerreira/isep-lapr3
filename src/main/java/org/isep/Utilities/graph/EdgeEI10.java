package org.isep.Utilities.graph;

public class EdgeEI10 {
    private final Vertex startVertex;
    private final Vertex targetVertex;
    private final double capacity;
    private double flow;


    // Constructor
    // -------------------------------
    public EdgeEI10(Vertex startVertex, Vertex targetVertex, double capacity) {
        this.startVertex = startVertex;
        this.targetVertex = targetVertex;
        this.capacity = capacity;
        this.flow = 0.0;
    }

    public EdgeEI10(EdgeEI10 edgeEI10) {
        this.startVertex = edgeEI10.getStartVertex();
        this.targetVertex = edgeEI10.getTargetVertex();
        this.capacity = edgeEI10.getCapacity();
        this.flow = edgeEI10.getFlow();
    }


    // Getters
    // ---------------------------------
    public Vertex getStartVertex() {
        return startVertex;
    }

    public Vertex getTargetVertex() {
        return targetVertex;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getFlow() {
        return flow;
    }


    // toString
    // ----------------------------------
    @Override
    public String toString() {
        return startVertex + " - " + targetVertex + " - " + capacity + " - " + flow;
    }


    // Methods
    // ----------------------------------
    public Vertex getOther(Vertex vertex) {
        if (vertex == startVertex) {
            return targetVertex;
        } else {
            return startVertex;
        }
    }

    public double getResidualCapacity(Vertex vertex) {
        if (vertex == startVertex) {
            return flow; // backward edge
        } else {
            return capacity - flow; // forward edge
        }
    }

    public void addResidualFlowTo(Vertex vertex, double deltaFlow) {

        if (vertex == startVertex) {
            flow = flow - deltaFlow; // backward edge
        } else {
            flow = flow + deltaFlow; // forward edge
        }
    }
}
