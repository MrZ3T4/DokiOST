package dev.mrz3t4.dokiost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextInputEditText editText;
    private RecyclerView recyclerView;
    private NestedScrollView nestedScrollView;

    private Search search;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.result_textView);
        editText = findViewById(R.id.editText);
        recyclerView = findViewById(R.id.recyclerview);
        nestedScrollView = findViewById(R.id.nestedScrollView);

        search = new Search(recyclerView, textView, nestedScrollView, this);

        editTextListener();

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                if (!editText.getText().toString().isEmpty()){
                    nestedScrollView.animate().alpha(0f).setDuration(300).start();
                    search.getDataFromQuery(editText.getText().toString());
                    editText.clearFocus();
                    textView.setText("Resultados de " + "\"" + editText.getText() + "\"");

                } else {
                    Toast.makeText(MainActivity.this, "Campo vacio", Toast.LENGTH_SHORT).show();
                }
            }
            return false;
        });

    }

    private void editTextListener() {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0){
                   nestedScrollView.animate().alpha(0f).setDuration(300).start();
                   }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


}