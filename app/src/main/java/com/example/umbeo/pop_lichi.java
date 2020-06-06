package com.example.umbeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class pop_lichi extends AppCompatActivity {

    ImageView ad, minu;
    TextView count;
    Button addtocart;
    int counter=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_lichi);

        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width= dm.widthPixels;
        int height= dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.8));


        count=(TextView)findViewById(R.id.count);
        ad=(ImageView)findViewById(R.id.add);

        ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter++;
                count.setText(Integer.toString(counter));
            }
        });
        minu=(ImageView)findViewById(R.id.minus);
        minu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                count.setText(Integer.toString(counter));
                if(counter<1){
                    counter=1;
                    count.setText(Integer.toString(1));}
            }
        });

        addtocart=(Button)findViewById(R.id.button);


        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(pop_lichi.this,"Added to Cart Successfully",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplication(),login.class));
            }
        });

    }
}
