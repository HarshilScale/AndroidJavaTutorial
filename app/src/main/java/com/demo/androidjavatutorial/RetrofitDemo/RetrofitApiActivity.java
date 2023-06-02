package com.demo.androidjavatutorial.RetrofitDemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.demo.androidjavatutorial.MainActivity;
import com.demo.androidjavatutorial.RetrofitDemo.Adapter.MainAdapter;
import com.demo.androidjavatutorial.RetrofitDemo.Utils.Loder;
import com.demo.androidjavatutorial.AllUtils.ApiClient;
import com.demo.androidjavatutorial.AllUtils.ApiInterface;
import com.example.androidjavatutorial.R;
import com.demo.androidjavatutorial.RetrofitDemo.Model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitApiActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    EditText edt_search_text;
    TextView txt_refersh;
    ImageView iv_search, iv_clear_text;
    LottieAnimationView animation_view, lottie_data;
    RecyclerView recycler;
    MainAdapter myAdapter;
    List<User> arrayList;
    boolean connected = false;
    Loder loder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_api);

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

    private void initView() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        edt_search_text = findViewById(R.id.edt_search_text);
        txt_refersh = findViewById(R.id.txt_refersh);
        iv_search = findViewById(R.id.iv_search);
        iv_clear_text = findViewById(R.id.iv_clear_text);
        animation_view = findViewById(R.id.animation_view);
        lottie_data = findViewById(R.id.lottie_data);
        recycler = findViewById(R.id.recycler);

        arrayList = new ArrayList<>();
        recycler = findViewById(R.id.recycler);
        LinearLayoutManager manager = new LinearLayoutManager(RetrofitApiActivity.this);
        recycler.setLayoutManager(manager);

        edt_search_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        edt_search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    iv_clear_text.setVisibility(View.GONE);
                    iv_search.setVisibility(View.VISIBLE);
                } else {
                    iv_clear_text.setVisibility(View.VISIBLE);
                    iv_search.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                myAdapter.getFilter().filter(text);
                iv_search.setVisibility(View.GONE);
                iv_clear_text.setVisibility(View.VISIBLE);
            }
        });

        iv_clear_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_search_text.setText("");
                iv_clear_text.setVisibility(View.GONE);
                iv_search.setVisibility(View.VISIBLE);
            }
        });

    }

    private void getApi() {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        apiInterface.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if (response != null) {
                    arrayList = response.body();
                    myAdapter = new MainAdapter(RetrofitApiActivity.this, arrayList);
                    recycler.setAdapter(myAdapter);
                    loder.dismissDialog();
                    System.out.println("RetrofitApiActivity" + arrayList);
                    lottie_data.setVisibility(View.GONE);
                } else {
                    animation_view.setVisibility(View.GONE);
                    lottie_data.setVisibility(View.VISIBLE);

                    txt_refersh.setVisibility(View.GONE);
                    Toast.makeText(RetrofitApiActivity.this, "Data Not Found!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                System.out.println("Error");
            }
        });
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

    @SuppressLint("MissingPermission")
    public void checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
            getApi();
            txt_refersh.setVisibility(View.GONE);
            animation_view.setVisibility(View.GONE);
        } else {
            if (arrayList.isEmpty()) {
                arrayList.clear();
            }
            myAdapter = new MainAdapter(RetrofitApiActivity.this, arrayList);
            recycler.setAdapter(myAdapter);
            txt_refersh.setVisibility(View.VISIBLE);
            animation_view.setVisibility(View.VISIBLE);
            connected = false;
            Toast.makeText(RetrofitApiActivity.this, "Check Internet Connection!", Toast.LENGTH_LONG).show();
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
        startActivity(new Intent(RetrofitApiActivity.this, MainActivity.class));
        finish();
    }
}