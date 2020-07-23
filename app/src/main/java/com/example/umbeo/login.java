package com.example.umbeo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.umbeo.Storage.SharedprefManager;
import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.Api;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.LoginResponse;
import com.example.umbeo.response_data.UserGetProfileResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class login extends AppCompatActivity {

    String emailPattern = "^(?:\\d{10}|\\w+@\\w+\\.\\w{2,3})$";
    Button  login;
    TextView signu;
    UserPreference preference;
    ImageView back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = new UserPreference(getApplicationContext());

        if(preference.getTheme()==1){
            setContentView(R.layout.dark_login);
        }
        else
        setContentView(R.layout.activity_login);
        signu=(TextView)findViewById(R.id.signin);


        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                HomeScreenActivity.viewPager.setCurrentItem(0);
            }
        });

        signu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, com.example.umbeo.signup.class));
            }
        });
        login = (Button) findViewById(R.id.button);

        final TextView forgotpassword = (TextView) findViewById(R.id.forget);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, forget_password.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlogin();
            }
        });
    }

    private void userlogin() {
        EditText uname = (EditText) findViewById(R.id.username);
        EditText pass = (EditText) findViewById(R.id.pass);
        TextView err = (TextView) findViewById(R.id.status);

        String email = uname.getText().toString();
        String password = pass.getText().toString();

        login.setEnabled(false);

        if (email.isEmpty() && password.isEmpty()) {
            err.setBackgroundColor(Color.parseColor("#f0f8ff"));
            err.setText("Please Enter Email and password");
            login.setEnabled(true);
            return;
        }

        if (email.isEmpty()) {
            err.setBackgroundColor(Color.parseColor("#f0f8ff"));
            err.setText("Please Enter Email");
            login.setEnabled(true);
            return;
        }
        if (!(email.matches(emailPattern))) {
            err.setBackgroundColor(Color.parseColor("#f0f8ff"));
            err.setText("Please Enter valid Email/Phone Number");
            login.setEnabled(true);
            return;
        }
        if (password.isEmpty()) {
            err.setBackgroundColor(Color.parseColor("#f0f8ff"));
            login.setEnabled(true);
            err.setText("Please Enter a Password");
            return;
        }


        RetrofitClient api_manager = new RetrofitClient();
        Api retrofit_interface =api_manager.usersClient().create(Api.class);

      Call<LoginResponse> call =  retrofit_interface.userLogin(email,password,preference.getShopId());
      call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                try {
                    Log.e("LoginResponse",""+response);
                    Log.e("LoginResponse",response.code()+"");
                    Log.e("LoginResponse",response.message()+"");
                    Log.e("LoginResponse",response.body().getStatus()+"");
                    if (response.code() == 200) {
                        login.setEnabled(true);

                        Toast.makeText(com.example.umbeo.login.this,"Login Successful",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        //i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        i.putExtra("token",response.body().getData());
                        i.putExtra("intent",1);
                        startActivity(i);

                        getProfile(response.body().getData());
                    }
                    else if(response.code()==401){
                        Toast.makeText(com.example.umbeo.login.this,"Invalid email/password",Toast.LENGTH_LONG).show();
                    }
                    else {
                        login.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Error: " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    login.setEnabled(true);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                login.setEnabled(true);
            }
        });

        /*
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, final Response<LoginResponse> response) {
                        try {
                            if (response.code() == 200) {

                                LoginResponse loginResponse = response.body();
                                if (loginResponse.getStatus().toString().matches("success")) {
                                    //SharedprefManager.getInstance(com.example.umbeo.login.this).saveToken(loginResponse.getToken());
                                    //login.setEnabled(true);
                                    //Toast.makeText(com.example.umbeo.login.this,"Go to Cart to complete Payment",Toast.LENGTH_LONG).show();

                                    // startActivity(new Intent(login.this, shopscreen.class));
                                   // preference.setToken(response.body().getToken());

                                    Toast.makeText(com.example.umbeo.login.this,"Login Successful",Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(getApplicationContext(),HomeScreenActivity.class));
                                }
                                else{
                                    //login.setEnabled(true);
                                }

                            } else {
                                String s = response.errorBody().string();
                                JSONObject temp = new JSONObject(s);
                                //login.setEnabled(true);

                            }
                        } catch (IOException | JSONException e) {
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                           // login.setEnabled(true);
                        }
                    }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //login.setEnabled(true);
            }
        });

         */
    }
    private void getProfile(final String tokens){
        RetrofitClient api_manager = new RetrofitClient();
        Api retrofit_interface =api_manager.usersClient().create(Api.class);

        String token = "Bearer "+tokens;

        Call<UserGetProfileResponse> call= retrofit_interface.getProfile(token);

        call.enqueue(new Callback<UserGetProfileResponse>() {
            @Override
            public void onResponse(Call<UserGetProfileResponse> call, Response<UserGetProfileResponse> response) {
                Log.e("UserGetProfileResponse",response.code()+"");
                Log.e("UserGetProfileResponse",response.message()+"");

                if(response.code()==200) {
                    preference.setUserName(response.body().getData().getName());
                    preference.setEmail(response.body().getData().getEmail());
                    preference.setLoyaltyPoints(response.body().getData().getLoyaltyPoints());
                    preference.setAddresses(response.body().getData().getDeliveryAddresses());
                    preference.setProfilePic(response.body().getData().getProfile_pic());
                    preference.setToken(tokens);
                }
            }

            @Override
            public void onFailure(Call<UserGetProfileResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.finish();
    }


    public void back(){
        onBackPressed();
        HomeScreenActivity.refreshFragments();
    }

}
