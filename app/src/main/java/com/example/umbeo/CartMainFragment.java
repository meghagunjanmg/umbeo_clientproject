package com.example.umbeo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.api.UsersApi;
import com.example.umbeo.response_data.shop.ShopResponse;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.CartEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import spencerstudios.com.bungeelib.Bungee;

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
    double sum = 0,delivery=0;

    TextView address,change_address;
    ListView slots;

    final HashMap<String,Double> slotList = new HashMap<>();

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
    TextView delivery_charges;
RadioGroup rgb;
    RadioButton slot1,slot2,slot3;
    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        preference = new UserPreference(getContext());

        shopData();

        delivery_card = v.findViewById(R.id.delivery_card);
        delivery_charges = v.findViewById(R.id.delivery_charges);
        rgb = v.findViewById(R.id.radio_grp);
         slot1 = v.findViewById(R.id.slot1);
         slot2 = v.findViewById(R.id.slot2);
         slot3 = v.findViewById(R.id.slot3);

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
                Bungee.fade(getContext());
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
                    Bungee.fade(getContext());
                }
                else {
                    Intent i = new Intent(getContext(), PaymentActivity.class);
                    i.putExtra("total", total_amount.getText().toString());
                    startActivity(i);
                    Bungee.fade(getContext());
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
                Bungee.fade(getContext());
            }
        });

        db.cartDao().getAll().observe(this, new Observer<List<CartEntity>>() {
            @Override
            public void onChanged(List<CartEntity> entities) {
                entityList = entities;


                /*'
                for (int i = 0; i < entityList.size(); i++) {
                    sum = sum + (entities.get(i).getQuantity() * entities.get(i).getPrice());
                }

                 */
                cartAdapter.setData(entityList);
                sum = cartAdapter.grandTotal();
                subtotal.setText("$ "+String.format("%.2f",sum));
                Double d = sum+delivery;
                total_amount.setText("$ "+String.format("%.2f",d));
            }
        });






      /*  SlotAdapter myAdapter = new SlotAdapter(slotList, getContext());
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

        RadioGroup rgb = v.findViewById(R.id.radio_grp);
        final RadioButton slot1 = v.findViewById(R.id.slot1);
        final RadioButton slot2 = v.findViewById(R.id.slot2);
        final RadioButton slot3 = v.findViewById(R.id.slot3);

        if(preference.getTheme()==1){
            slot1.setTextColor(Color.WHITE);
            slot2.setTextColor(Color.WHITE);
            slot3.setTextColor(Color.WHITE);
        }

        final ArrayList mData = new ArrayList(slotList.entrySet());
        final List<Double> price = new ArrayList<>();
        for(int i=0;i<mData.size();i++){
            Map.Entry<String, Double> item = (Map.Entry) mData.get(i);
            if(i==0){
                slot1.setText(item.getKey());
                price.add(item.getValue());
            }
            if(i==1){
                slot2.setText(item.getKey());
                price.add(item.getValue());
            }
            if(i==2){
                slot3.setText(item.getKey());
                price.add(item.getValue());
            }
        }
        slot1.setChecked(true);
        delivery_charges.setText("$ "+price.get(0));
        delivery = price.get(0);

        subtotal.setText("$ "+String.format("%.2f",sum));
        Double d = sum+delivery;
        total_amount.setText("$ "+String.format("%.2f",d));

        rgb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(slot1.isChecked()){
                    delivery_charges.setText("$ "+price.get(0));
                    delivery = price.get(0);

                    subtotal.setText("$ "+String.format("%.2f",sum));
                    Double d = sum+delivery;
                    total_amount.setText("$ "+String.format("%.2f",d));
                }

                else if(slot2.isChecked()){
                    delivery_charges.setText("$ "+price.get(1));
                    delivery = price.get(1);

                    subtotal.setText("$ "+String.format("%.2f",sum));
                    Double d = sum+delivery;
                    total_amount.setText("$ "+String.format("%.2f",d));
                }

               else if(slot3.isChecked()){
                    delivery_charges.setText("$ "+price.get(2));
                    delivery = price.get(2);

                    subtotal.setText("$ "+String.format("%.2f",sum));
                    Double d = sum+delivery;
                    total_amount.setText("$ "+String.format("%.2f",d));
                }
            }
        });

       */



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
                                subtotal.setText("$ "+String.format("%.2f",sum));
                                Double d = sum+delivery-preference.getLoyaltyPoints();
                                total_amount.setText("$ "+String.format("%.2f",d));
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

                            subtotal.setText("$ "+String.format("%.2f",sum));
                            Double d = sum+delivery;
                            total_amount.setText("$ "+String.format("%.2f",d));
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

                subtotal.setText("$ "+String.format("%.2f",sum));
                Double d = sum+delivery;
                total_amount.setText("$ "+String.format("%.2f",d));
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

                    subtotal.setText("$ "+String.format("%.2f",sum));
                    Double d = sum+delivery;
                    total_amount.setText("$ "+String.format("%.2f",d));
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


    private void shopData(){
        RetrofitClient api_manager = new RetrofitClient();
        UsersApi retrofit_interface =api_manager.usersClient().create(UsersApi.class);

        Call<ShopResponse> call= retrofit_interface.getShopProfile(preference.getShopId());

        call.enqueue(new Callback<ShopResponse>() {
            @Override
            public void onResponse(Call<ShopResponse> call, Response<ShopResponse> response) {
                try {
                    Log.e("shopResponse",response+"");
                    Log.e("shopResponse",response.code()+"");
                    Log.e("shopResponse",response.message()+"");

                    preference.setShopTimeSlot(response.body().getData().getDeliverySlots());
                    preference.setShopDeliveryCharges(response.body().getData().getDeliveryCharges());
                    preference.setShopPh(response.body().getData().getPhone());

                    SimpleDateFormat dateFormat = new SimpleDateFormat( "dd MMM, EEE");
                    Calendar cal = Calendar.getInstance();
                    cal.add( Calendar.DATE, 1 );
                    String convertedDate = dateFormat.format(cal.getTime());
                    Log.e("Slots 00",response.body().getData().getDeliverySlots().toString());
                    try {
                        for(int i = 0;i<3;i++){
                            slotList.put(response.body().getData().getDeliverySlots().get(i)+"\n"+convertedDate,Double.parseDouble(response.body().getData().getDeliveryCharges().get(i)));
                        }
                        Log.e("Slots 0",slotList.toString());
                        setdata();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    Log.e("shopResponse",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<ShopResponse> call, Throwable t) {
                Log.e("shopResponse",t.getLocalizedMessage()+"");
            }
        });
    }

    private void setdata() {

        final ArrayList mData = new ArrayList(slotList.entrySet());
        Log.e("Slots 1",mData.toString());
        final List<Double> price = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            Map.Entry<String, Double> item = (Map.Entry) mData.get(i);
            if (i == 0) {
                slot1.setText(item.getKey());
                price.add(item.getValue());
            }
            if (i == 1) {
                slot2.setText(item.getKey());
                price.add(item.getValue());
            }
            if (i == 2) {
                slot3.setText(item.getKey());
                price.add(item.getValue());
            }
        }
        slot1.setChecked(true);
        delivery_charges.setText("$ " + price.get(0));
        delivery = price.get(0);

        subtotal.setText("$ " + String.format("%.2f", sum));
        Double d = sum + delivery;
        total_amount.setText("$ " + String.format("%.2f", d));

        rgb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (slot1.isChecked()) {
                    delivery_charges.setText("$ " + price.get(0));
                    delivery = price.get(0);

                    subtotal.setText("$ " + String.format("%.2f", sum));
                    Double d = sum + delivery;
                    total_amount.setText("$ " + String.format("%.2f", d));
                } else if (slot2.isChecked()) {
                    delivery_charges.setText("$ " + price.get(1));
                    delivery = price.get(1);

                    subtotal.setText("$ " + String.format("%.2f", sum));
                    Double d = sum + delivery;
                    total_amount.setText("$ " + String.format("%.2f", d));
                } else if (slot3.isChecked()) {
                    delivery_charges.setText("$ " + price.get(2));
                    delivery = price.get(2);

                    subtotal.setText("$ " + String.format("%.2f", sum));
                    Double d = sum + delivery;
                    total_amount.setText("$ " + String.format("%.2f", d));
                }
            }
        });

    }

}
