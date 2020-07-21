package com.example.umbeo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.Api;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.ProductModel;
import com.example.umbeo.response_data.ProductResponse;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.CartEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    TextView editAddress;
    TextView lichipopupText, strawberryText, orangeText, pineappleText;
    Dialog mDialog;
    ImageView orange_plus, lichi_plus, strawberry_plus;
    static List<ItemModel> mFlowerList;
    static RecyclerView item_recycler;
    static ItemAdapter myAdapter;

    AppDatabase db;
    static int staw_count = 0, lichi_count = 0, orange_count = 0 ,quant = 0;
    LinearLayout straw_linear, orange_linear, lichi_linear;
    ImageView back_btn,cart_btn, add, remove, add2, remove2, add3, remove3;
    TextView category_name, quantity, quantity3, quantity2;

    String category_id="",categoryName="";

    List<CartEntity> entities = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserPreference preference = new UserPreference(this);
        if(preference.getTheme()==1){
            setContentView(R.layout.dark_category);
        }
        else
        setContentView(R.layout.activity_category);

        category_name = findViewById(R.id.category_name);


        try {
            category_id = getIntent().getStringExtra("category_id");
            categoryName = getIntent().getStringExtra("category_name");
            category_name.setText(categoryName+"");

            getProducts(preference.getShopId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(category_id == null)
        {
            category_name.setText("Deals");
        }



        back_btn = findViewById(R.id.back_btn);
        cart_btn = findViewById(R.id.cart_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() ,HomeScreenActivity.class));
            }
        });
        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                HomeScreenActivity.viewPager.setCurrentItem(1);
            }
        });

        if (db == null) {
            db = AppDatabase.getInstance(getApplicationContext());
        }

        LoadAllDB();

        item_recycler = findViewById(R.id.item_recycler);


        editAddress = findViewById(R.id.editAddress);
        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapActivity.class));
            }
        });
    }

    private void LoadAllDB() {


        db.cartDao().getAll().observe(CategoryActivity.this, new Observer<List<CartEntity>>() {
            @Override
            public void onChanged(List<CartEntity> cartEntities) {
                entities = cartEntities;
            }
        });

    }

    private void getProducts(String shopId) {
        RetrofitClient api_manager = new RetrofitClient();
        Api retrofit_interface =api_manager.usersClient().create(Api.class);

        Call<ProductResponse> call = retrofit_interface.fetchAllProducts(shopId,category_id);

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                try {
                    Log.e("ProductResponse",response+"");
                    Log.e("ProductResponse",response.code()+"");
                    Log.e("ProductResponse",response.message()+"");
                    if(response.code()==200){
                        List<ProductModel> productModels = response.body().getData().getProducts();
                        Log.e("ProductResponse",productModels+"");

                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2, LinearLayoutManager.VERTICAL,false);

                        item_recycler.setLayoutManager(gridLayoutManager);
                        myAdapter = new ItemAdapter(productModels,CategoryActivity.this);
                        item_recycler.setAdapter(myAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
            }
        });
    }

}