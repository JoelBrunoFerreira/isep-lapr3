package org.isep.Controllers;



import org.isep.Utilities.graph.Vertex;
import org.isep.Utilities.graph.matrix.MatrixGraph;


import java.util.*;



public class GetNStrategicHubs {

    static final String distancias_big = "distancias_big.csv";
    static final String distancias_small = "distancias_small.csv";
    static final String locais_big = "locais_big.csv";
    static final String locais_small = "locais_small.csv";
    private List<Vertex> locals;
    private MatrixGraph<Vertex, Double> matrixGraph;

    public GetNStrategicHubs(String locais, String distancias) {
        this.locals = LoadData.readCSV2(locais);
        this.matrixGraph = LoadData.fillMatrixGraph(distancias, locals);
    }


    //O(V logV)
    public List<Vertex> getNStrategicHubs(int numberOfStrategicHubs) {
        List<Vertex> nStrategicHubs = new ArrayList<>(); //O(1)

        ArrayList<Vertex> allLocalsSorted = getAllVerticesSorted(); //O(V logV)

        for (int i = 0; i < numberOfStrategicHubs; i++) { //O(n)
            nStrategicHubs.add(allLocalsSorted.get(i));
        }

        return nStrategicHubs;
    }

    //O(V logV)
    private ArrayList<Vertex> getAllVerticesSorted() {

        ArrayList<Vertex> vertices = matrixGraph.vertices();
        Comparator<Vertex> vertexComparator = new Comparator<Vertex>() { //O(V logV)
            @Override
            public int compare(Vertex v1, Vertex v2) {
                int degreeComparison = Integer.compare(v2.getDegree(), v1.getDegree());
                if (degreeComparison != 0) {
                    return degreeComparison;
                }

                int numMinPathsComparison  = Integer.compare(v2.getNumMinPaths(), v1.getNumMinPaths());
                if (numMinPathsComparison  != 0) {
                    return numMinPathsComparison ;
                }

                return Double.compare(v1.getAverageDistance(), v2.getAverageDistance());
            }
        };

        vertices.sort(vertexComparator);

        return vertices;

    }

}
