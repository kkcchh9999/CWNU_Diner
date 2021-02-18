package com.example.cwnu_diner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchStoreActivity extends AppCompatActivity implements SearchAdapter.StoreClick {

    private SearchAdapter adapter;
    private List<SearchData> storeList;

    String[] auto_list={"one", "oneee", "two","onewwer"}; //일단

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchstore);

        //툴바 달기
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpRecyclerView();

    }

    /****************************************************
     리사이클러뷰, 어댑터 셋팅
     ***************************************************/
    private void setUpRecyclerView() {
        //recyclerview
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        //recyclerView.setHasFixedSize(true); //오류ㅠㅠ
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        //adapter
        storeList = new ArrayList<>(); //샘플테이터
        fillData();
        adapter = new SearchAdapter(storeList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL); //밑줄
        recyclerView.addItemDecoration(dividerItemDecoration);

        //데이터셋변경시
        //adapter.dataSetChanged(exampleList);

        //어댑터의 리스너 호출
        //이게 맞나 adapter.setOnClickListener((SearchAdapter.onItemListener) this);
    }

    private void fillData() {
        storeList = new ArrayList<>(); //샘플테이터
        storeList.add(new SearchData(R.drawable.ic_launcher_background, "Oneaa", "1"));
        storeList.add(new SearchData(R.drawable.ic_launcher_background, "Oneaad", "2"));
        storeList.add(new SearchData(R.drawable.ic_launcher_background, "Oneea", "3"));
        storeList.add(new SearchData(R.drawable.ic_launcher_background, "Oneae", "4"));
        storeList.add(new SearchData(R.drawable.ic_launcher_background, "Oneaawq", "5"));
        storeList.add(new SearchData(R.drawable.ic_launcher_background, "Oneaaq", "6"));
        storeList.add(new SearchData(R.drawable.ic_launcher_background, "Oneaafgraz", "7"));
        }

    /****************************************************
     onCreateOptionsMenu SearchView  기능구현
     ***************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);


        MenuItem searchItem = menu.findItem(R.id.bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        //SearchAutoComplete
        SearchView.SearchAutoComplete autoComplete = searchView.findViewById(R.id.search_src_text);
        ArrayAdapter<String> auto_adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, auto_list);
        autoComplete.setAdapter(auto_adapter);
        //

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void storeClick(SearchData searchData) {
        startActivity(new Intent(new Intent(SearchStoreActivity.this, StoreClickActivity.class).putExtra("data", searchData)));
    }
}