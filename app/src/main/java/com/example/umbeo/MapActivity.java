package com.example.umbeo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.Api;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.SignUpResquest;
import com.example.umbeo.response_data.UserGetProfileResponse;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    TextView current_location,my_addresses;
    EditText line1,line2,line3;
    double latitude, longitude;
    private GoogleApiClient mGoogleApiClient;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    private Location mLastLocation;
    UserPreference preference;
    Button add_address;
    List<String> addresses ;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = new UserPreference(getApplicationContext());

        if(preference.getTheme()==1){
            setContentView(R.layout.dark_map);
        }
        else 
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
        add_address = findViewById(R.id.add_address);

        try {
            if( preference.getAddresses().size()==0) {
                my_addresses.setVisibility(View.GONE);
            }
            else {
                my_addresses.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            my_addresses.setVisibility(View.GONE);
        }
        my_addresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyAddresses.class));
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
                if(preference.getAddresses().size()!=0){
                    addresses.addAll(preference.getAddresses());
                    String new_add = line1.getText().toString();
                    addresses.add(new_add);
                    preference.getAddresses().clear();
                    preference.setAddresses(addresses);
                }

                updateAddress(addresses);

                startActivity(new Intent(getApplicationContext(),MyAddresses.class));
            }
        });
    }

    private void updateAddress(List<String> addresses) {

        RetrofitClient api_manager = new RetrofitClient();
        Api retrofit_interface =api_manager.usersClient().create(Api.class);
        final SignUpResquest request = new SignUpResquest();
        request.setShop(preference.getShopId());
        request.setProfilePic(preference.getProfilePic());
        request.setName(preference.getUserName());
        request.setDeliveryAddresses(addresses);

        String token = "Bearer "+preference.getToken();
        //String token = "Bearer "+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVlZTRmMjhhOTJjNWE3MDAxNzIwZjE1YSIsImlhdCI6MTU5MzE2MjUzNywiZXhwIjoxNTkzMTY2MTM3fQ.wAC-uZyjgi9bFCj2pK2wOdP8MB2SytNpYwqrYFC3Dy8";

        Log.e("PREFERNCES: 1",preference.getAddresses().toString());
        Log.e("PREFERNCES: 2",addresses.toString());
        Log.e("PREFERNCES: 3",request.getShop().toString());
        Log.e("PREFERNCES: 4",request.getName().toString());
        Log.e("PREFERNCES: 5",request.getProfilePic().toString());
        Log.e("PREFERNCES: 6",token);
        Call<ResponseBody> call= retrofit_interface.updateUser(token,request);
           call.enqueue(new Callback<ResponseBody>() {
               @Override
               public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                   if(response.code()==200){
                       Toast.makeText(getApplicationContext(),"Address Added",Toast.LENGTH_LONG).show();
                       startActivity(new Intent(getApplicationContext(), MainActivity.class));
                   }
                   else Toast.makeText(getApplicationContext(),response.code()+" "+response.message(),Toast.LENGTH_LONG).show();

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

                        LatLng latLng = new LatLng(latitude,longitude);
                        mMap.addMarker(new MarkerOptions().position(latLng).title("My location"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
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

                line1.setText(address);
                if (city != null /*&& TextUtils.isEmpty(mTextViewLocality.getText())*/)
                {
                    line2.setText(city);
                }
                if (postalCode != null /*&& TextUtils.isEmpty(mTextViewPinCode.getText())*/)
                    line3.setText(postalCode);

                String currentLocation;

                if (!TextUtils.isEmpty(address)) {
                    currentLocation = address;

                    if (!TextUtils.isEmpty(address1)) {

                        currentLocation += "\n" + address1;
                    }

                    if (!TextUtils.isEmpty(city)) {
                        currentLocation += "\n" + city;

                        if (!TextUtils.isEmpty(postalCode)) {
                            currentLocation += " - " + postalCode;
                        }
                    } else {
                        if (!TextUtils.isEmpty(postalCode)) {

                            currentLocation += "\n" + postalCode;
                        }
                    }

                    if (!TextUtils.isEmpty(state)) {
                        currentLocation += "\n" + state;
                    }
                    if (!TextUtils.isEmpty(country)) {
                        currentLocation += "\n" + country;
                    }


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
}