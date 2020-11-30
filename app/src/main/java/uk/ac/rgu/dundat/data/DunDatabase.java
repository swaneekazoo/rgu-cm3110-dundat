package uk.ac.rgu.dundat.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Guide.class, version = 1)
public abstract class DunDatabase extends RoomDatabase {
    public abstract GuideDao guideDao();
    private static DunDatabase INSTANCE;

    public static DunDatabase getInstance(final Context context){
        if (INSTANCE == null){
            synchronized (DunDatabase.class){
                if (INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
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
