package org.isep.Controllers;

import org.isep.Utilities.graph.Edge;
import org.isep.Utilities.graph.Vertex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

                    for (Vertex v: vertexList) {
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
}
