package com.demo.androidjavatutorial.SelectUserChk;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidjavatutorial.R;

import java.util.ArrayList;

public class SelectuserAdapter extends RecyclerView.Adapter<SelectuserAdapter.ViewHolder> {

    Activity activity;
    ArrayList<SelectUserModel> selectUserModelArrayList;
    LayoutInflater inflater;
    static ClickListener clickListener;

    public interface ClickListener {
        void onItemClick(SelectUserModel model, boolean isAddTo);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        SelectuserAdapter.clickListener = clickListener;
    }

    public SelectuserAdapter(SelectUserActivity selectUserActivity, ArrayList<SelectUserModel> selectUserModelList) {
        this.activity = selectUserActivity;
        this.selectUserModelArrayList = selectUserModelList;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_selectuser, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt.setText(selectUserModelArrayList.get(position).getUserName());

        holder.chk.setChecked(selectUserModelArrayList.get(position).isSelected);

        holder.chk.setTag(selectUserModelArrayList.get(position));

        holder.chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                SelectUserModel selectUserModel = (SelectUserModel) cb.getTag();

                if (!selectUserModelArrayList.get(position).isSelected) {
                    selectUserModel.setSelected(cb.isChecked());
                    selectUserModelArrayList.get(position).setSelected(cb.isChecked());
                    clickListener.onItemClick(selectUserModelArrayList.get(position), true);
                } else {
                    selectUserModel.setSelected(!cb.isChecked());
                    selectUserModelArrayList.get(position).setSelected(false);
                    clickListener.onItemClick(selectUserModelArrayList.get(position), false);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return selectUserModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txt;
        CheckBox chk;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            txt = itemView.findViewById(R.id.txt);
            chk = itemView.findViewById(R.id.chk);
        }
    }
}
