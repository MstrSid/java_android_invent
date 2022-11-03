package by.kos.techinventory;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
    techDAO.add(techItem)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> {
          shouldCloseScreen.setValue(true);
        });

  }
}
