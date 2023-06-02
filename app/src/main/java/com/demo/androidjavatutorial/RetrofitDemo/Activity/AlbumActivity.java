package com.demo.androidjavatutorial.RetrofitDemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.demo.androidjavatutorial.MainActivity;
import com.demo.androidjavatutorial.RetrofitDemo.Utils.Loder;
import com.demo.androidjavatutorial.AllUtils.ApiClient;
import com.demo.androidjavatutorial.AllUtils.ApiInterface;
import com.example.androidjavatutorial.R;
import com.demo.androidjavatutorial.RetrofitDemo.Adapter.AlbumAdapter;
import com.demo.androidjavatutorial.RetrofitDemo.Model.AlbumsData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumActivity extends AppCompatActivity {

    RecyclerView recycler_album;
    AlbumAdapter myListAdapter;
    List<AlbumsData> albumsDataList;
    TextView txt_refersh, title;
    ImageView img_back;
    int num;
    boolean connected = false;
    Loder loder;
    LottieAnimationView animation_view, lottie_data;
    SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        initView();
        checkInternet();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
                loding();
                checkInternet();
                getApi();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    public void initView() {
        txt_refersh = findViewById(R.id.txt_refersh);
        animation_view = findViewById(R.id.animation_view);
        lottie_data = findViewById(R.id.lottie_data);
        img_back = findViewById(R.id.ic_img);
        title = findViewById(R.id.title);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        num = sharedPreferences.getInt("userId", 0);
        String str = sharedPreferences.getString("name", null);

        title.setText(str);

        albumsDataList = new ArrayList<>();
        recycler_album = findViewById(R.id.recycler_album);
        myListAdapter = new AlbumAdapter(this, albumsDataList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycler_album.setLayoutManager(manager);
        recycler_album.setAdapter(myListAdapter);

    }

    private void getApi() {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        apiInterface.getTitle(num).enqueue(new Callback<List<AlbumsData>>() {
            @Override
            public void onResponse(Call<List<AlbumsData>> call, Response<List<AlbumsData>> response) {

                if (response != null) {
                    albumsDataList = response.body();
                    myListAdapter.setUserList(albumsDataList);
                    System.out.println("AlbumActivity" + albumsDataList);
                    lottie_data.setVisibility(View.GONE);
                    loder.dismissDialog();
                } else {
                    animation_view.setVisibility(View.GONE);
                    lottie_data.setVisibility(View.VISIBLE);
                    txt_refersh.setVisibility(View.GONE);
                    Toast.makeText(AlbumActivity.this, "Data Not Found!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<AlbumsData>> call, Throwable t) {
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
            txt_refersh.setVisibility(View.VISIBLE);
            animation_view.setVisibility(View.VISIBLE);
            connected = false;
            Toast.makeText(AlbumActivity.this, "Check Internet Connection!", Toast.LENGTH_LONG).show();
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

    @Override
    protected void onStart() {
        super.onStart();
        loding();
        checkInternet();
    }

    @Override
    public void onBackPressed() {
        loder.back();
        startActivity(new Intent(AlbumActivity.this, MainActivity.class));
        finish();
    }
}