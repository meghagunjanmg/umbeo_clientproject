package com.example.umbeo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.UsersApi;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.ProductResponse;
import com.example.umbeo.response_data.shop.ShopResponse;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.CategoryModel;
import com.example.umbeo.room.ProductEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import spencerstudios.com.bungeelib.Bungee;

public class MainActivity extends AppCompatActivity {
    AppDatabase db;
    private int SPLASH_TIME_OUT=1000;
    UserPreference preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = new UserPreference(getApplicationContext());
        setContentView(R.layout.activity_main);

        if (db == null) {
            db = AppDatabase.getInstance(getApplicationContext());
        }

        shopData();
        getProducts(preference.getShopId());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //getProducts(preference.getShopId());
                //shopData();
            }
        });

       // intent();
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

    private void DeleteAllDB(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    db.productDao().nukeTable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void InsertAllDB(final List<ProductEntity> productEntities){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    db.productDao().insertAll(productEntities);
                    Log.e("ProductResponse","DB INSERT");
                    Log.e("SEARCHLIST","123  "+productEntities.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void shopData(){
        RetrofitClient api_manager = new RetrofitClient();
        UsersApi retrofit_interface =api_manager.usersClient().create(UsersApi.class);

        Call<ShopResponse> call= retrofit_interface.getShopProfile(preference.getShopId());

        call.enqueue(new Callback<ShopResponse>() {
            @Override
            public void onResponse(Call<ShopResponse> call, final Response<ShopResponse> response) {
                try {
                    Log.e("shopResponse", response.body().getStatus() + "");
                    Log.e("shopResponse", response.code() + "");
                    Log.e("shopResponse", response.message() + "");
                    Log.e("shopResponse",response.body().getData().getCategories().toString());

                    preference.setShopTimeSlot(response.body().getData().getDeliverySlots());
                    preference.setShopDeliveryCharges(response.body().getData().getDeliveryCharges());
                    preference.setShopPh(response.body().getData().getPhone());
                    preference.setShopCategory(response.body().getData().getCategories());
                    preference.setCurrency(response.body().getData().getCurrency());



                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            db.productDao().nukeCategory();
                        }
                    });

                    DashBoardFragment.categoryModel = new ArrayList<>();
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            db.productDao().insertAllCategory(response.body().getCategories());
                            DashBoardFragment.categoryModel = db.productDao().loadAllCategory();
                            Log.e("SEARCHLIST","1.0  "+response.body().getCategories().toString()+"\n"+DashBoardFragment.categoryModel.toString());

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ShopResponse> call, Throwable t) {
                shopData();
                Log.e("shopResponse",t.getLocalizedMessage()+"");
            }
        });
    }

    private void intent(){
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

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    int i = getIntent().getIntExtra("intent",0);
                    if(i==1) {
                        Intent intent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        Bungee.fade(MainActivity.this);
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, HomeScreenActivity.class);
                        startActivity(intent);
                        Bungee.fade(MainActivity.this);
                    }
                }
            },SPLASH_TIME_OUT);
        }
    }


    private void getProducts(final String shopId) {
        RetrofitClient api_manager = new RetrofitClient();
        UsersApi retrofit_interface =api_manager.usersClient().create(UsersApi.class);

        Call<ProductResponse> call = retrofit_interface.fetchAllProducts(shopId);

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                try {
                    Log.e("ProductResponse",response+"");
                    Log.e("ProductResponse",response.code()+"");
                    Log.e("ProductResponse",response.message()+"");
                    if(response.code()==200){
                        List<ProductEntity> productModels = response.body().getData().getProducts();
                        DeleteAllDB();
                        InsertAllDB(productModels);
                        intent();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("ProductResponse",t.getLocalizedMessage()+"");
                Toast.makeText(getApplicationContext(),"Check your Internet connection and try again",Toast.LENGTH_LONG).show();
                getProducts(shopId);
            }
        });
    }

}
