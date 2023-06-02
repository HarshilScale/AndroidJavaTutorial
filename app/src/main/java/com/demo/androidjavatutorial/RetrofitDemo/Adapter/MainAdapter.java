package com.demo.androidjavatutorial.RetrofitDemo.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidjavatutorial.R;
import com.demo.androidjavatutorial.RetrofitDemo.Activity.AlbumActivity;
import com.demo.androidjavatutorial.RetrofitDemo.Model.User;
import com.demo.androidjavatutorial.RetrofitDemo.RetrofitApiActivity;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> implements Filterable {

    Activity activity;
    List<User> userList;
    List<User> userDataList;
    LayoutInflater inflater;

    public MainAdapter(RetrofitApiActivity mainActivity, List<User> arrayList) {
        this.activity = mainActivity;
        this.userList = arrayList;
        this.userDataList = arrayList;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.retrofit_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(userList.get(position).getName());
        holder.username.setText(userList.get(position).getUsername());
        holder.email.setText(userList.get(position).getEmail());

        holder.street.setText(userList.get(position).getAddress().getStreet());
        holder.suite.setText(userList.get(position).getAddress().getSuite());
        holder.city.setText(userList.get(position).getAddress().getCity());
        holder.zipcode.setText(userList.get(position).getAddress().getZipcode());
        holder.phone.setText(userList.get(position).getPhone());
        holder.website.setText(userList.get(position).getWebsite());
        holder.namec.setText(userList.get(position).getCompany().getName());
        holder.catchPhrase.setText(userList.get(position).getCompany().getCatchPhrase());
        holder.bs.setText(userList.get(position).getCompany().getBs());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = activity.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("userId", userList.get(position).getId());
                editor.putString("name", userList.get(position).getName());
                editor.apply();
                Intent intent = new Intent(activity, AlbumActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, username, email;
        TextView street, suite, city, zipcode;
        TextView phone, website;
        TextView namec, catchPhrase, bs;
        LinearLayout card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            username = itemView.findViewById(R.id.username);
            email = itemView.findViewById(R.id.email);
            street = itemView.findViewById(R.id.street);
            suite = itemView.findViewById(R.id.suite);
            city = itemView.findViewById(R.id.city);
            zipcode = itemView.findViewById(R.id.zipcode);
            phone = itemView.findViewById(R.id.phone);
            website = itemView.findViewById(R.id.website);
            namec = itemView.findViewById(R.id.C_name);
            catchPhrase = itemView.findViewById(R.id.catchPhrase);
            bs = itemView.findViewById(R.id.bs);
            card = itemView.findViewById(R.id.card);
        }
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {

                    userList = userDataList;

                } else {

                    ArrayList<User> tempFilteredList = new ArrayList<>();

                    for (User user : userDataList) {

                        // search for user title
                        if (user.getName().toLowerCase().contains(searchString) || user.getEmail().toLowerCase().contains(searchString)) {
                            tempFilteredList.add(user);
                        }
                    }

                    userList = tempFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = userList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                userList = (ArrayList<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
