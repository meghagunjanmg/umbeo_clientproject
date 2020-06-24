package com.example.umbeo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of getContext fragment.
 */
public class CategoryFragment extends Fragment {


    LinearLayout editAddress;
    TextView lichipopupText, strawberryText, orangeText, pineappleText;
    Dialog mDialog;
    ImageView orange_plus, lichi_plus, strawberry_plus;

    AppDatabase db;
    static int staw_count = 0, lichi_count = 0, orange_count = 0 ,quant = 0;
    LinearLayout straw_linear, orange_linear, lichi_linear;
    ImageView add, remove, add2, remove2, add3, remove3;
    TextView quantity, quantity3, quantity2;
    List<CartEntity> entitiesList;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
        Log.e("DEBUG", "onResume of CATFragment");
    }

    /**
     * Use getContext factory method to create a new instance of
     * getContext fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("DEBUG", "onCreate of CATFragment");
        if (getArguments() != null) {
            Log.e("DEBUG", "onCreate of CATFragment 1");
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            if (db == null) {
                db = AppDatabase.getInstance(getContext());
            }

            LoadAllDB();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getContext fragment
        return inflater.inflate(R.layout.activity_fruit_new, container, false);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        Log.e("DEBUG", "onViewCreated of CATFragment");

        straw_linear =v.findViewById(R.id.strawberry_linear);
        add = v.findViewById(R.id.add);
        remove = v.findViewById(R.id.remove);
        quantity = v.findViewById(R.id.quantity);
        entitiesList = new ArrayList<>();

        orange_linear = v.findViewById(R.id.orange_linear);
        add3 = v.findViewById(R.id.add3);
        remove3 = v.findViewById(R.id.remove3);
        quantity3 =v.findViewById(R.id.quantity3);

        lichi_linear = v.findViewById(R.id.lichi_linear);
        add2 = v.findViewById(R.id.add1);
        remove2 = v.findViewById(R.id.remove1);
        quantity2 = v.findViewById(R.id.quantity1);
        if (db == null) {
            db = AppDatabase.getInstance(getContext());
        }

        LoadAllDB();


        editAddress = (LinearLayout) v.findViewById(R.id.editAddress);
        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MapActivity.class));
            }
        });

        LinearLayout lichi = v.findViewById(R.id.lichi);
        LinearLayout orange = v.findViewById(R.id.orange);
        LinearLayout strawberry = v.findViewById(R.id.strawberry);


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


        orange_plus = v.findViewById(R.id.orange_plus);
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

                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("orange");
                addDB(new CartEntity("orange", Integer.parseInt(quantity3.getText().toString()), 50));
            }
        });
        lichi_plus = v.findViewById(R.id.lichi_plus);
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
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("lichi");
                addDB(new CartEntity("lichi", Integer.parseInt(quantity2.getText().toString()), 50));
            }
        });
        strawberry_plus = v.findViewById(R.id.strawberry_plus);
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
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("strawberry");
                addDB(new CartEntity("strawberry", Integer.parseInt(quantity.getText().toString()), 50));
            }
        });

       /*      orange = (ImageView)v.V.findViewById(R.id.orange);
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();
            }
        });

   pineappleText= (TextView)v.V.findViewById(R.id.pineappletext);
        pineappleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.popup1_pineapple, null);
                Button addtocart = mView.V.findViewById(R.id.button);

                addtocart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();

                        startActivity(new Intent(getContext(),login.class));

                    }
                });

                ImageView img= mView.V.findViewById(R.id.image);

                mBuilder.setView(mView);

                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
        pineapple=(ImageView)v.V.findViewById(R.id.pineapple);
        pineapple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();
            }
        });

        strawbe=(ImageView)v.V.findViewById(R.id.strawberry);
        strawbe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();

            }
        });


        strawberryText = (TextView)v.V.findViewById(R.id.straaaa);
        strawberryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.popup1_strawberry, null);
                Button addtocart = mView.V.findViewById(R.id.button);

                addtocart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getContext(),login.class));

                    }
                });

                ImageView img= mView.V.findViewById(R.id.image);

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

        if (db == null) {
            db = AppDatabase.getInstance(getContext());
        }

        LoadAllDB();

        quantity.setText(staw_count+"");
        if(staw_count==0){
            straw_linear.setVisibility(View.GONE);
            strawberry_plus.setVisibility(View.VISIBLE);
            DeleteDB("strawberry");
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
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
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
                }

                quantity.setText(staw_count+"");
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("strawberry");
                addDB(new CartEntity("strawberry",Integer.parseInt(quantity.getText().toString()),50));
            }
        });

        quantity2.setText(lichi_count+"");
        if(lichi_count==0){
            lichi_linear.setVisibility(View.GONE);
            lichi_plus.setVisibility(View.VISIBLE);
            DeleteDB("lichi");
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
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
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
                }

                quantity2.setText(lichi_count+"");
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("lichi");
                addDB(new CartEntity("lichi",Integer.parseInt(quantity2.getText().toString()),50));
            }
        });

        quantity3.setText(orange_count+"");
        if(orange_count==0){
            orange_linear.setVisibility(View.GONE);
            orange_plus.setVisibility(View.VISIBLE);
            DeleteDB("orange");
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
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
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
                }

                quantity3.setText(orange_count+"");
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("orange");
                addDB(new CartEntity("orange",Integer.parseInt(quantity3.getText().toString()),50));
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void dailog(final String name, final int quantity) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.generic_dailog, null);
        CardView cardView = mView.findViewById(R.id.cardview);
        cardView.setBackgroundDrawable(getContext().getDrawable(R.drawable.bg_dailog));
        Button addtocart = mView.findViewById(R.id.button);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getContext(), login.class));
            }
        });
        ImageView img = mView.findViewById(R.id.image);
        Glide.with(Objects.requireNonNull(getContext())).load(getImage(name)).into(img);

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
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB(name);
                addDB(new CartEntity(name,Integer.parseInt(quan.getText().toString()),50));
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public int getImage(String imageName) {

        int drawableResourceId = getContext().getResources().getIdentifier(imageName, "drawable", getContext().getPackageName());
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
        db.cartDao().getAll().observe(CategoryFragment.this, new Observer<List<CartEntity>>(){
            @Override
            public void onChanged(List<CartEntity> entities) {
                entitiesList = entities;

                if (entitiesList.size() != 0) {
                    for (int i = 0; i < entitiesList.size(); i++) {
                        if (entitiesList.get(i).getItem_name().equals("strawberry")) {
                            staw_count = entitiesList.get(i).getQuantity();
                            quantity.setText(staw_count + "");
                        }
                        if (entitiesList.get(i).getItem_name().equals("lichi")) {
                            lichi_count = entitiesList.get(i).getQuantity();
                            quantity2.setText(lichi_count + "");
                        }
                        if (entitiesList.get(i).getItem_name().equals("orange")) {
                            orange_count = entitiesList.get(i).getQuantity();
                            quantity3.setText(orange_count + "");
                        }
                    }

                    if(lichi_count==0){
                        lichi_linear.setVisibility(View.GONE);
                        lichi_plus.setVisibility(View.VISIBLE);
                        DeleteDB("lichi");
                    }
                    else {
                        lichi_linear.setVisibility(View.VISIBLE);
                        lichi_plus.setVisibility(View.GONE);
                    }
                    if(staw_count==0){
                        straw_linear.setVisibility(View.GONE);
                        strawberry_plus.setVisibility(View.VISIBLE);
                        DeleteDB("strawberry");
                    }
                    else {
                        straw_linear.setVisibility(View.VISIBLE);
                        strawberry_plus.setVisibility(View.GONE);
                    }
                    if(orange_count==0){
                        orange_linear.setVisibility(View.GONE);
                        orange_plus.setVisibility(View.VISIBLE);
                        DeleteDB("orange");
                    }
                    else {
                        orange_linear.setVisibility(View.VISIBLE);
                        orange_plus.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

}