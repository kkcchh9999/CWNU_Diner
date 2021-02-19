package com.example.cwnu_diner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class StoreClickActivity extends AppCompatActivity {

    TextView click;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storeclick);

        //툴바 달기
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        click = findViewById(R.id.click);

        Intent intent = getIntent();

        if(intent.getExtras() != null){
            SearchData data = (SearchData) intent.getSerializableExtra("data");

            click.setText(data.getName());
        }

    }
}
