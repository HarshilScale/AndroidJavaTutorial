package com.demo.androidjavatutorial.RetrofitDemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.demo.androidjavatutorial.RetrofitDemo.Adapter.PhotoAdapter;
import com.demo.androidjavatutorial.RetrofitDemo.Utils.Loder;
import com.demo.androidjavatutorial.AllUtils.ApiClient;
import com.demo.androidjavatutorial.AllUtils.ApiInterface;
import com.example.androidjavatutorial.R;
import com.demo.androidjavatutorial.RetrofitDemo.Model.PhotoData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoActivity extends AppCompatActivity {

    RecyclerView recycler_grid;
    PhotoAdapter gridAdapter;
    List<PhotoData> photoDataList;
    boolean connected = false;
    int num;
    Loder loder;
    LottieAnimationView animation_view, lottie_data;
    TextView txt_refersh;
    ImageView iv_search, iv_clear_text;
    SharedPreferences sharedPreferences;
    private GridLayoutManager gridLayoutManager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        initView();
        checkInternet();
    }

    public void initView() {
        txt_refersh = findViewById(R.id.txt_refersh);
        iv_search = findViewById(R.id.iv_search);
        iv_clear_text = findViewById(R.id.iv_clear_text);
        animation_view = findViewById(R.id.animation_view);
        lottie_data = findViewById(R.id.lottie_data);
        toolbar = findViewById(R.id.toolbar);

        toolbar.inflateMenu(R.menu.menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();
                switch (id) {
                    case R.id.menu_switch_layout:
                        switchLayout();
                        switchIcon(item);
                        return true;
                }
                return false;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        num = sharedPreferences.getInt("album_id", 0);

        photoDataList = new ArrayList<>();
        recycler_grid = findViewById(R.id.recycler_grid);
        gridLayoutManager = new GridLayoutManager(PhotoActivity.this, PhotoAdapter.SPAN_COUNT_ONE);
        recycler_grid.setLayoutManager(gridLayoutManager);
    }

    public void getApi() {

        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        apiInterface.getPhoto(num).enqueue(new Callback<List<PhotoData>>() {
            @Override
            public void onResponse(Call<List<PhotoData>> call, Response<List<PhotoData>> response) {
                if (response != null) {
                    photoDataList = response.body();
                    System.out.println("AlbumActivity" + photoDataList);
                    gridAdapter = new PhotoAdapter(PhotoActivity.this, gridLayoutManager, photoDataList);
                    recycler_grid.setAdapter(gridAdapter);
                    lottie_data.setVisibility(View.GONE);
                    loder.dismissDialog();
                } else {
                    animation_view.setVisibility(View.GONE);
                    lottie_data.setVisibility(View.VISIBLE);
                    txt_refersh.setVisibility(View.GONE);
                    Toast.makeText(PhotoActivity.this, "Data Not Found!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<PhotoData>> call, Throwable t) {
                System.out.println("Error");
            }
        });
    }

    public void checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
            getApi();
            txt_refersh.setVisibility(View.GONE);
            animation_view.setVisibility(View.GONE);
        } else {
            if (photoDataList.isEmpty()) {
                photoDataList.clear();
            }
            gridAdapter = new PhotoAdapter(PhotoActivity.this, gridLayoutManager, photoDataList);
            recycler_grid.setAdapter(gridAdapter);
            txt_refersh.setVisibility(View.VISIBLE);
            animation_view.setVisibility(View.VISIBLE);
            connected = false;
            Toast.makeText(PhotoActivity.this, "Check Internet Connection!", Toast.LENGTH_LONG).show();
        }
    }

    public void loding() {
        loder = new Loder(this);
        loder.startLoding();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loder.dismissDialog();
            }
        }, 5000);
    }

    private void switchLayout() {
        if (gridLayoutManager.getSpanCount() == PhotoAdapter.SPAN_COUNT_ONE) {
            gridLayoutManager.setSpanCount(PhotoAdapter.SPAN_COUNT_TWO);
        } else {
            gridLayoutManager.setSpanCount(PhotoAdapter.SPAN_COUNT_ONE);
        }
        gridAdapter.notifyItemRangeChanged(0, gridAdapter.getItemCount());
    }

    private void switchIcon(MenuItem item) {
        if (gridLayoutManager.getSpanCount() == PhotoAdapter.SPAN_COUNT_TWO) {
            item.setIcon(getResources().getDrawable(R.drawable.ic_grid));
        } else {
            item.setIcon(getResources().getDrawable(R.drawable.ic_list));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        loding();
        checkInternet();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PhotoActivity.this, AlbumActivity.class));
        finish();
    }
}