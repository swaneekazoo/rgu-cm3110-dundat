package uk.ac.rgu.dundat.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class GuideRepository {

    /**
     * The Singleton instance for this repository
     */
    private static GuideRepository INSTANCE;

    /**
     * The Context that the app is operating within
     */
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
        Guide guide1 = new Guide("Stac Pollaidh", 5, 3.6);
        Guide guide2 = new Guide("Lochnagar", 7, 6.2);
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
}
