package com.example.umbeo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.Api;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.UserGetProfileResponse;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    AppDatabase db;
    private int SPLASH_TIME_OUT=1599;
    UserPreference preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = new UserPreference(getApplicationContext());
        setContentView(R.layout.activity_main);

        if (db == null) {
            db = AppDatabase.getInstance(getApplicationContext());
        }
        /// DeleteAllDB();


        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show start activity
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent= new Intent(MainActivity.this,intro1.class);
                    startActivity(intent);
                    finish();
                }
            },SPLASH_TIME_OUT);
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();

        if(!isFirstRun)
        {

            try {
                String token = getIntent().getStringExtra("token");
                getProfile(token);

            } catch (Exception e) {
                e.printStackTrace();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    int i = getIntent().getIntExtra("intent",0);
                    try {
                        String token = getIntent().getStringExtra("token");
                        getProfile(token);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(i==1) {
                        Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, HomeScreenActivity.class);
                        startActivity(intent);
                    }
                }
            },SPLASH_TIME_OUT);
        }


    }


    private void onCallBtnClick(){
        if (Build.VERSION.SDK_INT < 23) {
        }else {

            if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            }else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        switch(requestCode){
            case 9:
                permissionGranted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                break;
        }
        if(permissionGranted){
        }else {
        }
    }

    private void getProfile(final String tokens){
        RetrofitClient api_manager = new RetrofitClient();
        Api retrofit_interface =api_manager.usersClient().create(Api.class);

        String token = "Bearer "+tokens;

        Call<UserGetProfileResponse> call= retrofit_interface.getProfile(token);

        call.enqueue(new Callback<UserGetProfileResponse>() {
            @Override
            public void onResponse(Call<UserGetProfileResponse> call, Response<UserGetProfileResponse> response) {
                Log.e("UserGetProfileResponse",response.code()+"");
                Log.e("UserGetProfileResponse",response.message()+"");

                if(response.code()==200) {
                    preference.setUserName(response.body().getData().getName());
                    preference.setEmail(response.body().getData().getEmail());
                    preference.setLoyaltyPoints(response.body().getData().getLoyaltyPoints());
                    preference.setAddresses(response.body().getData().getDeliveryAddresses());
                    preference.setProfilePic(response.body().getData().getProfile_pic());
                    preference.setUserId(response.body().getData().getId());
                    preference.setToken(tokens);

                    Log.e("UserGetProfileResponse",response.body().getData().getDeliveryAddresses().toString());

                }
            }

            @Override
            public void onFailure(Call<UserGetProfileResponse> call, Throwable t) {

            }
        });
    }

    private void DeleteAllDB(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    db.cartDao().nukeTable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
