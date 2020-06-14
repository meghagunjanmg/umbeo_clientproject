package com.example.umbeo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.umbeo.api.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signup extends AppCompatActivity {

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText nam, pno, mail, pass, add;
    Button sign, imageset;
    ImageView dp;

    String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        dp = (ImageView) findViewById(R.id.dp);
        sign = (Button) findViewById(R.id.button);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();


            }
        });

        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(signup.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
                } else
                    selectImage();
            }
        });




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(signup.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImageUrl = data.getData();
                if (selectedImageUrl != null) {
                    try {
                        // set the image to display...
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUrl);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        dp.setImageBitmap(bitmap);

                        //call function to go for base64 conversion...
                        // Following is the selected image file
                        File selectedImageFile = new File(getPathFromUri(selectedImageUrl));
                        // do the conversion...

                        encodedImage = encodeImage(selectedImageFile);

                    } catch (Exception e) {
                        Toast.makeText(signup.this, "Error: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    private String getPathFromUri(Uri contentUri) {
        String filePath;
        Cursor cursor = getContentResolver()
                .query(contentUri, null, null, null, null);
        if (cursor == null) {
            filePath = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;

    }


    private String encodeImage(File selectedImageFile) throws Exception {

        InputStream inputStream = new FileInputStream(selectedImageFile); // You can get an inputStream using any I/O API
        byte[] bytes;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            Toast.makeText(signup.this, "Error: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }

        bytes = output.toByteArray();
        String encoded = Base64.encodeToString(bytes, Base64.DEFAULT);

        return encoded;
    }


    public void upload() {

        nam = (EditText) findViewById(R.id.name);
        pno = (EditText) findViewById(R.id.phone);
        mail = (EditText) findViewById(R.id.mail);
        pass = (EditText) findViewById(R.id.pass);
        add = (EditText) findViewById(R.id.address);

        String name = nam.getText().toString().trim();
        String number = pno.getText().toString();
        String email = mail.getText().toString().trim();
        String password = pass.getText().toString();
        String add1 = add.getText().toString();

        String add2="new address";
        String[] address= {add1, "new address"};
        if(name.isEmpty()){
            Toast.makeText(signup.this,"Please enter a user name",Toast.LENGTH_LONG).show();
            return;
        }

        if(number.isEmpty()){
            Toast.makeText(signup.this,"Please enter a Number",Toast.LENGTH_LONG).show();
            return;
        }

        if(email.isEmpty()){
            Toast.makeText(signup.this,"Please enter a email address",Toast.LENGTH_LONG).show();
            return;
        }

        if(!(email.matches(emailPattern))) {
            Toast.makeText(signup.this, "Please enter a valid email address", Toast.LENGTH_LONG).show();
            return;
        }

        if(password.isEmpty()){
            Toast.makeText(signup.this,"Please enter a password",Toast.LENGTH_LONG).show();
            return;
        }

        if(add1.isEmpty()){
            Toast.makeText(signup.this,"Please enter a user address",Toast.LENGTH_LONG).show();
            return;
        }


        if (encodedImage == null) {
            // that is user has not selected a display image then we show the error and return so that he/she can try again...
            Toast.makeText(signup.this, "Please Select a Profile Picture first to continue", Toast.LENGTH_LONG).show();
            return;

        }

        // GPS API implementation is left...that's why taking pre-defined values of gps and latitude and longitude...
        String gps = "Delhi";
        String latlong = "112.00 78.015";

        // Also taking pre-defined value of shop for the time being...

        String shop = "5ec8b35d94e1c83f430781a2";

        Call<ResponseBody> call = RetrofitClient
                .getmInstance()
                .getApi()
                .Signup(name, number, email, password, gps, latlong, address, shop, encodedImage);

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


                            } else {
                                String s = response.errorBody().string();
                                JSONObject temp = new JSONObject(s);
                                Toast.makeText(getApplicationContext(), "Error: " + temp.get("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (IOException | JSONException e) {
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
                        Toast.makeText(getApplicationContext(), "Error: " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}