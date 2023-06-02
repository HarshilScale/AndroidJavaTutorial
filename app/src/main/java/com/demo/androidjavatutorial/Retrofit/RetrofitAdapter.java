package com.demo.androidjavatutorial.Retrofit;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidjavatutorial.R;

import java.util.List;

public class RetrofitAdapter extends RecyclerView.Adapter<RetrofitAdapter.ViewHolder> {

    Activity activity;
    List<RetrofitData> retrofitDataList;
    LayoutInflater inflater;

    public RetrofitAdapter(RetrofitActivity retrofitActivity, List<RetrofitData> retrofitData) {
        this.activity = retrofitActivity;
        this.retrofitDataList = retrofitData;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.volley_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtUserId.setText(String.valueOf(retrofitDataList.get(position).getUserId()));
        holder.txtId.setText(String.valueOf(retrofitDataList.get(position).getId()));
        holder.txtTitle.setText(retrofitDataList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return retrofitDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtUserId, txtId, txtTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserId = itemView.findViewById(R.id.txt_userId);
            txtId = itemView.findViewById(R.id.txt_id);
            txtTitle = itemView.findViewById(R.id.txt_title);
        }
    }
}
