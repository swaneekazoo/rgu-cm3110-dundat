package uk.ac.rgu.dundat.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

/**
 * An ArrayList of Waypoints with an associated title.
 * @author Adam Weir
 */
@Entity(tableName = "Guide")
public class Guide {
    @NonNull
    @PrimaryKey
    private String title;
    private ArrayList<Waypoint> waypoints;
//    private double distance;

    public Guide(String title, ArrayList<Waypoint> waypoints) {
        this.title = title;
        this.waypoints = waypoints;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Waypoint> getWaypoints() {
        return waypoints;
    }

    public int getWpCount() {
        return waypoints.size();
    }

    public double getDistance() {
        return 5.6;
    }
}
