package org.isep.Controllers;

import org.isep.Utilities.graph.Vertex;
import org.isep.Utilities.graph.matrix.MatrixGraph;

import java.util.*;
import java.util.function.BinaryOperator;

import static org.isep.Utilities.graph.Algorithms.shortestPath;

public class GetNStategicClusters {

    static final String distancias_big = "distancias_big.csv";
    static final String distancias_small = "distancias_small.csv";
    static final String locais_big = "locais_big.csv";
    static final String locais_small = "locais_small.csv";
    private List<Vertex> locals;
    private MatrixGraph<Vertex, Double> matrixGraph;
    private static final LoadData loader = new LoadData();

    public GetNStategicClusters(String locais, String distancias) {
        this.locals = LoadData.readCSV2(locais);
        this.matrixGraph = LoadData.fillMatrixGraph(distancias, locals);
    }

    public Map<ArrayList<Vertex>, Double> getClustersAndSilhouette(List<String> ids){

        Map<ArrayList<Vertex>, Double> clustersAndSilhouette = new HashMap<>();

        List<ArrayList<Vertex>> clusters = getNStrategicClusters(ids);
        List<Double> silhouetteCoefficients = calculateSilhouetteCoefficients(clusters);

        for (int i = 0; i < clusters.size(); i++) {
            ArrayList<Vertex> cluster = clusters.get(i);
            Double silhouetteCoefficient = silhouetteCoefficients.get(i);

            clustersAndSilhouette.put(cluster, silhouetteCoefficient);
        }


        return clustersAndSilhouette;
    }



    public List<ArrayList<Vertex>> getNStrategicClusters(List<String> hubsID) {

        List<ArrayList<Vertex>> clusters = new ArrayList<>();
        List<Vertex> centroids = new ArrayList<>();

        Comparator<Double> comparator = Comparator.naturalOrder();
        BinaryOperator<Double> sum = Double::sum;
        Double zero = 0.0;


        InitializeCentroids(hubsID, centroids);

        InitializeClusters(clusters, centroids);


        for (Vertex vertex : matrixGraph.vertices()) {
            Vertex allocatedCentroid = null;

            double minDistance = Double.MAX_VALUE;


            for (Vertex centroid : centroids) {

                if (!vertex.equals(centroid) && !centroids.contains(vertex)) {

                    LinkedList<Vertex> shortestPath = new LinkedList<>();
                    Vertex vOrig = centroid;
                    Vertex vDest = vertex;

                    Double distance = shortestPath(matrixGraph, vOrig, vDest, comparator, sum, zero, shortestPath);
                    if (distance < minDistance) {
                        minDistance = distance;
                        allocatedCentroid = centroid;
                    }
                }

            }


            for (ArrayList<Vertex> cluster : clusters) {
                if (cluster.contains(allocatedCentroid)) {
                    cluster.add(vertex);
                }
            }

        }

        return clusters;
    }

    public void InitializeClusters(List<ArrayList<Vertex>> clusters, List<Vertex> centroids) {

        for (int i = 0; i < centroids.size(); i++) {
            ArrayList<Vertex> cluster = new ArrayList<>();
            cluster.add(centroids.get(i));
            clusters.add(cluster);
        }
    }


    public void InitializeCentroids(List<String> hubsID, List<Vertex> centroids) {

        for (String id : hubsID) {
            for (Vertex vertex : matrixGraph.vertices()) {
                if (vertex.getName().equals(id)) {
                    centroids.add(vertex);
                }

            }
        }
    }



    public List<Double> calculateSilhouetteCoefficients(List<ArrayList<Vertex>> clusters) {
        List<Double> clusterSilhouetteCoefficients = new ArrayList<>();
        int totalVertices = matrixGraph.numVertices();

        for (ArrayList<Vertex> cluster : clusters) {
            double clusterSilhouetteCoefficient = 0;

            for (Vertex vertex : cluster) {
                double a = calculateAverageDistance(vertex, cluster);
                double b = calculateAverageDistance(vertex, clusters, cluster);

                double silhouetteCoefficient = (b - a) / Math.max(a, b);

                clusterSilhouetteCoefficient += silhouetteCoefficient;
            }

            clusterSilhouetteCoefficients.add(clusterSilhouetteCoefficient / cluster.size());
        }

        return clusterSilhouetteCoefficients;
    }

    private double calculateAverageDistance(Vertex vertex, ArrayList<Vertex> cluster) {
        double totalDistance = 0.0;
        int numOfVertices = 0;

        Comparator<Double> comparator = Comparator.naturalOrder();
        BinaryOperator<Double> sum = Double::sum;
        Double zero = 0.0;

        for (Vertex otherVertex : cluster) {
            if (!vertex.equals(otherVertex)) {
                LinkedList<Vertex> shortestPath = new LinkedList<>();

                totalDistance += shortestPath(matrixGraph, vertex, otherVertex, comparator, sum, zero, shortestPath);
                numOfVertices++;
            }
        }

        if (numOfVertices == 0) {
            return 0;
        }
        return totalDistance / numOfVertices;
    }

    private double calculateAverageDistance(Vertex vertex, List<ArrayList<Vertex>> clusters, ArrayList<Vertex> cluster) {
        double minDistance = Double.MAX_VALUE;

        for (ArrayList<Vertex> otherCluster : clusters) {
            if (otherCluster != cluster) {
                double distance = calculateAverageDistance(vertex, otherCluster);
                minDistance = Math.min(minDistance, distance);
            }
        }

        return minDistance;
    }


}
