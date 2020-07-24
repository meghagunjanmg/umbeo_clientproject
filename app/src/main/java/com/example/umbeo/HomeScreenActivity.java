package com.example.umbeo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.UsersApi;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.UserGetProfileResponse;
import com.example.umbeo.response_data.shop.ShopResponse;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private static ViewPagerAdapter adapter;
     public static CustomViewPager viewPager;
    static FrameLayout explore,cart,order,profile;
    AppDatabase db;
    int id;
    public static boolean payment_frag = false;
    public static boolean category_frag = false;
    static LinearLayout nav_linear;
    static ImageView icon1,icon2,icon3,icon4;
    static TextView text1,text2,text3,text4;
    static Context context;

    FragmentManager fragmentManager = getSupportFragmentManager();
     static UserPreference preference ;

    public static void refreshFragments() {

        viewPager.setAdapter(adapter);
        setIcons();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_activity);

        context = this;

        viewPager = findViewById(R.id.pager);
        explore = findViewById(R.id.explore);
        cart = findViewById(R.id.cart);
        order = findViewById(R.id.order);
        profile = findViewById(R.id.profile);
        preference = new UserPreference(getApplicationContext());

        fragmentManager.popBackStack();

        explore.setOnClickListener(this);
        cart.setOnClickListener(this);
        order.setOnClickListener(this);
        profile.setOnClickListener(this);



        nav_linear = findViewById(R.id.nav_linear);
         icon1 = findViewById(R.id.icon1);
         text1 = findViewById(R.id.text1);
         icon2 = findViewById(R.id.icon2);
         text2 = findViewById(R.id.text2);
         icon3 = findViewById(R.id.icon3);
         text3 = findViewById(R.id.text3);
         icon4 = findViewById(R.id.icon4);
         text4 = findViewById(R.id.text4);


        getProfile();



        if (db == null) {
            db = AppDatabase.getInstance(getApplicationContext());
        }


        DeleteAllDB();

        setupViewPager();


        try {
            int catIntent = getIntent().getIntExtra("Cat",0);
            if(catIntent==5){
                viewPager.setCurrentItem(1);
                setIcons();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        setIcons();


        viewPager.setPagingEnabled(false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setIcons();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void setupViewPager() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DashBoardFragment(),"Home");
        adapter.addFragment(new CartMainFragment(),"Cart");
        adapter.addFragment(new LoyaltyPointsFragment(),"Points");
        adapter.addFragment(new ProfileMainFragment(),"Profile");


        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.explore:{
                viewPager.setCurrentItem(0);
                setIcons();
            }break;
            case R.id.cart:{
                viewPager.setCurrentItem(1);
                setIcons();
            }break;
            case R.id.order:{
                if(preference.getUserName()==null)
                {
                    startActivity(new Intent(HomeScreenActivity.this, signup.class));
                    viewPager.setCurrentItem(0);
                }
                else viewPager.setCurrentItem(2);
                setIcons();
            }break;
            case R.id.profile:{
                        if(preference.getUserName()==null)
                        {
                            startActivity(new Intent(HomeScreenActivity.this, signup.class));
                            viewPager.setCurrentItem(0);
                        }
                        else viewPager.setCurrentItem(3);
                setIcons();
            }break;
        }
    }


    private static void setIcons(){
        nav_linear.setBackgroundColor(Color.WHITE);

        if(preference.getTheme()==0) {
            nav_linear.setBackgroundColor(Color.WHITE);
            if (viewPager.getCurrentItem() == 0) {
                DrawableCompat.setTint(icon1.getDrawable(), ContextCompat.getColor(context, R.color.main));


                //icon1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#F84B18")));
                //text1.setTextColor(ColorStateList.valueOf(Color.parseColor("#F84B18")));

                DrawableCompat.setTint(icon2.getDrawable(), ContextCompat.getColor(context, R.color.darker_gray));
                text2.setTextColor(ColorStateList.valueOf(Color.parseColor("#1E1E1E")));
                DrawableCompat.setTint(icon3.getDrawable(), ContextCompat.getColor(context, R.color.darker_gray));
                text3.setTextColor(ColorStateList.valueOf(Color.parseColor("#1E1E1E")));
                DrawableCompat.setTint(icon4.getDrawable(), ContextCompat.getColor(context, R.color.darker_gray));
                text4.setTextColor(ColorStateList.valueOf(Color.parseColor("#1E1E1E")));

            }
            if (viewPager.getCurrentItem() == 1) {

                DrawableCompat.setTint(icon2.getDrawable(), ContextCompat.getColor(context, R.color.main));
                text2.setTextColor(ColorStateList.valueOf(Color.parseColor("#F84B18")));


                DrawableCompat.setTint(icon1.getDrawable(), ContextCompat.getColor(context, R.color.darker_gray));
                text1.setTextColor(ColorStateList.valueOf(Color.parseColor("#1E1E1E")));
                DrawableCompat.setTint(icon3.getDrawable(), ContextCompat.getColor(context, R.color.darker_gray));
                text3.setTextColor(ColorStateList.valueOf(Color.parseColor("#1E1E1E")));
                DrawableCompat.setTint(icon4.getDrawable(), ContextCompat.getColor(context, R.color.darker_gray));
                text4.setTextColor(ColorStateList.valueOf(Color.parseColor("#1E1E1E")));
            }
            if (viewPager.getCurrentItem() == 2) {

                DrawableCompat.setTint(icon3.getDrawable(), ContextCompat.getColor(context, R.color.main));
                text3.setTextColor(ColorStateList.valueOf(Color.parseColor("#F84B18")));


                DrawableCompat.setTint(icon2.getDrawable(), ContextCompat.getColor(context, R.color.darker_gray));
                text2.setTextColor(ColorStateList.valueOf(Color.parseColor("#1E1E1E")));
                DrawableCompat.setTint(icon1.getDrawable(), ContextCompat.getColor(context, R.color.darker_gray));
                text1.setTextColor(ColorStateList.valueOf(Color.parseColor("#1E1E1E")));
                DrawableCompat.setTint(icon4.getDrawable(), ContextCompat.getColor(context, R.color.darker_gray));
                text4.setTextColor(ColorStateList.valueOf(Color.parseColor("#1E1E1E")));
            }
            if (viewPager.getCurrentItem() == 3) {

                DrawableCompat.setTint(icon4.getDrawable(), ContextCompat.getColor(context, R.color.main));
                text4.setTextColor(ColorStateList.valueOf(Color.parseColor("#F84B18")));


                DrawableCompat.setTint(icon2.getDrawable(), ContextCompat.getColor(context, R.color.darker_gray));
                text2.setTextColor(ColorStateList.valueOf(Color.parseColor("#1E1E1E")));
                DrawableCompat.setTint(icon3.getDrawable(), ContextCompat.getColor(context, R.color.darker_gray));
                text3.setTextColor(ColorStateList.valueOf(Color.parseColor("#1E1E1E")));
                DrawableCompat.setTint(icon1.getDrawable(), ContextCompat.getColor(context, R.color.darker_gray));
                text1.setTextColor(ColorStateList.valueOf(Color.parseColor("#1E1E1E")));
            }
        }
       else if (preference.getTheme()==1) {
            nav_linear.setBackgroundColor(Color.BLACK);
            if (viewPager.getCurrentItem() == 0) {
                icon1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#F84B18")));
                text1.setTextColor(ColorStateList.valueOf(Color.parseColor("#F84B18")));

                icon2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                text2.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                icon3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                text3.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                icon4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                text4.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

            }
            if (viewPager.getCurrentItem() == 1) {

                icon2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#F84B18")));
                text2.setTextColor(ColorStateList.valueOf(Color.parseColor("#F84B18")));


                icon1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                text1.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                icon3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                text3.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                icon4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                text4.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            }
            if (viewPager.getCurrentItem() == 2) {

                icon3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#F84B18")));
                text3.setTextColor(ColorStateList.valueOf(Color.parseColor("#F84B18")));


                icon2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                text2.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                icon1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                text1.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                icon4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                text4.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            }
            if (viewPager.getCurrentItem() == 3) {

                icon4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#F84B18")));
                text4.setTextColor(ColorStateList.valueOf(Color.parseColor("#F84B18")));


                icon2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                text2.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                icon3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                text3.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                icon1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                text1.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            }
        }
    }


        @Override
        public void onBackPressed() {
          if(viewPager.getCurrentItem()!=0){
              viewPager.setCurrentItem(0);
          }
          if(payment_frag){
              startActivity(new Intent(getApplicationContext(),HomeScreenActivity.class));
          }

        }


    private void DeleteAllDB(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    db.cartDao().nukeTable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getProfile(){
        RetrofitClient api_manager = new RetrofitClient();
        UsersApi retrofit_interface =api_manager.usersClient().create(UsersApi.class);

        String token = "Bearer "+preference.getToken();
        Call<UserGetProfileResponse> call= retrofit_interface.getProfile(token);

        call.enqueue(new Callback<UserGetProfileResponse>() {
            @Override
            public void onResponse(Call<UserGetProfileResponse> call, Response<UserGetProfileResponse> response) {
                if(response.code()==200) {
                    preference.setUserName(response.body().getData().getName());
                    preference.setEmail(response.body().getData().getEmail());
                    preference.setLoyaltyPoints(response.body().getData().getLoyaltyPoints());
                    preference.setProfilePic(response.body().getData().getProfile_pic());
                    preference.setUserId(response.body().getData().getId());
                    preference.setAddresses(response.body().getData().getDeliveryAddresses());
                    preference.setAchievments(response.body().getData().getAchievements());

                    Log.e("Achievement",response.body().getData().getAchievements().toString());
                    Log.e("Achievement",preference.getAchievments().toString());

                }
            }

            @Override
            public void onFailure(Call<UserGetProfileResponse> call, Throwable t) {

            }
        });
    }




}