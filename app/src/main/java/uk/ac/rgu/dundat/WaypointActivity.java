package uk.ac.rgu.dundat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import uk.ac.rgu.dundat.data.Guide;
import uk.ac.rgu.dundat.data.GuideRepository;
import uk.ac.rgu.dundat.data.Waypoint;

public class WaypointActivity extends AppCompatActivity implements OnMapReadyCallback {
    // Repository
    private GuideRepository guideRepository;
    // Data corresponding to this activity
    private Guide guide;
    private Waypoint waypoint;
    // Intent extras
    private String EXTRA_TITLE;
    // Map
    private GoogleMap mMap;

    private void updateGUI() {
        TextView tv_secret_name = findViewById(R.id.tv_secret_name);
        TextView tv_secret_message = findViewById(R.id.tv_secret_message);

        tv_secret_name.setText(waypoint.getName());
        tv_secret_message.setText(waypoint.getSecret());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waypoint);

        // Repository
        guideRepository = GuideRepository.getInstance(getApplicationContext());

        // Intent that launched this activity
        Intent launcher = getIntent();

        // Guide and Waypoint the Intent refers to
        EXTRA_TITLE = getString(R.string.EXTRA_TITLE);
        if (launcher.hasExtra(EXTRA_TITLE)) {
            String title = launcher.getStringExtra(EXTRA_TITLE);
            guide = guideRepository.dbFindByTitle(title);
            waypoint = guide.getWaypoints().get(0);
        }

        // Set up Map
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.frag_guide_map);
        mapFragment.getMapAsync(this);

        updateGUI();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        // Add marker for Waypoint
        mMap.addMarker(new MarkerOptions().position(waypoint.getLatLng()).title(waypoint.getName()));

        // Move camera to pin
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(waypoint.getLatLng(), 18));
    }
}