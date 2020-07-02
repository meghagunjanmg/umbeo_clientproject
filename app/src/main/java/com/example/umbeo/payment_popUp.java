package com.example.umbeo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class payment_popUp extends AppCompatActivity {

    Button shopping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_pop_up);

        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width= dm.widthPixels;
        int height= dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.9));

        shopping=(Button) findViewById(R.id.send);
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeScreenActivity.class));
            }
        });

       TextView go_order = findViewById(R.id.go_order);
       go_order.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Fragment orderFragment = new OrderMainFragment();
               getSupportFragmentManager().beginTransaction().replace(R.id.frameSelected,orderFragment)
                       .commit();
           }
       });
    }
}