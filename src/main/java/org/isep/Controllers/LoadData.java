package org.isep.Controllers;

import org.isep.Utilities.graph.Edge;
import org.isep.Utilities.graph.Vertex;
import org.isep.Utilities.graph.matrix.MatrixGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LoadData {
    private static final List<Edge> graph = new ArrayList<>();
    private static final List<Vertex> vertexList = new ArrayList<>();

    // Methods
    // ---------------------------------------------------
    public static List<Edge> readCSV1(String file) { // distancias_*.csv

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 3) {
                    Vertex vOrig = new Vertex(data[0]);
                    Vertex vDest = new Vertex(data[1]);
                    Edge edge = new Edge(vOrig, vDest, data[2]);
                    graph.add(edge);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return graph;
    }

    public static List<Vertex> readCSV2(String file) { // locais_*.csv

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (!vertexList.contains(new Vertex(data[0]))) {
                    vertexList.add(new Vertex(data[0]));

                    for (Vertex v : vertexList) {
                        if (v.getName().equals(data[0])) {
                            v.setLatitude(Double.parseDouble(data[1]));
                            v.setLongitude(Double.parseDouble(data[2]));
                        }
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return vertexList;
    }


    public static MatrixGraph<Vertex, Double> fillMatrixGraph(String file, List<Vertex> locals) {
        MatrixGraph<Vertex, Double> matrixGraph = new MatrixGraph<>(false);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Vertex vDest = null;
                Vertex vOrig = null;
                for (Vertex local : locals) {
                    if (data[0].equals(local.getName())) {
                        vOrig = local;
                    }
                    if (data[1].equals(local.getName())) {
                        vDest = local;
                    }
                }
                if (vOrig != null && vDest != null) {
                    matrixGraph.addEdge(vOrig, vDest, Double.parseDouble(data[2]));
                }


            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

       setVertexDegrees(matrixGraph, locals);
       setMaxDistance(matrixGraph, locals);
       //setNumOfMinPaths(matrixGraph, locals);

        return matrixGraph;
    }

    private static void setMaxDistance(MatrixGraph<Vertex, Double> matrixGraph, List<Vertex> locals) {
        for (Vertex vertex : locals) {
            double maxDistance = 0;

            Collection<Edge<Vertex, Double>> edges = matrixGraph.outgoingEdges(vertex);

            for (Edge<Vertex, Double> edge : edges) {
                double distance = edge.getWeight();

                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }

            vertex.setAdjMaxDistance(maxDistance);
        }
    }

    private static void setVertexDegrees(MatrixGraph<Vertex, Double> matrixGraph, List<Vertex> locals) {
        for (Vertex local : locals) {
            local.setDegree(matrixGraph.inDegree(local));
        }
    }

    private static void setNumOfMinPaths(MatrixGraph<Vertex, Double> matrixGraph, List<Vertex> locals){

    }
}
