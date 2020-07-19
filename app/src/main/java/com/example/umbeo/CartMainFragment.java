package com.example.umbeo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.CartEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class CartMainFragment extends Fragment {
    ImageView address;
    Button add,paym,shop;
    TextView total_amount;

    AppDatabase db;
    List<CartEntity> entityList;
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    MutableLiveData<List<CartEntity>> data;

     TextView no_item;
      static LinearLayout main_scroll,no_item_linear;

    static List<Integer> amounts = new ArrayList<>();

    CheckBox loyalty;
    UserPreference preference;
    TextView loyalty_point;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartMainFragment() {
        // Required empty public constructor

        //grandTotal=0;

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartMainFragment newInstance(String param1, String param2) {
        CartMainFragment fragment = new CartMainFragment();
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
        return inflater.inflate(R.layout.activity_new_cart, container, false);
    }


    @SuppressLint("FragmentLiveDataObserve")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        preference = new UserPreference(getContext());
        no_item_linear = v.findViewById(R.id.no_item_linear);
        shop = v.findViewById(R.id.shop);
        no_item = v.findViewById(R.id.no_item);
        main_scroll = v.findViewById(R.id.main_linear);

        recyclerView= v.findViewById(R.id.cartItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         total_amount = v.findViewById(R.id.grand_total);

         loyalty = v.findViewById(R.id.loyalty);
        loyalty_point = v.findViewById(R.id.loyalty_point);
        loyalty_point.setText("- ₹ "+preference.getLoyaltyPoints());

        if (db == null) {
            db = AppDatabase.getInstance(getContext());
        }
        entityList = new ArrayList<>();
        LoadAllDB();


        address=(ImageView)v.findViewById(R.id.editAddress);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MapActivity.class));
            }
        });

        add=(Button)v.findViewById(R.id.additem);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create new fragment and transaction
              startActivity(new Intent(getContext(),HomeScreenActivity.class));
            }
        });
        paym=(Button)v.findViewById(R.id.payment);
        paym.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                HomeScreenActivity.payment_frag = true;
                PaymentFragment paymentFragment = new PaymentFragment(total_amount.getText().toString());
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameSelected, paymentFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        if(entityList.size()==0){
            no_item_linear.setVisibility(View.VISIBLE);
            main_scroll.setVisibility(View.GONE);
        }
        else {
            no_item_linear.setVisibility(View.GONE);
            main_scroll.setVisibility(View.VISIBLE);
        }

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),HomeScreenActivity.class));
            }
        });

        loyalty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("FragmentLiveDataObserve")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    loyalty.setTextColor(Color.parseColor("#000000"));
                    loyalty_point.setTextColor(Color.parseColor("#000000"));

                    db.cartDao().getAll().observe(CartMainFragment.this, new Observer<List<CartEntity>>(){
                        @Override
                        public void onChanged(List<CartEntity> entities) {
                            entityList = entities;
                            cartAdapter.notifyDataSetChanged();

                            cartAdapter = new CartAdapter(entityList, getContext(),db);
                            cartAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                                @Override
                                public void onChanged() {
                                    super.onChanged(); }
                            });
                            recyclerView.setAdapter(cartAdapter);

                            if(entityList.size()==0){
                                no_item_linear.setVisibility(View.VISIBLE);
                                main_scroll.setVisibility(View.GONE);
                            }
                            else {
                                no_item_linear.setVisibility(View.GONE);
                                main_scroll.setVisibility(View.VISIBLE);
                            }

                            int sum = 0;
                            for(int i=0;i<entityList.size();i++){
                                sum = sum+ (entities.get(i).getQuantity()*entities.get(i).getPrice());
                            }
                            total_amount.setText("$ "+((sum - preference.getLoyaltyPoints())+2));
                        }
                    });

                }

                else {

                    loyalty.setTextColor(Color.parseColor("#757575"));
                    loyalty_point.setTextColor(Color.parseColor("#757575"));

                    db.cartDao().getAll().observe(CartMainFragment.this, new Observer<List<CartEntity>>(){
                        @Override
                        public void onChanged(List<CartEntity> entities) {
                            entityList = entities;
                            cartAdapter.notifyDataSetChanged();

                            cartAdapter = new CartAdapter(entityList, getContext(),db);
                            cartAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                                @Override
                                public void onChanged() {
                                    super.onChanged(); }
                            });
                            recyclerView.setAdapter(cartAdapter);
                            if(entityList.size()==0){
                                no_item_linear.setVisibility(View.VISIBLE);
                                main_scroll.setVisibility(View.GONE);
                            }
                            else {
                                no_item_linear.setVisibility(View.GONE);
                                main_scroll.setVisibility(View.VISIBLE);
                            }

                            int sum = 0;
                            for(int i=0;i<entityList.size();i++){
                                sum = sum+ (entities.get(i).getQuantity()*entities.get(i).getPrice());
                            }
                            total_amount.setText("$ "+((sum - 1)+2));
                        }
                    });

                }
            }
        });


        db.cartDao().getAll().observe(CartMainFragment.this, new Observer<List<CartEntity>>(){
            @Override
            public void onChanged(List<CartEntity> entities) {
                entityList = entities;
                cartAdapter.notifyDataSetChanged();

                cartAdapter = new CartAdapter(entityList, getContext(),db);

                recyclerView.setAdapter(cartAdapter);
                if(entityList.size()==0){
                    no_item_linear.setVisibility(View.VISIBLE);
                    main_scroll.setVisibility(View.GONE);
                }
                else {
                    no_item_linear.setVisibility(View.GONE);
                    main_scroll.setVisibility(View.VISIBLE);
                }
                int sum = 0;
                for(int i=0;i<entityList.size();i++){
                   sum = sum+ (entities.get(i).getQuantity()*entities.get(i).getPrice());
                }
                total_amount.setText("$ "+((sum - 1)+2));
            }
        });





    }

    private void LoadAllDB(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                     entityList = db.cartDao().loadAll();
                    cartAdapter = new CartAdapter(entityList, getContext(),db);
                    recyclerView.setAdapter(cartAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            db.cartDao().getAll().observe(CartMainFragment.this, new Observer<List<CartEntity>>(){
                @Override
                public void onChanged(List<CartEntity> entities) {
                    entityList = entities;
                    cartAdapter.notifyDataSetChanged();

                    cartAdapter = new CartAdapter(entityList, getContext(),db);

                    recyclerView.setAdapter(cartAdapter);
                    if(entityList.size()==0){
                        no_item_linear.setVisibility(View.VISIBLE);
                        main_scroll.setVisibility(View.GONE);
                    }
                    else {
                        no_item_linear.setVisibility(View.GONE);
                        main_scroll.setVisibility(View.VISIBLE);
                    }
                    int sum = 0;
                    for(int i=0;i<entityList.size();i++){
                        sum = sum+ (entities.get(i).getQuantity()*entities.get(i).getPrice());
                    }
                    total_amount.setText("$ "+((sum - 1)+2));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        entityList = new ArrayList<>();
        LoadAllDB();

        if(entityList.size()==0){
            no_item_linear.setVisibility(View.VISIBLE);
            main_scroll.setVisibility(View.GONE);
        }
        else {
            no_item_linear.setVisibility(View.GONE);
            main_scroll.setVisibility(View.VISIBLE);
        }
    }
}
