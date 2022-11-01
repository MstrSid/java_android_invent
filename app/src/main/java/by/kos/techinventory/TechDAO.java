package by.kos.techinventory;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
interface TechDAO {

  @Insert
  void add(TechItem techItem);

  @Query("DELETE FROM tech WHERE id = :id")
  void remove(int id);

  @Query("SELECT * FROM tech")
  LiveData<List<TechItem>> getItems();

}
