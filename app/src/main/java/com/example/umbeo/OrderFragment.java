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

public class OrderFragment extends Fragment {

    Button history,current, all;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_order,container , false);

        history=(Button)v.findViewById(R.id.history);
        current=(Button)v.findViewById(R.id.current);
        all=(Button)v.findViewById(R.id.all);
        history.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                history.setEnabled(true);
                history.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.active));
                history.setTextColor(R.color.colorWhite);



            }
        });

        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setEnabled(true);
                current.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.active));
                current.setTextColor(R.color.colorWhite);


            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all.setEnabled(true);
                all.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.active));
                all.setTextColor(R.color.colorWhite);


            }
        });
        return v;
    }
}
