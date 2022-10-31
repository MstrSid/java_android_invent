package by.kos.techinventory;

import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddItemActivity extends AppCompatActivity {

  private Spinner spCondition;
  private TextInputLayout tilName;
  private TextInputEditText tiEdtName;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_item);

    initViews();

    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item,
        getResources().getStringArray(R.array.conditions));
    spCondition.setAdapter(adapter);

  }

  private void initViews() {
    spCondition = findViewById(R.id.spCondition);
    tilName = findViewById(R.id.tilName);
    tiEdtName = findViewById(R.id.tiEdtName);
  }

  public static Intent newIntent(Context context) {
    return new Intent(context, AddItemActivity.class);
  }
}