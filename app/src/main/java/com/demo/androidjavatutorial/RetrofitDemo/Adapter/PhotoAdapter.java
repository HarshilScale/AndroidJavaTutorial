package com.demo.androidjavatutorial.RetrofitDemo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.demo.androidjavatutorial.RetrofitDemo.Activity.ThiredActivity;
import com.example.androidjavatutorial.R;
import com.demo.androidjavatutorial.RetrofitDemo.Model.PhotoData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    Context activity;
    List<PhotoData> photoDataList;
    LayoutInflater inflater;
    public static final int SPAN_COUNT_ONE = 1;
    public static final int SPAN_COUNT_TWO = 2;

    private static final int VIEW_TYPE_SMALL = 1;
    private static final int VIEW_TYPE_BIG = 2;
    private GridLayoutManager mLayoutManager;

    public PhotoAdapter(Context photoActivity, GridLayoutManager gridLayoutManager, List<PhotoData> photoDataList) {
        this.activity = photoActivity;
        this.photoDataList = photoDataList;
        this.mLayoutManager = gridLayoutManager;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getItemViewType(int position) {
        int spanCount = mLayoutManager.getSpanCount();
        if (spanCount == SPAN_COUNT_ONE) {
            return VIEW_TYPE_BIG;
        } else {
            return VIEW_TYPE_SMALL;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_BIG) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        }
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt_title.setText(photoDataList.get(position).getTitle());
        Picasso.get().load(photoDataList.get(position).getUrl()).placeholder(R.drawable.cricle).into(holder.img);
        System.out.println("Img:--" + photoDataList.get(position).getUrl());

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ThiredActivity.class);
                intent.putParcelableArrayListExtra("myList", (ArrayList<? extends Parcelable>) photoDataList);
                intent.putExtra("pos", position);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txt_title;

        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            if (viewType == VIEW_TYPE_BIG) {
                img = itemView.findViewById(R.id.img);
                txt_title = itemView.findViewById(R.id.txt_title);
            } else {
                img = itemView.findViewById(R.id.img);
                txt_title = itemView.findViewById(R.id.txt_title);
            }

        }
    }
}
