package com.demo.androidjavatutorial.ExpandableRecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidjavatutorial.R;

import java.util.List;

public class ExpandableAdapter extends RecyclerView.Adapter<ExpandableAdapter.ViewHolder> {

    Activity activity;
    List<ExpandableModel> ExpandableModels;
    LayoutInflater inflater;

    public ExpandableAdapter(ExpandableRecyclerViewActivity ExpandableRecyclerViewActivity, List<ExpandableModel> ExpandableModels) {
        this.activity = ExpandableRecyclerViewActivity;
        this.ExpandableModels = ExpandableModels;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_expandable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_faq.setText(ExpandableModels.get(position).getName());
        holder.txt_description.setText(ExpandableModels.get(position).getDescription());

        boolean isExpandable = ExpandableModels.get(position).isExpandable();
        holder.expandable_layout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        holder.img_down_arrow.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        holder.img_up_arrow.setVisibility(isExpandable ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return ExpandableModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_faq, txt_description;
        ImageView img_up_arrow, img_down_arrow;
        RelativeLayout expandable_layout;
        RelativeLayout relative_layout;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_faq = itemView.findViewById(R.id.txt_faq);
            txt_description = itemView.findViewById(R.id.txt_description);
            img_up_arrow = itemView.findViewById(R.id.img_up_arrow);
            img_down_arrow = itemView.findViewById(R.id.img_down_arrow);
            relative_layout = itemView.findViewById(R.id.relative_layout);
            expandable_layout = itemView.findViewById(R.id.expandable_layout);
            view = itemView.findViewById(R.id.view);

            relative_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExpandableModel ExpandableModel = ExpandableModels.get(getAdapterPosition());
                    ExpandableModel.setExpandable(!ExpandableModel.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}