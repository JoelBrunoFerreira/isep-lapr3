package org.isep.ui;

import org.isep.Controllers.Controlador;
import org.isep.Controllers.LoadData;
import org.isep.Utilities.graph.Edge;
import org.isep.Utilities.graph.Vertex;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
   static final String distancias_big = "distancias_big.csv";
   static final String distancias_small = "distancias_small.csv";
   static final String locais_big = "locais_big.csv";
   static final String locais_small = "locais_small.csv";

    public static void main(String[] args) {
        /*
        Scanner read = new Scanner(System.in);
        boolean flag = false;

        while (!flag) {
            try {
                System.out.println("Dia e hora pretendida para verificação em formato: \"dd-MM-yyyy HH:mm\"");
                LocalDateTime dataHora = LocalDateTime.parse(read.nextLine(), DateTimeFormatter.ofPattern("d-MM-yyyy H:mm"));
                Controlador c = new Controlador(dataHora);

                if (c.checkDate()) {
                    System.out.println("Fora do plano de rega!");
                } else {
                    c.mostrarTodasAsTasks();
                    System.out.println();
                    c.mostrarParcelasARegar();
                }
                flag = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato errado!");
            }
        }
        */
        List<Edge> graph = LoadData.readCSV1(distancias_small);
        List<Vertex> vertexData = LoadData.readCSV2(locais_small);

        // Displaying the content of the edges list
        for (Edge edge : graph) {
            System.out.println(edge.getVOrig() + " to " + edge.getVDest() + ", Length: " + edge.getWeight());
        }

        System.out.println("===============================");

        // Display vertex data
        for (Vertex vertex : vertexData) {
            System.out.println(vertex.getName() + " Latitude: " + vertex.getLatitude() + " - Longitude: " + vertex.getLongitude() +
                    " - Employees: " + vertex.getNumerOfEmployees() + " - Required opening time: " + vertex.getOpeningTime() +
                    " - Required closing time: " + vertex.getClosingTime());
        }
    }
}