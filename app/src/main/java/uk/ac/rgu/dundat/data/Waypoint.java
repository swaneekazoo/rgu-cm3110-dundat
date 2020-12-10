package uk.ac.rgu.dundat.data;

public class Waypoint {
    private String name;
    private double latitude, longitude;
    private String message;

    public Waypoint(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
