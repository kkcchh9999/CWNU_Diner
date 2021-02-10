
package com.example.cwnu_diner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class SearchMenuActivity extends AppCompatActivity {

    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        settingList();

        final AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        autoCompleteTextView.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, list));


        ImageButton imageButton = findViewById(R.id.searchBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchMenuActivity.this, SearchStoreActivity.class);
                startActivity(intent);
            }
        });
    }


    private void settingList(){ //나중에는 xml 파일로? https://developer.android.com/training/keyboard-input/style?hl=ko
        list.add("mcdonald");
        list.add("subway");
        list.add("caffebene");
        list.add("oneplusone");
        list.add("caffeedco");
    }
}