package org.isep.Utilities.graph;

import org.isep.Utilities.graph.Local;

import java.util.List;

/**
 * Representa um percurso com um caminho, distância total e tempo total.
 */
public class Route {
    private List<Local> path;
    private double totalDistance;
    private double totalTime;
    private List<Double> distances;

    public Route(List<Local> path, double totalDistance, double totalTime, List<Double> distances) {
        this.path = path;
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
        this.distances = distances;
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

}
