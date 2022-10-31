package by.kos.techinventory;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

  private RecyclerView rcvMain;
  private FloatingActionButton fabAdd;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initViews();

    fabAdd.setOnClickListener(view -> {
      startActivity(AddItemActivity.newIntent(MainActivity.this));
    });
  }

  private void initViews() {
    rcvMain = findViewById(R.id.rcvMain);
    fabAdd = findViewById(R.id.fabAdd);
  }
}