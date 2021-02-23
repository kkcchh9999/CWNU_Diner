package com.example.cwnu_diner;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.cwnu_diner.R.id.googleMap;

public class MapFragment extends Fragment  implements OnMapReadyCallback {

    private MapView mapView = null;
    //JSON 파싱
    ArrayList<StoreLocationData> LOCdata;
    String jsonString;


    public MapFragment()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_map,container, false);

        //////JSON파싱
        JsonParse jsonParse = new JsonParse();
        jsonParse.execute("http://3.34.134.116/storeData.php");

        mapView = (MapView)layout.findViewById(googleMap);
        mapView.getMapAsync(this);

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
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

        Log.d("OnDESTROY",Integer.toString(LOCdata.size()));
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

            mapView.onCreate(savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

       LatLng location = new LatLng(35.2425, 128.689);

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

        private ArrayList<StoreLocationData> doParse()
        {
            ArrayList<StoreLocationData> tmpLOCdata = new ArrayList<>();
            try{
                JSONArray jsonArray = new JSONArray(jsonString);
                for(int i = 0; i<jsonArray.length(); i++)
                {
                    JSONObject item = jsonArray.getJSONObject(i);
                    StoreLocationData tmpStoreLocationData = new StoreLocationData(item.getString("storeName"), item.getString("type"), item.getDouble("latitude"), item.getDouble("longitude"));

                    tmpLOCdata.add(tmpStoreLocationData);
                    Log.d("tmpLocdata",Integer.toString(tmpLOCdata.size()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return tmpLOCdata;
        }
    }


}
