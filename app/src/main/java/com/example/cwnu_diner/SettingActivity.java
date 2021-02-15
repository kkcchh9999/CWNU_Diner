package com.example.cwnu_diner;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.bumptech.glide.Glide;

public class SettingActivity extends AppCompatActivity {

    private ImageView image_info;
    private TextView txt_info;

    private Switch switch_dark;
    private Button btn_logout;
    //String themeColor;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();
        String nickname = intent.getStringExtra("nickname" );
        String photoUrl = intent.getStringExtra("photoUrl" );

        txt_info = findViewById(R.id.txt_info);
        txt_info.setText(nickname);

        image_info = findViewById(R.id.image_info);
        Glide.with(this).load(photoUrl).into(image_info);

        //로그아웃버튼 누르면 메인으로 화면전환//
        btn_logout=findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        //themeColor=sharedPreferences.getString("themeColor",DarkTheme.DEFAULT_MODE);
        switch_dark=(Switch)findViewById(R.id.switch_dark);
        switch_dark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheked) {
                if(isCheked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    //themeColor=DarkTheme.DARK_MODE;
                    //DarkTheme.applyTheme(themeColor);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    //themeColor=DarkTheme.LIGHT_MODE;
                    //DarkTheme.applyTheme(themeColor);
                }
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    //APP SDK 버전이 Q보다 클 때 (API 29 이상)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                }
                else {
                    //APP SDK 버전이 Q보다 작을 때 (API 29 미만)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                }*/
            }
        });
    }
}