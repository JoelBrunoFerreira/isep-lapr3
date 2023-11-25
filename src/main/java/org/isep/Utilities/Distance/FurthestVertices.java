package org.isep.Utilities.Distance;

import org.isep.Utilities.graph.Vertex;

import java.util.ArrayList;
import java.util.List;
public class FurthestVertices {

    public static List<Vertex> findFurthestVertices(List<Vertex> vertices) {
        List<Vertex> result = new ArrayList<>();

        Vertex first = null;
        Vertex second = null;
        double maxDistance = 0.0;

        for (int i = 0; i < vertices.size() - 1; i++) {
            Vertex v1 = vertices.get(i);
            for (int j = i + 1; j < vertices.size(); j++) {
                Vertex v2 = vertices.get(j);
                double distance = calculateDistance(v1, v2);
                if (distance > maxDistance) {
                    maxDistance = distance;
                    first = v1;
                    second = v2;
                }
            }
        }
        result.add(first);
        result.add(second);
        return result;
    }

    private static double calculateDistance(Vertex v1, Vertex v2) {
        double lat1 = Math.toRadians(v1.getLatitude());
        double lon1 = Math.toRadians(v1.getLongitude());
        double lat2 = Math.toRadians(v2.getLatitude());
        double lon2 = Math.toRadians(v2.getLongitude());

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Radius of the Earth in kilometers
        double radius = 6371.0;

        return radius * c;
    }
}
