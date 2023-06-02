package com.demo.androidjavatutorial.Volley;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidjavatutorial.R;

import java.util.List;

public class VolleyAdapter extends RecyclerView.Adapter<VolleyAdapter.MyViewHolder> {

    Activity activity;
    List<VolleyData> volleyDataList;
    LayoutInflater inflater;

    public VolleyAdapter(VolleyActivity volleyActivity, List<VolleyData> volleyDataList) {
        this.activity = volleyActivity;
        this.volleyDataList = volleyDataList;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.volley_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtUserId.setText(String.valueOf(volleyDataList.get(position).getUserId()));
        holder.txtId.setText(String.valueOf(volleyDataList.get(position).getId()));
        holder.txtTitle.setText(volleyDataList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return volleyDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtUserId, txtId, txtTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserId = itemView.findViewById(R.id.txt_userId);
            txtId = itemView.findViewById(R.id.txt_id);
            txtTitle = itemView.findViewById(R.id.txt_title);
        }
    }
}
