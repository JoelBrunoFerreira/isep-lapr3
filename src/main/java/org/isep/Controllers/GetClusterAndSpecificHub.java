package org.isep.Controllers;

import org.isep.Utilities.graph.Algorithms;
import org.isep.Utilities.graph.Edge;
import org.isep.Utilities.graph.Graph;
import org.isep.Utilities.graph.Vertex;
import org.isep.Utilities.graph.matrix.MatrixGraph;

import java.util.*;
import java.util.function.BinaryOperator;

import static org.isep.Utilities.graph.Algorithms.shortestPath;

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


    public Map<ArrayList<Vertex>, Vertex> getClustersAndSpecificHub(int numberOfClusters){

        Map<ArrayList<Vertex>, Vertex> clustersAndSpecificHub = new HashMap<>();

        List<ArrayList<Vertex>> clusters = getNStrategicClusters(numberOfClusters);

        for (ArrayList <Vertex> cluster: clusters) {
            Vertex specificHub = getSpecificHub(cluster);
            clustersAndSpecificHub.put(cluster, specificHub);
        }

        return clustersAndSpecificHub;
    }




    private List<ArrayList<Vertex>> getNStrategicClusters(int numberOfClusters) {

        List<ArrayList<Vertex>> clusters = new ArrayList<>();

        Graph<Vertex, Double> minimumSpanningTree = Algorithms.kruskalAlgorithm(matrixGraph);

        Collection<Edge<Vertex, Double>> edges = minimumSpanningTree.edges();
        List<Edge<Vertex, Double>> edgeList = new ArrayList<>(edges);
        SortEdgesByWeight(edgeList);

        int obtainedClusters = 0;
        int i = 0;
        while (obtainedClusters < numberOfClusters && i < edgeList.size()) {
            Edge<Vertex, Double> currentEdge = edgeList.get(i);
            Vertex vOrig = currentEdge.getVOrig();
            Vertex vDest = currentEdge.getVDest();

            minimumSpanningTree.removeEdge(vOrig, vDest);

            List<ArrayList<Vertex>> connectedComponents = getConnectedComponents(minimumSpanningTree);

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


    private void SortEdgesByWeight(List<Edge<Vertex, Double>> edgeList) {

        int n = edgeList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Edge<Vertex, Double> edge1 = edgeList.get(j);
                Edge<Vertex, Double> edge2 = edgeList.get(j + 1);


                if (edge1.getWeight() > edge2.getWeight()) {
                    Collections.swap(edgeList, j, j + 1);
                }
            }
        }

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
