package com.example.cwnu_diner;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
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

import java.util.ArrayList;

public class ListFragment extends Fragment implements View.OnClickListener {

    Button all, k, j, c, w, f;
    private RecyclerView recyclerView;
    ArrayList<StoreData> stores = new ArrayList<>();
    SearchAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.scrollToPosition(0);

        adapter = new SearchAdapter(stores,getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());


        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        String serverUrl="http://3.34.134.116/storeData.php";

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.POST, serverUrl, null, new Response.Listener<JSONArray>() {
            //volley 라이브러리의 GET방식은 버튼 누를때마다 새로운 갱신 데이터를 불러들이지 않음. 그래서 POST 방식 사용
            @Override
            public void onResponse(JSONArray response) {


                //파라미터로 응답받은 결과 JsonArray를 분석

                stores.clear();

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



                        stores.add(new StoreData(storeName, address,  tel,  type, openingHours,  starRatingAvg,  latitude, longitude));
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {e.printStackTrace();}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        //실제 요청 작업을 수행해주는 요청큐 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());

        //요청큐에 요청 객체 생성
        requestQueue.add(jsonArrayRequest);


        all = (Button)view.findViewById(R.id.btn_all);
        all.setOnClickListener(this);
        k = (Button)view.findViewById(R.id.btn_korea);
        k.setOnClickListener(this);
        j = (Button)view.findViewById(R.id.btn_japan);
        j.setOnClickListener(this);
        c = (Button)view.findViewById(R.id.btn_china);
        c.setOnClickListener(this);
        w = (Button)view.findViewById(R.id.btn_west);
        w.setOnClickListener(this);
        f = (Button)view.findViewById(R.id.btn_flour);
        f.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_all:
                System.out.println("dd");
                adapter.getFilter().filter(null);
                break;

            case R.id.btn_korea:
                System.out.println("한식");
                adapter.getFilter().filter("한식");
                break;

            case R.id.btn_japan:
                adapter.getFilter().filter("일식");
                break;

            case R.id.btn_china:
                adapter.getFilter().filter("중식");
                break;

            case R.id.btn_west:
                adapter.getFilter().filter("양식");
                break;

            case R.id.btn_flour:
                adapter.getFilter().filter("분식");
                break;

        }

    }
}
