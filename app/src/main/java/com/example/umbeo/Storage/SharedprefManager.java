package com.example.umbeo.Storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedprefManager {

    private static final String SHARED_PREF_NAME="my_shared_preference";

    private static  SharedprefManager mInstance;
    private Context mCtx;

    private SharedprefManager(Context mCtx){
        this.mCtx=mCtx;
    }

    public static synchronized SharedprefManager getInstance(Context mCtx){
        if(mInstance == null){
            mInstance = new SharedprefManager(mCtx);
        }
        return mInstance;
    }

    public void saveToken(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("token", token);

        editor.apply();
    }

    public String getToken(){

        SharedPreferences sharedPreferences= mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        String token= sharedPreferences.getString("token",null);

        return token;
    }

    public void clear(){
        SharedPreferences sharedPreferences= mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }



}
