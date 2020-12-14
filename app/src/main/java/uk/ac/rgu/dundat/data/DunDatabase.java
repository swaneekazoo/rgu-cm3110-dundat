package uk.ac.rgu.dundat.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Database(entities = {Guide.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class DunDatabase extends RoomDatabase {
    public abstract GuideDao guideDao();
    private static DunDatabase INSTANCE = null;

    public static DunDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (DunDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                                    DunDatabase.class, "database")
                                    .fallbackToDestructiveMigration()
                                    .allowMainThreadQueries()
                                    .build();
                }
            }
        }
        return INSTANCE;
    }
}
