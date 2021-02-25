package com.example.cwnu_diner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class AddReviewActivity extends AppCompatActivity {

    Button btn_reviewAdd;
    EditText et_review;
    RatingBar ratingBar;
    StoreData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        /////툴바 달기
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ///인텐트로 정보 받아오기
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            data = (StoreData) intent.getSerializableExtra("data");
        }

        ratingBar = (RatingBar)findViewById(R.id.ratingBar);

        btn_reviewAdd = (Button)findViewById(R.id.btn_reviewAdd);
        btn_reviewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    }
}