package com.example.umbeo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class shopscreen extends AppCompatActivity implements View.OnClickListener {
private CardView shopCard1,shopCard2,shopcard3,shopCard4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopscreen);

        shopCard1=(CardView) findViewById(R.id.cardShop1);
        shopCard2=(CardView) findViewById(R.id.cardShop2);
        shopcard3=(CardView) findViewById(R.id.cardShop3);
        shopCard4=(CardView) findViewById(R.id.cardShop4);

        shopCard1.setOnClickListener(this);
        shopCard2.setOnClickListener(this);
        shopcard3.setOnClickListener(this);
        shopCard4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent i;

        switch (v.getId()){
            case R.id.cardShop1 : i= new Intent(this, homescreen.class);
            startActivity(i);
            break;
            default:break;
        }

    }
}
