package com.example.cwnu_diner;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

public class SplashActivity extends Activity {

    SharedPreferences AppData; //스위치 값을 저장해놓는 곳

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //야간모드 유지
        AppData=getSharedPreferences("AppData", Activity.MODE_PRIVATE);
        boolean switch_state=AppData.getBoolean("switch_state",false);
        if(switch_state){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            findViewById(R.id.splash).setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_dark_000000_ffffff));
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}
