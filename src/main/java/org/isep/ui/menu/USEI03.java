package org.isep.ui.menu;

import org.isep.Controllers.LoadData;
import org.isep.Utilities.Distance.FurthestVertices;
import org.isep.Utilities.graph.DijkstraAlgorithm;
import org.isep.Utilities.graph.EdgeEI03;
import org.isep.Utilities.graph.Vertex;

import java.util.List;

public class USEI03 {

    public static void usei03Start() {

        // Create instances
        Vertex CT1 = new Vertex("CT1");
        Vertex CT2 = new Vertex("CT2");
        Vertex CT3 = new Vertex("CT3");
        Vertex CT4 = new Vertex("CT4");
        Vertex CT5 = new Vertex("CT5");
        Vertex CT6 = new Vertex("CT6");
        Vertex CT7 = new Vertex("CT7");
        Vertex CT8 = new Vertex("CT8");
        Vertex CT9 = new Vertex("CT9");
        Vertex CT10 = new Vertex("CT10");
        Vertex CT11 = new Vertex("CT11");
        Vertex CT12 = new Vertex("CT12");
        Vertex CT13 = new Vertex("CT13");
        Vertex CT14 = new Vertex("CT14");
        Vertex CT15 = new Vertex("CT15");
        Vertex CT16 = new Vertex("CT16");
        Vertex CT17 = new Vertex("CT17");


        CT1.addNeighbor(new EdgeEI03(56.717, CT1, CT6));

        CT2.addNeighbor(new EdgeEI03(65.574, CT2, CT7));
        CT2.addNeighbor(new EdgeEI03(125.105, CT2, CT8));
        CT2.addNeighbor(new EdgeEI03(163.996, CT2, CT11));

        CT3.addNeighbor(new EdgeEI03(157.223, CT3, CT4));

        CT4.addNeighbor(new EdgeEI03(157.223, CT4, CT3));
        CT4.addNeighbor(new EdgeEI03(162.527, CT4, CT9));

        CT5.addNeighbor(new EdgeEI03(90.186, CT5, CT9));
        CT5.addNeighbor(new EdgeEI03(100.563, CT5, CT6));
        CT5.addNeighbor(new EdgeEI03(111.134, CT5, CT17));

        CT6.addNeighbor(new EdgeEI03(67.584, CT6, CT10));

        CT7.addNeighbor(new EdgeEI03(95.957, CT7, CT14));

        CT8.addNeighbor(new EdgeEI03(207.558, CT8, CT14));

        CT10.addNeighbor(new EdgeEI03(63.448, CT10, CT13));
        CT10.addNeighbor(new EdgeEI03(67.584, CT10, CT6));
        CT10.addNeighbor(new EdgeEI03(110.848, CT10, CT1));
        CT10.addNeighbor(new EdgeEI03(125.041, CT10, CT5));

        CT11.addNeighbor(new EdgeEI03(62.655, CT11, CT5));
        CT11.addNeighbor(new EdgeEI03(121.584, CT11, CT13));
        CT11.addNeighbor(new EdgeEI03(142.470, CT11, CT10));

        CT12.addNeighbor(new EdgeEI03(50.467, CT12, CT3));
        CT12.addNeighbor(new EdgeEI03(62.877, CT12, CT1));
        CT12.addNeighbor(new EdgeEI03(70.717, CT12, CT15));

        CT13.addNeighbor(new EdgeEI03(111.686, CT13, CT7));

        CT14.addNeighbor(new EdgeEI03(89.813, CT14, CT13));
        CT14.addNeighbor(new EdgeEI03(95.957, CT14, CT7));
        CT14.addNeighbor(new EdgeEI03(114.913, CT14, CT2));
        CT14.addNeighbor(new EdgeEI03(207.558, CT14, CT8));

        CT15.addNeighbor(new EdgeEI03(43.598, CT15, CT3));

        CT16.addNeighbor(new EdgeEI03(68.957, CT16, CT3));
        CT16.addNeighbor(new EdgeEI03(79.560, CT16, CT17));
        CT16.addNeighbor(new EdgeEI03(82.996, CT16, CT12));
        CT16.addNeighbor(new EdgeEI03(103.704, CT16, CT9));
        CT16.addNeighbor(new EdgeEI03(110.133, CT16, CT4));

        CT17.addNeighbor(new EdgeEI03(62.879, CT17, CT9));
        CT17.addNeighbor(new EdgeEI03(69.282, CT17, CT1));
        CT17.addNeighbor(new EdgeEI03(73.828, CT17, CT6));

        // Read from csv file
        List<Vertex> vertexData =  LoadData.readCSV2("locais_small.csv");

        // Calculate the furthest vertices
        List<Vertex> result = FurthestVertices.findFurthestVertices(vertexData);

        System.out.println("Os locais mais afastados da rede de distribuição são: " + result.get(0).getName() +
                " e o " + result.get(1).getName());

        // Shortest Path
        DijkstraAlgorithm algorithmV2 = new DijkstraAlgorithm();
        int numberOfStops = algorithmV2.computePath(CT8, 300);

        System.out.println("Caminho mais curto entre ambos --> " + algorithmV2.getShortestPathTo(CT4));
        if (numberOfStops > 0) {
            System.out.println("O veiculo terá de recarregar : " + numberOfStops + " vez(es)");
        }
    }
}
