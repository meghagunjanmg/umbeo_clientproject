package com.example.umbeo;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class homescreen extends AppCompatActivity implements View.OnClickListener {
    private CardView picCard1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        picCard1 = (CardView) findViewById(R.id.cardPic1);
        picCard1.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        Intent i;

        switch (v.getId()) {
            case R.id.cardPic1:i = new Intent(this, itemScreen.class);
                startActivity(i);
                break;
            default:
                break;

        }
    }
}