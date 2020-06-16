package com.example.umbeo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.CartEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryActivity extends AppCompatActivity {

    LinearLayout editAddress;
    TextView lichipopupText, strawberryText, orangeText, pineappleText;
    Dialog mDialog;
    ImageView orange_plus, lichi_plus, strawberry_plus;

    AppDatabase db;
    static int staw_count = 0, lichi_count = 0, orange_count = 0 ,quant = 0;
    LinearLayout straw_linear, orange_linear, lichi_linear;
    ImageView add, remove, add2, remove2, add3, remove3;
    TextView quantity, quantity3, quantity2;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_new);
        straw_linear =findViewById(R.id.strawberry_linear);
        add = findViewById(R.id.add);
        remove = findViewById(R.id.remove);
        quantity = findViewById(R.id.quantity);

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

        editAddress = (LinearLayout) findViewById(R.id.editAddress);
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
                addDB(new CartEntity("orange", Integer.parseInt(quantity3.getText().toString()), 50));
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
                addDB(new CartEntity("lichi", Integer.parseInt(quantity2.getText().toString()), 50));
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
                addDB(new CartEntity("strawberry", Integer.parseInt(quantity.getText().toString()), 50));
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
    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of HomeFragment");
        super.onResume();
        quantity.setText(staw_count+"");
        if(staw_count==0){
            straw_linear.setVisibility(View.GONE);
            strawberry_plus.setVisibility(View.VISIBLE);
            DeleteDB("strawberry");
            addDB(new CartEntity("strawberry",Integer.parseInt(quantity.getText().toString()),50));
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
                addDB(new CartEntity("strawberry",Integer.parseInt(quantity.getText().toString()),50));
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
                    addDB(new CartEntity("strawberry",Integer.parseInt(quantity.getText().toString()),50));
                }

                quantity.setText(staw_count+"");
                Toast.makeText(getApplicationContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("strawberry");
                addDB(new CartEntity("strawberry",Integer.parseInt(quantity.getText().toString()),50));
            }
        });

        quantity2.setText(lichi_count+"");
        if(lichi_count==0){
            lichi_linear.setVisibility(View.GONE);
            lichi_plus.setVisibility(View.VISIBLE);
            DeleteDB("lichi");
            addDB(new CartEntity("lichi",Integer.parseInt(quantity2.getText().toString()),50));
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
                addDB(new CartEntity("lichi",Integer.parseInt(quantity2.getText().toString()),50));
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
                    addDB(new CartEntity("lichi",Integer.parseInt(quantity2.getText().toString()),50));
                }

                quantity2.setText(lichi_count+"");
                Toast.makeText(getApplicationContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("lichi");
                addDB(new CartEntity("lichi",Integer.parseInt(quantity2.getText().toString()),50));
            }
        });

        quantity3.setText(orange_count+"");
        if(orange_count==0){
            orange_linear.setVisibility(View.GONE);
            orange_plus.setVisibility(View.VISIBLE);
            DeleteDB("orange");
            addDB(new CartEntity("orange",Integer.parseInt(quantity3.getText().toString()),50));
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
                addDB(new CartEntity("orange",Integer.parseInt(quantity3.getText().toString()),50));
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
                    addDB(new CartEntity("orange",Integer.parseInt(quantity3.getText().toString()),50));
                }

                quantity3.setText(orange_count+"");
                Toast.makeText(getApplicationContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("orange");
                addDB(new CartEntity("orange",Integer.parseInt(quantity3.getText().toString()),50));
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
                addDB(new CartEntity(name,Integer.parseInt(quan.getText().toString()),50));
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
                    addDB(new CartEntity(name,Integer.parseInt(quan.getText().toString()),50));
                }
                quan.setText(quant+"");
                Toast.makeText(getApplicationContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB(name);
                addDB(new CartEntity(name,Integer.parseInt(quan.getText().toString()),50));
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