package com.example.umbeo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.UsersApi;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.OrderResponse;
import com.example.umbeo.response_data.orderRequest.OrderRequest;
import com.example.umbeo.response_data.orderRequest.Product;
import com.example.umbeo.response_data.orderRequest.Product_;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.CartDao;
import com.example.umbeo.room.CartEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import spencerstudios.com.bungeelib.Bungee;

public class PaymentActivity extends AppCompatActivity  implements RadioGroup.OnCheckedChangeListener{
    UserPreference preference;
    TextView amount;
    private int counter=1;
    RadioGroup radioGroup;
    boolean flag = true;
    ImageView back_btn,cart_btn;
    RadioButton cas,amazonpay,applepay,paypal,amazonupi,googleupi;
    Button sendd;
    String Total,deliveryTime,deliveryAdd,deliveryIns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = new UserPreference(this);

        if(preference.getTheme()==1){
            setContentView(R.layout.dark_payment);
        }
        else setContentView(R.layout.activity_payment);

        Total = getIntent().getStringExtra("total");
        deliveryTime = getIntent().getStringExtra("deliveryTime");
        deliveryAdd = getIntent().getStringExtra("deliveryAdd");
        deliveryIns = getIntent().getStringExtra("deliveryIns");


        radioGroup = findViewById(R.id.role_radioGroup_ID);
        radioGroup.setOnCheckedChangeListener(this);


        amount=(TextView) findViewById(R.id.amount);
        amount.setText(Total+"");


        cas=(RadioButton) findViewById(R.id.cash);
        amazonpay=(RadioButton) findViewById(R.id.amazonPay);
        applepay=(RadioButton) findViewById(R.id.applePay);
        paypal=(RadioButton) findViewById(R.id.payPal);
        amazonupi=(RadioButton) findViewById(R.id.amazonPayUPI);
        googleupi=(RadioButton) findViewById(R.id.googleUPI);


        sendd=(Button) findViewById(R.id.send);
        sendbt();

        back_btn = findViewById(R.id.back_btn);
        cart_btn = findViewById(R.id.cart_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                HomeScreenActivity.viewPager.setCurrentItem(1);
            }
        });
        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),HomeScreenActivity.class);
                i.putExtra("Cat",5);
                startActivity(i);
                Bungee.fade(PaymentActivity.this);
            }
        });

    }

    private void dailog() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(PaymentActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_payment_pop_up, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();


        dialog.setCancelable(false);

        Button shopping=(Button) mView.findViewById(R.id.send);
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),HomeScreenActivity.class);
                //i.putExtra("Cat",5);
                startActivity(i);
                Bungee.fade(PaymentActivity.this);
            }
        });

        TextView go_order = mView.findViewById(R.id.go_order);
        go_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyOrderActivity.class));
                Bungee.fade(PaymentActivity.this);
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

                Toast.makeText(getApplicationContext(), "You have selected Cash as mode of Payment", Toast.LENGTH_LONG).show();
            } else {
                counter++;
                Toast.makeText(getApplicationContext(), "You have selected Cash as mode of Payment", Toast.LENGTH_LONG).show();
            }
        } else if (amazonpay.isChecked() == true) {
            if (counter > 1) {
                cas.setChecked(false);
                applepay.setChecked(false);
                paypal.setChecked(false);
                amazonupi.setChecked(false);
                googleupi.setChecked(false);


                Toast.makeText(getApplicationContext(), "You have selected Amazon Pay as mode of Payment", Toast.LENGTH_LONG).show();
            } else {
                counter++;
                Toast.makeText(getApplicationContext(), "You have selected Amazon Pay as mode of Payment", Toast.LENGTH_LONG).show();

            }
        } else if (applepay.isChecked() == true) {
            if (counter > 1) {
                amazonpay.setChecked(false);
                cas.setChecked(false);
                paypal.setChecked(false);
                amazonupi.setChecked(false);
                googleupi.setChecked(false);

                Toast.makeText(getApplicationContext(), "You have selected Apple Pay as mode of Payment", Toast.LENGTH_LONG).show();
            } else {
                counter++;
                Toast.makeText(getApplicationContext(), "You have selected Apple Pay as mode of Payment", Toast.LENGTH_LONG).show();

            }
        } else if (paypal.isChecked() == true) {
            if (counter > 1) {
                amazonpay.setChecked(false);
                applepay.setChecked(false);
                cas.setChecked(false);
                amazonupi.setChecked(false);
                googleupi.setChecked(false);

                Toast.makeText(getApplicationContext(), "You have selected Pay Pal as mode of Payment", Toast.LENGTH_LONG).show();
            } else {
                counter++;
                Toast.makeText(getApplicationContext(), "You have selected Pay Pal as mode of Payment", Toast.LENGTH_LONG).show();

            }
        } else if (amazonupi.isChecked() == true) {
            if (counter > 1) {
                amazonpay.setChecked(false);
                applepay.setChecked(false);
                paypal.setChecked(false);
                cas.setChecked(false);
                googleupi.setChecked(false);

                Toast.makeText(getApplicationContext(), "You have selected Amazon UPI as mode of Payment", Toast.LENGTH_LONG).show();
            } else {
                counter++;
                Toast.makeText(getApplicationContext(), "You have selected Amazon UPI as mode of Payment", Toast.LENGTH_LONG).show();

            }
        } else if (googleupi.isChecked() == true) {
            if (counter > 1) {
                amazonpay.setChecked(false);
                applepay.setChecked(false);
                paypal.setChecked(false);
                amazonupi.setChecked(false);
                cas.setChecked(false);

                Toast.makeText(getApplicationContext(), "You have selected Google UPI as mode of Payment", Toast.LENGTH_LONG).show();
            } else {
                counter++;
                Toast.makeText(getApplicationContext(), "You have selected Google UPI as mode of Payment", Toast.LENGTH_LONG).show();

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
                    createOrder(Total);
                    //dailog();
                }
            });
        }
        else {
            sendd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // startActivity(new Intent(getContext(),payment_popUp.class));
                    Toast.makeText(getApplicationContext(),"Select Payment option",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void createOrder(String total) {
        DBLoadAll();

        final UserPreference preference = new UserPreference(this);
        RetrofitClient api_manager = new RetrofitClient();
        UsersApi retrofit_interface =api_manager.usersClient().create(UsersApi.class);

        String token = "Bearer "+preference.getToken();

        String amt = total.replace("$","");
        double amount = Double.parseDouble(amt.trim());

        final OrderRequest request = new OrderRequest();
        request.setTotalAmount(amount);
        request.setShopId(preference.getShopId());

        List<Product> products = new ArrayList<>();

        for(int i = 0 ; i<cartEntities.size();i++) {
            Product product = new Product();

            Product_ p = new Product_();
            p.setCategoryId(cartEntities.get(i).getCategoryId());
            p.setName(cartEntities.get(i).getName());
            p.setDescription(cartEntities.get(i).getDescription());
            p.setPrice(cartEntities.get(i).getPrice());
            p.setSubCategory(cartEntities.get(i).getSubCategoryId());

            product.setQuantity(cartEntities.get(i).getQuantity());
            product.setProduct(p);
            products.add(product);
        }

        request.setProducts(products);
        request.setStatus(0);
        request.setDeliveryAdress(deliveryAdd);
        if(deliveryIns.equals("")||deliveryIns.isEmpty()){
            deliveryIns = " ";
        }
        request.setDeliveryInstructions(deliveryIns);
        request.setDeliverySlot(deliveryTime);

        Call<OrderResponse> call = retrofit_interface.CreateOrder(token,request);

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                Log.e("OrderResponse",response+"");
                Log.e("OrderResponse",response.code()+"");
                Log.e("OrderResponse",response.message()+"");
                if(response.code()==200){
                    dailog();

                    DBDeleteAll();
                }
               // else if(preference.getUserName()==null){Toast.makeText(getApplicationContext(),"First SignUp/Login", Toast.LENGTH_SHORT).show();}
                else Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e("OrderResponse",t.getLocalizedMessage()+"");
            }
        });
    }

    List<CartEntity> cartEntities = new ArrayList<>();
    private void DBLoadAll() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                CartDao dao = AppDatabase.getInstance(getApplicationContext()).cartDao();
                cartEntities =  dao.loadAll();
            }
        });
    }

    private void DBDeleteAll() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                CartDao dao = AppDatabase.getInstance(getApplicationContext()).cartDao();
                dao.nukeTable();
            }
        });
    }
}