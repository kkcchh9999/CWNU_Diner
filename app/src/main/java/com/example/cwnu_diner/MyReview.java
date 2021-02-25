package com.example.cwnu_diner;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyReview extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<MyReviewData> myReviewList= new ArrayList<>();;
    private MyReviewAdapter myReviewAdapter;
    private MyReviewData myReviewData;

    private TextView txt_count;
    private int review_count=0;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //로그인정보 가져오기

    private static String serverUrl = "http://3.34.134.116/reviewData.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_review);

        txt_count=findViewById(R.id.txt_count);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);

        loadMyReviewList();

        myReviewAdapter = new MyReviewAdapter(myReviewList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myReviewAdapter);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    public void loadMyReviewList() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, serverUrl, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                myReviewAdapter.notifyDataSetChanged();

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String userID = jsonObject.getString("userID");
                        if(user.getEmail().equals(userID)){
                            String storeName = jsonObject.getString("storeName");
                            String review = jsonObject.getString("review");
                            String menu = jsonObject.getString("menu");
                            int starRating = jsonObject.getInt("starRating");
                            String date = jsonObject.getString("date");

                            myReviewList.add(new MyReviewData(userID, storeName, review, menu, starRating, date));
                            myReviewAdapter.notifyItemInserted(0);
                            review_count =review_count+1;
                        }
                    }
                    if(review_count==0){
                        txt_count.setText("0");
                    }
                    else{
                        txt_count.setText(Integer.toString(review_count));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyReview.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }
}