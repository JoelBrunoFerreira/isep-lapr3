package org.isep.ui.menu;

import org.isep.Utilities.graph.EdgeEI10;
import org.isep.Utilities.graph.FlowNetwork;
import org.isep.Utilities.graph.FordFulkerson;
import org.isep.Utilities.graph.Vertex;

import java.util.ArrayList;
import java.util.List;

public class USEI10 {

    public static void usei10Start() {

        FlowNetwork flowNetwork = new FlowNetwork(9); // --> Represent the numbers of vertex connections
        Vertex CT1 = new Vertex(0, "CT1");
        Vertex CT2 = new Vertex(1, "CT2");
        Vertex CT3 = new Vertex(2, "CT3");
        Vertex CT4 = new Vertex(3, "CT4");
        Vertex CT5 = new Vertex(4, "CT5");
        Vertex CT6 = new Vertex(5, "CT6");

        List<Vertex> vertexList = new ArrayList<>();
        vertexList.add(CT1);
        vertexList.add(CT2);
        vertexList.add(CT3);
        vertexList.add(CT4);
        vertexList.add(CT5);
        vertexList.add(CT6);

        // Flow Network == 9 edge instances == numberOfVertices 'connections'
        flowNetwork.addEdge(new EdgeEI10(CT1, CT2, 4));
        flowNetwork.addEdge(new EdgeEI10(CT1, CT3, 5));

        flowNetwork.addEdge(new EdgeEI10(CT2, CT4, 7));

        flowNetwork.addEdge(new EdgeEI10(CT3, CT2, 4));
        flowNetwork.addEdge(new EdgeEI10(CT3, CT4, 1));
        flowNetwork.addEdge(new EdgeEI10(CT3, CT5, 3));

        flowNetwork.addEdge(new EdgeEI10(CT4, CT5, 3));
        flowNetwork.addEdge(new EdgeEI10(CT4, CT6, 6));

        flowNetwork.addEdge(new EdgeEI10(CT5, CT6, 2));


        // Call Ford-Fulkerson Algorithm
        FordFulkerson algorithm = new FordFulkerson(flowNetwork, CT1, CT6);

        // Display Maximum Flow
        System.out.println();
        System.out.println("Numero maximo de cabazes: " + algorithm.getMaxFlow());

        System.out.println();

        // Display path + Start Vertex + Target Vertex
        System.out.println("Rede de Transporte: ");
        for (int v = 0; v < vertexList.size(); v++) {

            if (algorithm.isInCut(v)) {
                System.out.print(vertexList.get(v).toString2() + " -> ");
            }
        }
        System.out.print(CT6.toString2()); // Destination Vertex

        System.out.println();
        App.askAgain();
    }
}
