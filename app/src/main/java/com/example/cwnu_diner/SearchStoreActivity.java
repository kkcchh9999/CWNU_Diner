package com.example.cwnu_diner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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
import java.util.Collections;
import java.util.List;

public class SearchStoreActivity extends AppCompatActivity{

    private SearchAdapter adapter;
    private List<SearchData> storeList = new ArrayList<SearchData>();

    private static String serverUrl = "http://3.34.134.116/storeData.php";
    private static String TAG = "phptest";

    ArrayList<String> autocomplete_list= new ArrayList<>();//일단

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchstore);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        loadStoreList();

        adapter = new SearchAdapter(storeList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        final SearchView.SearchAutoComplete autoComplete = searchView.findViewById(R.id.search_src_text);
        ArrayAdapter<String> auto_adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, autocomplete_list);
        autoComplete.setThreshold(1);
        autoComplete.setAdapter(auto_adapter);

        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString = (String) adapterView.getItemAtPosition(itemIndex);
                autoComplete.setText(queryString + "");
                autoComplete.setSelection(autoComplete.length()); //커서를 끝으로
            }
        });

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.toolbar) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadStoreList() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, serverUrl, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                //storeList.clear();
                adapter.notifyDataSetChanged();

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String storeName = jsonObject.getString("storeName");
                        String star = jsonObject.getString("starRatingAvg");
                        String openingHours = jsonObject.getString("openingHours");
                        String tel = jsonObject.getString("tel");
                        String address = jsonObject.getString("address");
                        String type = jsonObject.getString("type");

                        storeList.add(new SearchData(storeName, star, openingHours, tel, address, type));
                        autocomplete_list.add(storeName);
                        if(!autocomplete_list.contains(type)){
                            autocomplete_list.add(type);
                        }
                        adapter.notifyItemInserted(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchStoreActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);

        Collections.sort(autocomplete_list);

    }
}