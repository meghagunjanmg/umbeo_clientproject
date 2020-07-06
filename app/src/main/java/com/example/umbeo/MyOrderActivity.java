package com.example.umbeo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MyOrderActivity extends AppCompatActivity {
    Button history,current, all;
    ImageView back_btn;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() ,HomeScreenActivity.class));
            }
        });

        history=(Button)findViewById(R.id.history);
        current=(Button)findViewById(R.id.current);
        all=(Button)findViewById(R.id.all);

        current.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.active));
        current.setTextColor(R.color.colorWhite);

        history.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorWhite));
        history.setTextColor(R.color.active);

        all.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorWhite));
        all.setTextColor(R.color.active);

        // Create new fragment and transaction
        Fragment newFragment = new OrderCurrentFragment();
        // consider using Java coding conventions (upper first char class names!!!)

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, newFragment).commit();

        // Commit the transaction

        history.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                history.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.active));
                history.setTextColor(R.color.colorWhite);

                current.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorWhite));
                current.setTextColor(R.color.active);

                all.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorWhite));
                all.setTextColor(R.color.active);

                // Create new fragment and transaction
                Fragment newFragment = new OrderHistoryFragment();
                // consider using Java coding conventions (upper first char class names!!!)

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, newFragment).commit();

                // Commit the transaction

            }
        });

        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                current.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.active));
                current.setTextColor(R.color.colorWhite);

                history.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorWhite));
                history.setTextColor(R.color.active);

                all.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorWhite));
                all.setTextColor(R.color.active);

                // Create new fragment and transaction
                Fragment newFragment = new OrderCurrentFragment();
                // consider using Java coding conventions (upper first char class names!!!)

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, newFragment).commit();
                // Commit the transaction
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                all.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.active));
                all.setTextColor(R.color.colorWhite);

                history.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorWhite));
                history.setTextColor(R.color.active);

                current.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorWhite));
                current.setTextColor(R.color.active);


                // Create new fragment and transaction
                Fragment newFragment = new OrderAllFragment();
                // consider using Java coding conventions (upper first char class names!!!)

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, newFragment).commit();


            }
        });
    }
}