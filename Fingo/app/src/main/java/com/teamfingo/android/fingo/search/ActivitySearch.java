package com.teamfingo.android.fingo.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.teamfingo.android.fingo.R;
import com.teamfingo.android.fingo.model.SearchMovie;
import com.teamfingo.android.fingo.utils.AppController;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySearch extends AppCompatActivity {

    Toolbar searchToolbar;

    RecyclerView mSearchRecyclerView;
    RecyclerAdapterSearch mRecyclerAdapterSearch;
    EditText mToolbarEditText;

    ArrayList<SearchMovie> mSearchMovies = new ArrayList<>();

    String searchWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        mSearchRecyclerView = (RecyclerView) findViewById(R.id.search_recyclerView);
        mToolbarEditText = (EditText) findViewById(R.id.editText_toolbar);

        mRecyclerAdapterSearch = new RecyclerAdapterSearch(this, mSearchMovies, R.layout.item_search_list);
        mSearchRecyclerView.setAdapter(mRecyclerAdapterSearch);
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(ActivitySearch.this));

        mToolbarEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 검색 버튼을 누를 때마다 새로 검색
                        mSearchMovies.clear();                                                                                                  

                        searchWord = mToolbarEditText.getText().toString();

                        Call<ArrayList<SearchMovie>> searchMovieCall = AppController.getFingoService()
                                .getSearchMovie(AppController.getToken(), searchWord);

                        searchMovieCall.enqueue(new Callback<ArrayList<SearchMovie>>() {
                            @Override
                            public void onResponse(Call<ArrayList<SearchMovie>> call, Response<ArrayList<SearchMovie>> response) {
                                ArrayList<SearchMovie> data = response.body();
                                mSearchMovies.addAll(data);

                                mRecyclerAdapterSearch.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<ArrayList<SearchMovie>> call, Throwable t) {

                            }
                        });
                        break;
                }
                return true;
            }
        });

    }

}
