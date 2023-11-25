package org.isep.ui.menu;

import org.isep.Controllers.GetNStategicClusters;
import org.isep.Controllers.GetNStrategicHubs;
import org.isep.Utilities.graph.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class USEI02 {

    static Scanner sc = new Scanner(System.in);

    static final String distancias_big = "distancias_big.csv";
    static final String distancias_small = "distancias_small.csv";
    static final String locais_big = "locais_big.csv";
    static final String locais_small = "locais_small.csv";

    static GetNStrategicHubs getNStrategicHubs = new GetNStrategicHubs(locais_small, distancias_small);

    public static void usei02Start() {

        System.out.println("Esta funcionalidade permite determinar os vértices ideais para a localização" +
                "de N hubs de modo a otimizar a rede de distribuição. ");
        System.out.println("Quantos hubs pretende introduzir na rede de distribuição? ");

        int numLines = countLines(locais_small) - 1;


        int numClusters;
        do {
            System.out.println("(só são válidos números entre 1 e " + numLines + ") ");
            while (!sc.hasNextInt()) {
                System.out.println("Input inválido");
                sc.next();
            }
            numClusters = sc.nextInt();
        } while (numClusters < 1 || numClusters > numLines);



        List<Vertex> hubs = getNStrategicHubs.getNStrategicHubs(numClusters);

        for(Vertex vertex : hubs){
            System.out.println("Localidade: " + vertex.getName());
            System.out.println("Horário de abertura: " + vertex.getOpeningTime());
            System.out.println("Horário de encerramento: " + vertex.getClosingTime());
            System.out.println("Número de funcionários necessários: " + vertex.getNumerOfEmployees());
            System.out.println("Número de caminhos mínimos: " + vertex.getNumMinPaths());
            System.out.println("Número de localidades vizinhas: " + vertex.getDegree());

            double distanceInKm = vertex.getAverageDistance() / 1000;
            int decimalPlaces = 2;
            System.out.println("Distância média para todas as localidades: " +
                    Math.round(distanceInKm * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces) + " km");

            System.out.println();
        }

        System.out.println();
        App.askAgain();

    }


    private static int countLines(String filePath) {
        int lineCount = 0;

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                scanner.nextLine();
                lineCount++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return lineCount;
    }
}
