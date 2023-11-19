package org.isep.Controllers;



import org.isep.Utilities.graph.Graph;
import org.isep.Utilities.graph.Vertex;
import org.isep.Utilities.graph.matrix.MatrixGraph;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.isep.Utilities.graph.Algorithms.BreadthFirstSearch;
import static org.isep.Utilities.graph.Algorithms.DepthFirstSearch;


public class GetNStrategicHubs {

    private Graph<String, Double> matrixGraph;
    private static final LoadData loader = new LoadData();

    public GetNStrategicHubs(String file) {
        this.matrixGraph = loader.fillMatrixGraph(file);
    }

    public List<Vertex> getNStrategicHubs(int numberOfStrategicHubs) {
        List<Vertex> nStrategicHubs = new ArrayList<>();
        LinkedList<String> list = DepthFirstSearch(matrixGraph, "CT8");
        System.out.println(list.size());









        return nStrategicHubs;
    }


}
