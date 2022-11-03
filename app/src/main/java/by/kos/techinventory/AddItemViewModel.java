package by.kos.techinventory;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddItemViewModel extends AndroidViewModel {

  public LiveData<Boolean> getShouldCloseScreen() {
    return shouldCloseScreen;
  }

  private final TechDAO techDAO;
  private final MutableLiveData<Boolean> shouldCloseScreen = new MutableLiveData<>();
  private final CompositeDisposable compositeDisposable = new CompositeDisposable();

  public AddItemViewModel(@NonNull Application application) {
    super(application);
    techDAO = TechDatabase.getInstance(application).techDAO();
  }

  public void saveTech(TechItem techItem) {
    Disposable disposable = saveTechRx(techItem).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> shouldCloseScreen.setValue(true));
    compositeDisposable.add(disposable);
  }

  private Completable saveTechRx(TechItem item) {
    return Completable.fromAction(() -> {
      techDAO.add(item);
    });
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    compositeDisposable.dispose();
  }
}
