package com.example.cwnu_diner;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private FirebaseAuth auth;// 로그아웃 관리 위한 인증
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient googleSignInClient;

    private ImageView image_info;
    private TextView txt_info;

    private Switch switch_dark;
    private Button btn_logout;

    private SharedPreferences AppData; //스위치 값을 저장
    private SharedPreferences.Editor editor; //스위치 값 수정

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
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(view.getContext());
                alt_bld.setMessage("로그아웃 하시겠습니까?").setCancelable(false)
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                signOut();

                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = alt_bld.create();
                alert.setTitle("로그아웃");
                alert.show();
            }
        });

        //야간모드 기능
        switch_dark=(Switch)findViewById(R.id.switch_dark);

        AppData=getSharedPreferences("AppData", Activity.MODE_PRIVATE);
        editor=AppData.edit();
        Boolean switch_state=AppData.getBoolean("switch_state",false);
        switch_dark.setChecked(switch_state);

        switch_dark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheked) {
                if(isCheked==true){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("switch_state",isCheked);
                    editor.commit();
                }

                else if(isCheked==false){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("switch_state",isCheked);
                    editor.commit();
                }

                else{
                    // 안드로이드 10 이상
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    }
                    // 안드로이드 10 미만
                    else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                    }
                }
            }

        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v("알림","onConnectionFailed");
    }

    private void signOut() {
        googleSignInClient.signOut();
    }

}