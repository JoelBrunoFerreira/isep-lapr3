package org.isep.Controllers;


import org.isep.Utilities.graph.Vertex;
import org.isep.Utilities.graph.matrix.MatrixGraph;


import java.util.*;


public class GetNStrategicHubs<T extends Vertex, Local> {

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

    public List<T> getNStrategicHubs(int numberOfStrategicHubs) {
        List<T> nStrategicHubs = new ArrayList<>(); // O(1)

        List<T> allLocalsSorted = getAllVerticesSorted(); // O(V logV)

        for (int i = 0; i < numberOfStrategicHubs && i < allLocalsSorted.size(); i++) { // O(min(n, V))
            nStrategicHubs.add(allLocalsSorted.get(i));
        }

        return nStrategicHubs;
    }

    // O(V logV)
    private List<T> getAllVerticesSorted() {
        List<T> vertices = (List<T>) new ArrayList<>(matrixGraph.vertices());

        Comparator<T> vertexComparator = Comparator
                .<T, Integer>comparing(Vertex::getDegree)
                .thenComparing(Vertex::getNumMinPaths)
                .thenComparingDouble(Vertex::getAverageDistance)
                .reversed();

        vertices.sort(vertexComparator);

        return vertices;
    }

}
