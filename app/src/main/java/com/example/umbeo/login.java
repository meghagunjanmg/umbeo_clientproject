package com.example.umbeo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.UsersApi;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.LoginResponse;
import com.example.umbeo.response_data.SignUpResponse;
import com.example.umbeo.response_data.UserGetProfileResponse;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import spencerstudios.com.bungeelib.Bungee;


public class login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    String phonePattern = "^(?:\\d{10}|\\w+@\\w+\\.\\w{2,3})$";
    String emailPattern = "^([0-9]{9})|([A-Za-z0-9._%\\+\\-]+@[a-z0-9.\\-]+\\.[a-z]{2,3})$";
    Button  login;
    MaterialButton google_sign_in_button;
    MaterialButton fb_sign_in_button;
    TextView signu;
    String ThirdToken;
    UserPreference preference;
    ImageView back_btn;
    private GoogleApiClient googleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;
    private static final int FB_SIGN_IN = 1;
    private CallbackManager callbackManager;
    Boolean facebookClicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = new UserPreference(getApplicationContext());

        if(preference.getTheme()==1){
            setContentView(R.layout.dark_login);
        }
        else
        setContentView(R.layout.activity_login);



        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.umbeo.fashion",                  //Insert your own package name.
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }

        signu=(TextView)findViewById(R.id.signin);

        fb_sign_in_button = findViewById(R.id.fb_sign_in_button);
        google_sign_in_button = findViewById(R.id.google_sign_in_button);


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
                Bungee.fade(login.this);
            }
        });
        login = (Button) findViewById(R.id.button);

        final TextView forgotpassword = (TextView) findViewById(R.id.forget);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, forget_password.class));
                Bungee.fade(login.this);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlogin();
            }
        });


        google_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(login.this, gso);

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
       FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
        fb_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callbackManager = CallbackManager.Factory.create();
                facebookClicked = true;

//                fb_sign_in_button.setPermissions(Arrays.asList("email","public_profile"));
                LoginManager.getInstance().logInWithReadPermissions(login.this,Arrays.asList("public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                Log.e("facebookLogin"," "+loginResult.getAccessToken()+" "+loginResult.toString());
                                facebookLogin(loginResult.getAccessToken());
                            }

                            @Override
                            public void onCancel() {
                                // App code
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Log.e("facebookLogin"," "+exception.getLocalizedMessage());
                            }
                        });
            }
        });
    }
    private String BitmapToBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public String getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            String bit = BitmapToBase64(myBitmap);
            return bit;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
        if (!(email.matches(emailPattern)) && !(email.matches(phonePattern))) {
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
        UsersApi retrofit_interface =api_manager.usersClient().create(UsersApi.class);

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

                        getProfile(response.body().getData());

                    }
                    else Toast.makeText(com.example.umbeo.login.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
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
    private void getProfile(final String Token){
        RetrofitClient api_manager = new RetrofitClient();
        UsersApi retrofit_interface =api_manager.usersClient().create(UsersApi.class);

        final String token = "Bearer "+Token;
        Call<UserGetProfileResponse> call= retrofit_interface.getProfile(token);

        call.enqueue(new Callback<UserGetProfileResponse>() {
            @Override
            public void onResponse(Call<UserGetProfileResponse> call, Response<UserGetProfileResponse> response) {
                Log.e("Get_Profile_data",response+"");
                Log.e("Get_Profile_data",response.code()+"");
                Log.e("Get_Profile_data",response.message()+"");
                if(response.code()==200) {
                    preference.setUserName(response.body().getData().getName());
                    preference.setEmail(response.body().getData().getEmail());
                    preference.setLoyaltyPoints(response.body().getData().getLoyaltyPoints());
                    preference.setProfilePic(response.body().getData().getProfile_pic());
                    preference.setUserId(response.body().getData().getId());
                    preference.setAddresses(response.body().getData().getDeliveryAddresses());
                    //preference.setAchievments(response.body().getData().getAchievements());
                    preference.setToken(Token);



                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    //i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    i.putExtra("token",response.body().getData());
                    i.putExtra("intent",1);
                    startActivity(i);
                    Bungee.fade(login.this);

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

         if(facebookClicked) callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.startTracking();
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            Log.e("facebookLogin"," "+currentAccessToken);

            if(currentAccessToken==null)
            {
            }
            else facebookLogin(currentAccessToken);
        }
    };


    String personName,personEmail;

    private void facebookLogin(AccessToken acct) {
        if (acct != null) {
            GraphRequest graphRequest = GraphRequest.newMeRequest(acct, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        personName = object.getString("first_name");
                        personEmail = object.getString("email");
                        thirdLogin(personName,personEmail);

                        Log.e("facebookLogin"," "+personName+" "+personEmail+" ");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("facebookLogin"," "+personName+" "+personEmail+" "+e.getLocalizedMessage());
                    }
                }
            });

            Bundle parameter = new Bundle();
            parameter.putString("fields","first_name,last_name,email,id");
            graphRequest.setParameters(parameter);
            graphRequest.executeAsync();

        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            googleLogin(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("googleSignIn", "signInResult:failed code=" + e.getStatusCode());
            googleLogin(null);
        }
    }

    private void googleLogin(GoogleSignInAccount acct) {
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            thirdLogin(personName,personEmail);

            Log.e("googleSignIn"," "+personName+" "+personGivenName+" "+personEmail+" "+personId+" "+personPhoto);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }




    private void thirdLogin(String name,String email){
        RetrofitClient api_manager = new RetrofitClient();
        UsersApi retrofit_interface =api_manager.usersClient().create(UsersApi.class);

        Call<SignUpResponse> call =  retrofit_interface.userThirdLogin(email,name,preference.getShopId());
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                try {
                    Log.e("ThirdLoginResponse",""+response);
                    Log.e("ThirdLoginResponse",response.code()+"");
                    Log.e("ThirdLoginResponse",response.message()+"");
                    if (response.code() == 201) {
                        login.setEnabled(true);

                        Toast.makeText(com.example.umbeo.login.this,"Login Successful",Toast.LENGTH_LONG).show();

                        getProfile(response.body().getData().getToken());

                    }
                    else Toast.makeText(com.example.umbeo.login.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    login.setEnabled(true);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                login.setEnabled(true);
                Toast.makeText(com.example.umbeo.login.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }
}
