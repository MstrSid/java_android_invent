package by.kos.techinventory;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private RecyclerView rcvMain;
  private FloatingActionButton fabAdd;
  private TechRVAdapter techRVAdapter;
  private TechDatabase techDatabase;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    techDatabase = TechDatabase.getInstance(getApplication());
    initViews();

    techRVAdapter = new TechRVAdapter();
    rcvMain.setAdapter(techRVAdapter);
    rcvMain.setLayoutManager(new LinearLayoutManager(this));
    techDatabase.techDAO().getItems().observe(this, new Observer<List<TechItem>>() {
      @Override
      public void onChanged(List<TechItem> techItems) {
        techRVAdapter.setTechItems(techItems);
        techRVAdapter.notifyDataSetChanged();
      }
    });

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
        new SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
          @Override
          public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder,
              @NonNull ViewHolder target) {
            return false;
          }

          @Override
          public void onSwiped(@NonNull ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            TechItem techItem = techRVAdapter.getTechItems().get(position);
            Thread thread = new Thread(() -> {
              techDatabase.techDAO().remove(techItem.getId());
            });
            thread.start();
          }
        });

    itemTouchHelper.attachToRecyclerView(rcvMain);

    fabAdd.setOnClickListener(view -> {
      startActivity(AddItemActivity.newIntent(MainActivity.this));
    });
  }

  private void initViews() {
    rcvMain = findViewById(R.id.rcvMain);
    fabAdd = findViewById(R.id.fabAdd);
  }
}