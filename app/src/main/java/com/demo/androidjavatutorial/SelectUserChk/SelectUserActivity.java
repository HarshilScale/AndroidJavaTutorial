package com.demo.androidjavatutorial.SelectUserChk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.demo.androidjavatutorial.MainActivity;
import com.example.androidjavatutorial.R;

import java.util.ArrayList;

public class SelectUserActivity extends AppCompatActivity {

    RecyclerView user_recyclerView;
    TextView txt_count;
    SelectuserAdapter selectuserAdapter;
    ArrayList<SelectUserModel> selectUserModelList = new ArrayList<>();
    ArrayList<SelectUserModel> selectedarraylist = new ArrayList<>();
    String[] name = {"Hello", "World", "Welcome", "Best", "Good Morning", "Good Nigth", "Morning", "Afternoon", "Happy"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        user_recyclerView = findViewById(R.id.user_recyclerView);
        txt_count = findViewById(R.id.txt_count);

        for (int i = 0; i < name.length; i++) {
            SelectUserModel selectUserModel = new SelectUserModel(name[i]);
            selectUserModelList.add(selectUserModel);
        }

        selectuserAdapter = new SelectuserAdapter(this, selectUserModelList);
        user_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        user_recyclerView.setAdapter(selectuserAdapter);

        selectuserAdapter.setOnItemClickListener(new SelectuserAdapter.ClickListener() {
            @Override
            public void onItemClick(SelectUserModel model, boolean isAddTo) {
                if (isAddTo) {
                    selectedarraylist.add(model);
                } else {
                    selectedarraylist.remove(model);
                }
                selectuserAdapter.notifyDataSetChanged();
                txt_count.setText(String.valueOf(selectedarraylist.size() + " Selected User"));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SelectUserActivity.this, MainActivity.class));
        finish();
    }
}