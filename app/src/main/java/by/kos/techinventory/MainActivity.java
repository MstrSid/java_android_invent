package by.kos.techinventory;

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
  private MainViewModel mainViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mainViewModel = new MainViewModel(getApplication());
    initViews();

    techRVAdapter = new TechRVAdapter();
    rcvMain.setAdapter(techRVAdapter);
    rcvMain.setLayoutManager(new LinearLayoutManager(this));
    mainViewModel.getTechItems().observe(this, new Observer<List<TechItem>>() {
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
            mainViewModel.remove(techItem);
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