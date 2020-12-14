package uk.ac.rgu.dundat.data;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Converters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static String arrayListToString(ArrayList<Waypoint> arrayList) {
        return gson.toJson(arrayList, new TypeToken<List<Waypoint>>(){}.getType());
    }

    @TypeConverter
    public static ArrayList<Waypoint> stringToArrayList(String string) {
        return gson.fromJson(string, new TypeToken<List<Waypoint>>(){}.getType());
    }
}
