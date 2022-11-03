package by.kos.techinventory;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

  private final TechDatabase techDatabase;
  private final CompositeDisposable compositeDisposable = new CompositeDisposable();

  public MainViewModel(@NonNull Application application) {
    super(application);
    techDatabase = TechDatabase.getInstance(application);
  }

  public void remove(TechItem techItem) {
    Disposable disposable = techDatabase.techDAO().remove(techItem.getId())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> {
          Log.d("mainViewModel",
              String.format("removed: %s, %s", techItem.getName(), techItem.getInvent()));
        }, throwable -> {
          Log.d("MainViewModel", "Error removing item");
        });
    compositeDisposable.add(disposable);
  }

  public LiveData<List<TechItem>> getTechItems() {
    return techDatabase.techDAO().getItems();
  }
}
