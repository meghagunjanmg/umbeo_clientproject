package com.example.umbeo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.UsersApi;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.SignUpResquest;
import com.example.umbeo.response_data.UserGetProfileResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import spencerstudios.com.bungeelib.Bungee;

import static android.widget.AbsListView.CHOICE_MODE_SINGLE;

public class MyAddresses extends AppCompatActivity {
    static ListView address_list;
    static UserPreference preference;
    Button add;
    ImageView back_btn;
    static AdapterAddress adapterAddress;
    public static Activity activity = null;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = new UserPreference(getApplicationContext());
        if(preference.getTheme()==1){
            setContentView(R.layout.dark_addresses);
        }
        else setContentView(R.layout.list_of_address);

        activity = this;

        address_list = findViewById(R.id.address_list);
        add = findViewById(R.id.add);
        //getProfile();


        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        try {
            if(preference.getAddresses()!=null && preference.getAddresses().size()!=0 ){
                RecyclerView address_recycler = findViewById(R.id.address_recycler);
                address_recycler.setLayoutManager(new LinearLayoutManager(this));
                adapterAddress = new AdapterAddress(preference.getAddresses(),MyAddresses.this);
                address_recycler.setAdapter(adapterAddress);


                //myAdapter = new AddressAdapter(this, R.layout.my_address_lists, preference.getAddresses());
                //address_list.setChoiceMode(CHOICE_MODE_SINGLE);
                //address_list.setAdapter(myAdapter);
            }

            else if(Objects.requireNonNull(preference.getAddresses()).size()==0 || preference.getAddresses().get(0).equals(" ")){
                startActivity(new Intent(getApplicationContext(), MapActivity.class));
                finish();
                Bungee.fade(MyAddresses.this);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapActivity.class));
                finish();
                Bungee.fade(MyAddresses.this);
            }
        });
    }

    public static void selectItem(final int i) {
        for (int j = 0; j < address_list.getAdapter().getCount(); j++) {
            if (j == i) {
                address_list.setItemChecked(j, true);
            } else {
                address_list.setItemChecked(j, false);
            }
        }
    }


    public static void changeAdd(final int i, final String newstring, final Context context) {
        List<String> addressList = new ArrayList<>();
        for (int j = 0; j < preference.getAddresses().size(); j++) {
            if (j == i) {
                addressList.add(j, newstring);
            } else {
                addressList.add(preference.getAddresses().get(j));
            }
        }
        preference.setAddresses(addressList);
        //AddressAdapter myAdapter = new AddressAdapter(context, R.layout.my_address_lists, addressList);
        //address_list.setAdapter(myAdapter);

        adapterAddress.notifyDataSetChanged();


        updateAddress(context);
    }


    public static void removeAdd(int i, Context context) {
        List<String> addressList = new ArrayList<>();
        for (int j = 0; j < preference.getAddresses().size(); j++) {
            if (j == i) {
                preference.getAddresses().remove(j);
            } else {
                addressList.add(preference.getAddresses().get(j));
            }
        }
        preference.setAddresses(addressList);
        adapterAddress.notifyDataSetChanged();

        //AddressAdapter myAdapter = new AddressAdapter(context, R.layout.my_address_lists, addressList);
        //address_list.setAdapter(myAdapter);


        updateAddress(context);
    }


    public static void Back(){


        activity.finish();
    }

    private static void updateAddress(final Context context) {
        RetrofitClient api_manager = new RetrofitClient();
        UsersApi retrofit_interface =api_manager.usersClient().create(UsersApi.class);
        final SignUpResquest request = new SignUpResquest();
        request.setShop(preference.getShopId());
        request.setProfilePic(preference.getProfilePic());
        request.setName(preference.getUserName());
        request.setDeliveryAddresses(preference.getAddresses());

        String token = "Bearer "+preference.getToken();
        //String token = "Bearer "+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVlZTRmMjhhOTJjNWE3MDAxNzIwZjE1YSIsImlhdCI6MTU5MzE2MjUzNywiZXhwIjoxNTkzMTY2MTM3fQ.wAC-uZyjgi9bFCj2pK2wOdP8MB2SytNpYwqrYFC3Dy8";

        Log.e("PREFERNCES: 1",preference.getAddresses().toString());
        Log.e("PREFERNCES: 2",request.getDeliveryAddresses().toString());
        Log.e("PREFERNCES: 3",request.getShop().toString());
        Log.e("PREFERNCES: 4",request.getName().toString());
        Log.e("PREFERNCES: 5",request.getProfilePic().toString());
        Log.e("PREFERNCES: 6",token);
        Call<ResponseBody> call= retrofit_interface.updateUser(token,request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    Toast.makeText(context,"Address Updated",Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(context,response.code()+" "+response.message(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}

