package com.demo.androidjavatutorial.MultipalDataSelect;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.androidjavatutorial.MainActivity;
import com.example.androidjavatutorial.R;

import java.util.ArrayList;

public class MultipalDataSelectActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Model> mData = new ArrayList<>();
    TextView txt;
    ImageView img;
    // action mode
    public static boolean isInActionMode = false;
    public static ArrayList<Model> selectionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multipal_data_select);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txt = findViewById(R.id.txt);
        img = findViewById(R.id.img);
        setSupportActionBar(toolbar);

        // recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // create data
        String[] array = getResources().getStringArray(R.array.array_text);
        for (String text : array) {
            Model model = new Model(R.mipmap.ic_launcher, text);
            mData.add(model);
        }


        // adapter
        mAdapter = new MulpalDataAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    public void prepareToolbar(int position) {

        // prepare action mode
        txt.setVisibility(View.GONE);
        img.setVisibility(View.GONE);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_action_mode);
        isInActionMode = true;
        mAdapter.notifyDataSetChanged();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        prepareSelection(position);
    }


    public void prepareSelection(int position) {

        if (!selectionList.contains(mData.get(position))) {
            selectionList.add(mData.get(position));
        } else {
            selectionList.remove(mData.get(position));
        }

        updateViewCounter();
    }


    private void updateViewCounter() {
        int counter = selectionList.size();
        if (!selectionList.isEmpty()) {
            // edit
            toolbar.getMenu().getItem(0).setVisible(true);
        } else {
            isInActionMode = false;
            toolbar.getMenu().clear();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
            toolbar.setTitle("");
            txt.setVisibility(View.VISIBLE);
            img.setVisibility(View.VISIBLE);
        }

//        toolbar.setTitle(counter + " item(s) selected");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_edit) {
            if (selectionList.size() == 1) {
                final EditText editText = new EditText(this);
                new AlertDialog.Builder(this)
                        .setTitle("Edit")
                        .setView(editText)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Model model = selectionList.get(0);
                                model.setText(editText.getText().toString());
                                isInActionMode = false;

                                ((MulpalDataAdapter) mAdapter).changeDataItem(getCheckedLastPosition(), model);
                                clearActionMode();
                            }
                        })
                        .create().show();
            }
        } else if (item.getItemId() == R.id.item_delete) {
            isInActionMode = false;
            ((MulpalDataAdapter) mAdapter).removeData(selectionList);
            clearActionMode();
        } else if (item.getItemId() == R.id.item_setting) {
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.search) {
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == android.R.id.home) {
            clearActionMode();
            mAdapter.notifyDataSetChanged();
        }

        return true;
    }

    public void clearActionMode() {
        isInActionMode = false;
        toolbar.getMenu().clear();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        toolbar.setTitle("");
        txt.setVisibility(View.VISIBLE);
        img.setVisibility(View.VISIBLE);
        selectionList.clear();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBackPressed() {
        if (isInActionMode) {
            clearActionMode();
            mAdapter.notifyDataSetChanged();
        } else {
            startActivity(new Intent(MultipalDataSelectActivity.this, MainActivity.class));
            finish();
        }
    }

    public int getCheckedLastPosition() {
        ArrayList<Model> dataSet = MulpalDataAdapter.getDataSet();
        for (int i = 0; i < dataSet.size(); i++) {
            if (dataSet.get(i).equals(selectionList.get(0))) {
                return i;
            }
        }
        return 0;
    }
}