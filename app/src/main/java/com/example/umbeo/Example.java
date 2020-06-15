package com.example.umbeo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;
import static java.security.AccessController.getContext;

public class Example extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener{
    AppDatabase db;
    FragmentManager fragmentManager;
    BottomNavigationView bottomNavigationView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        db = Room.databaseBuilder(this,
                AppDatabase.class, "database-name").build();

        DeleteAllDB();


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new homescreen())
                .commit();
    }

    private  BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;

                    switch(item.getItemId()){
                        case R.id.navigationMyOrder:
                                selectedFragment= new OrderFragment();
                                break;
                        case R.id.navigationHome:
                                selectedFragment= new homescreen();
                                break;
                        case R.id.navigationPerson:
                                selectedFragment= new ProfileFragment();
                                break;
                        case R.id.navigationCart:
                                selectedFragment= new CartFragment();
                                break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment)
                            .commit();
                    return true;
                }
            };

    @Override
    public void onBackStackChanged() {
        //navListner.onNavigationItemSelected(bottomNavigationView.getMenu().getItem(0));
        //bottomNavigationView.setOnNavigationItemSelectedListener(navListner);
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
