package uk.ac.rgu.dundat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.List;

import uk.ac.rgu.dundat.data.Guide;
import uk.ac.rgu.dundat.data.GuideRepository;

public class MainActivity extends AppCompatActivity {
    // Debug tag
    private String dTag;
    // SharedPreferences
    private SharedPreferences sharedPreferences;
    // Data
    private GuideRepository guideRepository;
    private List<Guide> guides;
    // RecyclerView
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Debug tag
        this.dTag = getString(R.string.D_TAG);

        Spinner spinner = findViewById(R.id.sp_main_order);

        // SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREF_FILE_NAME), MODE_PRIVATE);
        int ascending = sharedPreferences.getInt(getString(R.string.pref_key_listAscending), 0);
        spinner.setSelection(ascending);


        // Repository
        guideRepository = GuideRepository.getInstance(getApplicationContext());

        // Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] items = getResources().getStringArray(R.array.sp_main_order);
                int selectedIndex = getListOrder();
                String selectedItem = items[selectedIndex];
                if (selectedIndex == 0) {
                    updateRecyclerView(false);
                } else {
                    updateRecyclerView(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Set up RecyclerView with Adapter
        updateRecyclerView(true);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREF_FILE_NAME), MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        String pref_key_listOrder = getString(R.string.pref_key_listAscending);
        int pref_value_listOrder = getListOrder();
        prefsEditor.putInt(pref_key_listOrder, pref_value_listOrder);
        prefsEditor.apply();

        Log.d(dTag, "Preferences saved.");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh after deleting a Guide
        updateRecyclerView(true);
    }

    /**
     * Get the data and update the RecyclerView.
     */
    private void updateRecyclerView(Boolean ascending) {
        guides = ascending ? guideRepository.dbGetAllAsc() : guideRepository.dbGetAllDesc();
        recyclerView = findViewById(R.id.rv_guideRecyclerView);
        RecyclerView.Adapter adapter = new GuideRecyclerViewAdapter(getApplicationContext(), guides);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private int getListOrder() {
        Spinner spinner = findViewById(R.id.sp_main_order);
        int selectedIndex = spinner.getSelectedItemPosition();
        String[] items = getResources().getStringArray(R.array.sp_main_order);
        return selectedIndex;
    }
}