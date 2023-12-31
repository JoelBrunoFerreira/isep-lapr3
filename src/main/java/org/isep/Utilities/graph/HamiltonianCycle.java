package org.isep.Utilities.graph;

import java.util.ArrayList;
import java.util.List;

public class HamiltonianCycle {
    private final List<Vertex> vertices;
    private final List<EdgeEI08> edges;
    private List<Vertex> bestPath;
    private double bestDistance;


    // Constructor
    // ------------------------------------
    public HamiltonianCycle(List<Vertex> vertices, List<EdgeEI08> edges) {
        this.vertices = vertices;
        this.edges = edges;
        this.bestPath = new ArrayList<>();
        this.bestDistance = Integer.MAX_VALUE;
    }


    // Methods
    // --------------------------------------
    public void findHamiltonianCycle(int vehicleCapacity, int vehicleSpeed) {
        List<Vertex> currentPath = new ArrayList<>();
        boolean[] visited = new boolean[vertices.size()];

        currentPath.add(vertices.get(0)); // Start in the first Vertex in ArrayList
        visited[0] = true;

        findHamiltonianCycleUtil(vertices.get(0), currentPath, visited, 1, 0);

        if (bestPath.size() > 0) {
            System.out.println();
            System.out.println("Local de origem: " + vertices.get(0).toString2());
            bestPath.addLast(vertices.get(0));
            System.out.println("Locais de passagem: " + displayPath((ArrayList<Vertex>) bestPath));
            System.out.printf("Distância total minima: %.2f Km\n", bestDistance);
            System.out.println("Numero de colaboradores: " + getNumberOfCollaborators(bestPath));
            getTimeInfo(bestDistance, vehicleCapacity, vehicleSpeed);
        } else {
            System.out.println("Não foi possível encontrar um circuito!");
        }
    }

    private void findHamiltonianCycleUtil(Vertex currentVertex, List<Vertex> currentPath, boolean[] visited, int count, double distance) {
        if (count == vertices.size() && areAdjacent(currentVertex, vertices.get(0))) {
            distance += getEdgeLength(currentVertex, vertices.get(0));
            if (distance < bestDistance) {
                bestDistance = distance;
                bestPath = new ArrayList<>(currentPath);
            }
            return;
        }

        for (int i = 0; i < vertices.size(); i++) {
            Vertex nextVertex = vertices.get(i);
            if (!visited[i] && areAdjacent(currentVertex, nextVertex)) {
                visited[i] = true;
                currentPath.add(nextVertex);

                // Recursive call
                findHamiltonianCycleUtil(nextVertex, currentPath, visited, count + 1, distance + getEdgeLength(currentVertex, nextVertex));
                visited[i] = false;
                currentPath.remove(currentPath.size() - 1);
            }
        }
    }

    private boolean areAdjacent(Vertex source, Vertex destination) {
        for (EdgeEI08 edge : edges) {
            if (edge.getSource().equals(source) && edge.getDestination().equals(destination)) {
                return true;
            }
        }
        return false;
    }

    private double getEdgeLength(Vertex source, Vertex destination) {
        for (EdgeEI08 edge : edges) {
            if (edge.getSource().equals(source) && edge.getDestination().equals(destination)) {
                return edge.getLength();
            }
        }
        return 0.0; // If there is no Edge between vertices
    }

    private int getNumberOfCollaborators(List<Vertex> bestPath) {
        int numberOfCollaborators = 0;

        for (int i = 0; i < bestPath.size()-1; i++) {
            Vertex v = bestPath.get(i);
            String numericPart = v.getName().substring(2);
            numberOfCollaborators += Integer.parseInt(numericPart);
        }
        return numberOfCollaborators;
    }

    private void getTimeInfo(double bestDistance, int vehicleCapacity, int vehicleSpeed) {

        int numberOfCharges = ((int)bestDistance / vehicleCapacity);
        double chargingTime = 0.5; // 30 minutes
        double timeToUnloadBaskets = 0.5; // 30 minutes
        double numberOfStops = vertices.size() -1; // last stop doesn't count, because its origin again
        double tripDuration = bestDistance / vehicleSpeed;
        double unload = timeToUnloadBaskets * numberOfStops;
        double charges = chargingTime * numberOfCharges;
        double totalTime =  (bestDistance / vehicleSpeed) + (timeToUnloadBaskets * numberOfStops) + (chargingTime * numberOfStops);

        // Display
        System.out.println();
        System.out.printf("Tempo em viagem: %.2f horas à velocidade média de %d km/h\n", tripDuration, vehicleSpeed);
        System.out.println("Numero de carregamentos necessários: " + numberOfCharges + " - para um veículo com autonomia de " + vehicleCapacity + " kms");
        System.out.println("Tempo necessaŕio para efectuar um carregamento: " + chargingTime + "h");
        System.out.println("Tempo necessário para efectuar todos os carregamentods: " + charges + "h");
        System.out.println();
        System.out.println("Numero de paragens: " + (int)numberOfStops);
        System.out.println("Tempo necessário para efectuar uma descarga de mercadoria: " + timeToUnloadBaskets + "h");
        System.out.println("Tempo necessário para efectuar todas as descargas : " + unload + "h");
        System.out.printf("Tempo total do circuito: %.2f horas\n", totalTime);
        System.out.println();
    }

    public String displayPath(ArrayList<Vertex> list) {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            builder.append(list.get(i).toString2());
            if (i < list.size() - 1) {
                builder.append(" -> ");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
