package com.demo.androidjavatutorial;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.demo.androidjavatutorial.CameraImage.CameraImageActivity;
import com.demo.androidjavatutorial.ChipTagView.TagActivity;
import com.demo.androidjavatutorial.Excel.ExcelActivity;
import com.demo.androidjavatutorial.ExpandableRecyclerView.ExpandableRecyclerViewActivity;
import com.demo.androidjavatutorial.GoogleMapSearch.MapSearchActivity;
import com.demo.androidjavatutorial.ImageSlider.ImageSliderActivity;
import com.demo.androidjavatutorial.MultipalDataSelect.MultipalDataSelectActivity;
import com.example.androidjavatutorial.R;
import com.demo.androidjavatutorial.Retrofit.RetrofitActivity;
import com.demo.androidjavatutorial.RetrofitDemo.RetrofitApiActivity;
import com.demo.androidjavatutorial.SelectUserChk.SelectUserActivity;
import com.demo.androidjavatutorial.Sqlite.SqliteActivity;
import com.demo.androidjavatutorial.UploadImage.UploadImageActivity;
import com.demo.androidjavatutorial.UploadPdf.UploadPdfActivity;
import com.demo.androidjavatutorial.UploadPdfVolleyApi.UploadPdfVolleyApiActivity;
import com.demo.androidjavatutorial.Volley.VolleyActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView txtMarquee, txt_timestamp;
    AppCompatButton btnVolley, btnRetrofit, btnSqlite, btnRetrofitAppDemo;
    AppCompatButton btnMultipalDataSelect, btnSelectUser, btnslider, btnCameraImages;
    AppCompatButton btnexpan, btnMapSearch, btnUploadImg, btnUploadPDF;
    AppCompatButton btnUploadPDFVolleyApi, btnUploadExcel, btnTagView;
    String iPos = "2";

    int i = 0;
    ArrayList<Integer> str = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMarquee = findViewById(R.id.txtMarquee);
        txt_timestamp = findViewById(R.id.txt_timestamp);
        btnVolley = findViewById(R.id.btnVolley);
        btnRetrofit = findViewById(R.id.btnRetrofit);
        btnSqlite = findViewById(R.id.btnSqlite);
        btnRetrofitAppDemo = findViewById(R.id.btnRetrofitAppDemo);
        btnMultipalDataSelect = findViewById(R.id.btnMultipalDataSelect);
        btnSelectUser = findViewById(R.id.btnSelectUser);
        btnexpan = findViewById(R.id.btnexpan);
        btnslider = findViewById(R.id.btnslider);
        btnMapSearch = findViewById(R.id.btnMapSearch);
        btnUploadImg = findViewById(R.id.btnUploadImg);
        btnUploadPDF = findViewById(R.id.btnUploadPDF);
        btnUploadPDFVolleyApi = findViewById(R.id.btnUploadPDFVolleyApi);
        btnUploadExcel = findViewById(R.id.btnUploadExcel);
        btnTagView = findViewById(R.id.btnTagView);
        btnCameraImages = findViewById(R.id.btnCameraImages);

        txtMarquee.setSelected(true);

        long timestamp = Long.parseLong("1670395432597") * 1000L;
        if (String.valueOf(timestamp) != null) {
            String time = getFormattedDate(timestamp);
            txt_timestamp.setText("Timestamp get time/date/status:- " + time);
        }

        btnVolley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VolleyActivity.class));
                finish();
            }
        });

        btnRetrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RetrofitActivity.class));
                finish();
            }
        });

        btnSqlite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SqliteActivity.class));
                finish();
            }
        });

        btnRetrofitAppDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RetrofitApiActivity.class));
                finish();
            }
        });

        btnMultipalDataSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MultipalDataSelectActivity.class));
                finish();
            }
        });

        btnSelectUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelectUserActivity.class));
                finish();
            }
        });

        btnslider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ImageSliderActivity.class));
                finish();
            }
        });

        btnexpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExpandableRecyclerViewActivity.class));
                finish();
            }
        });

        btnMapSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapSearchActivity.class));
                finish();
            }
        });

        btnUploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UploadImageActivity.class));
                finish();
            }
        });

        btnUploadPDFVolleyApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UploadPdfVolleyApiActivity.class));
                finish();
            }
        });

        btnUploadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UploadPdfActivity.class));
                finish();
            }
        });

        btnUploadExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExcelActivity.class));
                finish();
            }
        });

        btnTagView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TagActivity.class));
                finish();
            }
        });

        btnCameraImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CameraImageActivity.class));
                finish();
            }
        });

        str.add(1);
        str.add(2);
        str.add(4);

        for (int i = 0;i<str.size();i++){
            boolean flag1 = iPos.contains(str.get(i).toString());
            if (flag1 == true) {
                System.out.println("posTrue:" + "aList contains element 2");
            } else {
                System.out.println("aList doesn't contains element 2");
            }
        }


    }

    public String getFormattedDate(long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);

        Calendar now = Calendar.getInstance();

        java.text.DateFormat sdf = new SimpleDateFormat("hh:mm", Locale.US);
        Date netDate = (new Date(smsTimeInMilis));
        final String dateTimeFormatString = "EEEE, MMMM d, h:mm aa";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return sdf.format(netDate);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return "Yesterday";
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("dd/MM/yy", smsTime).toString();
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}