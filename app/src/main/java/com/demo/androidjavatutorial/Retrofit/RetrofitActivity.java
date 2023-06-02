package com.demo.androidjavatutorial.Retrofit;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.androidjavatutorial.MainActivity;
import com.demo.androidjavatutorial.AllUtils.ApiClient;
import com.example.androidjavatutorial.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity {

    RecyclerView retrofit_recycler;
    List<RetrofitData> retrofitData;
    RetrofitAdapter retrofitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        retrofitData = new ArrayList<>();
        retrofit_recycler = findViewById(R.id.retrofit_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        retrofit_recycler.setLayoutManager(linearLayoutManager);

        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        apiInterface.getData().enqueue(new Callback<List<RetrofitData>>() {
            @Override
            public void onResponse(Call<List<RetrofitData>> call, Response<List<RetrofitData>> response) {
                System.out.println("Retrofit:-- " + response.body());
                retrofitData = response.body();
                retrofitAdapter = new RetrofitAdapter(RetrofitActivity.this, retrofitData);
                retrofit_recycler.setAdapter(retrofitAdapter);
            }

            @Override
            public void onFailure(Call<List<RetrofitData>> call, Throwable t) {
                System.out.println("Error");
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RetrofitActivity.this, MainActivity.class));
        finish();
    }
}