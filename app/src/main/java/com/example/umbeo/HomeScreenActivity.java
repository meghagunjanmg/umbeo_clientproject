package com.example.umbeo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;

import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPagerAdapter adapter;
     static ViewPager viewPager;
    LinearLayout explore,cart,order,profile;
    AppDatabase db;
    int id;
    FragmentManager fragmentManager = getSupportFragmentManager();

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_activity);
        viewPager = findViewById(R.id.pager);
        explore = findViewById(R.id.explore);
        cart = findViewById(R.id.cart);
        order = findViewById(R.id.order);
        profile = findViewById(R.id.profile);

        fragmentManager.popBackStack();

        explore.setOnClickListener(this);
        cart.setOnClickListener(this);
        order.setOnClickListener(this);
        profile.setOnClickListener(this);



        if (db == null) {
            db = AppDatabase.getInstance(getApplicationContext());
        }


        DeleteAllDB();

        setupViewPager();

        id = getIntent().getIntExtra("Id",0);
        if(id==1){
            viewPager.setCurrentItem(1);
        }

        setIcons();


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
        adapter.addFragment(new OrderMainFragment(),"Order");
        adapter.addFragment(new ProfileMainFragment(),"Profile");
        adapter.addFragment(new CategoryFragment(),"cat");
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
                viewPager.setCurrentItem(2);
                setIcons();
            }break;
            case R.id.profile:{
                viewPager.setCurrentItem(3);
                setIcons();
            }break;
        }
    }


    private void setIcons(){
        ImageView icon1 = findViewById(R.id.icon1);
        TextView text1 = findViewById(R.id.text1);
        ImageView icon2 = findViewById(R.id.icon2);
        TextView text2 = findViewById(R.id.text2);
        ImageView icon3 = findViewById(R.id.icon3);
        TextView text3 = findViewById(R.id.text3);
        ImageView icon4 = findViewById(R.id.icon4);
        TextView text4 = findViewById(R.id.text4);

        if(viewPager.getCurrentItem()==0){
            icon1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#c86dd7")));
            text1.setTextColor(ColorStateList.valueOf(Color.parseColor("#c86dd7")));

            icon2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            text2.setTextColor(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            icon3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            text3.setTextColor(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            icon4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            text4.setTextColor(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));

        }
        if(viewPager.getCurrentItem()==1){

            icon2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#c86dd7")));
            text2.setTextColor(ColorStateList.valueOf(Color.parseColor("#c86dd7")));


            icon1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            text1.setTextColor(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            icon3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            text3.setTextColor(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            icon4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            text4.setTextColor(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
        }
        if(viewPager.getCurrentItem()==2){

            icon3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#c86dd7")));
            text3.setTextColor(ColorStateList.valueOf(Color.parseColor("#c86dd7")));


            icon2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            text2.setTextColor(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            icon1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            text1.setTextColor(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            icon4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            text4.setTextColor(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
        }
        if(viewPager.getCurrentItem()==3){

            icon4.setImageTintList(ColorStateList.valueOf(Color.parseColor("#c86dd7")));
            text4.setTextColor(ColorStateList.valueOf(Color.parseColor("#c86dd7")));


            icon2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            text2.setTextColor(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            icon3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            text3.setTextColor(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            icon1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
            text1.setTextColor(ColorStateList.valueOf(Color.parseColor("#b2b2b2")));
        }
    }


        @Override
        public void onBackPressed() {
          if(viewPager.getCurrentItem()!=0){
              viewPager.setCurrentItem(0);
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

}