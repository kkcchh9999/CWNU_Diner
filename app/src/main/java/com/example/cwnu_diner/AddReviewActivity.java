package com.example.cwnu_diner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddReviewActivity extends AppCompatActivity {

    Button btn_reviewAdd;
    EditText et_review;
    RatingBar ratingBar;
    StoreData data;
    Spinner spinner;

    int rating;
    String review;
    ArrayList<MenuData> menuData = new ArrayList<>();
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        /////툴바 달기
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ///인텐트로 정보 받아오기
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            data = (StoreData) intent.getSerializableExtra("data");
        }

        //////////////메뉴 데이터 받아오기
        String serverURL = "http://3.34.134.116/menuData.php";
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.POST, serverURL, null, new Response.Listener<JSONArray>() {
            //volley 라이브러리의 GET방식은 버튼 누를때마다 새로운 갱신 데이터를 불러들이지 않음. 그래서 POST 방식 사용
            @Override
            public void onResponse(JSONArray response) {

                //파라미터로 응답받은 결과 JsonArray를 분석
                menuData.clear();
                try {
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject= response.getJSONObject(i);


                        String menu = jsonObject.getString("menu");
                        String storeName=jsonObject.getString("storeName");
                        int price = jsonObject.getInt("price");
                        String menuType=jsonObject.getString("menuType");


                        if(storeName.equals(data.getStoreName())) {
                            menuData.add(new MenuData(menu, storeName, price, menuType));
                            arrayList.add(menu);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }

                } catch (JSONException e) {
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
        spinner = (Spinner)findViewById(R.id.spinner_menu);

        arrayAdapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_dropdown_item,arrayList);

        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)view).setText(arrayList.get(i));
                Toast.makeText(getApplicationContext(),arrayList.get(i),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        et_review = (EditText)findViewById(R.id.et_review);

        btn_reviewAdd = (Button)findViewById(R.id.btn_reviewAdd);
        btn_reviewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        rating = (int)v;
                    }
                });

                review = String.valueOf(et_review.getText());



            }
        });

    }
}