package com.demo.androidjavatutorial.RetrofitDemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.androidjavatutorial.R;
import com.demo.androidjavatutorial.RetrofitDemo.Adapter.ViewPagerAdapter;
import com.demo.androidjavatutorial.RetrofitDemo.Model.PhotoData;

import java.util.ArrayList;
import java.util.List;

public class ThiredActivity extends AppCompatActivity {

    List<PhotoData> photoDataList = new ArrayList<>();
    ViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;
    EditText edt_search_text;
    TextView txt_refersh;
    ImageView iv_search, iv_clear_text, img_back;
    boolean connected = false;
    LottieAnimationView animation_view, lottie_data;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thired);

        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        initView();
        checkInternet();
    }

    public void initView() {
        edt_search_text = findViewById(R.id.edt_search_text);
        txt_refersh = findViewById(R.id.txt_refersh);
        iv_search = findViewById(R.id.iv_search);
        iv_clear_text = findViewById(R.id.iv_clear_text);
        animation_view = findViewById(R.id.animation_view);
        lottie_data = findViewById(R.id.lottie_data);
        img_back = findViewById(R.id.ic_img);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        photoDataList = getIntent().getParcelableArrayListExtra("myList");
        pos = getIntent().getIntExtra("pos", 0);

        if (photoDataList != null) {
            viewPager = findViewById(R.id.viewPager);
            viewPagerAdapter = new ViewPagerAdapter(this, photoDataList);
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.setCurrentItem(pos);
        }
    }

    public void checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
            txt_refersh.setVisibility(View.GONE);
            animation_view.setVisibility(View.GONE);
        } else {
            viewPager.setSaveFromParentEnabled(false);
            txt_refersh.setVisibility(View.VISIBLE);
            animation_view.setVisibility(View.VISIBLE);
            connected = false;
            Toast.makeText(ThiredActivity.this, "Check Internet Connection!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkInternet();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ThiredActivity.this, PhotoActivity.class));
        finish();
    }
}