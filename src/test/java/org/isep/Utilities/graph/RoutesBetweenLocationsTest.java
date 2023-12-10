package org.isep.Utilities.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoutesBetweenLocationsTest {

    @Test
    void findAllRoutes() {
    }

    @Test
    void calculateTime() {
    }
    @Test
    void testValidInput() {
        MatrixGraph<Local, Double> graph = createSampleGraph();
        String origName = "A";
        String destName = "D";
        double autonomyKm = 150.0;
        double velocityKmH = 60.0;

        List<Route> routes = RoutesBetweenLocations.findAllRoutes(graph, origName, destName, autonomyKm, velocityKmH);

        assertNotNull(routes);
        assertFalse(routes.isEmpty());
    }

    @Test
    void testInvalidInput() {
        MatrixGraph<Local, Double> graph = createSampleGraph();
        String invalidName = "X";
        double autonomyKm = 150.0;
        double velocityKmH = 60.0;

        List<Route> routes = RoutesBetweenLocations.findAllRoutes(graph, invalidName, invalidName, autonomyKm, velocityKmH);

        assertNull(routes);
    }

    @Test
    void testNoRouteWithinAutonomy() {
        MatrixGraph<Local, Double> graph = createSampleGraph();
        String origName = "A";
        String destName = "D";
        double autonomyKm = 10.0;
        double velocityKmH = 60.0;

        List<Route> routes = RoutesBetweenLocations.findAllRoutes(graph, origName, destName, autonomyKm, velocityKmH);

        assertNotNull(routes);
        assertTrue(routes.isEmpty());
    }

    private MatrixGraph<Local, Double> createSampleGraph() {
        // Create a sample graph
        MatrixGraph<Local, Double> graph = new MatrixGraph<>();
        Local A = new Local("A", 0, 0);
        Local B = new Local("B", 10, 0);
        Local C = new Local("C", 10, 10);
        Local D = new Local("D", 0, 10);

        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(D);

        graph.addEdge(A, B, 10.0);
        graph.addEdge(B, C, 10.0);
        graph.addEdge(C, D, 10.0);
        graph.addEdge(D, A, 10.0);

        return graph;
    }
}