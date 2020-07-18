package com.example.umbeo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

    String category_id,categoryName;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        straw_linear =findViewById(R.id.strawberry_linear);
        add = findViewById(R.id.add);
        remove = findViewById(R.id.remove);
        quantity = findViewById(R.id.quantity);
        category_name = findViewById(R.id.category_name);

        UserPreference preference = new UserPreference(this);

        category_id = getIntent().getStringExtra("category_id");
        categoryName = getIntent().getStringExtra("category_name");
        category_name.setText(categoryName+"");

        getProducts(preference.getShopId());

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
                Intent i = new Intent(getApplicationContext(),HomeScreenActivity.class);
                i.putExtra("Cat",5);
                startActivity(i);

            }
        });

        orange_linear = findViewById(R.id.orange_linear);
        add3 = findViewById(R.id.add3);
        remove3 = findViewById(R.id.remove3);
        quantity3 =findViewById(R.id.quantity3);

        lichi_linear = findViewById(R.id.lichi_linear);
        add2 = findViewById(R.id.add1);
        remove2 = findViewById(R.id.remove1);
        quantity2 = findViewById(R.id.quantity1);
        if (db == null) {
            db = AppDatabase.getInstance(getApplicationContext());
        }

        LoadAllDB();

        item_recycler = findViewById(R.id.item_recycler);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this,2);
        item_recycler.setLayoutManager(mGridLayoutManager);

        mFlowerList = new ArrayList<>();

        int Cat = getIntent().getIntExtra("Cat",0);

        if(Cat==1){
            ImageView title_img = findViewById(R.id.title_img);
            int res = getResources().getIdentifier("farmers", "drawable", this.getPackageName());
            title_img.setImageResource(res);


            mFlowerList.add(new ItemModel("Apple","pic_0",0));
            mFlowerList.add(new ItemModel("Lichi","pic_1",0));
            mFlowerList.add(new ItemModel("Apple","pic_0",0));
            mFlowerList.add(new ItemModel("Lichi","pic_1",0));
            mFlowerList.add(new ItemModel("Apple","pic_0",0));
            mFlowerList.add(new ItemModel("Lichi","pic_1",0));
            mFlowerList.add(new ItemModel("Apple","pic_0",0));
            mFlowerList.add(new ItemModel("Lichi","pic_1",0));
        }
        else if(Cat==2){
            ImageView title_img = findViewById(R.id.title_img);
            int res = getResources().getIdentifier("personals", "drawable", this.getPackageName());
            title_img.setImageResource(res);

            mFlowerList.add(new ItemModel("Colgate","pic_2",0));
            mFlowerList.add(new ItemModel("Hair oil","pic_3",0));
            mFlowerList.add(new ItemModel("Colgate","pic_2",0));
            mFlowerList.add(new ItemModel("Hair oil","pic_3",0));
            mFlowerList.add(new ItemModel("Colgate","pic_2",0));
            mFlowerList.add(new ItemModel("Hair oil","pic_3",0));
            mFlowerList.add(new ItemModel("Colgate","pic_2",0));
            mFlowerList.add(new ItemModel("Hair oil","pic_3",0));

        }

      //  myAdapter = new ItemAdapter(mFlowerList, this);
        item_recycler.setAdapter(myAdapter);


        editAddress = findViewById(R.id.editAddress);
        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapActivity.class));
            }
        });

       LinearLayout lichi = findViewById(R.id.lichi);
       LinearLayout orange = findViewById(R.id.orange);
       LinearLayout strawberry = findViewById(R.id.strawberry);


        strawberry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailog("strawberry",staw_count);
            }
        });


        lichi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dailog("lichi",lichi_count);
            }
        });


        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailog("orange",orange_count);
            }
        });


        orange_plus = findViewById(R.id.orange_plus);
        orange_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orange_count++;
                if (orange_count > 0) {
                    orange_linear.setVisibility(View.VISIBLE);
                    orange_plus.setVisibility(View.GONE);
                    quantity3.setText(orange_count + "");
                } else {

                }

                Toast.makeText(getApplicationContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("orange");
                addDB(new CartEntity("orange", Integer.parseInt(quantity3.getText().toString()), 5));
            }
        });
        lichi_plus = findViewById(R.id.lichi_plus);
        lichi_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lichi_count++;
                if (lichi_count > 0) {
                    lichi_linear.setVisibility(View.VISIBLE);
                    lichi_plus.setVisibility(View.GONE);
                    quantity2.setText(lichi_count + "");
                } else {

                }
                Toast.makeText(getApplicationContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("lichi");
                addDB(new CartEntity("lichi", Integer.parseInt(quantity2.getText().toString()), 5));
            }
        });
        strawberry_plus = findViewById(R.id.strawberry_plus);
        strawberry_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staw_count++;
                if (staw_count > 0) {
                    straw_linear.setVisibility(View.VISIBLE);
                    strawberry_plus.setVisibility(View.GONE);
                    quantity.setText(staw_count + "");
                } else {

                }
                Toast.makeText(getApplicationContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("strawberry");
                addDB(new CartEntity("strawberry", Integer.parseInt(quantity.getText().toString()), 5));
            }
        });

       /*      orange = (ImageView)v.findViewById(R.id.orange);
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();
            }
        });

   pineappleText= (TextView)v.findViewById(R.id.pineappletext);
        pineappleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.popup1_pineapple, null);
                Button addtocart = mView.findViewById(R.id.button);

                addtocart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();

                        startActivity(new Intent(getContext(),login.class));

                    }
                });

                ImageView img= mView.findViewById(R.id.image);

                mBuilder.setView(mView);

                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
        pineapple=(ImageView)v.findViewById(R.id.pineapple);
        pineapple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();
            }
        });

        strawbe=(ImageView)v.findViewById(R.id.strawberry);
        strawbe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();

            }
        });


        strawberryText = (TextView)v.findViewById(R.id.straaaa);
        strawberryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.popup1_strawberry, null);
                Button addtocart = mView.findViewById(R.id.button);

                addtocart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getContext(),login.class));

                    }
                });

                ImageView img= mView.findViewById(R.id.image);

                mBuilder.setView(mView);

                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

         */
    }

    private void getProducts(String shopId) {

        RetrofitClient api_manager = new RetrofitClient();
        Api retrofit_interface =api_manager.usersClient().create(Api.class);

        Call<ProductResponse> call = retrofit_interface.fetchAllProducts(shopId,category_id);

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                Log.e("ProductResponse",response+"");
                Log.e("ProductResponse",response.code()+"");
                Log.e("ProductResponse",response.message()+"");
                Log.e("ProductResponse",response.body().getStatus()+"");
                if(response.code()==200){
                    List<ProductModel> productModels = response.body().getData().getProducts();
                    Log.e("ProductResponse",productModels+"");
                    GridLayoutManager mGridLayoutManager = new GridLayoutManager(CategoryActivity.this,2);
                    item_recycler.setLayoutManager(mGridLayoutManager);
                    myAdapter = new ItemAdapter(productModels,CategoryActivity.this);
                    item_recycler.setAdapter(myAdapter);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of HomeFragment");
        super.onResume();
        quantity.setText(staw_count+"");
        if(staw_count==0){
            straw_linear.setVisibility(View.GONE);
            strawberry_plus.setVisibility(View.VISIBLE);
            DeleteDB("strawberry");
            addDB(new CartEntity("strawberry",Integer.parseInt(quantity.getText().toString()),5));
        }
        else {
            straw_linear.setVisibility(View.VISIBLE);
            strawberry_plus.setVisibility(View.GONE);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staw_count++;
                quantity.setText(staw_count+"");
                Toast.makeText(getApplicationContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("strawberry");
                addDB(new CartEntity("strawberry",Integer.parseInt(quantity.getText().toString()),5));
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staw_count--;
                if(staw_count==0){
                    straw_linear.setVisibility(View.GONE);
                    strawberry_plus.setVisibility(View.VISIBLE);
                    DeleteDB("strawberry");
                    addDB(new CartEntity("strawberry",Integer.parseInt(quantity.getText().toString()),5));
                }

                quantity.setText(staw_count+"");
                Toast.makeText(getApplicationContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("strawberry");
                addDB(new CartEntity("strawberry",Integer.parseInt(quantity.getText().toString()),5));
            }
        });

        quantity2.setText(lichi_count+"");
        if(lichi_count==0){
            lichi_linear.setVisibility(View.GONE);
            lichi_plus.setVisibility(View.VISIBLE);
            DeleteDB("lichi");
            addDB(new CartEntity("lichi",Integer.parseInt(quantity2.getText().toString()),5));
        }
        else {
            lichi_linear.setVisibility(View.VISIBLE);
            lichi_plus.setVisibility(View.GONE);
        }
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lichi_count++;
                quantity2.setText(lichi_count+"");
                Toast.makeText(getApplicationContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("lichi");
                addDB(new CartEntity("lichi",Integer.parseInt(quantity2.getText().toString()),5));
            }
        });
        remove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lichi_count--;
                if(lichi_count==0){
                    lichi_linear.setVisibility(View.GONE);
                    lichi_plus.setVisibility(View.VISIBLE);
                    DeleteDB("lichi");
                    addDB(new CartEntity("lichi",Integer.parseInt(quantity2.getText().toString()),5));
                }

                quantity2.setText(lichi_count+"");
                Toast.makeText(getApplicationContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("lichi");
                addDB(new CartEntity("lichi",Integer.parseInt(quantity2.getText().toString()),5));
            }
        });

        quantity3.setText(orange_count+"");
        if(orange_count==0){
            orange_linear.setVisibility(View.GONE);
            orange_plus.setVisibility(View.VISIBLE);
            DeleteDB("orange");
            addDB(new CartEntity("orange",Integer.parseInt(quantity3.getText().toString()),5));
        }
        else {
            orange_linear.setVisibility(View.VISIBLE);
            orange_plus.setVisibility(View.GONE);
        }
        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orange_count++;
                quantity3.setText(orange_count+"");
                Toast.makeText(getApplicationContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("orange");
                addDB(new CartEntity("orange",Integer.parseInt(quantity3.getText().toString()),5));
            }
        });
        remove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orange_count--;
                if(orange_count==0){
                    orange_linear.setVisibility(View.GONE);
                    orange_plus.setVisibility(View.VISIBLE);
                    DeleteDB("orange");
                    addDB(new CartEntity("orange",Integer.parseInt(quantity3.getText().toString()),5));
                }

                quantity3.setText(orange_count+"");
                Toast.makeText(getApplicationContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("orange");
                addDB(new CartEntity("orange",Integer.parseInt(quantity3.getText().toString()),5));
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void dailog(final String name, final int quantity) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.generic_dailog, null);
        CardView cardView = mView.findViewById(R.id.cardview);
        cardView.setBackgroundDrawable(this.getDrawable(R.drawable.bg_dailog));
        Button addtocart = mView.findViewById(R.id.button);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), login.class));
            }
        });
        ImageView img = mView.findViewById(R.id.image);
        Glide.with(Objects.requireNonNull(getApplicationContext())).load(getImage(name)).into(img);

        TextView name1 = mView.findViewById(R.id.name);
        name1.setText(name.toUpperCase());

        ImageView add = mView.findViewById(R.id.add);
        ImageView remove = mView.findViewById(R.id.remove);
        final TextView quan = mView.findViewById(R.id.quantity1111);

        quant = quantity;
        quan.setText(quant+"");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant++;
                quan.setText(quant+"");
                Toast.makeText(getApplicationContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB(name);
                addDB(new CartEntity(name,Integer.parseInt(quan.getText().toString()),5));
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant--;
                if(quant==0){
                    //  orange_linear.setVisibility(View.GONE);
                    //  orange_plus.setVisibility(View.VISIBLE);
                    DeleteDB(name);
                    addDB(new CartEntity(name,Integer.parseInt(quan.getText().toString()),5));
                }
                quan.setText(quant+"");
                Toast.makeText(getApplicationContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB(name);
                addDB(new CartEntity(name,Integer.parseInt(quan.getText().toString()),5));
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public int getImage(String imageName) {

        int drawableResourceId = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());
        return drawableResourceId;
    }

    private void addDB(final CartEntity entity){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    db.cartDao().insertOne(entity);
                    Log.e("roomDB",entity.getItem_name());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void DeleteDB(final String name){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    db.cartDao().deleteOne(name);
                    Log.e("roomDB",name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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


    private void LoadAllDB(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<CartEntity> entities = new ArrayList<>();
                    entities = db.cartDao().loadAll();

                    for(int i=0;i<entities.size();i++){
                        if(entities.get(i).getItem_name().equals("strawberry")){
                            staw_count = entities.get(i).getQuantity();
                        }
                        if(entities.get(i).getItem_name().equals("lichi")){
                            lichi_count = entities.get(i).getQuantity();
                        }
                        if(entities.get(i).getItem_name().equals("orange")){
                            orange_count = entities.get(i).getQuantity();
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}