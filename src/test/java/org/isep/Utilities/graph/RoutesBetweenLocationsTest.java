package org.isep.Utilities.graph;

import org.isep.Controllers.Loader;
import org.isep.Utilities.graph.matrix.MatrixGraph;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoutesBetweenLocationsTest {
    private static final String VERTEX_FILE = "locais_small.csv";

    private static final String EDGE_FILE = "distancias_small.csv";
    private static MatrixGraph<Local, Double> graph;
    private List<Route> routes;
    private String origName;
    private String destName;
    private double autonomyKm;
    private double velocityKmH;

    @BeforeAll
    static void setUp() {
        graph = Loader.fillMatrixGraph(VERTEX_FILE, EDGE_FILE, false);
    }

    @Test
    void testFindLocalByName() {
        Local l1 = RoutesBetweenLocations.findLocalByName(graph, "CT88");
        Local l2 = RoutesBetweenLocations.findLocalByName(graph, "CT7");
        assertNull(l1);
        assertNotNull(l2);
    }

    @Test
    void testValidInput() {
        String origName = "CT7";
        String destName = "CT14";
        autonomyKm = 150.0;
        velocityKmH = 60.0;

        List<Route> routes = RoutesBetweenLocations.findAllRoutes(graph, origName, destName, autonomyKm, velocityKmH);

        assertNotNull(routes);
        assertFalse(routes.isEmpty());
    }

    @Test
    void testInvalidLocalNameInput() {
        origName = "";
        destName = "NA";
        autonomyKm = 50.0;
        velocityKmH = 60.0;
        routes = RoutesBetweenLocations.findAllRoutes(graph, origName, destName, autonomyKm, velocityKmH);
        assertNull(routes);
    }

    @Test
    void testInvalidAutonomyInput() {
        origName = "CT13";
        destName = "CT14";
        autonomyKm = -1;
        velocityKmH = 60.0;
        routes = RoutesBetweenLocations.findAllRoutes(graph, origName, destName, autonomyKm, velocityKmH);
        assertNull(routes);
    }

    @Test
    void testInvalidVelocityInput() {
        origName = "CT13";
        destName = "CT14";
        autonomyKm = 150;
        velocityKmH = -1.0;
        routes = RoutesBetweenLocations.findAllRoutes(graph, origName, destName, autonomyKm, velocityKmH);
        assertNull(routes);
    }

    @Test
    void testNoRouteWithinAutonomy() {
        String origName = "CT7";
        String destName = "CT14";
        autonomyKm = 50;
        velocityKmH = 60.0;

        List<Route> routes = RoutesBetweenLocations.findAllRoutes(graph, origName, destName, autonomyKm, velocityKmH);

        assertNotNull(routes);
        assertTrue(routes.isEmpty());
    }

    @Test
    void testRouteWithinAutonomy() {
        Local l1 = RoutesBetweenLocations.findLocalByName(graph, "CT7");
        Local l2 = RoutesBetweenLocations.findLocalByName(graph, "CT14");
        Local l3 = RoutesBetweenLocations.findLocalByName(graph, "CT2");

        double expectedTotalDistance1 = 95.957;
        double expectedTotalDistance2 = 180.487;

        double expectedTotalTime1 = 0.9595699999999999;
        double expectedTotalTime2 = 1.80487;

        List<Local> expectedLocal1 = new ArrayList<>(List.of(l1, l2));
        List<Local> expectedLocal2 = new ArrayList<>(List.of(l1, l3, l2));

        List<Double> expectedDistances1 = new ArrayList<>(List.of(95.957));
        List<Double> expectedDistances2 = new ArrayList<>(List.of(65.574, 114.913));

        List<Local> expectedHubs1 = new ArrayList<>(List.of(l2));
        List<Local> expectedHubs2 = new ArrayList<>(List.of(l3, l2));

        String origName = "CT7";
        String destName = "CT14";
        double autonomyKm = 200;
        double velocityKmH = 100;

        List<Route> routes = RoutesBetweenLocations.findAllRoutes(graph, origName, destName, autonomyKm, velocityKmH);
        assertNotNull(routes);

        //verificar os locais em cada rota:
        List<Local> actualLocal1 = routes.get(0).getPath();
        List<Local> actualLocal2 = routes.get(1).getPath();

        assertEquals(expectedLocal1, actualLocal1);
        assertEquals(expectedLocal2, actualLocal2);

        //verificar as distâncias entre locais:
        List<Double> actualDistances1 = routes.get(0).getDistances();
        List<Double> actualDistances2 = routes.get(1).getDistances();

        assertEquals(expectedDistances1, actualDistances1);
        assertEquals(expectedDistances2, actualDistances2);

        //verificar a distância total:
        double actualTotalDistance1 = routes.get(0).getTotalDistance();
        double actualTotalDistance2 = routes.get(1).getTotalDistance();

        assertEquals(expectedTotalDistance1, actualTotalDistance1);
        assertEquals(expectedTotalDistance2, actualTotalDistance2);

        //verificar o tempo total:
        double actualTotalTime1 = routes.get(0).getTotalTime();
        double actualTotalTime2 = routes.get(1).getTotalTime();

        assertEquals(expectedTotalTime1, actualTotalTime1);
        assertEquals(expectedTotalTime2, actualTotalTime2);

        //verificar os hubs em cada rota:
        List<Local> actualHubs1 = routes.get(0).getHubs();
        List<Local> actualHubs2 = routes.get(1).getHubs();

        assertEquals(expectedHubs1, actualHubs1);
        assertEquals(expectedHubs2, actualHubs2);

        //print
        System.out.println("Percursos desde " + origName + " para " + destName + " com " + autonomyKm + "km de autonomia a uma velocidade de "+velocityKmH+"km/h:");
        Collections.sort(routes);
        for (Route route : routes) {
            System.out.println("Percurso: " + route.getPath());
            System.out.println("Distâncias entre locais: " + route.getDistances());
            System.out.printf("Distância total: %.3fKm\n", route.getTotalDistance());
            System.out.printf("Tempo total: %.2f horas.\n", route.getTotalTime());
            System.out.printf("Hubs: %s", route.getHubs());
            System.out.println("---------------");
        }
    }

}