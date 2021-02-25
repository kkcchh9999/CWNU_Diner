package com.example.cwnu_diner;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class StoreClickActivity extends AppCompatActivity implements OnMapReadyCallback {

    RecyclerView recyclerView;
    ArrayList<ReviewData> items = new ArrayList<>();
    ReviewAdapter reviewAdapter;

    String URL = "http://3.34.134.116/reviewData.php"; //리뷰데이터 파싱
    TextView tv_storeName, tv_star, tv_opening, tv_tel, tv_adress;
    StoreData data;

    private FragmentManager fragmentManager;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storeclick);

        ///리사이클러뷰로 리뷰 열기
        recyclerView=findViewById(R.id.recyclerview_review);
        reviewAdapter = new ReviewAdapter(this, items);
        recyclerView.setAdapter(reviewAdapter);

        /////지도 켜기
        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.click_map);
        mapFragment.getMapAsync(this);


        /////툴바 달기
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ///Intent로 activity간 정보 가져오기
        Intent intent = getIntent();


        if(intent.getExtras() != null){
             data = (StoreData) intent.getSerializableExtra("data");





        }

        tv_storeName = (TextView)findViewById(R.id.storeName);
        tv_storeName.setText(data.getStoreName());

        tv_star = (TextView)findViewById(R.id.star);
        tv_star.setText(data.getStarRatingAvg());

        tv_tel = (TextView)findViewById(R.id.tel);
        tv_tel.setText(data.getTel());

        tv_adress = (TextView)findViewById(R.id.address);
        tv_adress.setText(data.getAddress());

        tv_opening = (TextView)findViewById(R.id.openingHours);
        tv_opening.setText(data.getOpeningHours());

        // --------------- Review 리사이클러뷰--------------------------

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL, null, new Response.Listener<JSONArray>() {
            //volley 라이브러리의 GET방식은 버튼 누를때마다 새로운 갱신 데이터를 불러들이지 않음. 그래서 POST 방식 사용
            @Override
            public void onResponse(JSONArray response) {
                items.clear();
                reviewAdapter.notifyDataSetChanged();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String storeName = jsonObject.getString("storeName");
                        String review = jsonObject.getString("review");
                        String menu = jsonObject.getString("menu");
                        String userID = jsonObject.getString("userID");
                        String starRating = jsonObject.getString("starRating");

                        if(storeName.equals(data.getStoreName())) {
                            items.add(new ReviewData(userID, storeName, review, menu, starRating));
                        }
                        reviewAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        //실제 요청 작업을 수행해주는 요청큐 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        //요청큐에 요청 객체 생성
        requestQueue.add(jsonArrayRequest);

        //----------------------------------------------------
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
