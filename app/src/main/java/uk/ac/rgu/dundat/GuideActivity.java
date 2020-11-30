package uk.ac.rgu.dundat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import uk.ac.rgu.dundat.data.Guide;
import uk.ac.rgu.dundat.data.GuideRepository;

public class GuideActivity extends AppCompatActivity {
    private Guide guide;
    // Convert to string resource
    private final String EXTRA_GUIDE_ID = "id";

    GuideRepository repository;

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
        updateGUI();
    }

    private void updateGUI() {
        TextView tv_guideView_title = findViewById(R.id.tv_guideView_title);
        tv_guideView_title.setText(guide.getTitle());
    }


}