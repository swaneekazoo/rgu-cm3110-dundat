package uk.ac.rgu.dundat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import uk.ac.rgu.dundat.data.Guide;
import uk.ac.rgu.dundat.data.GuideRepository;

public class MainActivity extends AppCompatActivity {
    // Debug tag
    private String dTag;

    // RecyclerView
    RecyclerView recyclerView;

    // Keys for storing instance state
    private final String KEY_LOCATION = "location";
    private final String KEY_DATE = "date";
    private final String KEY_MAXTEMP = "maxTemp";
    private final String KEY_MINTEMP = "minTemp";
    private final String KEY_WEATHER = "weather";

    // Intent Extras
    private static final String EXTRA_GUIDE_ID = "uk.ac.rgu.dundat.GUIDE_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.dTag = getString(R.string.d_tag);

        // Get 2 Guides for testing
        List<Guide> guides = GuideRepository.getRepository(getApplicationContext()).getGuides();
        Log.d(dTag, String.valueOf(guides.size()));

        // Set up RecyclerView with Adapter
        recyclerView = findViewById(R.id.rv_guideRecyclerView);
        RecyclerView.Adapter adapter = new GuideRecyclerViewAdapter(getApplicationContext(), guides);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}