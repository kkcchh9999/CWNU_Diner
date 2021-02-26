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

public class MapMainFragment extends Fragment  implements OnMapReadyCallback {

    MapView mapView = null;

    ArrayList<StoreData> LocData = new ArrayList<>();

    public MapMainFragment()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LocData = (ArrayList<StoreData>)getArguments().get("LocData");


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

        for(int i = 0; i<LocData.size(); i++)
        {
            LatLng location1 = new LatLng(LocData.get(i).getLatitude(), LocData.get(i).getLongitude());
            MarkerOptions markerOptions1 = new MarkerOptions();
            markerOptions1.title(LocData.get(i).getStoreName());
            markerOptions1.snippet(LocData.get(i).getType());
            markerOptions1.position(location1);
            googleMap.addMarker(markerOptions1);

        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,16));

    }


}
