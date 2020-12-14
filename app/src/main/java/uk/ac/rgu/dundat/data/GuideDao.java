package uk.ac.rgu.dundat.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GuideDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Guide... g);

    @Delete
    void delete(Guide... g);

    @Query("SELECT * FROM guide ORDER BY title ASC")
    List<Guide> getAllDesc();

    @Query("SELECT * FROM guide ORDER BY title DESC")
    List<Guide> getAllAsc();

    @Query("SELECT * FROM guide WHERE title is :title")
    Guide findByTitle(String title);
}
