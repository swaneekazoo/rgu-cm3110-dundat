package uk.ac.rgu.dundat.data;

public class Guide {
    private String title;
    private int wpCount;
    private double distance;


    public Guide(String title, int wpCount, double distance) {
        this.title = title;
        this.wpCount = wpCount;
        this.distance = distance;
    }

    public String getTitle() {
        return title;
    }

    public int getWPCount() {
        return wpCount;
    }

    public double getDistance() {
        return distance;
    }
}
