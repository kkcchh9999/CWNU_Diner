package com.example.cwnu_diner;

import android.app.FragmentManager;
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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import static com.example.cwnu_diner.R.id.googleMap;


public class StoreMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    Button btn_roulette, btn_switchList;
    ImageButton btn_search, btn_setting;

    //JSON 파싱
    ArrayList<StoreLocationData> LOCdata;
    String jsonString;


    private GoogleMap mMap;

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
        setContentView(R.layout.activity_storemap);

        Intent intent = getIntent();
        final String nickname = intent.getStringExtra("nickname" );
        final String photoUrl = intent.getStringExtra("photoUrl" );
        //////JSON파싱
        JsonParse jsonParse = new JsonParse();
        jsonParse.execute("http://3.34.134.116/storeData.php");

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(googleMap);
        mapFragment.getMapAsync(this);

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
                AlertDialog.Builder ad = new AlertDialog.Builder(StoreMapActivity.this);
                ad.setIcon(R.drawable.food);
                ad.setTitle("랜덤 메뉴 룰렛");
                ad.setMessage("오늘은 "+ menuText.get(randnumber.nextInt(16)) +" 어떠세요?");

                AlertDialog alertDialog = ad.create();
                alertDialog.show();
            }
        });
/////////////////////////리스트 버튼//////////////////////////////////////////////////////////////////

        btn_switchList = (Button)findViewById(R.id.btn_switchList);
        btn_switchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StoreListActivity.class);
                intent.putExtra("nickname", nickname);
                intent.putExtra("photoUrl", photoUrl);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
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

    }   /// On Create 끝

    /////////////////JSON 파싱
    public class JsonParse extends AsyncTask<String, Void, String>
    {
        String TAG = "JsonParseTest";
        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            try{
                URL serverURL = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) serverURL.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if(responseStatusCode == httpURLConnection.HTTP_OK){
                    inputStream = httpURLConnection.getInputStream();
                }else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = "";

                while((line=bufferedReader.readLine())!=null){
                    sb.append(line);
                }
                Log.d(TAG,sb.toString().trim());
                bufferedReader.close();

                return sb.toString().trim();
            }catch(Exception e) {
                String errorString= e.toString();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s == null)
            {

            }
            else{
                jsonString = s;
                LOCdata = doParse();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        private  ArrayList<StoreLocationData> doParse()
        {
            ArrayList<StoreLocationData> tmpLOCdata = new ArrayList<>();
            try{
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("StoreLocationData");

                for(int i = 0; i<jsonArray.length(); i++)
                {
                    JSONObject item = jsonArray.getJSONObject(i);
                    StoreLocationData tmpStoreLocationData = new StoreLocationData(item.getString("storeName"), item.getString("type"), item.getDouble("latitude"), item.getDouble("longitude"));

                    tmpLOCdata.add(tmpStoreLocationData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return tmpLOCdata;
        }
    }



//////////////////////////구글 맵 호출//////////////////////////
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        LatLng location = new LatLng(35.245732262051746, 128.69199976866827); //창원대
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("창원대학교");
        markerOptions.snippet("본부");
        markerOptions.position(location);
        googleMap.addMarker(markerOptions);

        for(int i = 0; i<LOCdata.size(); i++)
        {
            LatLng location1 = new LatLng(LOCdata.get(i).getLat(), LOCdata.get(i).getLng());
            MarkerOptions markerOptions1 = new MarkerOptions();
            markerOptions1.title(LOCdata.get(i).getStorename());
            markerOptions1.snippet(LOCdata.get(i).getType());
            markerOptions1.position(location1);
            googleMap.addMarker(markerOptions1);
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,16));
    }
}