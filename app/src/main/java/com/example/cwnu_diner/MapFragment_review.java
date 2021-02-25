package com.example.cwnu_diner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.cwnu_diner.R.id.googleMap;

public class MapFragment_review extends Fragment  implements OnMapReadyCallback {

    MapView mapView = null;

    ArrayList<StoreData> LocData = new ArrayList<>();
    String serverUrl="http://3.34.134.116/storeData.php";

    public MapFragment_review()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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



                        LocData.add(new StoreData(storeName,address, tel, type, openingHours, starRatingAvg, latitude, longitude));

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

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_map,container, false);
        mapView = view.findViewById(googleMap);
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
//        mapView.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

            mapView.onCreate(savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng location = new LatLng(35.2425, 128.689);
//
//        LatLng location1 = new LatLng(LocData.get(i).getLatitude(), LocData.get(i).getLongitude());
//        MarkerOptions markerOptions1 = new MarkerOptions();
//        markerOptions1.title(LocData.get(i).getStoreName());
//        markerOptions1.snippet(LocData.get(i).getType());
//        markerOptions1.position(location1);
//        googleMap.addMarker(markerOptions1);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,16));

    }


}
