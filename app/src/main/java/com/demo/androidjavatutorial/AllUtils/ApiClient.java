package com.demo.androidjavatutorial.AllUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit retrofit = null;
    public static String URI = "https://jsonplaceholder.typicode.com/";

    public static Retrofit getRetrofit(){

        retrofit = new Retrofit.Builder()
                .baseUrl(URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

}
