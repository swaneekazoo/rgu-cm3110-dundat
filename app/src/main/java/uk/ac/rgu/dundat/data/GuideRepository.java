package uk.ac.rgu.dundat.data;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuideRepository {
    private static GuideRepository INSTANCE;
    private Context context;

    /**
     * A list of two {@link Guide}s
     */
    private List<Guide> guides;

    /**
     * Gets the singleton instance
     * @return The singleton instance
     */
    public static GuideRepository getRepository(Context context){
        if (INSTANCE == null){
            synchronized (GuideRepository.class) {
                if (INSTANCE == null)
                    INSTANCE = new GuideRepository();
                INSTANCE.context = context;
            }
        }
        return INSTANCE;
    }

    /**
     * Private constructor, initialises the list of guides with two
     */
    private GuideRepository() {
        guides = new ArrayList<>();
        ArrayList<Waypoint> waypoints = new ArrayList<>();
        waypoints.add(new Waypoint("Sycamore"));
        waypoints.add(new Waypoint("Shitting spot"));
        Guide guide1 = new Guide(1, "Stac Pollaidh", 5, 3.6);
        Guide guide2 = new Guide(2, "Lochnagar", 7, 6.2);
//        guide2.setWaypoints(waypoints);
        guides.add(guide1);
        guides.add(guide2);
    }

    /**
     * Returns two {@link Guide}s
     * @return two {@link Guide}s
     */
    public List<Guide> getGuides(){
        return guides;
    }

    /**
     * Returns {@link Guide} by ID
     */
    public Guide getGuideByID(int id) {
        for (Guide guide : guides) {
            if (guide.getId() == id) {
                return guide;
            }
        }
        return guides.get(0);
    }
}
