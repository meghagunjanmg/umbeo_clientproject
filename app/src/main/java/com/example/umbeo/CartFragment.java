package com.example.umbeo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.CartEntity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartFragment extends Fragment {
    public CartFragment() {
    }


    ImageView address;
    Button add,paym;

    AppDatabase db;
    List<CartEntity> entities;
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    MutableLiveData<List<CartEntity>> data;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_new_cart,container , false);

        recyclerView= v.findViewById(R.id.cartItem);



        db = Room.databaseBuilder(Objects.requireNonNull(getContext()),
                AppDatabase.class, "database-name").build();

        ///DeleteAllDB();

        LoadAllDB();

        address=(ImageView)v.findViewById(R.id.editAddress);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MapActivity.class));
            }
        });

        add=(Button)v.findViewById(R.id.additem);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryFragment fruits= new CategoryFragment();
                getFragmentManager().beginTransaction().replace(R.id.frameSelected, fruits).commit();
            }
        });
        paym=(Button)v.findViewById(R.id.payment);
        paym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentFragment paymentFragment = new PaymentFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,paymentFragment).commit();
            }
        });

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoadAllDB();
        onResume();
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadAllDB();
    }

    private void LoadAllDB(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    entities = new ArrayList<>();
                    entities = db.cartDao().loadAll();
                    cartAdapter = new CartAdapter(entities, getContext(),db);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                    recyclerView.setAdapter(cartAdapter);

                    Log.e("roomDB",entities.get(0).getItem_name());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
