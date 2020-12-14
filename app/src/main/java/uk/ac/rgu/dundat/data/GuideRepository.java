package uk.ac.rgu.dundat.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Provides a SPOT for {@link Guide}s in the app.
 * @author Adam Weir
 */
public class GuideRepository {
    // Singleton instance
    private static GuideRepository INSTANCE;
    private Context context;
    // Data Access Object
    private GuideDao dao;

    /**
     * Gets the singleton instance for the Repository.
     * @return The singleton instance
     */
    public static GuideRepository getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (GuideRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GuideRepository();
                    INSTANCE.context = context;
                    INSTANCE.dao = DunDatabase.getInstance(context).guideDao();
                    populateDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Read {@code guide} JSON file from {@code res}, parse JSON array into a {@link List} of {@link Guide}s, and insert Guides into the database.
     */
    private static void populateDatabase(Context context) {
        Gson gson = new Gson();

        // Request URL
        String url = "https://cm3110.firebaseio.com/.json";

        // Request
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String result = response;

                        // Convert JSON to List of Guides
                        ArrayList<Guide> guides = gson.fromJson(result, new TypeToken<List<Guide>>() {}.getType());

                        // Insert Guides into Database
                        for (Guide g : guides) {
                            INSTANCE.dao.insert(g);
                            Log.d("DEBUG", "Inserted: " + g.getTitle());
                            Log.d("DEBUG", g.getWaypoints().toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        // Make request
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public void dbInsert(Guide... g) {
        dao.insert(g);
    }

    public void dbDelete(Guide... g) {
        dao.delete(g);
    }

    public List<Guide> dbGetAllAsc() {
        return dao.getAllAsc();
    }

    public List<Guide> dbGetAllDesc() {
        return dao.getAllDesc();
    }

    public Guide dbFindByTitle(String title) {
        return dao.findByTitle(title);
    }
}
