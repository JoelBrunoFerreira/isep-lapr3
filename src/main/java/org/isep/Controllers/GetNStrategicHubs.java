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
    private static final LoadData loader = new LoadData();

    public GetNStrategicHubs(String locais, String distancias) {
        this.locals = LoadData.readCSV2(locais);
        this.matrixGraph = LoadData.fillMatrixGraph(distancias, locals);
    }


    public List<Vertex> getNStrategicHubs(int numberOfStrategicHubs) {
        List<Vertex> nStrategicHubs = new ArrayList<>();

        ArrayList<Vertex> allLocalsSorted = getAllVerticesSorted();

        for (int i = 0; i < numberOfStrategicHubs; i++) {
            nStrategicHubs.add(allLocalsSorted.get(i));
        }

        return nStrategicHubs;
    }

    private ArrayList<Vertex> getAllVerticesSorted() {

        ArrayList<Vertex> vertices = matrixGraph.vertices();
        Comparator<Vertex> vertexComparator = new Comparator<Vertex>() {
            @Override
            public int compare(Vertex v1, Vertex v2) {
                int degreeComparison = Integer.compare(v2.getDegree(), v1.getDegree());
                if (degreeComparison != 0) {
                    return degreeComparison;
                }

                int avgDistanceComparison = Integer.compare(v2.getNumMinPaths(), v1.getNumMinPaths());
                if (avgDistanceComparison != 0) {
                    return avgDistanceComparison;
                }

                // Compare by numMinPaths in decreasing order
                return Double.compare(v1.getAverageDistance(), v2.getAverageDistance());
            }
        };

        vertices.sort(vertexComparator);


        System.out.println("All:");
        for(Vertex v : vertices){
            System.out.println(v.getDegree() + " " + v.getName() + " " + v.getAverageDistance() + " " + v.getNumMinPaths());
        }

        return vertices;

    }

    public List<Vertex> getLocals() {
        return locals;
    }
}
