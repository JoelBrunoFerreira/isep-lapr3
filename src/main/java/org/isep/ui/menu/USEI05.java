package org.isep.ui.menu;

import org.isep.Controllers.GetNStategicClusters;
import org.isep.Utilities.graph.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class USEI05 {

    static Scanner sc = new Scanner(System.in);
    static final String distancias_big = "distancias_big.csv";
    static final String distancias_small = "distancias_small.csv";
    static final String locais_big = "locais_big.csv";
    static final String locais_small = "locais_small.csv";

    static GetNStategicClusters getNStategicClusters = new GetNStategicClusters(locais_small, distancias_small);




    public static void usei05Start() {

        System.out.println("Esta funcionalidade permite dividir a rede em N clusters o mais conexos e coesos possíveis.");
        System.out.println("Deve indicar o número de clusters a obter e posteriormente as localidades que vão agrupar os pontos mais próximos.");
        System.out.println("Quantos clusters deseja obter?");
        int numClusters = sc.nextInt();
        sc.nextLine();

        List<String> ids = new ArrayList<>();
        int[] minAndMax = findMinAndMax(locais_small);

        boolean wrongInput = false;
        for (int i = 0; i < numClusters; i++) {
            String clusterId;
            do {
                if(wrongInput == true){
                    System.out.println("Formato errado.");
                }
                System.out.println("Digite o ID do local " + (i + 1) + " no formato CT + número do local (por exemplo, CT10):");
                System.out.println("Digite localidades entre CT" + minAndMax[0] + "  e CT" + minAndMax[1] + " .");

                clusterId = sc.nextLine();

                if(!clusterId.matches("^CT\\d+$") || Integer.parseInt(clusterId.substring(2))> minAndMax[1] ||
                        Integer.parseInt(clusterId.substring(2)) < minAndMax[0]){
                    wrongInput = true;
                }else{
                    wrongInput = false;
                }
            } while (!clusterId.matches("^CT\\d+$") || Integer.parseInt(clusterId.substring(2))> minAndMax[1] ||
                    Integer.parseInt(clusterId.substring(2)) < minAndMax[0]);
            ids.add(clusterId);
        }


        Map<ArrayList<Vertex>, Double> clustersAndSilhouette = getNStategicClusters.getClustersAndSilhouette(ids);

        int i = 0;
        for (Map.Entry<ArrayList<Vertex>, Double> entry : clustersAndSilhouette.entrySet()) {
            ArrayList<Vertex> cluster = entry.getKey();
            Double silhouetteCoefficient = entry.getValue();

            System.out.println("Cluster: " + ids.get(i));
            System.out.println("Número de vértices: " + cluster.size());
            System.out.println("Silhouette Coefficient: " + silhouetteCoefficient);
            System.out.println();

            i++;
        }

        System.out.println();
        App.askAgain();
    }

    private static int[] findMinAndMax(String path) {
        int minId = Integer.MAX_VALUE;
        int maxId = Integer.MIN_VALUE;

        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);


            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                if (parts.length >= 1) {
                    String idString = parts[0].trim();
                    if (idString.matches("^CT\\d+$")) {
                        int id = Integer.parseInt(idString.substring(2));

                        minId = Math.min(minId, id);
                        maxId = Math.max(maxId, id);
                    }
                }
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new int[]{minId, maxId};
    }


}
