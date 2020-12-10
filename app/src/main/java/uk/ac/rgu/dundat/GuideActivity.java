package uk.ac.rgu.dundat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uk.ac.rgu.dundat.data.Guide;
import uk.ac.rgu.dundat.data.GuideRepository;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
    GuideRepository repository;
    private Guide guide;
    // Convert to string resource
    private final String EXTRA_GUIDE_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        repository = GuideRepository.getRepository(getApplicationContext());

        // Intent that launched this activity
        Intent launcher = getIntent();

        // Update from Intent
        if (launcher.hasExtra("id")) {
            int id = launcher.getIntExtra(EXTRA_GUIDE_ID, 0);
            guide = repository.getGuideByID(id);
        }

        // Set on-click listeners
        Button btn_map = findViewById(R.id.btn_guideView_map);
        btn_map.setOnClickListener(this);

//        guide = repository.getGuideByID(2);
        updateGUI();
    }

    @SuppressLint("StringFormatMatches")
    private void updateGUI() {
        // Get Views
        TextView tv_guideView_title = findViewById(R.id.tv_guideView_title);
        TextView tv_guideView_waypoints = findViewById(R.id.tv_guideView_waypoints);
        TextView tv_guideView_distance = findViewById(R.id.tv_guideView_distance);
        // Update Views
        tv_guideView_title.setText(guide.getTitle());
        tv_guideView_waypoints.setText(getString(R.string.tv_guideView_wpCount, guide.getWpCount()));
        tv_guideView_distance.setText(getString(R.string.tv_guideView_distance, guide.getDistance()));
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Uri uri = Uri.parse("geo:37.7749,-122.4194");
        Log.d("Dundat", "Clicd da butin");
        if (v.getId() == R.id.btn_guideView_map) {
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}