package com.example.cwnu_diner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import org.w3c.dom.Text;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AddReviewActivity extends AppCompatActivity {

    Button btn_reviewAdd;
    EditText et_review;
    RatingBar ratingBar;
    StoreData data;
    Spinner spinner;
    private static String IP_ADDRESS = "http://3.34.134.116/reviewinsert.php";

    int rating = 3;
    String review, userID, menu;
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
        if (intent.getExtras() != null) {
            data = (StoreData) intent.getSerializableExtra("data");
            userID = intent.getStringExtra("userID");
        }

        getSupportActionBar().setTitle(data.getStoreName());
        //////////////메뉴 데이터 받아오기
        String serverURL = "http://3.34.134.116/menuData.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, serverURL, null, new Response.Listener<JSONArray>() {
            //volley 라이브러리의 GET방식은 버튼 누를때마다 새로운 갱신 데이터를 불러들이지 않음. 그래서 POST 방식 사용
            @Override
            public void onResponse(JSONArray response) {

                //파라미터로 응답받은 결과 JsonArray를 분석
                menuData.clear();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String storeName = jsonObject.getString("storeName");
                        if (storeName.equals(data.getStoreName())) {


                        String menu = jsonObject.getString("menu");
                        //String storeName=jsonObject.getString("storeName");
                        int price = jsonObject.getInt("price");
                        String menuType = jsonObject.getString("menuType");

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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //요청큐에 요청 객체 생성
        requestQueue.add(jsonArrayRequest);
        spinner = (Spinner) findViewById(R.id.spinner_menu);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) view).setText(arrayList.get(i));
                //Toast.makeText(getApplicationContext(),arrayList.get(i),Toast.LENGTH_SHORT).show();
                menu = arrayList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = (int) v;
                if(rating<=1) {
                    rating = 1;
                }
            }
        });

        et_review = (EditText) findViewById(R.id.et_review);

        btn_reviewAdd = (Button) findViewById(R.id.btn_reviewAdd);
        btn_reviewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                review = String.valueOf(et_review.getText());

                ////////////////////데이터 올리기///////////////
                InsertData task = new InsertData();
                task.execute(IP_ADDRESS, userID, data.getStoreName(), review, menu, Integer.toString(rating));

                finish();

            }
        });

    }


    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(AddReviewActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {

            String userID = (String) params[1];
            String store = (String) params[2];
            String menu = (String) params[4];
            String review = (String) params[3];
            int rating = (Integer.parseInt(params[5]));
            String serverURL = (String) params[0];

            String postParameters = "userID=" + userID + "&storeName=" + store + "&review=" + review + "&menu=" + menu + "&starRating=" + rating;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                Log.d("tagggggg", postParameters);
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("TAGGGGGG", "POSTrsponsecode" + responseStatusCode);

            } catch (Exception e) {
                Log.d("TAGGGWRONG", "아진짜 이게 터지네");
            }


            return null;
        }
    }


}