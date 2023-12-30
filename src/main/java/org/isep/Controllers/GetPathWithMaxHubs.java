package org.isep.Controllers;

import org.isep.Utilities.graph.Vertex;
import org.isep.Utilities.graph.matrix.MatrixGraph;

import java.time.LocalTime;
import java.util.*;
import java.util.function.BinaryOperator;

import static org.isep.Utilities.graph.Algorithms.shortestPath;

public class GetPathWithMaxHubs<T extends Vertex> {

    static final String distancias_big = "distancias_big.csv";
    static final String distancias_small = "distancias_small.csv";
    static final String locais_big = "locais_big.csv";
    static final String locais_small = "locais_small.csv";
    private List<Vertex> locals;
    private final MatrixGraph<Vertex, Double> matrixGraph;



    public GetPathWithMaxHubs(String locais, String distancias) {
        this.locals = LoadData.readCSV2(locais);
        this.matrixGraph = LoadData.fillMatrixGraph(distancias, locals);
    }

    public MatrixGraph<Vertex, Double> getMatrixGraph() {
        return matrixGraph;
    }


    public LinkedList<Vertex> getPathWithMaxHubs(LocalTime initialTime, LocalTime finalTime, Vertex origin,
                                                 double autonomy, double averageSpeed, double unloadingTime, double chargingTime, int numberOfHubs) {

        LinkedList<Vertex> path = new LinkedList<>();

        List<Vertex> hubs = getNStrategicHubs(numberOfHubs);
        for(Vertex local: hubs){
            local.IsHub();
        }
        if(origin.getIsHub()){
            hubs.remove(origin);
        }


        Map.Entry<Vertex, LinkedList<Vertex>> nextHubToVisitAndPath = getNextHubToVisit(hubs, origin, averageSpeed, autonomy, initialTime, chargingTime);
        Vertex nextHubToVisit = nextHubToVisitAndPath.getKey();
        LocalTime nextArrivalTime = nextHubToVisit.getArrivalTime();
        LinkedList<Vertex> partialPath = nextHubToVisitAndPath.getValue();

        if (nextArrivalTime.isAfter(finalTime)) {
            return null;
        }


        long unloadingTimeInSeconds = (long) (unloadingTime * 3600);
        double maxAutonomy = autonomy;
        LocalTime currentTime = initialTime;

        while (nextArrivalTime.isBefore(finalTime)) {

            //percorrer o caminho (obter autonomia)
            double finalAutonomy = computePath(partialPath, autonomy, maxAutonomy, averageSpeed, chargingTime, currentTime);
            partialPath.remove(partialPath.getLast());
            path.addAll(partialPath);

            //atualizar local e hora
            currentTime = nextArrivalTime;
            Vertex currentLocal = nextHubToVisit;
            //calcular hora de partida do local atual
            long currentTimeInSeconds = currentTime.toSecondOfDay();
            long departureTimeInSeconds = currentTimeInSeconds + unloadingTimeInSeconds;
            long hours = departureTimeInSeconds / 3600;
            long minutes = (departureTimeInSeconds % 3600) / 60;
            long seconds = departureTimeInSeconds % 60;
            LocalTime departureTime = LocalTime.of((int) hours, (int) minutes, (int) seconds);

            //remover visitado de hubs
            hubs.remove(nextHubToVisit);

            //determinar o próximo a visitar
            if (!hubs.isEmpty()) {
                nextHubToVisitAndPath = getNextHubToVisit(hubs, currentLocal, averageSpeed, finalAutonomy, departureTime, chargingTime);
                nextHubToVisit = nextHubToVisitAndPath.getKey();
                nextArrivalTime = nextHubToVisit.getArrivalTime();
                partialPath = nextHubToVisitAndPath.getValue();
                autonomy = finalAutonomy;
            }else{
                break;
            }

        }
        path.add(partialPath.getLast());

        return path;
    }

    private double computePath(LinkedList<Vertex> partialPath, double autonomy, double maxAutonomy, double averageSpeed, double chargingTime, LocalTime currentTime) {

        double speedInMettersPerSecond = averageSpeed + 1000 / 3600;
        long currentTimeInSeconds = currentTime.toSecondOfDay();



        for (int i = 0; i < partialPath.size() - 1; i++) {
            Vertex currentLocal = partialPath.get(i);
            Vertex nextLocal = partialPath.get(i + 1);

            currentLocal.setDepartureTime(currentTime);

            double distance = matrixGraph.edge(currentLocal, nextLocal).getWeight();
            double distanceInKm = distance / 1000;
            if (autonomy < distanceInKm) {
                autonomy = maxAutonomy;
                currentLocal.IsUsedAsChargingPoint();
                currentTimeInSeconds += chargingTime * 3600;
            }
            autonomy = autonomy - distanceInKm;
            double spentTime = Math.round(distance / speedInMettersPerSecond);

            currentTimeInSeconds += spentTime;
            long hours = currentTimeInSeconds / 3600;
            long minutes = (currentTimeInSeconds % 3600) / 60;
            long seconds = currentTimeInSeconds % 60;

            currentLocal = nextLocal;
            if (hours < 24) {
                currentLocal.setArrivalTime(LocalTime.of((int) hours, (int) minutes, (int) seconds));

            }




        }

        return autonomy;
    }

    private Map.Entry<Vertex, LinkedList<Vertex>> getNextHubToVisit(List<Vertex> hubs, Vertex vOrig, double averageSpeed, double autonomy, LocalTime currentTime, double chargingTime) {
        Comparator<Double> comparator = Comparator.naturalOrder(); //O(1)
        BinaryOperator<Double> sum = Double::sum; //O(1)
        Double zero = 0.0; //O(1)

        Comparator<Vertex> arrivalTimeComparator = Comparator
                .comparing(Vertex::getArrivalTime);

        Map<Vertex, LinkedList<Vertex>> hubAndShortestPath = new TreeMap<>(arrivalTimeComparator);

        for (Vertex hub : hubs) {
            LinkedList<Vertex> shortestPath = new LinkedList<>();

            shortestPath(matrixGraph, vOrig, hub, comparator, sum, zero, shortestPath);

            //distance in metters
            double totalDistance = calculateTotalDistance(shortestPath);
            //speed from km/h to m/s
            double speed = averageSpeed + 1000 / 3600;
            //time spent in seconds
            double spentTime = Math.round(totalDistance / speed);
            long currentTimeInSeconds = currentTime.toSecondOfDay();

            int numberOfChargings = countChargings(shortestPath, autonomy);

            long chargingTimeInSeconds = (long) (numberOfChargings * chargingTime * 3600);
            long arrivalTime = (long) (spentTime + currentTimeInSeconds + chargingTimeInSeconds);
            long hours = arrivalTime / 3600;
            long minutes = (arrivalTime % 3600) / 60;
            long seconds = arrivalTime % 60;
            if(hours < 24) {
                hub.setArrivalTime(LocalTime.of((int) hours, (int) minutes, (int) seconds));
            }
            hubAndShortestPath.put(hub, shortestPath);
        }

        return hubAndShortestPath.entrySet().iterator().next();
    }


    private int countChargings(LinkedList<Vertex> path, double autonomy) {
        int chargings = 0;
        double maxAutonomy = autonomy;

        for (int i = 0; i < path.size() - 1; i++) {
            //distance in km
            double distance = matrixGraph.edge(path.get(i), path.get(i + 1)).getWeight() / 1000;
            if (autonomy <= distance) {
                chargings++;
                autonomy = maxAutonomy;
            } else {
                autonomy -= distance;
            }
        }
        return chargings;
    }


    public List<Vertex> getNStrategicHubs(int numberOfStrategicHubs) {
        List<Vertex> nStrategicHubs = new ArrayList<>(); // O(1)

        List<T> allLocalsSorted = getAllVerticesSorted(); // O(V logV)

        for (int i = 0; i < numberOfStrategicHubs && i < allLocalsSorted.size(); i++) { // O(min(n, V))
            nStrategicHubs.add(allLocalsSorted.get(i));
        }

        for (Vertex hub : nStrategicHubs) {
            hub.IsHub();
        }

        return nStrategicHubs;
    }

    // O(V logV)
    private List<T> getAllVerticesSorted() {
        List<T> vertices = (List<T>) new ArrayList<>(matrixGraph.vertices());

        Comparator<T> vertexComparator = Comparator
                .<T, Integer>comparing(Vertex::getDegree)
                .thenComparing(Vertex::getNumMinPaths)
                .thenComparingDouble(Vertex::getAverageDistance)
                .reversed();

        vertices.sort(vertexComparator);

        return vertices;
    }


    public double calculateTotalDistance(LinkedList<Vertex> path) {
        double totalDistance = 0;

        int i = 0;
        for (Vertex local : path) {
            if (i < path.size() - 1) {
                Vertex vOrig = path.get(i);
                Vertex vDest = path.get(i + 1);
                totalDistance += matrixGraph.edge(vOrig, vDest).getWeight();
            }
        }

        return totalDistance;
    }

    public LocalTime calculateUnloadingTime(List<Vertex> hubs, LocalTime unloadingTime) {
        if (hubs == null) {
            return null;
        }

        int numberOfHubs = hubs.size();
        int totalChargingTimeMinutes = numberOfHubs * unloadingTime.getMinute();

        return LocalTime.of(totalChargingTimeMinutes / 60, totalChargingTimeMinutes % 60);

    }

    public LocalTime calculateChargingTime(List<Vertex> hubs, LocalTime chargingTime) {
        if (hubs == null) {
            return null;
        }

        int numberOfHubs = hubs.size();
        int totalChargingTimeMinutes = numberOfHubs * chargingTime.getMinute();

        return LocalTime.of(totalChargingTimeMinutes / 60, totalChargingTimeMinutes % 60);
    }


}