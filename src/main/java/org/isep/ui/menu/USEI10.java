package org.isep.ui.menu;

import org.isep.Utilities.graph.EdgeEI10;
import org.isep.Utilities.graph.FlowNetwork;
import org.isep.Utilities.graph.FordFulkerson;
import org.isep.Utilities.graph.Vertex;

import java.util.ArrayList;
import java.util.List;

public class USEI10 {

    public static void usei10Start() {

        FlowNetwork flowNetwork = new FlowNetwork(38); // --> Represent the numbers of vertex connections

        Vertex CT1 = new Vertex(1, "CT1");
        Vertex CT2 = new Vertex(2, "CT2");
        Vertex CT3 = new Vertex(3, "CT3");
        Vertex CT4 = new Vertex(4, "CT4");
        Vertex CT5 = new Vertex(5, "CT5");
        Vertex CT6 = new Vertex(6, "CT6");
        Vertex CT7 = new Vertex(7, "CT7");
        Vertex CT8 = new Vertex(8, "CT8");
        Vertex CT9 = new Vertex(9, "CT9");
        Vertex CT10 = new Vertex(10, "CT10");
        Vertex CT11 = new Vertex(11, "CT11");
        Vertex CT12 = new Vertex(12, "CT12");
        Vertex CT13 = new Vertex(13, "CT13");
        Vertex CT14 = new Vertex(14, "CT14");
        Vertex CT15 = new Vertex(15, "CT15");
        Vertex CT16 = new Vertex(16, "CT16");
        Vertex CT17 = new Vertex(17, "CT17");

        List<Vertex> vertexList = new ArrayList<>();

        vertexList.add(CT1);
        vertexList.add(CT2);
        vertexList.add(CT3);
        vertexList.add(CT4);
        vertexList.add(CT5);
        vertexList.add(CT6);
        vertexList.add(CT7);
        vertexList.add(CT8);
        vertexList.add(CT9);
        vertexList.add(CT10);
        vertexList.add(CT11);
        vertexList.add(CT12);
        vertexList.add(CT13);
        vertexList.add(CT14);
        vertexList.add(CT15);
        vertexList.add(CT16);
        vertexList.add(CT17);

        // Flow Network == 38 edge instances == numberOfVertices 'connections'
        flowNetwork.addEdge(new EdgeEI10(CT1, CT6, 12));
        flowNetwork.addEdge(new EdgeEI10(CT2, CT7, 5));
        flowNetwork.addEdge(new EdgeEI10(CT2, CT8, 6));
        flowNetwork.addEdge(new EdgeEI10(CT2, CT11, 7));
        flowNetwork.addEdge(new EdgeEI10(CT3, CT4, 8));
        flowNetwork.addEdge(new EdgeEI10(CT4, CT3, 8));
        flowNetwork.addEdge(new EdgeEI10(CT4, CT9, 5));
        flowNetwork.addEdge(new EdgeEI10(CT5, CT9, 9));
        flowNetwork.addEdge(new EdgeEI10(CT5, CT6, 7));
        flowNetwork.addEdge(new EdgeEI10(CT5, CT17, 9));
        flowNetwork.addEdge(new EdgeEI10(CT6, CT10, 8));
        flowNetwork.addEdge(new EdgeEI10(CT7, CT14, 10));
        flowNetwork.addEdge(new EdgeEI10(CT8, CT14, 6));
        flowNetwork.addEdge(new EdgeEI10(CT9, CT17, 8));
        flowNetwork.addEdge(new EdgeEI10(CT10, CT13, 6));
        flowNetwork.addEdge(new EdgeEI10(CT10, CT6, 8));
        flowNetwork.addEdge(new EdgeEI10(CT10, CT1, 8));
        flowNetwork.addEdge(new EdgeEI10(CT10, CT5, 7));
        flowNetwork.addEdge(new EdgeEI10(CT11, CT5, 10));
        flowNetwork.addEdge(new EdgeEI10(CT11, CT13, 8));
        flowNetwork.addEdge(new EdgeEI10(CT11, CT10, 10));
        flowNetwork.addEdge(new EdgeEI10(CT12, CT3, 5));
        flowNetwork.addEdge(new EdgeEI10(CT12, CT1, 9));
        flowNetwork.addEdge(new EdgeEI10(CT12, CT15, 6));
        flowNetwork.addEdge(new EdgeEI10(CT13, CT7, 7));
        flowNetwork.addEdge(new EdgeEI10(CT14, CT13, 10));
        flowNetwork.addEdge(new EdgeEI10(CT14, CT7, 10));
        flowNetwork.addEdge(new EdgeEI10(CT14, CT2, 8));
        flowNetwork.addEdge(new EdgeEI10(CT14, CT8, 6));
        flowNetwork.addEdge(new EdgeEI10(CT15, CT3, 7));
        flowNetwork.addEdge(new EdgeEI10(CT16, CT3, 9));
        flowNetwork.addEdge(new EdgeEI10(CT16, CT17, 9));
        flowNetwork.addEdge(new EdgeEI10(CT16, CT12, 7));
        flowNetwork.addEdge(new EdgeEI10(CT16, CT9, 5));
        flowNetwork.addEdge(new EdgeEI10(CT16, CT4, 10));
        flowNetwork.addEdge(new EdgeEI10(CT17, CT9, 8));
        flowNetwork.addEdge(new EdgeEI10(CT17, CT1, 9));
        flowNetwork.addEdge(new EdgeEI10(CT17, CT6, 7));


        // Call Ford-Fulkerson Algorithm
        FordFulkerson algorithm = new FordFulkerson(flowNetwork, CT7, CT17);

        // Display Maximum Flow
        System.out.println();
        System.out.println("Numero maximo de cabazes: " + algorithm.getMaxFlow());

        System.out.println();

        // Display path + Start Vertex + Target Vertex
        System.out.println("Rede de Transporte: ");
        System.out.print(CT7.toString2() + " -> "); // Start Vertex

        for (int v = 0; v < vertexList.size(); v++) {

            if (algorithm.isInCut(v)) {
                System.out.print(vertexList.get(v).toString2() + " -> ");
            }
        }
        System.out.print(CT17.toString2()); // Destination Vertex

        System.out.println();
        App.askAgain();
    }
}
