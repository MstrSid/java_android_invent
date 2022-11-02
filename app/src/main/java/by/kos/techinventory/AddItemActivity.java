package by.kos.techinventory;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {

  private Spinner spCondition;
  private TextInputLayout tilName;
  private TextInputEditText tiEdtName;
  private TextInputLayout tilInvent;
  private TextInputEditText tiEdtInvent;

  private Button btnAdd;
  private AddItemViewModel addItemViewModel;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_item);
    addItemViewModel = new ViewModelProvider(this).get(AddItemViewModel.class);
    addItemViewModel.getShouldCloseScreen().observe(this, shouldClose -> {
      if (shouldClose) {
        finish();
      }
    });
    initViews();

    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item,
        getResources().getStringArray(R.array.conditions));
    spCondition.setAdapter(adapter);

    btnAdd.setOnClickListener(view -> {
      saveTech(view);
    });
  }

  private int getCondition() {
    return spCondition.getSelectedItemPosition();
  }

  private void saveTech(View view) {
    String textName = tiEdtName.getText().toString();
    String textInvent = tiEdtInvent.getText().toString();
    if (!textName.isEmpty() && !textInvent.isEmpty()) {
      int conditionIndex = getCondition();
      addItemViewModel.saveTech(new TechItem(textName, textInvent, conditionIndex));
    } else {
      Snackbar.make(view, R.string.txt_error_empty, Snackbar.LENGTH_SHORT).show();
    }
  }

  private void initViews() {
    spCondition = findViewById(R.id.spCondition);
    tilName = findViewById(R.id.tilName);
    tiEdtName = findViewById(R.id.tiEdtName);
    btnAdd = findViewById(R.id.btnAdd);
    tilInvent = findViewById(R.id.tilInvent);
    tiEdtInvent = findViewById(R.id.tiEdtInvent);
  }

  public static Intent newIntent(Context context) {
    return new Intent(context, AddItemActivity.class);
  }
}