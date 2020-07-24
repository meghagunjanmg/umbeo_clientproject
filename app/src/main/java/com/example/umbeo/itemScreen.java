package com.example.umbeo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import spencerstudios.com.bungeelib.Bungee;

public class itemScreen extends AppCompatActivity {

    Button buy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z_activity_item_screen);


        buy=(Button)findViewById(R.id.buynow);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login.class));
                Bungee.fade(itemScreen.this);
            }
        });
    }
}
