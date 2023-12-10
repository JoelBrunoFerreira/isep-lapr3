package org.isep.Utilities.graph;

import java.util.Objects;

public class Local implements Comparable<Local> {
    private String name;
    private double latitude;
    private double longitude;

    public Local(String name) {
        this.name = name;
    }

    public Local(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Local local = (Local) o;
        return Objects.equals(name, local.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude);
    }

    @Override
    public int compareTo(Local o) {
        if (this.getName().length() == 3 && o.getName().length() == 3) {
            return this.getName().compareTo(o.getName());
        } else if (this.getName().length() == 4 && o.getName().length() == 4) {
            return this.getName().compareTo(o.getName());
        } else {
            return Integer.compare(this.getName().length(), o.getName().length());
        }
    }
    @Override
    public String toString() {
        return String.format("%s", this.name);
    }
}
