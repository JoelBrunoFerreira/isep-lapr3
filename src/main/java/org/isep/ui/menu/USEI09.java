package org.isep.ui.menu;

import org.isep.Controllers.GetClusterAndSpecificHub;
import org.isep.Controllers.GetNStategicClusters;
import org.isep.Utilities.graph.Vertex;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class USEI09 {

    static Scanner sc = new Scanner(System.in);
    static final String distancias_big = "distancias_big.csv";
    static final String distancias_small = "distancias_small.csv";
    static final String locais_big = "locais_big.csv";
    static final String locais_small = "locais_small.csv";

    static GetClusterAndSpecificHub getClusterAndSpecificHub = new GetClusterAndSpecificHub(locais_small, distancias_small);

    public static void usei09Start() {

        System.out.println("Esta funcionalidade permite dividir a rede em N clusters que garantam apenas 1 hub por cluster de localidades.");
        System.out.println("Quantos clusters deseja obter?");


        boolean valid = false;
        int n = 0;

        while (valid == false) {
            try {
                n = sc.nextInt();
                if (n < 1 || n > getClusterAndSpecificHub.getMatrixGraph().vertices().size()) {
                    System.out.println("Indique um número válido. A rede tem " + getClusterAndSpecificHub.getMatrixGraph().vertices().size() + " localidades.");
                    System.out.println("Deve indicar um número entre 1 e " + getClusterAndSpecificHub.getMatrixGraph().vertices().size() + ".");
                } else {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Digite um input válido. Apenas serão aceites números.");
            }
            sc.nextLine();
        }

        Map<Vertex, ArrayList<Vertex>> clustersAndSpecificHub = getClusterAndSpecificHub.getClustersAndSpecificHub(n);

        System.out.println("Hubs pretendidos: " + n);
        int i = 1;
        for (Map.Entry<Vertex, ArrayList<Vertex>> entry : clustersAndSpecificHub.entrySet()) {
            System.out.print("Hub " + i + " - ");
            System.out.println(entry.getKey().getName());
            System.out.println("Horário de abertura: " + entry.getKey().getOpeningTime());
            System.out.println("Horário de encerramento: " + entry.getKey().getClosingTime());
            System.out.println("Número de funcionários: " + entry.getKey().getNumerOfEmployees());
            System.out.println("Cluster: ");
            ArrayList<Vertex> cluster = entry.getValue();
            for (Vertex local : cluster) {
                System.out.print(local.getName() + " | ");

            }
            System.out.println();
            System.out.println();
            i++;
        }

        App.askAgain();

    }
}
