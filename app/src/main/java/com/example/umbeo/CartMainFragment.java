package com.example.umbeo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.umbeo.Storage.PaymentActivity;
import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.CartEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.widget.AbsListView.CHOICE_MODE_SINGLE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class CartMainFragment extends Fragment {
    Button paym,shop;
    TextView add,total_amount,subtotal;

    AppDatabase db;
    List<CartEntity> entityList;
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    MutableLiveData<List<CartEntity>> data;

     TextView no_item;
      static LinearLayout main_scroll,no_item_linear;

    static List<Double> amounts = new ArrayList<Double>();

    CheckBox loyalty;
    UserPreference preference;
    TextView loyalty_point;
    double sum = 0;

    TextView address,change_address;
    ListView slots;
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

        preference = new UserPreference(getContext());
        if(preference.getTheme()==1){
            return inflater.inflate(R.layout.dark_cart, container, false);
        }
        else
        return inflater.inflate(R.layout.activity_new_cart, container, false);
    }

    CardView delivery_card;
    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        preference = new UserPreference(getContext());

        delivery_card = v.findViewById(R.id.delivery_card);

        if(preference.getTheme()==1){
            delivery_card.setCardBackgroundColor(Color.BLACK);
        }

        no_item_linear = v.findViewById(R.id.no_item_linear);
        shop = v.findViewById(R.id.shop);
        no_item = v.findViewById(R.id.no_item);
        main_scroll = v.findViewById(R.id.main_linear);

        recyclerView= v.findViewById(R.id.cartItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         total_amount = v.findViewById(R.id.grand_total);
        subtotal = v.findViewById(R.id.subtotal);
        slots = v.findViewById(R.id.slots);

         loyalty = v.findViewById(R.id.loyalty);
        loyalty_point = v.findViewById(R.id.loyalty_point);
        loyalty_point.setText(""+preference.getLoyaltyPoints());

        if (db == null) {
            db = AppDatabase.getInstance(getContext());
        }
        entityList = new ArrayList<>();
        LoadAllDB();

        address = v.findViewById(R.id.address);
        if(preference.getAddresses()!=null && preference.getAddresses().size()>0){
            address.setText(""+preference.getAddresses().get(0));
        }
        else {
            /////
        }

        change_address=v.findViewById(R.id.change_add);
        change_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MyAddresses.class));
            }
        });

        add=v.findViewById(R.id.additem);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeScreenActivity.viewPager.setCurrentItem(0);
            }
        });
        paym=(Button)v.findViewById(R.id.payment);
        paym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(preference.getUserName()==null){
                    Toast.makeText(getContext(),"First SignUp/Login", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getContext(), signup.class);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(getContext(), PaymentActivity.class);
                    i.putExtra("total", total_amount.getText().toString());
                    startActivity(i);
                }
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

        db.cartDao().getAll().observe(this, new Observer<List<CartEntity>>() {
            @Override
            public void onChanged(List<CartEntity> entities) {
                entityList = entities;
                for (int i = 0; i < entityList.size(); i++) {
                    sum = sum + (entities.get(i).getQuantity() * entities.get(i).getPrice());
                }
                subtotal.setText("$ "+String.format("%.2f",sum));
            }
        });



        final HashMap<String,Double> slotList = new HashMap<>();
        slotList.put("9 am - 11 am",1.0);
      //  slotList.put("1 pm - 3 pm",5.0);
       // slotList.put("5 pm - 8 pm",5.0);
        SlotAdapter myAdapter = new SlotAdapter(slotList, getContext());
        slots.setChoiceMode(CHOICE_MODE_SINGLE);
        slots.setAdapter(myAdapter);

        slots.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBox = view.findViewById(R.id.slot);
                if(checkBox.isChecked()){
                    sum = sum + 1;
                }
            }
        });





        loyalty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("FragmentLiveDataObserve")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(preference.getLoyaltyPoints()<1000){
                        int i = 1000-preference.getLoyaltyPoints();
                        Toast.makeText(getContext(),"You need "+i+" more crystals to redeem",Toast.LENGTH_LONG).show();
                        loyalty.setChecked(false);
                    }
                    else if(sum<30){
                        Toast.makeText(getContext(),"Minimum bill should be $30 to redeem crystal",Toast.LENGTH_LONG).show();
                        loyalty.setChecked(false);
                    }
                    else {
                        loyalty.setTextColor(Color.GREEN);
                        loyalty_point.setTextColor(Color.GREEN);

                        db.cartDao().getAll().observe(CartMainFragment.this, new Observer<List<CartEntity>>() {
                            @Override
                            public void onChanged(List<CartEntity> entities) {
                                entityList = entities;
                                cartAdapter.notifyDataSetChanged();

                                cartAdapter = new CartAdapter(entityList, getContext(), db);
                                cartAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                                    @Override
                                    public void onChanged() {
                                        super.onChanged();
                                    }
                                });
                                recyclerView.setAdapter(cartAdapter);

                                if (entityList.size() == 0) {
                                    no_item_linear.setVisibility(View.VISIBLE);
                                    main_scroll.setVisibility(View.GONE);
                                } else {
                                    no_item_linear.setVisibility(View.GONE);
                                    main_scroll.setVisibility(View.VISIBLE);
                                }

                               double amt = Double.parseDouble(String.format("%.2f", (sum - preference.getLoyaltyPoints()) + 2));
                                total_amount.setText("$ " + amt);
                            }
                        });
                    }
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

                            double amt = Double.parseDouble(String.format("%.2f",((sum - 1)+2)));
                            total_amount.setText("$ "+sum);
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

               double amt = Double.parseDouble(String.format("%.2f",((sum - 1)+2)));
                total_amount.setText("$ "+amt);
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

                    double amt = Double.parseDouble(String.format("%.2f",((sum - 1)+2)));
                    total_amount.setText("$ "+amt);
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
