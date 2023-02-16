package com.vicky.testmvvmretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.vicky.testmvvmretrofit.Repository.ResultModel;
import com.vicky.testmvvmretrofit.adapter.AdapterClass;
import com.vicky.testmvvmretrofit.viewmodel.RetroViewHolder;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<ResultModel> litresult;
    private SwipeRefreshLayout swipeRefreshLayout;
    SearchView searchView;
    RetroViewHolder retroViewHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retroViewHolder = ViewModelProviders.of(MainActivity.this).get(RetroViewHolder.class);

        initViews();
        setAdapter();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {@Override
        public void onRefresh() {

            //Changing the text when refresh
            retroViewHolder.getRetrofitListObservable().observe(MainActivity.this, new Observer<List<ResultModel>>() {
                @Override
                public void onChanged(List<ResultModel> resultModels) {
                    litresult = resultModels;
                    adapter.setWords(resultModels);
                }
            });

            //setting Refreshing to false
            swipeRefreshLayout.setRefreshing(false);

        }
        });
        retroViewHolder.getRetrofitListObservable().observe(this, new Observer<List<ResultModel>>() {
            @Override
            public void onChanged(List<ResultModel> resultModels) {
                litresult = resultModels;
                adapter.setWords(resultModels);
            }
        });
    }



    private void setAdapter() {
        adapter = new AdapterClass(this,litresult);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

       searchView.setActivated(true);
        searchView.setQueryHint("Search with title and year");
       searchView.onActionViewExpanded();
        searchView.setIconified(false);
       searchView.clearFocus();
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               adapter.getFilter().filter(newText);
               System.out.println("fgfgfgfgffg"+newText);
               if(newText.equalsIgnoreCase("")){
                   adapter = new AdapterClass(MainActivity.this,litresult);
                   recyclerView.setAdapter(adapter);
                   recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
               }
               return false;
           }
       });
    }

    RecyclerView recyclerView;
    private void initViews()
    {
        recyclerView = findViewById(R.id.recyclerview);
        searchView=findViewById(R.id.search);
        swipeRefreshLayout = ( SwipeRefreshLayout ) findViewById ( R.id.swipe) ;
    }

    AdapterClass adapter  = null;

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView = (SearchView) menu.findItem(R.id.action_search)
//                .getActionView();
//        searchView.setSearchableInfo(searchManager
//                .getSearchableInfo(getComponentName()));
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                adapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                adapter.getFilter().filter(query);
//                return false;
//            }
//        });
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_search) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}