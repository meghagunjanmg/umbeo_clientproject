package com.example.umbeo.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL1="https://viristore.herokuapp.com/api/v1/users/";
    private static final String BASE_URL="https://viristore.herokuapp.com";
    private static final String SHOP_URL="https://viristoresh.herokuapp.com";


    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    public RetrofitClient(){

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public Retrofit usersClient() {
        if (retrofit == null)
        {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }


    public Retrofit shopClient() {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(SHOP_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public static synchronized RetrofitClient getmInstance(){
        if(mInstance==null){
            mInstance=new RetrofitClient();
        }
        return mInstance;
    }

    public UsersApi getApi(){
        return retrofit.create(UsersApi.class);
    }
}

