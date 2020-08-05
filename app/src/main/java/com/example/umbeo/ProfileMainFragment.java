package com.example.umbeo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.UsersApi;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.SignUpResquest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import spencerstudios.com.bungeelib.Bungee;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileMainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileMainFragment newInstance(String param1, String param2) {
        ProfileMainFragment fragment = new ProfileMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        preference = new UserPreference(getContext());

        if(preference.getTheme()==1){
            return inflater.inflate(R.layout.dark_profile, container, false);
        }

        // Inflate the layout for this fragment
        else return inflater.inflate(R.layout.activity_profile_edit, container, false);
    }

    Switch darkTheme;
    Button logout;
    TextView my_addresses,myemail,myname,name_edit,profile_edit,loyalty_point,my_order,feedback;
    UserPreference preference;
    EditText myname_et;
    int count = 0;
    public static final int RESULT_GALLERY = 0;
    ImageView profilepic;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    Fragment fragment;
    private Resources res;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preference = new UserPreference(getContext());
        fragment = this;

        //getContext().setTheme(R.style.LightTheme);

        int theme = preference.getTheme();
        darkTheme = view.findViewById(R.id.theme);
        if(theme==1){
            darkTheme.setChecked(true);
            preference.setTheme(1);
        }
        darkTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(darkTheme.isChecked()){
                    preference.setTheme(1);
                    HomeScreenActivity.refreshFragments();
                }
                else {
                    preference.setTheme(0);
                    HomeScreenActivity.refreshFragments();

                }
            }
        });

        my_order = view.findViewById(R.id.my_order);
        my_addresses = view.findViewById(R.id.my_addresses);

        my_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getContext(),MyOrderActivity.class));
                Bungee.fade(getContext());
            }
        });

        my_addresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),MyAddresses.class));
                Bungee.fade(getContext());
            }
        });

        loyalty_point = view.findViewById(R.id.loyalty_point);
        loyalty_point.setText(preference.getLoyaltyPoints()+"");

        myemail = view.findViewById(R.id.myemail);
        myemail.setText(preference.getEmail());

        myname = view.findViewById(R.id.myname);
        myname.setText(preference.getUserName());

        myname_et = view.findViewById(R.id.myname_et);

        name_edit = view.findViewById(R.id.name_edit);
        myname_et.setText(myname.getText().toString());



        name_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                myname_et.setVisibility(View.VISIBLE);
                myname.setVisibility(View.GONE);
                String newName = myname_et.getText().toString();
                name_edit.setText("Save");
                if(count%2==0){
                    myname_et.setVisibility(View.GONE);
                    myname.setVisibility(View.VISIBLE);
                    if(newName.equals("")){
                        Toast.makeText(getContext(),"Profile Name can't be empty",Toast.LENGTH_LONG).show();
                    }
                    else {
                        preference.setUserName(newName);
                        myname.setText(preference.getUserName());
                        updateAddress(getContext());
                    }
                    name_edit.setText("Edit");
                }
            }
        });



        profile_edit = view.findViewById(R.id.profile_edit);
        profilepic = view.findViewById(R.id.dp);

        if( !preference.getProfilePic().equals("null"))
        profilepic.setImageBitmap(Base64ToBitmap(preference.getProfilePic()));

        profile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ContextCompat.checkSelfPermission(
                            getContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_CODE_STORAGE_PERMISSION);
                    }
                    else {
                        Intent galleryIntent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, RESULT_GALLERY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Intent galleryIntent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_GALLERY);
                }
            }
        });
        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference.logout();
                Toast.makeText(getContext(),"Logout Successfully",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getContext(),HomeScreenActivity.class));
            }
        });


        feedback = view.findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getContext(),Feedback.class);
                startActivity(intent);
                Bungee.fade(getContext());

                */

                feedbackDailog();
            }
        });




    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (getContext() != null && data!=null) {
                Uri imageUri = data.getData();
                Bitmap imageBitmap = getBitmapForMediaUri(getContext(), imageUri);
                profilepic.setImageBitmap(imageBitmap);
                assert imageBitmap != null;
                preference.setProfilePic(BitmapToBase64(imageBitmap));
                profilepic.setImageBitmap(Base64ToBitmap(preference.getProfilePic()));

                updateAddress(getContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Bitmap getBitmapForMediaUri(Context context, Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void updateAddress(final Context context) {
        UserPreference preference = new UserPreference(context);
        RetrofitClient api_manager = new RetrofitClient();
        UsersApi retrofit_interface =api_manager.usersClient().create(UsersApi.class);
        final SignUpResquest request = new SignUpResquest();
        request.setShop(preference.getShopId());
        request.setProfilePic(preference.getProfilePic());
        request.setName(preference.getUserName());
        request.setDeliveryAddresses(preference.getAddresses());

        String token = "Bearer "+preference.getToken();
        //String token = "Bearer "+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVlZTRmMjhhOTJjNWE3MDAxNzIwZjE1YSIsImlhdCI6MTU5MzE2MjUzNywiZXhwIjoxNTkzMTY2MTM3fQ.wAC-uZyjgi9bFCj2pK2wOdP8MB2SytNpYwqrYFC3Dy8";

        Log.e("PREFERNCES: 1",preference.getAddresses().toString());
        Log.e("PREFERNCES: 2",request.getDeliveryAddresses().toString());
        Log.e("PREFERNCES: 3",request.getShop().toString());
        Log.e("PREFERNCES: 4",request.getName().toString());
        Log.e("PREFERNCES: 5",request.getProfilePic().toString());
        Log.e("PREFERNCES: 6",token);
        Call<ResponseBody> call= retrofit_interface.updateUser(token,request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    Toast.makeText(context,"Updated",Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(context,response.code()+" "+response.message(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
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


    private void feedbackDailog(){
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert li != null;
        View mView = li.inflate(R.layout.activity_feedback, null);


       EditText editTextMessage = mView.findViewById(R.id.feedback_et);

       Button buttonSend = mView.findViewById(R.id.send);

      LinearLayout linearLayout =  mView.findViewById(R.id.main_linear);

        final String message = editTextMessage.getText().toString().trim();

        if(preference.getTheme()==1)
        {
            editTextMessage.setTextColor(Color.WHITE);
            editTextMessage.setHintTextColor(Color.WHITE);
            linearLayout.setBackgroundColor(Color.BLACK);
        }

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();


        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendEmail(message);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Something went wrong... Try again",Toast.LENGTH_LONG).show();
                } finally {
                    dialog.cancel();
                }

            }
        });



    }
    private void sendEmail(String message) {

        //Creating SendMail object
        SendMail sm = new SendMail(getContext(),  message);

        //Executing sendmail to send email
        sm.execute();
    }

}