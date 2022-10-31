package by.kos.techinventory;

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TechItem.class}, version = 1)
public abstract class TechDatabase extends RoomDatabase {

  private static TechDatabase instance = null;
  private static final String DB_NAME = "tech.db";

  public static TechDatabase getInstance(Application application) {
    if (instance == null) {
      instance = Room.databaseBuilder(application, TechDatabase.class, DB_NAME)
          .fallbackToDestructiveMigration().build();
    }
    return instance;
  }


  public abstract TechDAO techDAO();

}
