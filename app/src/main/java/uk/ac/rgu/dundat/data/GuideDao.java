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
    void insert(Guide g);

    @Delete
    void delete(Guide g);

    @Query("SELECT * FROM guide ORDER BY title asc")
    List<Guide> getAll();

//    @Query("SELECT * FROM guide WHERE id is id")
//    public Guide findByID(int id);
}
