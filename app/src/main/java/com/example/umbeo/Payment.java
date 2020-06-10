package com.example.umbeo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Payment extends AppCompatActivity {

    private int counter=1;
    RadioButton cas,amazonpay,applepay,paypal,amazonupi,googleupi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        addListner();
    }

    private void addListner(){

        cas=(RadioButton)findViewById(R.id.cash);
        amazonpay=(RadioButton)findViewById(R.id.amazonPay);
        applepay=(RadioButton)findViewById(R.id.applePay);
        paypal=(RadioButton)findViewById(R.id.payPal);
        amazonupi=(RadioButton)findViewById(R.id.amazonPayUPI);
        googleupi=(RadioButton)findViewById(R.id.googleUPI);

        if(cas.isChecked()==true){
            if(counter >1){
                amazonpay.setChecked(false);
                applepay.setChecked(false);
                paypal.setChecked(false);
                amazonupi.setChecked(false);
                googleupi.setChecked(false);

                Toast.makeText(getApplicationContext(),"You have selected Cash as mode of Payment",Toast.LENGTH_LONG).show();
            }
            else{
                counter++;
                Toast.makeText(getApplicationContext(),"You have selected Cash as mode of Payment",Toast.LENGTH_LONG).show();
            }
        }

        if(amazonpay.isChecked()==true){
            if(counter >1){
                cas.setChecked(false);
                applepay.setChecked(false);
                paypal.setChecked(false);
                amazonupi.setChecked(false);
                googleupi.setChecked(false);


                Toast.makeText(getApplicationContext(),"You have selected Amazon Pay as mode of Payment",Toast.LENGTH_LONG).show();
            }
            else{
                counter++;
                Toast.makeText(getApplicationContext(),"You have selected Amazon Pay as mode of Payment",Toast.LENGTH_LONG).show();

            }
        }

        if(applepay.isChecked()==true){
            if(counter >1){
                amazonpay.setChecked(false);
                cas.setChecked(false);
                paypal.setChecked(false);
                amazonupi.setChecked(false);
                googleupi.setChecked(false);

                Toast.makeText(getApplicationContext(),"You have selected Apple Pay as mode of Payment",Toast.LENGTH_LONG).show();
            }
            else{
                counter++;
                Toast.makeText(getApplicationContext(),"You have selected Apple Pay as mode of Payment",Toast.LENGTH_LONG).show();

            }
        }

        if(paypal.isChecked()==true){
            if(counter >1){
                amazonpay.setChecked(false);
                applepay.setChecked(false);
                paypal.setChecked(false);
                amazonupi.setChecked(false);
                googleupi.setChecked(false);

                Toast.makeText(getApplicationContext(),"You have selected Apple Pay as mode of Payment",Toast.LENGTH_LONG).show();
            }
            else{
                counter++;
                Toast.makeText(getApplicationContext(),"You have selected Apple Pay as mode of Payment",Toast.LENGTH_LONG).show();

            }
        }

        if (amazonupi.isChecked()==true){
            if(counter >1){
                amazonpay.setChecked(false);
                applepay.setChecked(false);
                paypal.setChecked(false);
                cas.setChecked(false);
                googleupi.setChecked(false);

                Toast.makeText(getApplicationContext(),"You have selected Amazon UPI as mode of Payment",Toast.LENGTH_LONG).show();
            }
            else{
                counter++;
                Toast.makeText(getApplicationContext(),"You have selected Amazon UPI as mode of Payment",Toast.LENGTH_LONG).show();

            }
        }

        if (googleupi.isChecked()==true){
            if(counter >1){
                amazonpay.setChecked(false);
                applepay.setChecked(false);
                paypal.setChecked(false);
                amazonupi.setChecked(false);
                cas.setChecked(false);

                Toast.makeText(getApplicationContext(),"You have selected Google UPI as mode of Payment",Toast.LENGTH_LONG).show();
            }
            else{
                counter++;
                Toast.makeText(getApplicationContext(),"You have selected Google UPI as mode of Payment",Toast.LENGTH_LONG).show();

            }
        }


    }
}
