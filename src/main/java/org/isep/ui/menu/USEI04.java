package org.isep.ui.menu;

import org.isep.Utilities.graph.EdgeEI04;
import org.isep.Utilities.graph.KruskalAlgorithm;
import org.isep.Utilities.graph.Vertex;

import java.util.ArrayList;
import java.util.List;

public class USEI04 {
    public static final List<Vertex> vertexList = new ArrayList<>();
    public static final List<EdgeEI04> edgeList = new ArrayList<>();

    public static void usei04Start() {

        loadGraph();
        KruskalAlgorithm algorithm = new KruskalAlgorithm();
        algorithm.runKruskal(vertexList,  edgeList);

    }

    public static void loadGraph() {

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


        edgeList.add(new EdgeEI04(56.717, CT1, CT6));

        edgeList.add(new EdgeEI04(65.574, CT2, CT7));
        edgeList.add(new EdgeEI04(125.105, CT2, CT8));
        edgeList.add(new EdgeEI04(163.996, CT2, CT11));

        edgeList.add(new EdgeEI04(157.223, CT3, CT4));

        edgeList.add(new EdgeEI04(157.223, CT4, CT3));
        edgeList.add(new EdgeEI04(162.527, CT4, CT9));

        edgeList.add(new EdgeEI04(90.186, CT5, CT9));
        edgeList.add(new EdgeEI04(100.563, CT5, CT6));
        edgeList.add(new EdgeEI04(111.134, CT5, CT17));

        edgeList.add(new EdgeEI04(67.584, CT6, CT10));

        edgeList.add(new EdgeEI04(95.957, CT7, CT14));

        edgeList.add(new EdgeEI04(207.558, CT8, CT14));

        edgeList.add(new EdgeEI04(63.448, CT10, CT13));
        edgeList.add(new EdgeEI04(67.584, CT10, CT6));
        edgeList.add(new EdgeEI04(110.848, CT10, CT1));
        edgeList.add(new EdgeEI04(125.041, CT10, CT5));
        edgeList.add(new EdgeEI04(62.655, CT11, CT5));
        edgeList.add(new EdgeEI04(121.584, CT11, CT13));
        edgeList.add(new EdgeEI04(142.470, CT11, CT10));

        edgeList.add(new EdgeEI04(50.467, CT12, CT3));
        edgeList.add(new EdgeEI04(62.877, CT12, CT1));
        edgeList.add(new EdgeEI04(70.717, CT12, CT15));

        edgeList.add(new EdgeEI04(111.686, CT13, CT7));

        edgeList.add(new EdgeEI04(89.813, CT14, CT13));
        edgeList.add(new EdgeEI04(95.957, CT14, CT7));
        edgeList.add(new EdgeEI04(114.913, CT14, CT2));
        edgeList.add(new EdgeEI04(207.558, CT14, CT8));

        edgeList.add(new EdgeEI04(43.598, CT15, CT3));

        edgeList.add(new EdgeEI04(68.957, CT16, CT3));
        edgeList.add(new EdgeEI04(79.560, CT16, CT17));
        edgeList.add(new EdgeEI04(82.996, CT16, CT12));
        edgeList.add(new EdgeEI04(103.704, CT16, CT9));
        edgeList.add(new EdgeEI04(110.133, CT16, CT4));

        edgeList.add(new EdgeEI04(62.879, CT17, CT9));
        edgeList.add(new EdgeEI04(69.282, CT17, CT1));
        edgeList.add(new EdgeEI04(73.828, CT17, CT6));
    }
}
