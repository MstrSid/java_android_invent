package by.kos.techinventory;

import android.app.Application;
import android.os.Handler;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

  private TechDatabase techDatabase;

  public MainViewModel(@NonNull Application application) {
    super(application);
    techDatabase = TechDatabase.getInstance(application);
  }

  public void remove(TechItem techItem) {
    Thread thread = new Thread(() -> {
      techDatabase.techDAO().remove(techItem.getId());
    });
    thread.start();
  }

  public LiveData<List<TechItem>> getTechItems() {
    return techDatabase.techDAO().getItems();
  }
}
