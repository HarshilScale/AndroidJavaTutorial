package com.demo.androidjavatutorial.RetrofitDemo.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.demo.androidjavatutorial.RetrofitDemo.Activity.ThiredActivity;
import com.demo.androidjavatutorial.RetrofitDemo.Utils.TouchViewPagerIMageView;
import com.example.androidjavatutorial.R;
import com.demo.androidjavatutorial.RetrofitDemo.Model.PhotoData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    Activity activity;
    List<PhotoData> photoDataList;
    TouchViewPagerIMageView imageView;

    public ViewPagerAdapter(ThiredActivity thiredActivity, List<PhotoData> photoDataList) {
        this.activity = thiredActivity;
        this.photoDataList = photoDataList;
    }

    @Override
    public int getCount() {
        return photoDataList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        imageView = new TouchViewPagerIMageView(activity);
        Picasso.get()
                .load(photoDataList.get(position).getUrl())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}
