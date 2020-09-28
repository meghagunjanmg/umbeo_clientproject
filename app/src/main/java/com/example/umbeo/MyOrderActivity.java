package com.example.umbeo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.UsersApi;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.CancelOrder;
import com.example.umbeo.response_data.GetOrders.GetOrderResponse;
import com.example.umbeo.response_data.GetOrders.OrdersList;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.OrderEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import spencerstudios.com.bungeelib.Bungee;

public class MyOrderActivity extends AppCompatActivity {
    TextView history,current, all;
    ImageView back_btn;
    UserPreference preference;
    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = new UserPreference(this);

        if(preference.getTheme()==1){
            setContentView(R.layout.dark_order);
        }
        else
        {
            setContentView(R.layout.activity_orders);
        }



        getAllOrder();

        context = this;

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() ,HomeScreenActivity.class));
                Bungee.fade(MyOrderActivity.this);
            }
        });

        history=findViewById(R.id.history);
        current=findViewById(R.id.current);
        all=findViewById(R.id.all);

       /* current.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.main2));
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
 */
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.setTextColor(ColorStateList.valueOf(Color.parseColor("#F84B18")));
                current.setBackgroundResource(R.drawable.bg_feature_card2);

                history.setBackgroundResource(R.drawable.bg_feature_card);
                history.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                history.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F84B18")));
                all.setTextColor(ColorStateList.valueOf(Color.parseColor("#F84B18")));
                all.setBackgroundResource(R.drawable.bg_feature_card2);



                // Create new fragment and transaction
                Fragment newFragment = new OrderHistoryFragment(historicOrder);
                // consider using Java coding conventions (upper first char class names!!!)

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, newFragment).commit();

                // Commit the transaction

            }
        });

        history.setTextColor(ColorStateList.valueOf(Color.parseColor("#F84B18")));
        history.setBackgroundResource(R.drawable.bg_feature_card2);

        current.setBackgroundResource(R.drawable.bg_feature_card);
        current.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        current.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F84B18")));
        all.setTextColor(ColorStateList.valueOf(Color.parseColor("#F84B18")));
        all.setBackgroundResource(R.drawable.bg_feature_card2);

        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                history.setTextColor(ColorStateList.valueOf(Color.parseColor("#F84B18")));
                history.setBackgroundResource(R.drawable.bg_feature_card2);

                current.setBackgroundResource(R.drawable.bg_feature_card);
                current.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                current.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F84B18")));
                all.setTextColor(ColorStateList.valueOf(Color.parseColor("#F84B18")));
                all.setBackgroundResource(R.drawable.bg_feature_card2);

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

                current.setTextColor(ColorStateList.valueOf(Color.parseColor("#F84B18")));
                current.setBackgroundResource(R.drawable.bg_feature_card2);

                all.setBackgroundResource(R.drawable.bg_feature_card);
                all.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                all.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F84B18")));
                history.setTextColor(ColorStateList.valueOf(Color.parseColor("#F84B18")));
                history.setBackgroundResource(R.drawable.bg_feature_card2);


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
        UsersApi retrofit_interface =api_manager.usersClient().create(UsersApi.class);

        String token = "Bearer "+preference.getToken();
        Log.e("GetOrderResponse ",token);

        Call<GetOrderResponse> call = retrofit_interface.GetOrder(token,preference.getShopId());

        call.enqueue(new Callback<GetOrderResponse>() {
            @Override
            public void onResponse(Call<GetOrderResponse> call, final Response<GetOrderResponse> response) {
                try {
                    Log.e("GetOrderResponse",response+"");
                    Log.e("GetOrderResponse",response.code()+"");
                    Log.e("GetOrderResponse",response.message()+"");
                    if(response.code()==200){
                        final List<OrderEntity> entities = new ArrayList<>();
                        for(int i=0;i<response.body().getData().size();i++){

                            OrdersList ordersList = response.body().getData().get(i);
                            String productName ="";
                            for (int j=0;j<response.body().getData().get(i).getProducts().size();j++){
                                productName = productName+","+response.body().getData().get(i).getProducts().get(j).getProduct().getName()+" X "+response.body().getData().get(i).getProducts().get(j).getQuantity();
                            }
                            entities.add(new OrderEntity(ordersList.getConfirmedByUser(),ordersList.getConfirmedByShop(),ordersList.getCancelledByUser(),ordersList.getCancelledByShop(),ordersList.getId(),ordersList.getOrderStatus(),ordersList.getTotalAmount(),productName,ordersList.getCreatedAt(),ordersList.getDeliveryInstructions()));
                            Log.e("GetOrderResponse",productName+"");
                            if(response.body().getData().get(i).getOrderStatus()!=3){
                                currentOrder.add(response.body().getData().get(i));
                            }
                            else historicOrder.add(response.body().getData().get(i));
                        }
                        Fragment newFragment = new OrderCurrentFragment(currentOrder);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, newFragment).commit();


                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                AppDatabase appDatabase = AppDatabase.getInstance(MyOrderActivity.this);
                                appDatabase.orderDao().nukeTable();
                                appDatabase.orderDao().insertAll(entities);

                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetOrderResponse> call, Throwable t) {
                Log.e("GetOrderResponse",t.getLocalizedMessage()+"");
                Fragment newFragment = new OrderCurrentFragment(currentOrder);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, newFragment).commit();
            }
        });
    }



    public static void cancelOrder(String orderId){
        UserPreference preference = new UserPreference(context);
        RetrofitClient api_manager = new RetrofitClient();
        UsersApi retrofit_interface =api_manager.usersClient().create(UsersApi.class);

        String token = "Bearer "+preference.getToken();
        Log.e("GetOrderResponse ",token);

        Call<CancelOrder> call = retrofit_interface.CancelOrder(token,orderId);

        call.enqueue(new Callback<CancelOrder>() {
            @Override
            public void onResponse(Call<CancelOrder> call, Response<CancelOrder> response) {
                try {
                    Log.e("cancelorders",response.code()+"");
                    Log.e("cancelorders",response.message()+"");
                    Log.e("cancelorders",response.body().getStatus()+"");
                    if(response.code()==200){
                        Toast.makeText(context,"Your Order has been cancelled",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CancelOrder> call, Throwable t) {
                Log.e("cancelorders",t.getLocalizedMessage()+"");
            }
        });
    }
}