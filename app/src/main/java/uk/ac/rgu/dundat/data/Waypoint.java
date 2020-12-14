package uk.ac.rgu.dundat.data;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

/**
 * A set of map coordinates with an associated name and message.
 * @author Adam Weir
 */
public class Waypoint {
    private String name;
    private String secret;
    private double latitude, longitude;

    public Waypoint(String name, double latitude, double longitude, String secret) {
        this.name = name;
        this.secret = secret;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getSecret() {
        return secret;
    }

    /**
     * Get a {@link LatLng} object for this {@link Waypoint}'s coordinates, for placing a Google Maps pin.
     * @return a {@link LatLng} object for this {@link Waypoint}'s coordinates
     */
    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    /**
     * Get a geo URI for this {@link Waypoint}'s coordinates with named pin label, for launching a Google Maps {@link android.content.Intent}.
     * @return A geo URI for this {@link Waypoint} coordinates
     */
    public Uri getUri() {
        String uriString = new StringBuilder().append("geo:")
                .append(latitude).append(",").append(longitude)
                .append("?q=")
                .append(latitude).append(",").append(longitude)
                // Pin label
                .append("(").append(name).append(")")
                .toString();
        return Uri.parse(uriString);
    }
}
