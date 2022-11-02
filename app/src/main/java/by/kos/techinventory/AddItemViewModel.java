package by.kos.techinventory;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AddItemViewModel extends AndroidViewModel {

  public LiveData<Boolean> getShouldCloseScreen() {
    return shouldCloseScreen;
  }

  private TechDAO techDAO;
  private MutableLiveData<Boolean> shouldCloseScreen = new MutableLiveData<>();

  public AddItemViewModel(@NonNull Application application) {
    super(application);
    techDAO = TechDatabase.getInstance(application).techDAO();
  }

  public void saveTech(TechItem techItem) {
    Thread thread = new Thread(() -> {
      techDAO.add(techItem);
      shouldCloseScreen.postValue(true);
    });
    thread.start();
  }
}
