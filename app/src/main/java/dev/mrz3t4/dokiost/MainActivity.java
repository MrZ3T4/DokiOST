package dev.mrz3t4.dokiost;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView search, settings;

    private Toolbar toolbar;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.toolbar_text_hint);
        search = findViewById(R.id.toolbar_search_btn);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        onClickSearch();

    }

    private void onClickSearch() {

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });


    }


}