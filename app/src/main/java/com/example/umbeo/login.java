package com.example.umbeo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.LoginResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class login extends AppCompatActivity {

    Button signup,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup=(Button)findViewById(R.id.button2);
        login=(Button)findViewById(R.id.button);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, com.example.umbeo.signup.class));
            }
        });
        final TextView forgotpassword=(TextView)findViewById(R.id.forget);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,forget_password.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlogin();
            }
        });
    }
    private void userlogin(){
        EditText uname=(EditText)findViewById(R.id.username);
        EditText pass=(EditText)findViewById(R.id.pass);

        String email = uname.getText().toString();
        String password= pass.getText().toString();
        Call<LoginResponse>call= RetrofitClient
                .getmInstance()
                .getApi()
                .userLogin(email,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, final Response<LoginResponse> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            if (response.code() == 200) {

                                LoginResponse loginResponse= response.body();
                                if(loginResponse.getStatus().toString().matches("success"))
                                    startActivity(new Intent(login.this,shopscreen.class));


                            }
                            else {
                                String s = response.errorBody().string();
                                JSONObject temp=new JSONObject(s);
                                Toast.makeText(getApplicationContext(),"Error: "+temp.get("message"),Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (IOException | JSONException e) {
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
}
