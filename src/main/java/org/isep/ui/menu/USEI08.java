package org.isep.ui.menu;

import org.isep.Utilities.graph.EdgeEI08;
import org.isep.Utilities.graph.HamiltonianCycle;
import org.isep.Utilities.graph.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class USEI08 {
    static Scanner read = new Scanner(System.in);

    public static void usei08Start() {

        List<Vertex> vertexList = new ArrayList<>();
        List<EdgeEI08> edgeList = new ArrayList<>();

        Vertex CT1 = new Vertex("CT1");
        Vertex CT2 = new Vertex("CT2");
        Vertex CT3 = new Vertex("CT3");
        Vertex CT4 = new Vertex("CT4");
        Vertex CT5 = new Vertex("CT5");
        Vertex CT6 = new Vertex("CT6");
        Vertex CT7 = new Vertex("CT7");

        vertexList.add(CT1);
        vertexList.add(CT2);
        vertexList.add(CT3);
        vertexList.add(CT4);
        vertexList.add(CT5);
        vertexList.add(CT6);
        vertexList.add(CT7);

        edgeList.add(new EdgeEI08(CT1, CT2, 230.55));
        edgeList.add(new EdgeEI08(CT1, CT3, 100.80));
        edgeList.add(new EdgeEI08(CT1, CT6, 380.38));

        edgeList.add(new EdgeEI08(CT2, CT1, 230.55));
        edgeList.add(new EdgeEI08(CT2, CT6, 260.14));
        edgeList.add(new EdgeEI08(CT2, CT4, 289.78));

        edgeList.add(new EdgeEI08(CT3, CT1, 100.80));
        edgeList.add(new EdgeEI08(CT3, CT5, 420.21));

        edgeList.add(new EdgeEI08(CT4, CT2, 289.78));
        edgeList.add(new EdgeEI08(CT4, CT5, 512.19));
        edgeList.add(new EdgeEI08(CT4, CT6, 120.55));
        edgeList.add(new EdgeEI08(CT4, CT7, 82.15));

        edgeList.add(new EdgeEI08(CT5, CT3, 420.21));
        edgeList.add(new EdgeEI08(CT5, CT4, 512.19));
        edgeList.add(new EdgeEI08(CT5, CT7, 50.68));

        edgeList.add(new EdgeEI08(CT6, CT1, 380.38));
        edgeList.add(new EdgeEI08(CT6, CT2, 260.14));
        edgeList.add(new EdgeEI08(CT6, CT4, 120.55));

        edgeList.add(new EdgeEI08(CT7, CT5, 50.68));
        edgeList.add(new EdgeEI08(CT7, CT4, 82.15));

        int numVertices = getNumberOfVertices(vertexList.size());
        int vehicleCapacity = getVehicleCapacity();
        int vehicleSpeed = getVehicleSpeed();

        HamiltonianCycle hamiltonianCycle = new HamiltonianCycle(vertexList.subList(0, numVertices), edgeList);
        hamiltonianCycle.findHamiltonianCycle(vehicleCapacity, vehicleSpeed);

        App.askAgain();
    }

    private static int getNumberOfVertices(int totalVertices) {
        System.out.println("Insira o número de hubs? (Entre 5 e " + totalVertices + ")");
        int numVertices = read.nextInt();

        while (numVertices < 5 || numVertices > totalVertices) {
            System.out.println("Por favor, insira um número válido de hubs.");
            numVertices = read.nextInt();
        }
        return numVertices;
    }

    private static int getVehicleCapacity() {
        System.out.println("Qual é a capacidade do veículo? (Km)");
        int vehicleCapacity = read.nextInt();

        while (vehicleCapacity < 100) {
            System.out.println("Por favor, insira um número válido para a capacidade do veiculo.");
            vehicleCapacity = read.nextInt();
        }
        return vehicleCapacity;
    }

    private static int getVehicleSpeed() {
        System.out.println("Qual é a velocidade do veículo? (km/h)");
        int vehicleSpeed = read.nextInt();

        while (vehicleSpeed < 40) {
            System.out.println("Por favor, insira um número válido para a velocidade do veiculo.");
            vehicleSpeed = read.nextInt();
        }

        return vehicleSpeed;
    }
}
