package dev.mrz3t4.dokiost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private EditText mSearchTextView;
    private ImageView mEmptyButton;
    private ImageView mVoiceSearchButton;

    private MaterialSearchView searchView;
    private Toolbar toolbar;

    private Search search;

    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.alpha_zero_to_one, R.anim.alpha_one_to_zero);
        setContentView(R.layout.activity_search);


        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview_result);
        searchView = findViewById(R.id.search_view);
        search = new Search(recyclerView, SearchActivity.this);

        setUpToolbar();


    }

    private void setUpToolbar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        searchView.setVoiceSearch(true);
        searchView.setBackgroundResource(R.drawable.toolbar_divider);
        searchView.showSearch(false);
        searchView.showVoice(true);
        searchView.setHint("Buscar soundtracks...");
        searchView.setVoiceIcon(ContextCompat.getDrawable(this, R.drawable.ic_voice));
        searchView.setBackIcon(ContextCompat.getDrawable(this, R.drawable.ic_back));
        searchView.setEllipsize(true);

        mEmptyButton =  searchView.findViewById(R.id.action_empty_btn);
        mSearchTextView = searchView.findViewById(R.id.searchTextView);
        ImageView mBackButton = searchView.findViewById(R.id.action_up_btn);
        mVoiceSearchButton = searchView.findViewById(R.id.action_voice_btn);

        mEmptyButton.setImageResource(R.drawable.close);
        mSearchTextView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.attr.colorOnSurface, typedValue, true);
        @ColorInt int color = typedValue.data;
        mSearchTextView.setTextColor(color);

        mVoiceSearchButton.setVisibility(View.VISIBLE);

        mSearchTextView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                System.out.println("XD: " + mSearchTextView.getText());
                search.getDataFromQuery(mSearchTextView.getText().toString());

                return true;
            }
            return false;
        });

        mEmptyButton.setOnClickListener(view -> {

            mVoiceSearchButton.setVisibility(View.VISIBLE);
            mEmptyButton.setVisibility(View.GONE);
            searchView.setQuery("",true);
            search.setRecyclerView(true);

        });

        mBackButton.setOnClickListener(v -> {
            onBackPressed();
            // overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_drawer, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView.setMenuItem(searchItem);

           return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
