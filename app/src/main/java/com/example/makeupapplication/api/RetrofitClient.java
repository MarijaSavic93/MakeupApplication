package com.example.makeupapplication.api;

import android.content.Context;

import com.example.makeupapplication.connection.InternetConnectivityInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://makeup-api.herokuapp.com";

    private static RetrofitClient instance = null;
    private MakeupApi api;

    private RetrofitClient(Context context){
       OkHttpClient oktHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new InternetConnectivityInterceptor(context)).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(oktHttpClient).build();

        api = retrofit.create(MakeupApi.class);
    }

    public static RetrofitClient getInstance(Context context){
        if(instance == null){
            instance = new RetrofitClient(context);
        }
        return instance;
    }
    public MakeupApi getApi(){
        return api;
    }
}
