package com.example.cwnu_diner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class GMail extends AppCompatActivity {

    private ImageButton btn_apply; //전송버튼
    private EditText mail_recipient; //받는사람
    private EditText text_title; //메일제목
    //private EditText text_reference; //참조파일
    private EditText text_content; //메일내용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gmail_send);

        btn_apply = findViewById(R.id.btn_apply);
        mail_recipient = findViewById(R.id.mail_recipient);
        text_title = findViewById(R.id.text_title);
        //text_reference = findViewById(R.id.text_reference);
        text_content = findViewById(R.id.text_content);
    }
}