package com.example.umbeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Fruits extends AppCompatActivity {

    ImageView lichi,strawbe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruits);

        lichi=(ImageView)findViewById(R.id.lichi);
        lichi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),pop_lichi.class));
            }
        });
        strawbe=(ImageView)findViewById(R.id.strawberry);
        strawbe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),pop_strawberry.class));
            }
        });
    }
}
