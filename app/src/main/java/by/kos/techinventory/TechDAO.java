package by.kos.techinventory;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import java.util.List;

@Dao
interface TechDAO {

  @Insert
  Completable add(TechItem techItem);

  @Query("DELETE FROM tech WHERE id = :id")
  Completable remove(int id);

  @Query("SELECT * FROM tech")
  Single<List<TechItem>> getItems();

}
