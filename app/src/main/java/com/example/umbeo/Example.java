package com.example.umbeo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class Example extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener{

    FragmentManager fragmentManager;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,
                new homescreen())
                .commit();
    }

    private  BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,
                            selectedFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                }
            };

    @Override
    public void onBackStackChanged() {
        navListner.onNavigationItemSelected(bottomNavigationView.getMenu().getItem(0));
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);
    }
}
