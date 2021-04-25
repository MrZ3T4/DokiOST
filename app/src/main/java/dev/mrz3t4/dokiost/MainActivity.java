package dev.mrz3t4.dokiost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout textInputLayout;
    private TextInputEditText editText;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        recyclerView = findViewById(R.id.recyclerview);

        Search search = new Search(recyclerView, this);


        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                if (!editText.getText().toString().isEmpty()){
                    recyclerView.animate().alpha(0f).setDuration(300).start();
                search.getDataFromQuery(editText.getText().toString());
                } else {
                    Toast.makeText(MainActivity.this, "Campo vacio", Toast.LENGTH_SHORT).show();
                }
            }
            return false;
        });

    }

}