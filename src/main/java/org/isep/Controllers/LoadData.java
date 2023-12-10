package org.isep.Controllers;

import org.isep.Utilities.graph.Edge;
import org.isep.Utilities.graph.Vertex;
import org.isep.Utilities.graph.matrix.MatrixGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.function.BinaryOperator;

import static org.isep.Utilities.graph.Algorithms.shortestPaths;

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

        calculateVertexDegrees(matrixGraph, locals);
        calculateAverageDistanceAndMinPaths(matrixGraph, locals);
        return matrixGraph;
    }

    private static void calculateAverageDistanceAndMinPaths(MatrixGraph<Vertex, Double> matrixGraph, List<Vertex> locals) {
        for (Vertex vertex : locals) {
            Comparator<Double> comparator = Comparator.naturalOrder();
            BinaryOperator<Double> sum = Double::sum;
            Double zero = 0.0;

            ArrayList<LinkedList<Vertex>> paths = new ArrayList<>();
            ArrayList<Double> dists = new ArrayList<>();

            boolean success = shortestPaths(matrixGraph, vertex, comparator, sum, zero, paths, dists);

            if (success) {
                double totalDist = 0;
                for(Double dist: dists){
                    totalDist += dist;
                }
                double averageDist = totalDist / dists.size();
                vertex.setAverageDistance(averageDist);


                for(LinkedList<Vertex> path : paths){
                    for(Vertex v : path){
                        if(!v.equals(vertex)){
                            v.incrementMinPaths();
                        }
                    }
                }
            }

        }
    }

    private static void calculateVertexDegrees(MatrixGraph<Vertex, Double> matrixGraph, List<Vertex> locals) {
        for (Vertex local : locals) {
            local.setDegree(matrixGraph.inDegree(local));
        }
    }


    public static void changeSchedule(String file, List<Vertex> locals) { // esinf11.csv

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                boolean found = false;
                String[] data = line.split(",");

                for (Vertex local: locals) {
                    if(data[0].equals(local.getName())){
                        found = true;
                        System.out.println("Horário anterior à alteração - " + local.getName() + ": ");
                        System.out.println("Horário de abertura: " + local.getOpeningTime());
                        System.out.println("Horário de fecho: " + local.getClosingTime());
                        System.out.println();

                        local.setOpeningTime(LocalTime.parse(data[1]));
                        local.setClosingTime(LocalTime.parse(data[2]));

                        System.out.println("Novo horário - " + local.getName() + ": ");
                        System.out.println("Horário de abertura: " + local.getOpeningTime());
                        System.out.println("Horário de fecho: " + local.getClosingTime());
                        System.out.println();
                        break;
                    }
                }
                if(!found){
                    System.out.println("ERRO: " + data[0] + " não existe no sistema.");
                }


            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

    }

}
