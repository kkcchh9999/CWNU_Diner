package com.example.cwnu_diner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;


public class StoreListActivity extends AppCompatActivity {

    ArrayList<StoreData> LocData = new ArrayList<>();
    Button btn_roulette, btn_switchMap;
    Button btn_search, btn_setting;
    //데이터 올리기
    private static String IP_ADDRESS = "http://3.34.134.116/userinsert.php";


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
        if (System.currentTimeMillis() > backKeyPressedTime + 1500) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "뒤로가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지나지 않았으면 종료
        if (System.currentTimeMillis() <= backKeyPressedTime + 1500) {
            toast.cancel();
            toast = Toast.makeText(this,"이용해 주셔서 감사합니다.",Toast.LENGTH_LONG);
            toast.show();
            finishAffinity();
        }
    }

    public ArrayList<String> ReadTextFile(String filename)
    {   //파일 읽어오기 함수
        //assets 텍스트파일 가져오기

        ArrayList<String> arrayList =new ArrayList<>();
        AssetManager assetManager = getResources().getAssets();
        InputStream inputStream = null;
        try{
            inputStream = assetManager.open(filename);
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
        final String userID = intent.getStringExtra("userID");

        Bundle bundle = new Bundle();
        bundle.putString("userID",userID);

        String serverUrl="http://3.34.134.116/storeData.php";
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.POST, serverUrl, null, new Response.Listener<JSONArray>() {
            //volley 라이브러리의 GET방식은 버튼 누를때마다 새로운 갱신 데이터를 불러들이지 않음. 그래서 POST 방식 사용
            @Override
            public void onResponse(JSONArray response) {

                //파라미터로 응답받은 결과 JsonArray를 분석
                LocData.clear();
                try {
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject= response.getJSONObject(i);


                        String storeName=jsonObject.getString("storeName");
                        String address=jsonObject.getString("address");
                        String tel=jsonObject.getString("tel");
                        String type=jsonObject.getString("type");
                        String openingHours=jsonObject.getString("openingHours");
                        String starRatingAvg=jsonObject.getString("starRatingAvg");
                        Double latitude=jsonObject.getDouble("latitude");
                        Double longitude=jsonObject.getDouble("longitude");



                        LocData.add(new StoreData(storeName,  starRatingAvg,openingHours,tel,address, type,  latitude, longitude));

                    }
                } catch (JSONException e) {e.printStackTrace();}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        //실제 요청 작업을 수행해주는 요청큐 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(this );
        //요청큐에 요청 객체 생성
        requestQueue.add(jsonArrayRequest);

        final Bundle bundle1 = new Bundle();
        bundle1.putSerializable("LocData",LocData);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        final ListFragment listFragment = new ListFragment();
        listFragment.setArguments(bundle);
        transaction.replace(R.id.frame,listFragment);
        transaction.addToBackStack(null);
        transaction.commit();



////////////////////데이터 올리기///////////////
        InsertData task = new InsertData();
        task.execute(IP_ADDRESS, nickname, userID);

/////////////////////////////////룰렛 버튼 작동 ////////////////////////////////////////////////////
        final ArrayList<String> menuText;
        menuText = ReadTextFile("roulette.txt");
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

            MapMainFragment mapFragment = new MapMainFragment(userID);
            @Override
            public void onClick(View view) {
                if(btn_switchMap.getText().equals("   지도"))
                {
                    btn_switchMap.setText("  목록");
                    mapFragment.setArguments(bundle1);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame, mapFragment, "map").addToBackStack(null).commit();

                }else
                {
                    btn_switchMap.setText("   지도");
                    mapFragment.onDestroy();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame,listFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });


///////////////////////////검색 버튼 작동///////////////////////////////////////////////////////////
        btn_search = (Button)findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchStoreActivity.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });
/////////////////////////설정 버튼 작동//////////////////////////////////////////////////////////////

        btn_setting =(Button)findViewById(R.id.btn_setting);
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



    class InsertData extends AsyncTask<String, Void, String >
    {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(StoreListActivity.this,"Please Wait",null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {
            String userName = (String)params[1];
            String userID = (String)params[2];

            String serverURL = (String)params[0];
            String postParameters = "userName="+userName+"&userID="+userID;

            try{
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                Log.d("tagggggg",postParameters);
                outputStream.flush();
                outputStream.close();



                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("TAGGGGGG","POSTrsponsecode"+responseStatusCode);

            }catch (Exception e){
                Log.d("TAGGGWRONG", "아진짜 이게 터지네");
            }


            return null;
        }
    }



}