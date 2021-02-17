package com.example.cwnu_diner;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchStoreActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SearchAdapter adapter;

    List<SearchData> storeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchstore);

        storeList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new SearchAdapter(storeList);


        //recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        storeList.add(new SearchData(R.drawable.ic_launcher_background,"abc","plz"));
        storeList.add(new SearchData(R.drawable.ic_launcher_background,"apple","plz"));
        storeList.add(new SearchData(R.drawable.ic_launcher_background,"abcd","plz"));
        storeList.add(new SearchData(R.drawable.ic_launcher_background,"applemango","plz"));

    }


}