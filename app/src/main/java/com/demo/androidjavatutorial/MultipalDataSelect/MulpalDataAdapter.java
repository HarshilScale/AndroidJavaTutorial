package com.demo.androidjavatutorial.MultipalDataSelect;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.androidjavatutorial.R;

import java.util.ArrayList;

public class MulpalDataAdapter extends RecyclerView.Adapter<MulpalDataAdapter.ViewHolder> {

    private Activity mActivity;
    private static ArrayList<Model> mDataset;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView mImageView;
        TextView mTextView;
        View mView;

        ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tvRowItem);
            mImageView = (ImageView) v.findViewById(R.id.ivRowItem);
            mView = v;
            v.setOnLongClickListener(this);
            mImageView.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            ((MultipalDataSelectActivity) mActivity).prepareToolbar(getAdapterPosition());
            return true;
        }

        @Override
        public void onClick(View view) {

            if (MultipalDataSelectActivity.isInActionMode) {
                ((MultipalDataSelectActivity) mActivity).prepareSelection(getAdapterPosition());
                notifyItemChanged(getAdapterPosition());
            }
        }

    }

    MulpalDataAdapter(Activity activity, ArrayList<Model> myDataset) {
        this.mActivity = activity;
        this.mDataset = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_multipaldata, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Model model = mDataset.get(position);
        holder.mView.setBackgroundResource(R.color.white);
        holder.mTextView.setText(model.getText());
        holder.mImageView.setImageResource(model.getImage());

        if (MultipalDataSelectActivity.isInActionMode) {
            if (MultipalDataSelectActivity.selectionList.contains(mDataset.get(position))) {
                holder.mView.setBackgroundResource(R.color.grey_200);
                holder.mImageView.setImageResource(R.drawable.ic_launcher_foreground);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void removeData(ArrayList<Model> list) {
        for (Model model : list) {
            mDataset.remove(model);
        }
        notifyDataSetChanged();
    }

    public void changeDataItem(int position, Model model) {
        mDataset.set(position, model);
        notifyDataSetChanged();
    }

    // for edit
    public static ArrayList<Model> getDataSet() {
        return mDataset;
    }
}