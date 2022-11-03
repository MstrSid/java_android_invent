package by.kos.techinventory;

import android.app.Application;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

  private final TechDatabase techDatabase;
  private final CompositeDisposable compositeDisposable = new CompositeDisposable();
  private MutableLiveData<List<TechItem>> techItems = new MutableLiveData<>();

  public MainViewModel(@NonNull Application application) {
    super(application);
    techDatabase = TechDatabase.getInstance(application);
  }

  public void refreshList() {
    Disposable disposable = getItemsRx().subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribe(techItemsFromDB -> {
          techItems.setValue(techItemsFromDB);
        });

    compositeDisposable.add(disposable);
  }

  private Single<List<TechItem>> getItemsRx() {
    return Single.fromCallable(() -> techDatabase.techDAO().getItems());
  }

  public void remove(TechItem techItem) {
    Disposable disposable = removeRx(techItem)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> {
          Log.d("mainViewModel",
              String.format("removed: %s, %s", techItem.getName(), techItem.getInvent()));
          refreshList();
        });
    compositeDisposable.add(disposable);
  }

  private Completable removeRx(TechItem item) {
    return Completable.fromAction(() -> {
      techDatabase.techDAO().remove(item.getId());
    });
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    compositeDisposable.dispose();
  }


  public LiveData<List<TechItem>> getTechItems() {
    return techItems;
  }
}
