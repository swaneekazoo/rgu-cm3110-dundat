package uk.ac.rgu.dundat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import uk.ac.rgu.dundat.data.Guide;
import uk.ac.rgu.dundat.data.GuideRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get 2 guides for testing
        List<Guide> guides = GuideRepository.getRepository(getApplicationContext()).getGuides();
        Log.d("CW1", String.valueOf(guides.size()));

        RecyclerView recyclerView = findViewById(R.id.rv_guideRecyclerView);
        RecyclerView.Adapter adapter = new GuideRecyclerViewAdapter(getApplicationContext(), guides);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}