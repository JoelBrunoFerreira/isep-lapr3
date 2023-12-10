package org.isep.ui.menu;

import org.isep.Controllers.Loader;
import org.isep.Utilities.graph.Local;
import org.isep.Utilities.graph.Route;
import org.isep.Utilities.graph.RoutesBetweenLocations;
import org.isep.Utilities.graph.matrix.MatrixGraph;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class USEI06 {
    private static final String VERTEX_FILE = "locais_small.csv";

    private static final String EDGE_FILE = "distancias_small.csv";

    private static Scanner sc = new Scanner(System.in);


    public static void usei06Start() {

        MatrixGraph<Local, Double> graph = Loader.fillMatrixGraph(VERTEX_FILE, EDGE_FILE, false);

        System.out.println("Esta funcionalidade permite encontrar todos os percursos possíveis entre um local de origem e destino, conforme a autonomia e velocidade do veículo.");
        System.out.println("Local Origem:");
        String source = sc.nextLine();
        System.out.println("Local Destino:");
        String hub = sc.nextLine();
        System.out.println("Autonomia: (km)");
        double autonomyKm = Double.parseDouble(sc.nextLine());
        System.out.println("Velocidade: (km/h)");
        double velocityKmH = Double.parseDouble(sc.nextLine());

        try {
            List<Route> routes = RoutesBetweenLocations.findAllRoutes(graph, source, hub, autonomyKm, velocityKmH);

            if (routes == null) {
                System.out.println("Local não existe!");
            } else if (routes.isEmpty()) {
                System.out.println("Capacidade de autonomia do automóvel é insuficiente para realizar um percurso entre " + source + " e " + hub + ".");
            } else {
                Collections.sort(routes);
                for (Route route : routes) {
                    System.out.println("Percurso: " + route.getPath());
                    System.out.println(route.getDistances());
                    System.out.printf("Distância total: %.3fKm\n", route.getTotalDistance());
                    System.out.printf("Tempo total: %.2f horas.\n", route.getTotalTime());
                    System.out.println("---------------");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
        App.askAgain();
    }

}
