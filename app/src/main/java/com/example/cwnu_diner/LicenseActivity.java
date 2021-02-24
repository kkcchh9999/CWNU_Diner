package com.example.cwnu_diner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LicenseActivity extends AppCompatActivity {

    TextView tv_license;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        String txt;
        tv_license = (TextView)findViewById(R.id.tv_License);
        txt = ReadTextFile("License.txt");
        tv_license.setText(txt);

    }
    public String ReadTextFile(String filename)
    {   //파일 읽어오기 함수
        //assets 텍스트파일 가져오기
        String text = "";
        AssetManager assetManager = getResources().getAssets();
        InputStream inputStream = null;
        try{
            inputStream = assetManager.open(filename);
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            String line ="";
            while((line = bf.readLine()) != null)
            {
                text = text+line+"\n";
            }
            inputStream.close();
        }        catch (Exception e)
        {
            e.printStackTrace();
        }

        return text;
    }
}