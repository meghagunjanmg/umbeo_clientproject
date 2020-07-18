package com.example.umbeo;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderMainFragment extends Fragment {
    Button history,current, all;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderMainFragment newInstance(String param1, String param2) {
        OrderMainFragment fragment = new OrderMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_orders, container, false);
    }


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        history=(Button)v.findViewById(R.id.history);
        current=(Button)v.findViewById(R.id.current);
        all=(Button)v.findViewById(R.id.all);

        current.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main2));
        current.setTextColor(R.color.colorWhite);

        history.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorWhite));
        history.setTextColor(R.color.main2);

        all.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorWhite));
        all.setTextColor(R.color.main2);

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

                history.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main2));
                history.setTextColor(R.color.colorWhite);

                current.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorWhite));
                current.setTextColor(R.color.main2);

                all.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorWhite));
                all.setTextColor(R.color.main2);

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

                current.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main2));
                current.setTextColor(R.color.colorWhite);

                history.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorWhite));
                history.setTextColor(R.color.main2);

                all.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorWhite));
                all.setTextColor(R.color.main2);

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

                all.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.main2));
                all.setTextColor(R.color.colorWhite);

                history.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorWhite));
                history.setTextColor(R.color.main2);

                current.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorWhite));
                current.setTextColor(R.color.main2);


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
    }
}
