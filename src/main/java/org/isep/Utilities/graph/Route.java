package org.isep.Utilities.graph;

import org.isep.Controllers.GetNStrategicHubs;
import org.isep.Utilities.graph.Local;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um percurso com um caminho, distância total e tempo total.
 */
public class Route implements Comparable<Route> {
    static final String distancias = "distancias_small.csv";
    static final String locais = "locais_small.csv";
    private List<Local> path;
    private double totalDistance;
    private double totalTime;
    private List<Double> distances;
    private List<Local> hubs;

    public Route(List<Local> path, double totalDistance, double totalTime, List<Double> distances) {
        this.path = path;
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
        this.distances = distances;
    }

    public Route(List<Local> path, double totalDistance, double totalTime, List<Double> distances, List<Local> hubs) {
        this.path = path;
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
        this.distances = distances;
        this.hubs = hubs;
    }

    public List<Local> getHubs() {
        return hubs;
    }

    public void setHubs(List<Local> hubs) {
        this.hubs = hubs;
    }

    public List<Double> getDistances() {
        return distances;
    }

    public List<Local> getPath() {
        return path;
    }

    public void setPath(List<Local> path) {
        this.path = path;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public int compareTo(Route o) {
        if (this.getPath().size()==o.getPath().size()){
            return Double.compare(this.getTotalDistance(), o.getTotalDistance());
        }
        return Integer.compare(this.getPath().size(), o.getPath().size());
    }

}
