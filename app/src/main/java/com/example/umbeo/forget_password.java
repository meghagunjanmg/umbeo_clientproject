package com.example.umbeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.forgetpassword_response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import spencerstudios.com.bungeelib.Bungee;

public class forget_password extends AppCompatActivity {

    Button send;
    TextView signu;
    UserPreference preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = new UserPreference(this);

        if(preference.getTheme()==1){
            setContentView(R.layout.dark_forgetpassword);
        }
        else
        setContentView(R.layout.activity_forget_password);

        signu=(TextView)findViewById(R.id.signin);

        signu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(forget_password.this, com.example.umbeo.signup.class));
                Bungee.fade(forget_password.this);
            }
        });

        send=(Button)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation();
            }
        });

    }
    private void operation(){
        EditText em=(EditText)findViewById(R.id.email);
        final String email=em.getText().toString();
        send.setEnabled(false);
        Call<forgetpassword_response> call= RetrofitClient
                .getmInstance()
                .getApi()
                .forgetPassword(email);
        call.enqueue(new Callback<forgetpassword_response>() {
            @Override
            public void onResponse(Call<forgetpassword_response> call, final Response<forgetpassword_response> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            if(response.code()==200){
                                forgetpassword_response rep=response.body();
                                if (rep.getStatus().matches("success")){
                                    Intent i = new Intent(forget_password.this,resetpassword.class);
                                    i.putExtra("mail",email);
                                    send.setEnabled(true);
                                    Toast.makeText(getApplicationContext(),"Email Sent for password reset",Toast.LENGTH_LONG).show();
                                    startActivity(i);

                                }
                            }
                            else {
                                String s=response.errorBody().string();
                                JSONObject temp=new JSONObject(s);
                                Toast.makeText(getApplicationContext(),"Error: "+temp.get("message"),Toast.LENGTH_LONG).show();
                                send.setEnabled(true);
                            }
                        }  catch (IOException | JSONException e) {
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                            send.setEnabled(true);
                        }

                    }
                });
            }

            @Override
            public void onFailure(Call<forgetpassword_response> call, Throwable t) {
                send.setEnabled(true);
            }
        });

    }
}
