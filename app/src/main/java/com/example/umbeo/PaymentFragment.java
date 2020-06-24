package com.example.umbeo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class PaymentFragment extends Fragment implements RadioGroup.OnCheckedChangeListener
{

    private int counter=1;
    RadioGroup radioGroup;
    boolean flag = true;

    RadioButton cas,amazonpay,applepay,paypal,amazonupi,googleupi;
    Button sendd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_payment,container , false);



        radioGroup = v.findViewById(R.id.role_radioGroup_ID);
        radioGroup.setOnCheckedChangeListener(this);

        cas=(RadioButton) v.findViewById(R.id.cash);
        amazonpay=(RadioButton) v.findViewById(R.id.amazonPay);
        applepay=(RadioButton) v.findViewById(R.id.applePay);
        paypal=(RadioButton) v.findViewById(R.id.payPal);
        amazonupi=(RadioButton) v.findViewById(R.id.amazonPayUPI);
        googleupi=(RadioButton) v.findViewById(R.id.googleUPI);


        sendd=(Button) v.findViewById(R.id.send);
        sendbt();
        
        return v;
    }

    private void dailog() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.activity_payment_pop_up, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        Button shopping=(Button) mView.findViewById(R.id.send);
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),HomeScreenActivity.class));
                dialog.cancel();
            }
        });

        TextView go_order = mView.findViewById(R.id.go_order);
        go_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment orderFragment = new OrderMainFragment();
                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                fragmentManager.beginTransaction().replace(R.id.fragment_container,orderFragment)
                        .commit();
                dialog.cancel();
            }
        });

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // find which radio button is selected
        if(checkedId == R.id.cash) {
            addListner();
            sendbt();
        } else if(checkedId == R.id.amazonPay) {
            addListner();
            sendbt();
        } else if(checkedId == R.id.applePay){
            addListner();
            sendbt();
        } else if(checkedId == R.id.payPal){
            addListner();
            sendbt();
        } else if(checkedId == R.id.amazonPayUPI){
            addListner();
            sendbt();
        } else if(checkedId == R.id.googleUPI){
            addListner();
            sendbt();
        }



    }
    private void addListner() {
        if (cas.isChecked() == true) {
            if (counter > 1) {
                amazonpay.setChecked(false);
                applepay.setChecked(false);
                paypal.setChecked(false);
                amazonupi.setChecked(false);
                googleupi.setChecked(false);

                Toast.makeText(getContext(), "You have selected Cash as mode of Payment", Toast.LENGTH_LONG).show();
            } else {
                counter++;
                Toast.makeText(getContext(), "You have selected Cash as mode of Payment", Toast.LENGTH_LONG).show();
            }
        } else if (amazonpay.isChecked() == true) {
            if (counter > 1) {
                cas.setChecked(false);
                applepay.setChecked(false);
                paypal.setChecked(false);
                amazonupi.setChecked(false);
                googleupi.setChecked(false);


                Toast.makeText(getContext(), "You have selected Amazon Pay as mode of Payment", Toast.LENGTH_LONG).show();
            } else {
                counter++;
                Toast.makeText(getContext(), "You have selected Amazon Pay as mode of Payment", Toast.LENGTH_LONG).show();

            }
        } else if (applepay.isChecked() == true) {
            if (counter > 1) {
                amazonpay.setChecked(false);
                cas.setChecked(false);
                paypal.setChecked(false);
                amazonupi.setChecked(false);
                googleupi.setChecked(false);

                Toast.makeText(getContext(), "You have selected Apple Pay as mode of Payment", Toast.LENGTH_LONG).show();
            } else {
                counter++;
                Toast.makeText(getContext(), "You have selected Apple Pay as mode of Payment", Toast.LENGTH_LONG).show();

            }
        } else if (paypal.isChecked() == true) {
            if (counter > 1) {
                amazonpay.setChecked(false);
                applepay.setChecked(false);
                cas.setChecked(false);
                amazonupi.setChecked(false);
                googleupi.setChecked(false);

                Toast.makeText(getContext(), "You have selected Pay Pal as mode of Payment", Toast.LENGTH_LONG).show();
            } else {
                counter++;
                Toast.makeText(getContext(), "You have selected Pay Pal as mode of Payment", Toast.LENGTH_LONG).show();

            }
        } else if (amazonupi.isChecked() == true) {
            if (counter > 1) {
                amazonpay.setChecked(false);
                applepay.setChecked(false);
                paypal.setChecked(false);
                cas.setChecked(false);
                googleupi.setChecked(false);

                Toast.makeText(getContext(), "You have selected Amazon UPI as mode of Payment", Toast.LENGTH_LONG).show();
            } else {
                counter++;
                Toast.makeText(getContext(), "You have selected Amazon UPI as mode of Payment", Toast.LENGTH_LONG).show();

            }
        } else if (googleupi.isChecked() == true) {
            if (counter > 1) {
                amazonpay.setChecked(false);
                applepay.setChecked(false);
                paypal.setChecked(false);
                amazonupi.setChecked(false);
                cas.setChecked(false);

                Toast.makeText(getContext(), "You have selected Google UPI as mode of Payment", Toast.LENGTH_LONG).show();
            } else {
                counter++;
                Toast.makeText(getContext(), "You have selected Google UPI as mode of Payment", Toast.LENGTH_LONG).show();

            }
        }
    }


    private void sendbt(){
        if (cas.isChecked()||amazonpay.isChecked()||applepay.isChecked()||paypal.isChecked()||amazonupi.isChecked()||googleupi.isChecked())
        {
            sendd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // startActivity(new Intent(getContext(),payment_popUp.class));

                    dailog();
                }
            });
        }
        else {
            sendd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // startActivity(new Intent(getContext(),payment_popUp.class));
                    Toast.makeText(getContext(),"Select Payment option",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
