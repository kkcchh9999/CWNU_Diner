package com.example.cwnu_diner;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends Activity {

    SharedPreferences AppData; //스위치 값을 저장해놓는 곳

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //야간모드 유지
        AppData = getSharedPreferences("AppData", Activity.MODE_PRIVATE);
        boolean switch_state = AppData.getBoolean("switch_state", false);

        if (switch_state) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            findViewById(R.id.splash).setBackgroundColor(Color.WHITE);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(getApplicationContext(), StoreListActivity.class);
                    intent.putExtra("nickname", user.getDisplayName());
                    intent.putExtra("userID", user.getEmail());
                    intent.putExtra("photoUrl", String.valueOf(user.getPhotoUrl()));
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },1500); //1.5초




    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}