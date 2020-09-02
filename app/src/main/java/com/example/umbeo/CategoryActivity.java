package com.example.umbeo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.UsersApi;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.ProductModel;
import com.example.umbeo.response_data.ProductResponse;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.CartEntity;
import com.example.umbeo.room.ProductEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import spencerstudios.com.bungeelib.Bungee;

import static com.example.umbeo.HomeScreenActivity.preference;

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
    ImageView title_img,back_btn,cart_btn, add, remove, add2, remove2, add3, remove3;
    TextView category_name, quantity, quantity3, quantity2;

    String category_id="",categoryName="";
    ProgressBar simpleProgressBar;
    List<String> itemList = new ArrayList<String>();
    AutoCompleteTextView autoComplete;
    List<CartEntity> entities = new ArrayList<>();
    List<ProductEntity> productModels = new ArrayList<>();
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
        title_img = findViewById(R.id.title_img);

        if(BuildConfig.USER_TYPE.equalsIgnoreCase("fashion")){
            title_img.setImageResource(R.drawable.imagesfashion);
        }
        else title_img.setImageResource(R.drawable.farmers);

        simpleProgressBar = findViewById(R.id.simpleProgressBar);

        simpleProgressBar.setVisibility(View.VISIBLE);
        item_recycler = findViewById(R.id.item_recycler);

        try {
            category_id = getIntent().getStringExtra("category_id");
            categoryName = getIntent().getStringExtra("category_name");
            category_name.setText(categoryName+"");
            if (db == null) {
                db = AppDatabase.getInstance(getApplicationContext());
            }

            LoadAllProduct();
            LoadAllDB();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(category_id.equalsIgnoreCase("0"))
        {
            simpleProgressBar.setVisibility(View.GONE);
            category_name.setText("Searched: "+ categoryName);
            String carListAsString = getIntent().getStringExtra("category_prods");
            Gson gson = new Gson();
            Type type = new TypeToken<List<ProductEntity>>(){}.getType();
            List<ProductEntity> productEntities = gson.fromJson(carListAsString, type);
            Log.e("Testing"," "+categoryName+" "+productEntities.get(0).getName());
            productModels = productEntities;
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
            item_recycler.setLayoutManager(gridLayoutManager);
            ItemAdapter myAdapter = new ItemAdapter(productModels, CategoryActivity.this);
            item_recycler.setAdapter(myAdapter);

        }
        back_btn = findViewById(R.id.back_btn);
        cart_btn = findViewById(R.id.cart_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                HomeScreenActivity.viewPager.setCurrentItem(0);
            }
        });
        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                HomeScreenActivity.viewPager.setCurrentItem(1);
            }
        });
        editAddress = findViewById(R.id.editAddress);
        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapActivity.class));
                Bungee.fade(CategoryActivity.this);
            }
        });

        autoComplete = findViewById(R.id.search);
        autoComplete.clearFocus();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        autoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==0){
                    LoadAllProduct();
                }
                if(s.toString().contains(" ") ||s.toString().length()>3)
                filter(s.toString());
            }
        });


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>
                (getApplicationContext(),android.R.layout.select_dialog_item,itemList);
        autoComplete.setThreshold(1);//will start working from first character
        autoComplete.setAdapter(adapter1);
    }

    private void LoadAllProduct() {
        productModels = new ArrayList<>();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                productModels = AppDatabase.getInstance(CategoryActivity.this).productDao().findById(category_id,true);
                Log.e("ProductResponse", productModels + "");
                for (ProductEntity p :productModels){
                    itemList.add(p.getName());
                }

                Log.e("SEARCHLIST","12...  "+itemList.toString());


            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
        item_recycler.setLayoutManager(gridLayoutManager);
        myAdapter = new ItemAdapter(productModels, CategoryActivity.this);
        item_recycler.setAdapter(myAdapter);


        if (productModels.size() == 0) {
            UserPreference preference = new UserPreference(this);
            getProducts(preference.getShopId());
        }
    }

    private void LoadAllDB() {


        db.cartDao().getAll().observe(CategoryActivity.this, new Observer<List<CartEntity>>() {
            @Override
            public void onChanged(List<CartEntity> cartEntities) {
                entities = cartEntities;
            }
        });

    }

    private void getProducts(final String shopId) {

            RetrofitClient api_manager = new RetrofitClient();
            UsersApi retrofit_interface = api_manager.usersClient().create(UsersApi.class);

            Call<ProductResponse> call = retrofit_interface.fetchAllProducts(shopId, category_id);

            call.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                    try {
                        Log.e("ProductResponse", response + "");
                        Log.e("ProductResponse", response.code() + "");
                        Log.e("ProductResponse", response.message() + "");
                        if (response.code() == 200) {
                            List<ProductEntity> productModels = response.body().getData().getProducts();
                            Log.e("ProductResponse", productModels + "");

                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);

                            item_recycler.setLayoutManager(gridLayoutManager);
                            myAdapter = new ItemAdapter(productModels, CategoryActivity.this);
                            item_recycler.setAdapter(myAdapter);


                            simpleProgressBar.setVisibility(View.INVISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                    }
                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                getProducts(shopId);
                }
            });
        }



    void filter(String text){
        List<ProductEntity> temp = new ArrayList();
        for(ProductEntity p: productModels){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(p.getName().toLowerCase().contains(text.toLowerCase().replace(" ",""))){
                temp.add(p);
            }
        }
        //update recyclerview
        updateList(temp);
    }

    public void updateList(List<ProductEntity> list){
        productModels = list;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
        item_recycler.setLayoutManager(gridLayoutManager);
        myAdapter = new ItemAdapter(productModels, CategoryActivity.this);
        item_recycler.setAdapter(myAdapter);

        myAdapter.notifyDataSetChanged();
    }
}