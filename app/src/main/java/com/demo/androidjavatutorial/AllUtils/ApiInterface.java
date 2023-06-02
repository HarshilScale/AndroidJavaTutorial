package com.demo.androidjavatutorial.AllUtils;

import com.demo.androidjavatutorial.RetrofitDemo.Model.AlbumsData;
import com.demo.androidjavatutorial.RetrofitDemo.Model.PhotoData;
import com.demo.androidjavatutorial.RetrofitDemo.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("users")
    Call<List<User>> getUsers();

    @GET("albums")
        Call<List<AlbumsData>> getTitle(@Query("userId") int userid);

    @GET("photos")
    Call<List<PhotoData>> getPhoto(@Query("albumId") int useid);

}