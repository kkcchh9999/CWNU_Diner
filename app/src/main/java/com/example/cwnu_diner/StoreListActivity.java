package com.example.cwnu_diner;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class StoreListActivity extends AppCompatActivity {

    Button btn_roulette;
    ImageButton btn_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storelist);

        ListView listview ;
        ListViewAdapter adapter = new ListViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.wow);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.a),
                "a", "aaa") ;
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.b),
                "b", "bbbbbbb") ;
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.c),
                "c", "ccccc") ;

/////////////////////////////////룰렛 버튼 작동 ////////////////////////////////////////////////////

        //assets 텍스트파일 가져오기
        AssetManager assetManager = getResources().getAssets();
        InputStream inputStream = null;
        final ArrayList<String> menuText= new ArrayList<>();
        try{
            inputStream = assetManager.open("roulette.txt");
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            String line ="";
            while((line = bf.readLine()) != null)
            {
                menuText.add(line);
            }
            inputStream.close();
        }        catch (Exception e)
        {
            e.printStackTrace();
        }
        if(inputStream != null)
        {
            try{
                inputStream.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
       //랜덤변수 생성
        final Random randnumber = new Random();

        btn_roulette = (Button)findViewById(R.id.roulette);
        btn_roulette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(StoreListActivity.this);
                ad.setIcon(R.drawable.food);
                ad.setTitle("랜덤 메뉴 룰렛");
                ad.setMessage("오늘은 "+ menuText.get(randnumber.nextInt(16)) +" 어떠세요?");

                AlertDialog alertDialog = ad.create();
                alertDialog.show();
            }
        });
///////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////검색 버튼 작동///////////////////////////////////////////////////////////
        btn_search = (ImageButton)findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchMenuActivity.class);
                Log.d("search button","?");
                startActivity(intent);
            }
        });
///////////////////////////////////////////////////////////////////////////////////////////////////
    }

}