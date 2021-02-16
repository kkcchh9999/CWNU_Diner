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
    ImageButton btn_search, btn_setting;

    public ArrayList<String> ReadTextFile(ArrayList<String> arrayList)
    {   //파일 읽어오기 함수
        //assets 텍스트파일 가져오기
        AssetManager assetManager = getResources().getAssets();
        InputStream inputStream = null;
        try{
            inputStream = assetManager.open("roulette.txt");
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            String line ="";
            while((line = bf.readLine()) != null)
            {
                arrayList.add(line);
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
        return arrayList;
    }
////////////////onCreate 생명주기 //////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storelist);

        Intent intent = getIntent();
        final String nickname = intent.getStringExtra("nickname" );
        final String photoUrl = intent.getStringExtra("photoUrl" );


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
        final ArrayList<String> menuText= new ArrayList<>();
        ReadTextFile(menuText);
        //랜덤변수 생성
        final Random randnumber = new Random();
        //룰렛버튼 작동
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
                Intent intent = new Intent(getApplicationContext(), SearchStoreActivity.class);
                Log.d("search button","?");
                startActivity(intent);
            }
        });
/////////////////////////설정 버튼 작동//////////////////////////////////////////////////////////////

        btn_setting =(ImageButton)findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                intent.putExtra("nickname", nickname);
                intent.putExtra("photoUrl", photoUrl);
                startActivity(intent);
            }
        });

    }

}