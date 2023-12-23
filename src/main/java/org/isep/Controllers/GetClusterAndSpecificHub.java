package org.isep.Controllers;

import org.isep.Utilities.graph.*;
import org.isep.Utilities.graph.matrix.MatrixGraph;

import java.util.*;
import java.util.function.BinaryOperator;

import static org.isep.Utilities.graph.Algorithms.shortestPaths;

public class GetClusterAndSpecificHub {

    static final String distancias_big = "distancias_big.csv";
    static final String distancias_small = "distancias_small.csv";
    static final String locais_big = "locais_big.csv";
    static final String locais_small = "locais_small.csv";
    private List<Vertex> locals;
    private final MatrixGraph<Vertex, Double> matrixGraph;



    public GetClusterAndSpecificHub(String locais, String distancias) {
        this.locals = LoadData.readCSV2(locais);
        this.matrixGraph = LoadData.fillMatrixGraph(distancias, locals);
    }

    public MatrixGraph<Vertex, Double> getMatrixGraph() {
        return matrixGraph;
    }

    public Map<Vertex, ArrayList<Vertex>> getClustersAndSpecificHub(int numberOfClusters){
        List<ArrayList<Vertex>> clusters = getNStrategicClusters(numberOfClusters);

        if(clusters == null){
            return null;
        }

        Map<Vertex, ArrayList<Vertex>> clustersAndSpecificHub = new HashMap<>();
        for (ArrayList <Vertex> cluster: clusters) {
            Vertex specificHub = getSpecificHub(cluster);
            clustersAndSpecificHub.put(specificHub, cluster);
        }

        return clustersAndSpecificHub;
    }




    private List<ArrayList<Vertex>> getNStrategicClusters(int numberOfClusters) {
        if(numberOfClusters <= 0 || numberOfClusters > matrixGraph.vertices().size()){
            return null;
        }

        List<ArrayList<Vertex>> clusters = new ArrayList<>();

        if(numberOfClusters == 1){
            ArrayList<Vertex> cluster = matrixGraph.vertices();
            clusters.add(cluster);
        }


        Collection<Edge<Vertex, Double>> edges = matrixGraph.edges();
        List<Edge<Vertex, Double>> edgeList = new ArrayList<>(edges);
        ArrayList<Distance> distances = GetDistancesList(matrixGraph, edgeList);
        sortDistancesByMinPaths(distances);

        int obtainedClusters = 1;
        int i = 0;
        while (obtainedClusters < numberOfClusters && i < distances.size()) {
            Distance currentEdge = distances.get(i);
            Vertex vOrig = currentEdge.getStartVertex();
            Vertex vDest = currentEdge.getTargetVertex();

            matrixGraph.removeEdge(vOrig, vDest);

            List<ArrayList<Vertex>> connectedComponents = getConnectedComponents(matrixGraph);

            if (connectedComponents.size() == numberOfClusters) {
                clusters.addAll(connectedComponents);
            }

            obtainedClusters = connectedComponents.size();

            i++;
        }

        return clusters;
    }


    private List<ArrayList<Vertex>> getConnectedComponents(Graph<Vertex, Double> graph) {
        List<ArrayList<Vertex>> connectedComponents = new ArrayList<>();
        ArrayList<Vertex> visited = new ArrayList<>();

        for (Vertex vertex : graph.vertices()) {
            if (!visited.contains(vertex)) {
                LinkedList<Vertex> dfsResult = Algorithms.DepthFirstSearch(graph, vertex);
                if(dfsResult != null) {
                    // Marcar vértices resultantes do dfs como visitados
                    for(Vertex visitedVertex: dfsResult){
                        visited.add(visitedVertex);
                    }
                    ArrayList<Vertex> component = new ArrayList<>(dfsResult);
                    connectedComponents.add(component);
                }

            }
        }

        return connectedComponents;
    }


    private ArrayList<Distance> GetDistancesList(MatrixGraph<Vertex, Double> matrixGraph, List<Edge<Vertex, Double>> edgeList) {
        ArrayList<Distance> distances = new ArrayList<>();

        for (Vertex vertex : locals) {
            Comparator<Double> comparator = Comparator.naturalOrder();
            BinaryOperator<Double> sum = Double::sum;
            Double zero = 0.0;

            ArrayList<LinkedList<Vertex>> paths = new ArrayList<>();
            ArrayList<Double> dists = new ArrayList<>();

            boolean success = shortestPaths(matrixGraph, vertex, comparator, sum, zero, paths, dists);

            if (success) {

                int i = 0;
                for (LinkedList<Vertex> path : paths) {
                    if (i < path.size() - 1) {
                        Vertex vOrig = path.get(i);
                        Vertex vDest = path.get(i + 1);
                        Distance distance = new Distance(matrixGraph.edge(vOrig, vDest).getWeight(), vOrig, vDest);
                        distances.add(distance);
                    }
                }

                i = 0;
                for(LinkedList<Vertex> path : paths){
                    if(i < path.size() - 1) {
                        for (Distance d : distances) {
                            if(path.get(i).equals(d.getStartVertex()) && path.get(i + 1).equals(d.getTargetVertex())){
                                d.incrementMinPaths();
                            }
                        }
                    }
                }

            }

        }
        return distances;
    }

    private void sortDistancesByMinPaths(ArrayList<Distance> distances) {
        distances.sort(Comparator.comparingInt(Distance::getMinPaths).reversed());
    }


    private Vertex getSpecificHub(ArrayList<Vertex> cluster) {
        Vertex specificHub = null;
        Comparator<Vertex> vertexComparator = Comparator
                .comparing(Vertex::getDegree)
                .thenComparing(Vertex::getNumMinPaths)
                .thenComparingDouble(Vertex::getAverageDistance)
                .reversed();

        specificHub = cluster.stream()
                .max(vertexComparator)
                .orElse(null);

        return specificHub;
    }















}
