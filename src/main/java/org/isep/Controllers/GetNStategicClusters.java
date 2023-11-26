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

    //O(n^4 * V * E)
    public Map<ArrayList<Vertex>, Double> getClustersAndSilhouette(List<String> ids){

        Map<ArrayList<Vertex>, Double> clustersAndSilhouette = new HashMap<>(); //O(1)

        List<ArrayList<Vertex>> clusters = getNStrategicClusters(ids); //O(n^2 * V * E)
        List<Double> silhouetteCoefficients = calculateSilhouetteCoefficients(clusters); //O(n^4 * V * E)

        for (int i = 0; i < clusters.size(); i++) {     //O(n)
            ArrayList<Vertex> cluster = clusters.get(i);    //O(n)
            Double silhouetteCoefficient = silhouetteCoefficients.get(i);   //O(n)

            clustersAndSilhouette.put(cluster, silhouetteCoefficient);   //O(n)
        }


        return clustersAndSilhouette;  //O(1)
    }



    //O(n^2 * V * E)
    public List<ArrayList<Vertex>> getNStrategicClusters(List<String> hubsID) {

        List<ArrayList<Vertex>> clusters = new ArrayList<>(); //O(1)
        List<Vertex> centroids = new ArrayList<>(); //O(1)

        Comparator<Double> comparator = Comparator.naturalOrder(); //O(1)
        BinaryOperator<Double> sum = Double::sum; //O(1)
        Double zero = 0.0; //O(1)


        InitializeCentroids(hubsID, centroids); //O(n)

        InitializeClusters(clusters, centroids); //O(n^2)


        for (Vertex vertex : matrixGraph.vertices()) { //O(n)
            Vertex allocatedCentroid = null;    //O(n)

            double minDistance = Double.MAX_VALUE;  //O(n)


            for (Vertex centroid : centroids) {  //O(n^2)

                if (!vertex.equals(centroid) && !centroids.contains(vertex)) {

                    LinkedList<Vertex> shortestPath = new LinkedList<>();
                    Vertex vOrig = centroid;
                    Vertex vDest = vertex;

                    Double distance = shortestPath(matrixGraph, vOrig, vDest, comparator, sum, zero, shortestPath); //O(n^2 * V * E)
                    if (distance < minDistance) {
                        minDistance = distance;
                        allocatedCentroid = centroid;
                    }
                }

            }


            for (ArrayList<Vertex> cluster : clusters) {  //O(n^2)
                if (cluster.contains(allocatedCentroid)) {
                    cluster.add(vertex);
                }
            }

        }

        return clusters;
    }

    //O(n)
    public void InitializeClusters(List<ArrayList<Vertex>> clusters, List<Vertex> centroids) {

        for (int i = 0; i < centroids.size(); i++) {//O(n)
            ArrayList<Vertex> cluster = new ArrayList<>();
            cluster.add(centroids.get(i));
            clusters.add(cluster);
        }
    }


    //O(n^2)
    public void InitializeCentroids(List<String> hubsID, List<Vertex> centroids) {

        for (String id : hubsID) { //O(n)
            for (Vertex vertex : matrixGraph.vertices()) { //O(n^2)
                if (vertex.getName().equals(id)) {
                    centroids.add(vertex);
                }

            }
        }
    }



    //O(n^4 * V * E)
    public List<Double> calculateSilhouetteCoefficients(List<ArrayList<Vertex>> clusters) {
        List<Double> clusterSilhouetteCoefficients = new ArrayList<>(); //O(1)
        int totalVertices = matrixGraph.numVertices(); //O(1)

        for (ArrayList<Vertex> cluster : clusters) { //O(n)
            double clusterSilhouetteCoefficient = 0; //O(n)

            for (Vertex vertex : cluster) { //O(n^2)
                double a = calculateAverageDistance(vertex, cluster); //O(n^3 * V * E)
                double b = calculateAverageDistance(vertex, clusters, cluster); //O(n^4 * V * E)

                double silhouetteCoefficient = (b - a) / Math.max(a, b); //O(n^2)

                clusterSilhouetteCoefficient += silhouetteCoefficient; //O(n^2)
            }

            clusterSilhouetteCoefficients.add(clusterSilhouetteCoefficient / cluster.size()); //O(n)
        }

        return clusterSilhouetteCoefficients; //O(1)
    }

    //O(n * V * E)
    private double calculateAverageDistance(Vertex vertex, ArrayList<Vertex> cluster) {
        double totalDistance = 0.0; //O(1)
        int numOfVertices = 0; //O(1)

        Comparator<Double> comparator = Comparator.naturalOrder(); //O(1)
        BinaryOperator<Double> sum = Double::sum; //O(1)
        Double zero = 0.0; //O(1)

        for (Vertex otherVertex : cluster) { //O(n)
            if (!vertex.equals(otherVertex)) {
                LinkedList<Vertex> shortestPath = new LinkedList<>();

                totalDistance += shortestPath(matrixGraph, vertex, otherVertex, comparator, sum, zero, shortestPath); //O(n * V * E)
                numOfVertices++;
            }
        }

        if (numOfVertices == 0) {
            return 0;
        }
        return totalDistance / numOfVertices;
    }

    //O(n^2 * V * E)
    private double calculateAverageDistance(Vertex vertex, List<ArrayList<Vertex>> clusters, ArrayList<Vertex> cluster) {
        double minDistance = Double.MAX_VALUE; //O(1)

        for (ArrayList<Vertex> otherCluster : clusters) { //O(n)
            if (otherCluster != cluster) {
                double distance = calculateAverageDistance(vertex, otherCluster); //O(n^2 * V * E)
                minDistance = Math.min(minDistance, distance);
            }
        }

        return minDistance;
    }


}
