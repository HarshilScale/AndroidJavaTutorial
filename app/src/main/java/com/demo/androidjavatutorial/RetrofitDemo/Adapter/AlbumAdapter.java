package com.demo.androidjavatutorial.RetrofitDemo.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.androidjavatutorial.RetrofitDemo.Activity.AlbumActivity;
import com.demo.androidjavatutorial.RetrofitDemo.Activity.PhotoActivity;
import com.example.androidjavatutorial.R;
import com.demo.androidjavatutorial.RetrofitDemo.Model.AlbumsData;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    Activity activity;
    List<AlbumsData> albumsData;
    List<AlbumsData> albumsDataList;
    LayoutInflater inflater;
    int id;

    public AlbumAdapter(AlbumActivity albumActivity, List<AlbumsData> albumsDataList) {
        this.activity = albumActivity;
        this.albumsData = albumsDataList;
        this.albumsDataList = albumsDataList;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.album_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt_name.setText(albumsData.get(position).getTitle());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("album_id", albumsData.get(position).getId());
                editor.apply();
                Intent intent = new Intent(activity, PhotoActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumsData.size();
    }

    public void setUserList(List<AlbumsData> albumsDataList) {
        this.albumsData = albumsDataList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        CardView card_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_title);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}
