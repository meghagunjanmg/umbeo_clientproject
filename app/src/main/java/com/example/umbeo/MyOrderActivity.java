package com.example.umbeo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.Api;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.GetOrders.GetOrderResponse;
import com.example.umbeo.response_data.GetOrders.OrdersList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderActivity extends AppCompatActivity {
    Button history,current, all;
    ImageView back_btn;
    UserPreference preference;
    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = new UserPreference(this);

        if(preference.getTheme()==1){
            setContentView(R.layout.dark_order);
        }
        else
        setContentView(R.layout.activity_orders);



        getAllOrder();


        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() ,HomeScreenActivity.class));
            }
        });

        history=(Button)findViewById(R.id.history);
        current=(Button)findViewById(R.id.current);
        all=(Button)findViewById(R.id.all);

        current.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.main2));
        current.setTextColor(R.color.colorWhite);

        history.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorWhite));
        history.setTextColor(R.color.main2);

        all.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorWhite));
        all.setTextColor(R.color.main2);

        // Create new fragment and transaction
        Fragment newFragment = new OrderCurrentFragment(currentOrder);
        // consider using Java coding conventions (upper first char class names!!!)

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, newFragment).commit();

        // Commit the transaction

        history.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                history.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.main2));
                history.setTextColor(R.color.colorWhite);

                current.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorWhite));
                current.setTextColor(R.color.main2);

                all.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorWhite));
                all.setTextColor(R.color.main2);

                // Create new fragment and transaction
                Fragment newFragment = new OrderHistoryFragment(historicOrder);
                // consider using Java coding conventions (upper first char class names!!!)

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, newFragment).commit();

                // Commit the transaction

            }
        });

        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                current.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.main2));
                current.setTextColor(R.color.colorWhite);

                history.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorWhite));
                history.setTextColor(R.color.main2);

                all.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorWhite));
                all.setTextColor(R.color.main2);

                // Create new fragment and transaction
                Fragment newFragment = new OrderCurrentFragment(currentOrder);
                // consider using Java coding conventions (upper first char class names!!!)

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, newFragment).commit();
                // Commit the transaction
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                all.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.main2));
                all.setTextColor(R.color.colorWhite);

                history.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorWhite));
                history.setTextColor(R.color.main2);

                current.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorWhite));
                current.setTextColor(R.color.main2);


                // Create new fragment and transaction
                Fragment newFragment = new OrderAllFragment(currentOrder,historicOrder);
                // consider using Java coding conventions (upper first char class names!!!)

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, newFragment).commit();


            }
        });
    }

    List<OrdersList> currentOrder = new ArrayList<>();
    List<OrdersList> historicOrder = new ArrayList<>();
    List<OrdersList> allOrder = new ArrayList<>();


    private void getAllOrder() {
        RetrofitClient api_manager = new RetrofitClient();
        Api retrofit_interface =api_manager.usersClient().create(Api.class);

        String token = "Bearer "+preference.getToken();

        Call<GetOrderResponse> call = retrofit_interface.GetOrder(token);

        call.enqueue(new Callback<GetOrderResponse>() {
            @Override
            public void onResponse(Call<GetOrderResponse> call, Response<GetOrderResponse> response) {
                Log.e("GetOrderResponse",response+"");
                Log.e("GetOrderResponse",response.code()+"");
                Log.e("GetOrderResponse",response.message()+"");
                if(response.code()==200){
                    for(int i=0;i<response.body().getData().size();i++){
                        if(response.body().getData().get(i).getOrderStatus()!=3){
                            currentOrder.add(response.body().getData().get(i));
                        }
                        else historicOrder.add(response.body().getData().get(i));
                    }
                }
            }

            @Override
            public void onFailure(Call<GetOrderResponse> call, Throwable t) {

            }
        });
    }


}