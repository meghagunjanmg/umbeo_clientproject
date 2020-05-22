package com.example.umbeo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.umbeo.api.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signup extends AppCompatActivity {

    EditText nam,pno,mail,pass;
    Button sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sign=(Button)findViewById(R.id.button);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });





    }

    public void upload(){

        nam=(EditText)findViewById(R.id.name);
        pno=(EditText)findViewById(R.id.phone);
        mail=(EditText)findViewById(R.id.mail);
        pass=(EditText)findViewById(R.id.pass);


        String name= nam.getText().toString().trim();
        String number=pno.getText().toString();
        String email=mail.getText().toString().trim();
        String password=pass.getText().toString();

        Call<ResponseBody> call = RetrofitClient
                .getmInstance()
                .getApi()
                .Signup(name,number,email,password);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            if (response.code() == 201) {

                                String s = response.body().string();
                                JSONObject temp = new JSONObject(s);
                                String res1 = temp.get("status").toString();
                                Toast.makeText(getApplicationContext(), "" + temp.get("status").toString(), Toast.LENGTH_LONG).show();
                                if (res1.matches("success"))
                                    startActivity(new Intent(signup.this, homescreen.class));
                                if (res1.matches("failure"))
                                    startActivity(new Intent(signup.this, signup.class));


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
            public void onFailure(Call<ResponseBody> call, final Throwable t) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Error: "+t.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
