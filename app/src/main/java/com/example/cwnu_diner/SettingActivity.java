package com.example.cwnu_diner;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    Switch dark_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        dark_mode=(Switch)findViewById(R.id.switch_dark);

        dark_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheked) {
                if(isCheked==true){
                    Toast.makeText(SettingActivity.this,"야간모드",Toast.LENGTH_SHORT).show();
                }
                else{

                }
            }
        });
    }
}