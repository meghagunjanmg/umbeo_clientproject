package com.example.umbeo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.umbeo.Storage.UserPreference;

public class Feedback extends AppCompatActivity implements View.OnClickListener {

//Declaring EditText
private EditText editTextMessage;

//Send button
private Button buttonSend;

private UserPreference preference;
private LinearLayout linearLayout;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = new UserPreference(this);
        setContentView(R.layout.activity_feedback);

        //Initializing the views
        editTextMessage = findViewById(R.id.feedback_et);

        buttonSend = findViewById(R.id.send);

        linearLayout =  findViewById(R.id.main_linear);

        //Adding click listener
        buttonSend.setOnClickListener(this);


        if(preference.getTheme()==1)
        {
                editTextMessage.setTextColor(Color.WHITE);
                linearLayout.setBackgroundColor(Color.BLACK);
        }


        }


private void sendEmail() {
        //Getting content for email
        String message = editTextMessage.getText().toString().trim();

        //Creating SendMail object
        SendMail sm = new SendMail(this,"" , message);

        //Executing sendmail to send email
        sm.execute();
        }

@Override
public void onClick(View v) {
        sendEmail();
        }
        }