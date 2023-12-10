package org.isep.Utilities.graph;

import org.isep.Utilities.graph.matrix.MatrixGraph;

import java.util.ArrayList;
import java.util.List;

public class RoutesBetweenLocations {

    /**
     * Encontra rotas entre um local de origem e um local dentro com uma certa autonomia e a uma certa velocidade.
     *
     * @param graph      o grafo a ser pesquisado
     * @param origName o nome da localização de origem
     * @param destName    o nome da localização do hub
     * @param autonomyKm a autonomia do veículo elétrico em quilómetros
     * @return uma lista de rotas
     */
    public static List<Route> findAllRoutes(MatrixGraph<Local, Double> graph, String origName, String destName, double autonomyKm, double velocityKmH) { // Complexidade: O(V + E)
        List<Route> routes = new ArrayList<>();

        Local origin = findLocalByName(graph, origName);
        Local destiny = findLocalByName(graph, destName);

        if (!graph.validVertex(origin) || !graph.validVertex(destiny)) {
            return null;
        }

        List<Local> currentPath = new ArrayList<>();
        currentPath.add(origin);

        findAllRoutesDFS(graph, origin, destiny, autonomyKm, 0, 0, velocityKmH, currentPath, routes);

        return routes;
    }

    /**
     * Método auxiliar para pesquisar em profundidade
     *
     * @param graph
     * @param currentLocal
     * @param finalLocal
     * @param autonomyKm
     * @param currentDistance
     * @param currentTime
     * @param velocityKmH
     * @param currentPath
     * @param routes
     */
    private static void findAllRoutesDFS(MatrixGraph<Local, Double> graph, Local currentLocal, Local finalLocal, double autonomyKm,
                                         double currentDistance, double currentTime, double velocityKmH, List<Local> currentPath, List<Route> routes) {  // Complexidade: O(V + E)

        if (currentLocal.equals(finalLocal) && currentDistance <= autonomyKm) {
            currentTime = calculateTime(currentDistance, velocityKmH);
            routes.add(new Route(new ArrayList<>(currentPath), currentDistance, currentTime, getDistances(currentPath, graph)));
            return;
        }

        for (Edge<Local, Double> edge : graph.outgoingEdges(currentLocal)) {
            Local nextLocal = edge.getVDest();
            double edgeWeight = edge.getWeight();

            if ((!currentPath.contains(nextLocal)) && (currentDistance + edgeWeight <= autonomyKm)) {
                currentPath.add(nextLocal);
                findAllRoutesDFS(graph, nextLocal, finalLocal, autonomyKm, currentDistance + edgeWeight, currentTime, velocityKmH, currentPath, routes);
                currentPath.remove(currentPath.size() - 1);
            }
        }
    }

    /**
     * Encontrar um vértice do grafo pelo nome.
     *
     * @param graph
     * @param name
     * @return vértice encontrado ou null
     */
    private static Local findLocalByName(MatrixGraph<Local, Double> graph, String name) { // Complexidade: O(V)
        for (Local local : graph.vertices()) {
            if (local.getName().equals(name)) {
                return local;
            }
        }
        return null;
    }

    /**
     * Calcula o tempo entre cada local
     * @param distanceKm
     * @param speedKmH
     * @return  tempo total da viagem
     */
    public static double calculateTime(double distanceKm, double speedKmH) { // Complexidade: O(1)
        if (speedKmH == 0) {
            throw new IllegalArgumentException("Velocidade não pode ser zero.");
        }
        return distanceKm / speedKmH;
    }


    /**
     * Calcula as distâncias entre os locais da rota.
     * @param path
     * @param graph
     * @return lista das distâncias
     */
    private static List<Double> getDistances(List<Local> path, MatrixGraph<Local, Double> graph) { // Complexidade: O(V)
        List<Double> distances = new ArrayList<>();

        for (int i = 0; i < path.size() - 1; i++) {
            Local current = path.get(i);
            Local next = path.get(i + 1);
            Edge<Local, Double> edge = graph.edge(current, next);

            if (edge != null) {
                distances.add(edge.getWeight());
            }
        }

        return distances;
    }
}
