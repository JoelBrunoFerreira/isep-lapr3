package org.isep.Controllers;

import org.isep.Utilities.graph.Edge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadData {
    private String distancias_big = "distancias_big.csv";
    private String distancias_small = "distancias_small.csv";
    private String locais_big = "locais_big.csv";
    private String locais_small = "locais_small.csv";



    // Methods
    // ---------------------------------------------------
    public static List<Edge> readCSV(String filePath) {
        List<Edge> edges = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 3) {
                    String vOrig = data[0];
                    String vDest = data[1];
                    // double weight = Double.parseDouble(data[2].trim());
                    String weight = data[2];
                    Edge edge = new Edge(vOrig, vDest, weight);
                    edges.add(edge);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return edges;
    }
}
