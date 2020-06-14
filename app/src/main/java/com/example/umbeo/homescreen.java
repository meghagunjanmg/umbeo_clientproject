package com.example.umbeo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.util.Objects;

import me.angeldevil.autoscrollviewpager.AutoScrollViewPager;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class homescreen extends Fragment {
    LinearLayout trending, popular, feature;

    LinearLayout lichi,strawberry;
    AutoScrollViewPager mViewPager;
    CardView cat1, cat2, cat3, cat4;
    ImageView orange_plus,lichi_plus,strawberry_plus;


    TextView log, fruit;
    TextView address;
    CardView fruits;
    TextView trending_txt, popular_txt, feature_txt;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_dashboard, container, false);
        trending = v.findViewById(R.id.trending);
        popular = v.findViewById(R.id.popular);
        feature = v.findViewById(R.id.feature);
        cat1 = v.findViewById(R.id.cat1);
        cat2 = v.findViewById(R.id.cat2);
        cat3 = v.findViewById(R.id.cat3);
        cat4 = v.findViewById(R.id.cat4);

        lichi = v.findViewById(R.id.lichi);
        strawberry = v.findViewById(R.id.strawberry);

        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FruitsFragment fruits = new FruitsFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fruits)
                        .addToBackStack(null).commit();
            }
        });
        cat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FruitsFragment fruits = new FruitsFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fruits)
                        .addToBackStack(null).commit();
            }
        });

        cat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FruitsFragment fruits = new FruitsFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fruits)
                        .addToBackStack(null).commit();
            }
        });

        cat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FruitsFragment fruits = new FruitsFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fruits)
                        .addToBackStack(null).commit();
            }
        });

        trending_txt = v.findViewById(R.id.trending_txt);
        feature_txt = v.findViewById(R.id.feature_txt);
        popular_txt = v.findViewById(R.id.popular_txt);


        Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getContext().getColor(R.color.green));
        popular.setBackground(wrappedDrawable);
        trending.setBackground(wrappedDrawable);


        Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
        Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
        DrawableCompat.setTint(wrappedDrawable2, getContext().getColor(R.color.purple));
        feature.setBackground(wrappedDrawable2);
        trending_txt.setTextColor(getContext().getColor(R.color.green));
        feature_txt.setTextColor(getContext().getColor(R.color.purple));
        popular_txt.setTextColor(getContext().getColor(R.color.green));

        trending.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, getContext().getColor(R.color.green));
                popular.setBackground(wrappedDrawable);
                feature.setBackground(wrappedDrawable);


                Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
                Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
                DrawableCompat.setTint(wrappedDrawable2, getContext().getColor(R.color.purple));
                trending.setBackground(wrappedDrawable2);

                trending_txt.setTextColor(getContext().getColor(R.color.purple));
                feature_txt.setTextColor(getContext().getColor(R.color.green));
                popular_txt.setTextColor(getContext().getColor(R.color.green));

                lichi.setVisibility(View.GONE);
                strawberry.setVisibility(View.VISIBLE);
            }
        });
        feature.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, getContext().getColor(R.color.green));
                popular.setBackground(wrappedDrawable);
                trending.setBackground(wrappedDrawable);


                Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
                Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
                DrawableCompat.setTint(wrappedDrawable2, getContext().getColor(R.color.purple));
                feature.setBackground(wrappedDrawable2);
                trending_txt.setTextColor(getContext().getColor(R.color.green));
                feature_txt.setTextColor(getContext().getColor(R.color.purple));
                popular_txt.setTextColor(getContext().getColor(R.color.green));

                lichi.setVisibility(View.VISIBLE);
                strawberry.setVisibility(View.VISIBLE);

            }
        });
        popular.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, getContext().getColor(R.color.green));
                trending.setBackground(wrappedDrawable);
                feature.setBackground(wrappedDrawable);


                Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
                Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
                DrawableCompat.setTint(wrappedDrawable2, getContext().getColor(R.color.purple));
                popular.setBackground(wrappedDrawable2);
                trending_txt.setTextColor(getContext().getColor(R.color.green));
                feature_txt.setTextColor(getContext().getColor(R.color.green));
                popular_txt.setTextColor(getContext().getColor(R.color.purple));

                strawberry.setVisibility(View.GONE);
                lichi.setVisibility(View.VISIBLE);
            }
        });
        mViewPager = v.findViewById(R.id.pager);

// This is just an example. You can use whatever collection of images.
        int[] mResources = {
                R.drawable.bananas1,
                R.drawable.strawberries,
                R.drawable.orange,
                R.drawable.lichi,
        };

        CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(getContext(), mResources);

        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.startAutoScroll(2000);

      /*  fruits = (CardView)v.findViewById(R.id.list1);
        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FruitsFragment fruits= new FruitsFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fruits)
                        .addToBackStack(null)
                        .commit();
            }
        });
       */
        log = (TextView) v.findViewById(R.id.login);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), login.class));
            }
        });
        address = (TextView) v.findViewById(R.id.addre);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapActivity.class));
            }
        });




        /*
        fruit=(TextView)v.findViewById(R.id.fruits);
        fruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FruitsFragment fruits= new FruitsFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fruits).commit();
                //startActivity(new Intent(getActivity(),FruitsFragment.class));
            }
        });

         */


        strawberry.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                dailog("strawbery");
            }
        });


        lichi.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                dailog("lichi");
            }
        });

        final LinearLayout orange = v.findViewById(R.id.orange);
        orange.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                dailog("orange");
            }
        });


        orange_plus = v.findViewById(R.id.orange_plus);
        orange_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
            }
        });
        lichi_plus = v.findViewById(R.id.lichi_plus);
        lichi_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
            }
        });
        strawberry_plus = v.findViewById(R.id.strawberry_plus);
        strawberry_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
            }
        });

       /*      orange = (ImageView)v.findViewById(R.id.orange);
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();
            }
        });

   pineappleText= (TextView)v.findViewById(R.id.pineappletext);
        pineappleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.popup1_pineapple, null);
                Button addtocart = mView.findViewById(R.id.button);

                addtocart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();

                        startActivity(new Intent(getContext(),login.class));

                    }
                });

                ImageView img= mView.findViewById(R.id.image);

                mBuilder.setView(mView);

                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
        pineapple=(ImageView)v.findViewById(R.id.pineapple);
        pineapple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();
            }
        });

        strawbe=(ImageView)v.findViewById(R.id.strawberry);
        strawbe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();

            }
        });


        strawberryText = (TextView)v.findViewById(R.id.straaaa);
        strawberryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.popup1_strawberry, null);
                Button addtocart = mView.findViewById(R.id.button);

                addtocart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getContext(),"Added to Cart Successfully",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getContext(),login.class));

                    }
                });

                ImageView img= mView.findViewById(R.id.image);

                mBuilder.setView(mView);

                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

         */

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void dailog(String name) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.generic_dailog, null);
        CardView cardView = mView.findViewById(R.id.cardview);
        cardView.setBackgroundDrawable(Objects.requireNonNull(getContext()).getDrawable(R.drawable.bg_dailog));
        Button addtocart = mView.findViewById(R.id.button);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getContext(), login.class));
            }
        });
        ImageView img = mView.findViewById(R.id.image);
        Glide.with(Objects.requireNonNull(getContext())).load(getImage(name)).into(img);

        TextView name1 = mView.findViewById(R.id.name);
        name1.setText(name.toUpperCase());

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public int getImage(String imageName) {

        int drawableResourceId = getContext().getResources().getIdentifier(imageName, "drawable", getContext().getPackageName());
        return drawableResourceId;
    }

}