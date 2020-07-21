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

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.Api;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.SignUpResponse;
import com.example.umbeo.response_data.SignUpResquest;
import com.example.umbeo.response_data.UserGetProfileResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.umbeo.ProfileMainFragment.getBitmapForMediaUri;
import static java.security.AccessController.getContext;

public class signup extends AppCompatActivity {

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText nam, pno, mail, pass, add;
    Button sign, imageset;
    ImageView dp;

    String encodedImage;
    UserPreference preference;
TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = new UserPreference(getApplicationContext());

        if(preference.getTheme()==1){
            setContentView(R.layout.dark_signup);
        }
        else setContentView(R.layout.activity_signup);

        dp = (ImageView) findViewById(R.id.dp);
        sign = (Button) findViewById(R.id.button);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup.this,login.class));
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
                        Bitmap imageBitmap = getBitmapForMediaUri(getApplicationContext(), selectedImageUrl);
                        // profilepic.setImageBitmap(imageBitmap);
                        assert imageBitmap != null;
                        preference.setProfilePic(BitmapToBase64(imageBitmap));
                        dp.setImageBitmap(Base64ToBitmap(preference.getProfilePic()));

                        encodedImage = BitmapToBase64(imageBitmap);
                    } catch (Exception e) {
                        Toast.makeText(signup.this, "Error: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }


    private String BitmapToBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private Bitmap Base64ToBitmap(String encodedImage)
    {
        byte[] imageAsBytes = Base64.decode(encodedImage.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
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
        pass = (EditText) findViewById(R.id.password);
        add = (EditText) findViewById(R.id.address);



        String name = nam.getText().toString().trim();
        String number = pno.getText().toString();
        String email = mail.getText().toString().trim();
        String password = pass.getText().toString();
        // String add1 = add.getText().toString();

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
        // GPS API implementation is left...that's why taking pre-defined values of gps and latitude and longitude...
        String gps = "Delhi";
        String latlong = "112.00 78.015";

        // Also taking pre-defined value of shop for the time being...

        String shop = preference.getShopId();

        List<String> address = new ArrayList<>();
        address.add(" ");

        SignUpResquest resquest = new SignUpResquest();
        resquest.setName(name);
        resquest.setPassword(password);
        resquest.setEmail(email);
        resquest.setDeliveryAddresses(address);
        resquest.setPhone(number);
        resquest.setShop(shop);
        resquest.setProfilePic(encodedImage);
        resquest.setLatlng(latlong);
        resquest.setGps(gps);

        RetrofitClient api_manager = new RetrofitClient();
        Api retrofit_interface =api_manager.usersClient().create(Api.class);


        Call<SignUpResponse> call= retrofit_interface.SignUp("application/json",resquest);

        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {

                try {
                    if(response.code()==201){
                        if(response.body().getStatus().equalsIgnoreCase("success")){
                            String userId = response.body().getData().getUserId();
                            String token = response.body().getData().getToken();
                            getProfile(token);
                            Toast.makeText(getApplicationContext(), "You have earned 500 crystal point as reward", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(signup.this,MainActivity.class));
                        }
                        else {
                            startActivity(new Intent(signup.this,signup.class));
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Error: " +response.code()+" "+response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


  /*      Call<ResponseBody> call = RetrofitClient
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

                                    startActivity(new Intent(signup.this, HomeScreenActivity.class));
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
    */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void getProfile(final String tokens){
        RetrofitClient api_manager = new RetrofitClient();
        Api retrofit_interface =api_manager.usersClient().create(Api.class);

        final String token = "Bearer "+tokens;
        Call<UserGetProfileResponse> call= retrofit_interface.getProfile(token);

        call.enqueue(new Callback<UserGetProfileResponse>() {
            @Override
            public void onResponse(Call<UserGetProfileResponse> call, Response<UserGetProfileResponse> response) {
                if(response.code()==200) {
                    preference.setUserName(response.body().getData().getName());
                    preference.setEmail(response.body().getData().getEmail());
                    preference.setLoyaltyPoints(response.body().getData().getLoyaltyPoints());
                    preference.setAddresses(response.body().getData().getDeliveryAddresses());
                    preference.setUserId(response.body().getData().getId());
                    preference.setProfilePic(response.body().getData().getProfile_pic());
                    preference.setToken(tokens);
                }
            }

            @Override
            public void onFailure(Call<UserGetProfileResponse> call, Throwable t) {

            }
        });
    }

}