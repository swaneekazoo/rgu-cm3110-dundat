package uk.ac.rgu.dundat.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "guide")
public class Guide {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
//    private ArrayList<Waypoint> waypoints;
    private String title;
    private int wpCount;
    private double distance;

    public Guide(int id, String title, int wpCount, double distance) {
        this.id = id;
        this.title = title;
        this.wpCount = wpCount;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getWpCount() {
        return wpCount;
    }

    public double getDistance() {
        return distance;
    }
}
