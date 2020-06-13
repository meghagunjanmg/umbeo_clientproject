package com.example.umbeo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class homescreen extends Fragment {

    TextView log,fruit;
    TextView address;
    CardView fruits;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_dashboard,container,false);

        fruits = (CardView)v.findViewById(R.id.list1);
        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FruitsFragment fruits= new FruitsFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fruits).commit();
            }
        });
        log=(TextView)v.findViewById(R.id.login);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),login.class));
            }
        });
        address=(TextView) v.findViewById(R.id.addre);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MapActivity.class));
            }
        });

        /*
        fruit=(TextView)v.findViewById(R.id.fruits);
        fruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FruitsFragment fruits= new FruitsFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fruits).commit();
                //startActivity(new Intent(getActivity(),FruitsFragment.class));
            }
        });

         */


        return v;
    }
}
