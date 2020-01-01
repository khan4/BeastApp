package com.example.beastandroid;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beastandroid.di.Resource;
import com.example.beastandroid.di.SearchResource;
import com.example.beastandroid.factory.ViewModelProviderFactory;
import com.example.beastandroid.model.Feed;
import com.example.beastandroid.model.User;
import com.example.beastandroid.model.Video;
import com.example.beastandroid.networking.SearchApi;
import com.example.beastandroid.networking.SearchData;
import com.example.beastandroid.recyclerview.RecyclerViewAdapter;
import com.example.beastandroid.viewmodels.MainViewModel;
import com.example.beastandroid.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Flowable;

public class MainActivity extends DaggerAppCompatActivity implements View.OnClickListener, RecyclerViewAdapter.ClickListener {

    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private SearchView searchView;
    private ImageView imageLogo, imgSearch;

    private ProgressBar progressBar;
    private SearchViewModel searchViewModel;
    private MainViewModel viewModel;
    private TextView tvInValidQuery;
    private List<User> userList;
    //Initilize Recycler Views
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        //views

        searchView = findViewById(R.id.searchBar);
        imageLogo = findViewById(R.id.imagelogo);
        imgSearch = findViewById(R.id.imageSearch);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        tvInValidQuery = findViewById(R.id.tvInValidQuery);

        //Click Listeners On buttons



        //Visibility
        tvInValidQuery.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        searchView.setVisibility(View.INVISIBLE);


        //Click listeners
        imgSearch.setOnClickListener(this);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (searchView.getQuery().toString().isEmpty()) {
                    searchView.setVisibility(View.INVISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        //View model Data and
        viewModel = ViewModelProviders.of(this, providerFactory).get(MainViewModel.class);
        searchViewModel = ViewModelProviders.of(this,providerFactory).get(SearchViewModel.class);

        subscribeObserver();
        suscribeSearchObserver();
        searchViewListening();

        viewModel.cacheData(1);

    }

    private void searchViewListening(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                searchViewModel.searchQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    private void subscribeObserver(){

        viewModel.observeData().observe(this, new Observer<Resource<Feed>>() {
            @Override
            public void onChanged(Resource<Feed> feedResource) {

                if (feedResource!=null){
                    switch (feedResource.status){

                        case LOADING:
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        case SUCCESS:
                            progressBar.setVisibility(View.INVISIBLE);
                            tvInValidQuery.setVisibility(View.INVISIBLE);
                            userList = feedResource.data.getFeedvideoList();
                            initRecyclerView(feedResource.data.getFeedvideoList());
                            break;
                        case ERROR:
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this, "Check internet connection", Toast.LENGTH_SHORT).show();
                            break;

                    }
                }

            }
        });

    }

    private void suscribeSearchObserver(){

        Log.d(TAG, "suscribeSearchObserver: 1st");
        searchViewModel.observeSearchData().observe(this, new Observer<SearchResource<Feed>>() {
            @Override
            public void onChanged(SearchResource<Feed> feedSearchResource) {

                if (feedSearchResource!=null){

                    switch (feedSearchResource.status){

                        case LOADING:
                            progressBar.setVisibility(View.VISIBLE);
                            break;

                        case SUCCESS:
                            progressBar.setVisibility(View.INVISIBLE);

                            if (feedSearchResource.data.getFeedvideoList().size()<=0){
                                tvInValidQuery.setVisibility(View.VISIBLE);
                                tvInValidQuery.setText("Make Sure All the words Spell properly or try some thing else");

                            }
                            else {
                                tvInValidQuery.setVisibility(View.INVISIBLE);
                            }
                            userList = feedSearchResource.data.getFeedvideoList();
                            initRecyclerView(feedSearchResource.data.getFeedvideoList());
                            break;

                        case ERROR:
                            Log.d(TAG, "onChanged: Search Error");
                            break;


                    }
                }

            }
        });
    }



    private void initRecyclerView(List<User> userList){
        Log.d(TAG, "initRecyclerView: This is outside of Recycler View");
        if (userList!=null) {

            adapter = new RecyclerViewAdapter(this,userList,this);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
           // adapter.setUserList(userList);
            recyclerView.setAdapter(adapter);
        }
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.imageSearch:
                toolbar.setVisibility(View.INVISIBLE);
                searchView.setVisibility(View.VISIBLE);
                searchView.setFocusable(true);
                searchView.setIconified(false);
                searchView.requestFocusFromTouch();
                break;


        }

    }

    @Override
    public void btnNextClickListener(int pageNo) {

        viewModel.cacheData(pageNo);
    }

    @Override
    public void btnBackClickListener(int pageNo) {

        viewModel.cacheData(pageNo);

    }

    @Override
    public void itemClickListener(int position) {

        if (userList!=null){

            User user = userList.get(position);
            Intent intent = new Intent(this,VideoActivity.class);
            intent.putExtra("User",user);
            startActivity(intent);
           // intent.put(user);

        }

    }

    @Override
    public void onBackPressed() {
        if (searchView.getVisibility() == View.VISIBLE){
            searchView.setVisibility(View.INVISIBLE);
            toolbar.setVisibility(View.VISIBLE);
        }
        else {
            super.onBackPressed();
        }

    }
}
