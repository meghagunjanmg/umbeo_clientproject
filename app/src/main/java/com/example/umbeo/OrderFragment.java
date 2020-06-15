package com.example.umbeo;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class OrderFragment extends Fragment {
    public OrderFragment() {
    }
    Button history,current, all;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_orders,container , false);

        history=(Button)v.findViewById(R.id.history);
        current=(Button)v.findViewById(R.id.current);
        all=(Button)v.findViewById(R.id.all);

        current.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.active));
        current.setTextColor(R.color.colorWhite);

        history.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorWhite));
        history.setTextColor(R.color.active);

        all.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorWhite));
        all.setTextColor(R.color.active);

        // Create new fragment and transaction
        Fragment newFragment = new OrderCurrentFragment();
        // consider using Java coding conventions (upper first char class names!!!)
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.frame_layout, newFragment);


        // Commit the transaction
        transaction.commit();

        history.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                history.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.active));
                history.setTextColor(R.color.colorWhite);

                current.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorWhite));
                current.setTextColor(R.color.active);

                all.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorWhite));
                all.setTextColor(R.color.active);

                // Create new fragment and transaction
                Fragment newFragment = new OrderHistoryFragment();
                // consider using Java coding conventions (upper first char class names!!!)
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frame_layout, newFragment);


                // Commit the transaction
                transaction.commit();

            }
        });

        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                current.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.active));
                current.setTextColor(R.color.colorWhite);

                history.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorWhite));
                history.setTextColor(R.color.active);

                all.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorWhite));
                all.setTextColor(R.color.active);

                // Create new fragment and transaction
                Fragment newFragment = new OrderCurrentFragment();
                // consider using Java coding conventions (upper first char class names!!!)
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frame_layout, newFragment);


                // Commit the transaction
                transaction.commit();
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                all.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.active));
                all.setTextColor(R.color.colorWhite);

                history.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorWhite));
                history.setTextColor(R.color.active);

                current.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorWhite));
                current.setTextColor(R.color.active);


                // Create new fragment and transaction
                Fragment newFragment = new OrderAllFragment();
                // consider using Java coding conventions (upper first char class names!!!)
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frame_layout, newFragment);


                // Commit the transaction
                transaction.commit();

            }
        });
        return v;
    }
}
