package com.demo.androidjavatutorial.ExpandableRecyclerView;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.androidjavatutorial.MainActivity;
import com.example.androidjavatutorial.R;

import java.util.ArrayList;
import java.util.List;

public class ExpandableRecyclerViewActivity extends AppCompatActivity {

    RecyclerView help_recycler;
    ExpandableAdapter expandableAdapter;
    String[] name = {"FAQ-1", "FAQ-2", "FAQ-3", "FAQ-4", "FAQ-5"};
    String[] description = {"It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.", "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.", "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.", "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.", "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout."};
    List<ExpandableModel> helpModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_recycler_view);
        help_recycler = findViewById(R.id.help_recycler);

        helpModels = new ArrayList<>();

        for (int i = 0; i < name.length; i++) {
            ExpandableModel hModel = new ExpandableModel();
            hModel.setName(name[i]);
            hModel.setDescription(description[i]);
            helpModels.add(hModel);
        }

        expandableAdapter = new ExpandableAdapter(this, helpModels);
        help_recycler.setAdapter(expandableAdapter);
        help_recycler.setHasFixedSize(true);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ExpandableRecyclerViewActivity.this, MainActivity.class));
        finish();
    }
}