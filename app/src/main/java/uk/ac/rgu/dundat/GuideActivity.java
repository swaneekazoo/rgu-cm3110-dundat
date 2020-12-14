package uk.ac.rgu.dundat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;

import uk.ac.rgu.dundat.data.Guide;
import uk.ac.rgu.dundat.data.GuideRepository;
import uk.ac.rgu.dundat.data.Waypoint;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    // Data
    private GuideRepository guideRepository;
    private Guide guide;
    // Intent extras
    private String EXTRA_TITLE;
    // Map
    private GoogleMap mMap;

    /**
     * Get the total distance between all {@link Waypoint}s in the member {@link Guide}.
     * @return The total distance between all {@link Waypoint}s in the member {@link Guide}
     */
    private String getDistance() {
        double distance = 0d;
        LatLng latLngPrevious = null;
        for (LatLng latLngCurrent: getLatLngs()) {
            if (latLngPrevious != null) {
                distance += SphericalUtil.computeDistanceBetween(latLngPrevious, latLngCurrent);
            }
            latLngPrevious = latLngCurrent;
        }
        return new DecimalFormat("#.#").format(toMiles(distance));
    }

    /**
     * Get all {@link Waypoint} coordinates as an {@link ArrayList} of {@link LatLng} objects.
     * @return All {@link Waypoint} coordinates as an {@link ArrayList} of {@link LatLng} objects
     */
    private ArrayList<LatLng> getLatLngs() {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        for (Waypoint w: guide.getWaypoints()) {
            latLngs.add(w.getLatLng());
        }
        return latLngs;
    }

    /**
     * Convert distance in metres to miles (approximately).
     * @param metres Distance in metres
     * @return Distance in miles
     */
    private double toMiles(double metres) {
        return metres / 1609;
    }

    @SuppressLint("StringFormatMatches")
    private void updateGUI() {
        // Get Views
        TextView tv_guide_title = findViewById(R.id.tv_guide_title);
        TextView tv_guide_waypoints = findViewById(R.id.tv_guide_waypoints);
        TextView tv_guide_distance = findViewById(R.id.tv_guide_distance);
        TextView tv_guide_wpNext_name = findViewById(R.id.tv_guide_wpNext_name);
        TextView tv_guide_wpNext_distance = findViewById(R.id.tv_guide_wpNext_distance);

        // Update Views
        tv_guide_title.setText(guide.getTitle());
        tv_guide_waypoints.setText(getString(R.string.tv_guide_wpCount, guide.getWpCount()));
        tv_guide_distance.setText(getString(R.string.tv_guide_distance, getDistance()));
        tv_guide_wpNext_name.setText(guide.getWaypoints().get(0).getName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        // Repository
        guideRepository = GuideRepository.getInstance(getApplicationContext());

        // Intent that launched this activity
        Intent launcher = getIntent();

        // Guide the Intent refers to
        EXTRA_TITLE = getString(R.string.EXTRA_TITLE);
        if (launcher.hasExtra(EXTRA_TITLE)) {
            String title = launcher.getStringExtra(EXTRA_TITLE);
            guide = guideRepository.dbFindByTitle(title);
        }

        // Set on-click listeners
        Button btn_map = findViewById(R.id.btn_guide_map);
        Button btn_secret = findViewById(R.id.btn_guide_secret);
        Button btn_delete = findViewById(R.id.btn_guide_delete);
        btn_map.setOnClickListener(this);
        btn_secret.setOnClickListener(this);
        btn_delete.setOnClickListener(this);

        // Set up Map
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.frag_guide_map);
        mapFragment.getMapAsync(this);

        updateGUI();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        // Map
        if (v.getId() == R.id.btn_guide_map) {
            Uri uri = guide.getWaypoints().get(0).getUri();
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        // Secret
        else if (v.getId() == R.id.btn_guide_secret) {
            intent = new Intent(this, WaypointActivity.class);
            intent.putExtra(EXTRA_TITLE, guide.getTitle());
            startActivity(intent);
        }
        // Delete
        else if (v.getId() == R.id.btn_guide_delete) {
            guideRepository.dbDelete(guide);
            finish();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        // Add markers for each Waypoint, and polylines between them
        ArrayList<Waypoint> waypoints = guide.getWaypoints();
        LatLngBounds.Builder builder = LatLngBounds.builder();
        LatLng latLngPrevious = null;
        for (Waypoint w: waypoints) {
            LatLng latLngCurrent = w.getLatLng();
            mMap.addMarker(new MarkerOptions().position(latLngCurrent).title(w.getName()));
            builder.include(latLngCurrent);
            if (latLngPrevious != null) {
                mMap.addPolyline(new PolylineOptions()
                .clickable(false)
                .color(R.color.design_default_color_primary)
                .add(latLngPrevious)
                .add(latLngCurrent));
            }
            latLngPrevious = latLngCurrent;
        }
        LatLngBounds bounds = builder.build();

        // Move camera to contain all pins
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 500, 500, 40));
    }
}