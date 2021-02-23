package com.example.cwnu_diner;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


public class StoreListActivity extends AppCompatActivity {

    Button btn_roulette, btn_switchMap;
    ImageButton btn_search, btn_setting;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    ArrayList<Store> stores = new ArrayList<>();
    StoreAdapter adapter;


////////////////// 뒤로가기 버튼 작동시 앱 종료 혹은 로그인 화면으로 돌아가기 방지
    // 마지막으로 뒤로 가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로 가기 버튼을 누를 때 표시
    private Toast toast;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        // 기존 뒤로 가기 버튼의 기능을 막기 위해 주석 처리 또는 삭제

        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지났으면 Toast 출력
        // 2500 milliseconds = 2.5 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2400) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지나지 않았으면 종료
        if (System.currentTimeMillis() <= backKeyPressedTime + 2400) {
            toast.cancel();
            toast = Toast.makeText(this,"이용해 주셔서 감사합니다.",Toast.LENGTH_LONG);
            toast.show();
            finishAffinity();
        }
    }

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


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ListFragment listFragment = new ListFragment();
        transaction.replace(R.id.frame,listFragment);
        transaction.addToBackStack(null);
        transaction.commit();


        Intent intent = getIntent();
        final String nickname = intent.getStringExtra("nickname" );
        final String photoUrl = intent.getStringExtra("photoUrl" );

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
///////////////////////////지도 버튼 작동///////////////////////////////////////////////////////////
        btn_switchMap = (Button)findViewById(R.id.btn_switchMap);
        btn_switchMap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(btn_switchMap.getText().equals("지도"))
                {
                    btn_switchMap.setText("리스트");
                    btn_switchMap.setTextSize(8);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    MapFragment mapfragment = new MapFragment();

                    transaction.replace(R.id.frame,mapfragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else
                {
                    btn_switchMap.setText("지도");
                    btn_switchMap.setTextSize(10);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    ListFragment listFragment = new ListFragment();
                    transaction.replace(R.id.frame,listFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });


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

    /*
    public void addItem(Drawable icon, String name, String star, String loc){
        StoreData data =  StoreData(int icon, String name, String star, String loc);
       // 수정하기 data.setIv_store(icon);
        data.setTv_name(name);


    }
*/

}