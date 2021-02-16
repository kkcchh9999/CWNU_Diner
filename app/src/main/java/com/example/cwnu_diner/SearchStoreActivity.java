package com.example.cwnu_diner;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class SearchStoreActivity extends Activity {
    SearchAdapter adapter;
    ListView listview = null;
    ArrayAdapter<String> search_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchstore);


        adapter = new SearchAdapter();

        listview=(ListView)findViewById(R.id.search_listView);
        listview.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.b), "b", "bbbb");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.b), "bana", "bb");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.a), "apple", "aa");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.a), "app", "aaaaa");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.c), "cherry", "c");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.c), "cherrddy", "ccc");

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int pos, long id) {
                ListViewItem item = (ListViewItem)parent.getItemAtPosition(pos);

                String titleStr = item.getName();
                String descStr = item.getText();
                Drawable iconDrawable = item.getIcon();

            }
        });

        EditText editTextFilter = (EditText)findViewById(R.id.editTextFilter) ;
        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            SearchStoreActivity.this.search_adapter.getFilter().filter(s);
            }
        });

    }

}