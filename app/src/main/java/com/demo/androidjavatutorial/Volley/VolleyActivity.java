package com.demo.androidjavatutorial.Volley;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.demo.androidjavatutorial.MainActivity;
import com.example.androidjavatutorial.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolleyActivity extends AppCompatActivity {

    String URI = "https://jsonplaceholder.typicode.com/albums";
    List<VolleyData> volleyDataList;
    RecyclerView recyclerView;
    VolleyAdapter volleyAdapter;
    JsonArrayRequest jsonArrayRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        volleyDataList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URI, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("Response:--" + response);

                for (int i = 0; i <= response.length(); i++) {

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);

                        VolleyData product = new VolleyData();
                        product.setUserId(jsonObject.getInt("userId"));
                        product.setId(jsonObject.getInt("id"));
                        product.setTitle(jsonObject.getString("title"));

                        volleyDataList.add(product);

                        volleyAdapter = new VolleyAdapter(VolleyActivity.this, volleyDataList);
                        recyclerView.setAdapter(volleyAdapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error");
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(VolleyActivity.this);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(VolleyActivity.this, MainActivity.class));
        finish();
    }
}