package org.isep.Utilities.graph;

import java.util.ArrayList;
import java.util.List;

public class FlowNetwork {
    private final int numOfVertices;
    private int numOfEdges;
    private final List<List<EdgeEI10>> adjacencyList;


    // Constructor
    // ------------------------------
    public FlowNetwork(int numOfVertices) {
        this.numOfVertices = numOfVertices;
        this.numOfEdges = 0;
        this.adjacencyList = new ArrayList<>();

        for(int i = 0 ; i < numOfVertices ; i++) {
            List<EdgeEI10> edgeEI10List = new ArrayList<>();
            adjacencyList.add(edgeEI10List);
        }
    }

    // Getters
    // ---------------------------------
    public int getNumOfVertices() {
        return numOfVertices;
    }


    // Methods
    // ---------------------------------
    public void addEdge(EdgeEI10 e) {
        Vertex v = e.getStartVertex();
        Vertex w = e.getTargetVertex();
        adjacencyList.get(v.getID()).add(e);
        adjacencyList.get(w.getID()).add(e);
        numOfEdges++;
    }

    public List<EdgeEI10> getAdjacenciesList(Vertex v) {
        return adjacencyList.get(v.getID());
    }
}
