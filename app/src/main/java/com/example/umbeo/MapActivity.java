package com.example.umbeo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.UsersApi;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.SignUpResquest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import spencerstudios.com.bungeelib.Bungee;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, TextWatcher {

    private GoogleMap mMap;
    TextView current_location, my_addresses;
    EditText line1, line2, line3, line4;
    Spinner line5, line6;
    double latitude, longitude;
    private GoogleApiClient mGoogleApiClient;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    private Location mLastLocation;
    UserPreference preference;
    Button add_address;
    List<String> addresses;
    String address;
    ImageView back_btn;
    int position,i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = new UserPreference(getApplicationContext());

        if (preference.getTheme() == 1) {
            setContentView(R.layout.dark_map);
        } else
            setContentView(R.layout.activity_map);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        addresses = new ArrayList<>();
        current_location = findViewById(R.id.current_location);
        my_addresses = findViewById(R.id.my_addresses);
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        line4 = findViewById(R.id.line4);
        line5 = findViewById(R.id.line5);
        line6 = findViewById(R.id.line6);
        add_address = findViewById(R.id.add_address);

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        i = getIntent().getIntExtra("Edit", 0);
        final String position = getIntent().getStringExtra("position");
        final String address = getIntent().getStringExtra("address");
        if (i == 1) {
            getLocationFromAddress(getApplicationContext(), address);
            add_address.setText("Update");
            //line1.addTextChangedListener(this);
        }


        String[] state = {"Ontario"};
        String[] country = {"Canada"};
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,state);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,country);
        line5.setAdapter(stateAdapter);
        line6.setAdapter(countryAdapter);

        //line5.setText("Ontario");
       // line6.setText("Canada");

        line1.addTextChangedListener(this);

        try {
            if (preference.getAddresses().size() == 0) {
                my_addresses.setVisibility(View.GONE);
            } else {
                my_addresses.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            my_addresses.setVisibility(View.GONE);
        }
        my_addresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyAddresses.class));
                Bungee.fade(MapActivity.this);
            }
        });

        current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildGoogleApiClient();
            }
        });

        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (i == 1) {
                    Log.e("Testing789", "1.  " + preference.getAddresses().toString());

                    List<String> addressList = new ArrayList<>();
                    for (int j = 0; j < preference.getAddresses().size(); j++) {
                        assert position != null;
                        Log.e("Testing789", "2.  " + position);
                        if (j == Integer.parseInt(position)) {
                            addressList.add(j, line1.getText().toString());
                        } else {
                            addressList.add(preference.getAddresses().get(j));
                        }
                    }

                    Log.e("Testing789", "3.  " + addressList.toString());

                    preference.setAddresses(addressList);
                    updateAddress(addressList);

                } else {

                    if (preference.getEmail() != null) {
                        String new_add = line1.getText().toString();

                        if (new_add.length() > 0) {
                            List<String> addresses = new ArrayList<>(preference.getAddresses());
                            addresses.add(new_add);
                            preference.getAddresses().clear();
                            preference.setAddresses(addresses);
                            updateAddress(addresses);
                            startActivity(new Intent(getApplicationContext(), MyAddresses.class));
                            Bungee.fade(MapActivity.this);
                        } else
                            Toast.makeText(getApplicationContext(), "Please enter your address", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "First login/signup to add new Address", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), signup.class));
                        Bungee.fade(MapActivity.this);
                    }
                }
            }
        });
    }

    private void updateAddress(final List<String> addresses) {

        RetrofitClient api_manager = new RetrofitClient();
        UsersApi retrofit_interface = api_manager.usersClient().create(UsersApi.class);
        final SignUpResquest request = new SignUpResquest();
        request.setShop(preference.getShopId());
        request.setProfilePic(preference.getProfilePic());
        request.setName(preference.getUserName());
        request.setDeliveryAddresses(addresses);

        String token = "Bearer " + preference.getToken();
        //String token = "Bearer "+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVlZTRmMjhhOTJjNWE3MDAxNzIwZjE1YSIsImlhdCI6MTU5MzE2MjUzNywiZXhwIjoxNTkzMTY2MTM3fQ.wAC-uZyjgi9bFCj2pK2wOdP8MB2SytNpYwqrYFC3Dy8";

        Log.e("PREFERNCES: 1", preference.getAddresses().toString());
        Log.e("PREFERNCES: 3", request.getShop().toString());
        Log.e("PREFERNCES: 4", request.getName().toString());
        Log.e("PREFERNCES: 5", request.getProfilePic().toString());
        Log.e("PREFERNCES: 6", token);
        Call<ResponseBody> call = retrofit_interface.updateUser(token, request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "Address Added", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    startActivity(new Intent(getApplicationContext(),MyAddresses.class));
                    finish();
                } else
                    Toast.makeText(getApplicationContext(), response.code() + " " + response.message(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog_precropping_generic by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(MapActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //  LatLng sydney = new LatLng(latitude, longitude);

        LatLng latLng = new LatLng(51.2538, 85.3232);//ontario
        mMap.addMarker(new MarkerOptions().position(latLng).title("My location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        mMap.setMaxZoomPreference(18);
    }

    private void getLocation() {

        //if (isPermissionGranted) {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                try {

                    if (ActivityCompat.checkSelfPermission(MapActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(MapActivity.this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();

                    LatLng latLng = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("My location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.setMaxZoomPreference(18);
                    getAddress();

                } catch (Exception e) {
                    e.printStackTrace();
                    //GPSTracker gpsTracker = new GPSTracker(mContext);
                }

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MapActivity.this, "Permission Denied\n" + deniedPermissions.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        };
        new TedPermission(MapActivity.this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("Accept GPS location permission")
                .setDeniedMessage(
                        "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setGotoSettingButtonText("Permission")
                .setPermissions(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)
                .check();
    }

    public void getAddress() {

        Address locationAddress = getAddress(latitude, longitude);
        try {
            if (locationAddress != null) {
                String address = locationAddress.getAddressLine(0);
                String address1 = locationAddress.getAddressLine(1);
                String city = locationAddress.getLocality();
                String state = locationAddress.getAdminArea();
                String country = locationAddress.getCountryName();
                String postalCode = locationAddress.getPostalCode();

                Log.e("Testing", " " + postalCode + " " + locationAddress.getPostalCode());
                Log.e("Testing", " " + city + " " + locationAddress.getAdminArea());
                Log.e("Testing", " " + country + " " + locationAddress.getCountryName());


                if (country.equalsIgnoreCase("Canada") && state.equalsIgnoreCase("Ontario")) {

                    line1.setText(address);
                    line3.setText(postalCode);
                    line4.setText(city);

                    if (address1.isEmpty()) {
                        line2.setVisibility(View.GONE);
                    } else {
                        line2.setText(address1);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Our service is not available in your area", Toast.LENGTH_LONG).show();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Address getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.ENGLISH);

        try {
            addresses = geocoder.getFromLocation(latitude, longitude,
                    1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());
            ///mMap.clear();

            Log.e("Testing123",location.getLatitude()+"  "+location.getLongitude());
            Address locationAddress = getAddress(location.getLatitude(),location.getLongitude());


            if (locationAddress != null) {
                String address0 = locationAddress.getAddressLine(0);
                String address1 = locationAddress.getAddressLine(1);
                String city = locationAddress.getLocality();
                String state = locationAddress.getAdminArea();
                String country = locationAddress.getCountryName();
                String postalCode = locationAddress.getPostalCode();

                Log.e("Testing123", " " + postalCode + " " + locationAddress.getPostalCode());
                Log.e("Testing123", " " + city + " " + locationAddress.getAdminArea());
                Log.e("Testing123", " " + country + " " + locationAddress.getCountryName());

                if (country.equalsIgnoreCase("Canada") && state.equalsIgnoreCase("Ontario")) {

                    line1.setText(address0);
                    line3.setText(postalCode);
                    line4.setText(city);

                    if (address1.isEmpty()) {
                        line2.setVisibility(View.GONE);
                    } else {
                        line2.setText(address1);
                    }


                    mMap.addMarker(new MarkerOptions().position(p1).title("My location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(p1));
                    mMap.setMaxZoomPreference(18);

                } else {
                    //Toast.makeText(getApplicationContext(), "Our service is not available in your area", Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){

        }
        return p1;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        getmapmarker(s.toString());
    }

    private void getmapmarker(String strAddress) {
        Geocoder coder = new Geocoder(MapActivity.this);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(p1).title("My location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(p1));
            mMap.setMaxZoomPreference(18);
        }catch (Exception e){}
    }

}