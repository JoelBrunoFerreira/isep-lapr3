package org.isep.Utilities.graph;

import java.util.LinkedList;
import java.util.Queue;

public class FordFulkerson {
    private boolean[] marked;
    // marked[v.getID] = true , it means that there is a path from source -> vertex in the residual graph

    private EdgeEI10[] edgeEI10To;
    // edgeTo[v] = edges in the augmentation path
    // Augmentation == the process of increasing the size, value, or quality of something by adding to it

    private double valueMaxFlow;


    // Constructor
    // ---------------------------------
    public FordFulkerson(FlowNetwork flowNetwork, Vertex s, Vertex t) { // s = start ; t = target

        while( hasAugmentingPath(flowNetwork, s, t) ) {

            double minValue = Double.POSITIVE_INFINITY;

            for(Vertex v = t; v != s; v = edgeEI10To[v.getID()].getOther(v)) {
                minValue = Math.min(minValue, edgeEI10To[v.getID()].getResidualCapacity(v));
            }

            for(Vertex v = t; v != s; v = edgeEI10To[v.getID()].getOther(v)){
                edgeEI10To[v.getID()].addResidualFlowTo(v, minValue);
            }

            valueMaxFlow = valueMaxFlow + minValue;
        }
    }

    // Getters
    // ----------------------------------
    public boolean isInCut(int index) {
        return marked[index];
    }

    public double getMaxFlow() {
        return this.valueMaxFlow;
    }

    // Methods
    // ------------------------------------
    private boolean hasAugmentingPath(FlowNetwork flowNetwork, Vertex s, Vertex t) {

        edgeEI10To = new EdgeEI10[flowNetwork.getNumOfVertices()];
        marked = new boolean[flowNetwork.getNumOfVertices()];

        Queue<Vertex> queue = new LinkedList<>();
        queue.add(s);
        marked[s.getID()] = true;

        while( !queue.isEmpty() && !marked[t.getID()]){

            Vertex v = queue.remove();

            for(EdgeEI10 e : flowNetwork.getAdjacenciesList(v)) {
                Vertex w = e.getOther(v);

                if( e.getResidualCapacity(w) > 0){
                    if( !marked[w.getID()]){
                        edgeEI10To[w.getID()] = e;
                        marked[w.getID()] = true;
                        queue.add(w);
                    }
                }
            }

        }

        return marked[t.getID()];
    }
}
