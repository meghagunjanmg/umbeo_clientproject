package com.example.umbeo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.Api;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.CategoryResponse;
import com.example.umbeo.response_data.ProductModel;
import com.example.umbeo.response_data.ProductResponse;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.CartEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.angeldevil.autoscrollviewpager.AutoScrollViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashBoardFragment extends Fragment {

    /////LinearLayout trending, popular, feature;

    LinearLayout lichi, strawberry;
    AutoScrollViewPager mViewPager;
    CardView cat1, cat2, cat3, cat4;
    ImageView orange_plus, lichi_plus;
    FrameLayout strawberry_plus;


    TextView log, fruit;
    TextView address;
    CardView fruits;
    TextView trending_txt, popular_txt, feature_txt;

    static AppDatabase db;
    static int staw_count = 0, lichi_count = 0, orange_count = 0, quant = 0;
    LinearLayout straw_linear, orange_linear, lichi_linear;
    ImageView add, remove, add2, remove2, add3, remove3;
    TextView quantity, quantity3, quantity2;
    ViewPager viewPager2;
    List<ItemModel> mMainItemsList, mFruitsItem, mPersonalItems;
     RecyclerView category_list, item_recycler, list_category,list_category_fruit;
     ItemAdapter myAdapter;

     List<com.example.umbeo.response_data.CategoryModel> categoryModel = new ArrayList<>();
    private float total = 0;
    List<CategoryModel> categoryModelList = new ArrayList<>();
    CardView see_more;
    static CategoryListAdapter categoryListAdapter,categoryListAdapter2;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<CartEntity> entitiesList;
    UserPreference preference;

    private Context Context;

    public DashBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashBoardFragment newInstance(String param1, String param2) {
        DashBoardFragment fragment = new DashBoardFragment();
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
            if (db == null) {
                db = AppDatabase.getInstance(getContext());
            }

            LoadAllDB();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_dashboard, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        preference = new UserPreference(getContext());

        item_recycler = v.findViewById(R.id.item_recycler);
        category_list = v.findViewById(R.id.category_list);
        list_category = v.findViewById(R.id.list_category);
        list_category_fruit = v.findViewById(R.id.list_category_fruit);

        getCategory();


        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        item_recycler.setLayoutManager(mGridLayoutManager);


        GridLayoutManager mGridLayoutManager2 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        list_category.setLayoutManager(mGridLayoutManager2);

     /*   mMainItemsList = new ArrayList<>();
        mFruitsItem = new ArrayList<>();
        mPersonalItems = new ArrayList<>();

        mFruitsItem.add(new ItemModel("Apple", "pic_0", 0));
        mFruitsItem.add(new ItemModel("Lichi", "pic_1", 0));
        mFruitsItem.add(new ItemModel("Apple", "pic_0", 0));
        mFruitsItem.add(new ItemModel("Lichi", "pic_1", 0));


        mPersonalItems.add(new ItemModel("Colgate", "pic_2", 0));
        mPersonalItems.add(new ItemModel("Hair oil", "pic_3", 0));
        mPersonalItems.add(new ItemModel("Colgate", "pic_2", 0));
        mPersonalItems.add(new ItemModel("Hair oil", "pic_3", 0));


        List<CategoryModel> categoryModelList = new ArrayList<>();
        List<CategoryModel> categoryModelList1 = new ArrayList<>();

        categoryModelList.add(new CategoryModel("Fruits", mFruitsItem));
        categoryListAdapter = new CategoryListAdapter(categoryModelList, getContext());
        list_category_fruit.setAdapter(categoryListAdapter);

        categoryModelList1.add(new CategoryModel("Personal Care", mPersonalItems));
        categoryListAdapter2 = new CategoryListAdapter(categoryModelList1, getContext());
        list_category.setAdapter(categoryListAdapter2);


        mMainItemsList.add(new ItemModel("Apple", "pic_0", 0));
        mMainItemsList.add(new ItemModel("Lichi", "pic_1", 0));
        mMainItemsList.add(new ItemModel("Colgate", "pic_2", 0));
*/

       // myAdapter = new ItemAdapter(mMainItemsList, getContext());
        item_recycler.setAdapter(myAdapter);

        log = (TextView) v.findViewById(R.id.login);

        if (preference.getUserName() != null) {
            log.setText(preference.getUserName());
        } else {
            log.setText("Log in / Signup");
        }

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), login.class));
            }
        });


        see_more = v.findViewById(R.id.see_more);
        see_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),CategoryActivity.class);
                i.putExtra("Cat",1);
                startActivity(i);
            }
        });



        address = (TextView) v.findViewById(R.id.addre);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preference.getUserName() != null)
                    startActivity(new Intent(getActivity(), MyAddresses.class));

                else startActivity(new Intent(getActivity(), signup.class));
            }
        });


        // trending = v.findViewById(R.id.trending);
        //popular = v.findViewById(R.id.popular);
        // feature = v.findViewById(R.id.feature);
        cat1 = v.findViewById(R.id.cat1);
        cat2 = v.findViewById(R.id.cat2);
        cat3 = v.findViewById(R.id.cat3);
        cat4 = v.findViewById(R.id.cat4);
        straw_linear = v.findViewById(R.id.strawberry_linear);
        add = v.findViewById(R.id.add);
        remove = v.findViewById(R.id.remove);
        quantity = v.findViewById(R.id.quantity);

        orange_linear = v.findViewById(R.id.orange_linear);
        add3 = v.findViewById(R.id.add3);
        remove3 = v.findViewById(R.id.remove3);
        quantity3 = v.findViewById(R.id.quantity3);

        lichi_linear = v.findViewById(R.id.lichi_linear);
        add2 = v.findViewById(R.id.add1);
        remove2 = v.findViewById(R.id.remove1);
        quantity2 = v.findViewById(R.id.quantity1);
        entitiesList = new ArrayList<>();


        if (db == null) {
            db = AppDatabase.getInstance(getContext());
        }

        LoadAllDB();

        lichi = v.findViewById(R.id.lichi);
        strawberry = v.findViewById(R.id.strawberry);


        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CategoryActivity.class);
                i.putExtra("Cat", 1);
                startActivity(i);
            }
        });
        cat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CategoryActivity.class);
                i.putExtra("Cat", 2);
                startActivity(i);

            }
        });

        cat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CategoryActivity.class));

            }
        });

        cat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CategoryActivity.class));

            }
        });


        trending_txt = v.findViewById(R.id.trending_txt);
        feature_txt = v.findViewById(R.id.feature_txt);
        popular_txt = v.findViewById(R.id.popular_txt);


        Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getContext().getColor(R.color.main));
        popular_txt.setBackground(wrappedDrawable);
        trending_txt.setBackground(wrappedDrawable);


        Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
        Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
        DrawableCompat.setTint(wrappedDrawable2, getContext().getColor(R.color.purple));
        feature_txt.setBackground(wrappedDrawable2);

        trending_txt.setTextColor(getContext().getColor(R.color.main));
        feature_txt.setTextColor(getContext().getColor(R.color.purple));
        popular_txt.setTextColor(getContext().getColor(R.color.main));


        trending_txt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, getContext().getColor(R.color.main));
                popular_txt.setBackground(wrappedDrawable);
                feature_txt.setBackground(wrappedDrawable);


                Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
                Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
                DrawableCompat.setTint(wrappedDrawable2, getContext().getColor(R.color.purple));
                trending_txt.setBackground(wrappedDrawable2);

                trending_txt.setTextColor(getContext().getColor(R.color.purple));
                feature_txt.setTextColor(getContext().getColor(R.color.main));
                popular_txt.setTextColor(getContext().getColor(R.color.main));


            }
        });
        feature_txt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, getContext().getColor(R.color.main));
                popular_txt.setBackground(wrappedDrawable);
                trending_txt.setBackground(wrappedDrawable);


                Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
                Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
                DrawableCompat.setTint(wrappedDrawable2, getContext().getColor(R.color.purple));
                feature_txt.setBackground(wrappedDrawable2);

                trending_txt.setTextColor(getContext().getColor(R.color.main));
                feature_txt.setTextColor(getContext().getColor(R.color.purple));
                popular_txt.setTextColor(getContext().getColor(R.color.main));

            }
        });
        popular_txt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, getContext().getColor(R.color.main));
                trending_txt.setBackground(wrappedDrawable);
                feature_txt.setBackground(wrappedDrawable);


                Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
                Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
                DrawableCompat.setTint(wrappedDrawable2, getContext().getColor(R.color.purple));
                popular_txt.setBackground(wrappedDrawable2);

                trending_txt.setTextColor(getContext().getColor(R.color.main));
                feature_txt.setTextColor(getContext().getColor(R.color.main));
                popular_txt.setTextColor(getContext().getColor(R.color.purple));


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
        mViewPager.startAutoScroll(5000);

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
                dailog("strawberry", staw_count);
            }
        });


        lichi.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                dailog("lichi", lichi_count);
            }
        });

        final LinearLayout orange = v.findViewById(R.id.orange);
        orange.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                dailog("orange", orange_count);
            }
        });


        orange_plus = v.findViewById(R.id.orange_plus);
        orange_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orange_count++;
                if (orange_count > 0) {
                    orange_linear.setVisibility(View.VISIBLE);
                    orange_plus.setVisibility(View.GONE);
                    quantity3.setText(orange_count + "");
                } else {

                }

                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("orange");
                addDB(new CartEntity("orange", Integer.parseInt(quantity3.getText().toString()), 5));
            }
        });
        lichi_plus = v.findViewById(R.id.lichi_plus);
        lichi_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lichi_count++;
                if (lichi_count > 0) {
                    lichi_linear.setVisibility(View.VISIBLE);
                    lichi_plus.setVisibility(View.GONE);
                    quantity2.setText(lichi_count + "");
                } else {

                }
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("lichi");
                addDB(new CartEntity("lichi", Integer.parseInt(quantity2.getText().toString()), 5));
            }
        });
        strawberry_plus = v.findViewById(R.id.strawberry_plus);
        strawberry_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staw_count++;
                if (staw_count > 0) {
                    straw_linear.setVisibility(View.VISIBLE);
                    strawberry_plus.setVisibility(View.GONE);
                    quantity.setText(staw_count + "");
                } else {

                }
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("strawberry");
                addDB(new CartEntity("strawberry", Integer.parseInt(quantity.getText().toString()), 5));
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
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

         */


        if (lichi_count == 0) {
            lichi_linear.setVisibility(View.GONE);
            lichi_plus.setVisibility(View.VISIBLE);
            DeleteDB("lichi");
        } else {
            lichi_linear.setVisibility(View.VISIBLE);
            lichi_plus.setVisibility(View.GONE);
        }
        if (staw_count == 0) {
            straw_linear.setVisibility(View.GONE);
            strawberry_plus.setVisibility(View.VISIBLE);
            DeleteDB("strawberry");
        } else {
            straw_linear.setVisibility(View.VISIBLE);
            strawberry_plus.setVisibility(View.GONE);
        }
        if (orange_count == 0) {
            orange_linear.setVisibility(View.GONE);
            orange_plus.setVisibility(View.VISIBLE);
            DeleteDB("orange");
        } else {
            orange_linear.setVisibility(View.VISIBLE);
            orange_plus.setVisibility(View.GONE);
        }
    }

    private void getCategoryProduct(final String categoryName, final String categoryId) {

        RetrofitClient api_manager = new RetrofitClient();
        Api retrofit_interface =api_manager.usersClient().create(Api.class);

        Call<ProductResponse> call = retrofit_interface.fetchAllProducts(preference.getShopId(),categoryId);

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                Log.e("ProductResponse",response+"");
                Log.e("ProductResponse",response.code()+"");
                Log.e("ProductResponse",response.message()+"");
                Log.e("ProductResponse",response.body().getStatus()+"");
                if(response.code()==200){
                    List<ProductModel> productModels = response.body().getData().getProducts();
                    Log.e("ProductResponse",productModels+"");

                    categoryModelList.add(new CategoryModel(categoryId,categoryName, productModels));

                    GridLayoutManager mGridLayoutManager2 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
                    list_category.setLayoutManager(mGridLayoutManager2);
                    categoryListAdapter = new CategoryListAdapter(categoryModelList, getContext());
                    list_category.setAdapter(categoryListAdapter);

                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });
    }

    private void getCategory() {

        RetrofitClient api_manager = new RetrofitClient();
        Api retrofit_interface =api_manager.usersClient().create(Api.class);

        Call<CategoryResponse> call = retrofit_interface.fetchAllCategory();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                Log.e("CategoryResponse",response+"");
                Log.e("CategoryResponse",response.code()+"");
                Log.e("CategoryResponse",response.message()+"");
                Log.e("CategoryResponse",response.body().getStatus()+"");
                if(response.code()==200){
                    categoryModel = response.body().getData().getCategories();
                    Log.e("CategoryResponse",categoryModel+"");

                    for(int i = 0;i<categoryModel.size();i++){
                        getCategoryProduct(categoryModel.get(i).getCategoryName(),categoryModel.get(i).getCategoryId());
                    }


                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                    MainCategoriesAdapter adapter = new MainCategoriesAdapter(categoryModel,getContext());
                    category_list.setLayoutManager(linearLayoutManager);
                    category_list.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of HomeFragment");
        super.onResume();

        if (db == null) {
            db = AppDatabase.getInstance(getContext());
        }

        LoadAllDB();

        quantity.setText(staw_count + "");
        if (staw_count == 0) {
            straw_linear.setVisibility(View.GONE);
            strawberry_plus.setVisibility(View.VISIBLE);
            DeleteDB("strawberry");
        } else {
            straw_linear.setVisibility(View.VISIBLE);
            strawberry_plus.setVisibility(View.GONE);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staw_count++;
                quantity.setText(staw_count + "");
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("strawberry");
                addDB(new CartEntity("strawberry", Integer.parseInt(quantity.getText().toString()), 5));
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staw_count--;
                if (staw_count == 0) {
                    straw_linear.setVisibility(View.GONE);
                    strawberry_plus.setVisibility(View.VISIBLE);
                    DeleteDB("strawberry");
                }

                quantity.setText(staw_count + "");
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("strawberry");
                addDB(new CartEntity("strawberry", Integer.parseInt(quantity.getText().toString()), 5));
            }
        });

        quantity2.setText(lichi_count + "");
        if (lichi_count == 0) {
            lichi_linear.setVisibility(View.GONE);
            lichi_plus.setVisibility(View.VISIBLE);
            DeleteDB("lichi");
        } else {
            lichi_linear.setVisibility(View.VISIBLE);
            lichi_plus.setVisibility(View.GONE);
        }
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lichi_count++;
                quantity2.setText(lichi_count + "");
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("lichi");
                addDB(new CartEntity("lichi", Integer.parseInt(quantity2.getText().toString()), 5));
            }
        });
        remove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lichi_count--;
                if (lichi_count == 0) {
                    lichi_linear.setVisibility(View.GONE);
                    lichi_plus.setVisibility(View.VISIBLE);
                    DeleteDB("lichi");
                }

                quantity2.setText(lichi_count + "");
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("lichi");
                addDB(new CartEntity("lichi", Integer.parseInt(quantity2.getText().toString()), 5));
            }
        });

        quantity3.setText(orange_count + "");
        if (orange_count == 0) {
            orange_linear.setVisibility(View.GONE);
            orange_plus.setVisibility(View.VISIBLE);
            DeleteDB("orange");
        } else {
            orange_linear.setVisibility(View.VISIBLE);
            orange_plus.setVisibility(View.GONE);
        }
        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orange_count++;
                quantity3.setText(orange_count + "");
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("orange");
                addDB(new CartEntity("orange", Integer.parseInt(quantity3.getText().toString()), 5));
            }
        });
        remove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orange_count--;
                if (orange_count == 0) {
                    orange_linear.setVisibility(View.GONE);
                    orange_plus.setVisibility(View.VISIBLE);
                    DeleteDB("orange");
                }

                quantity3.setText(orange_count + "");
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB("orange");
                addDB(new CartEntity("orange", Integer.parseInt(quantity3.getText().toString()), 5));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void dailog(final String name, final int quantity) {
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

        ImageView add = mView.findViewById(R.id.add);
        ImageView remove = mView.findViewById(R.id.remove);
        final TextView quan = mView.findViewById(R.id.quantity1111);

        quant = quantity;
        quan.setText(quant + "");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant++;
                quan.setText(quant + "");
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB(name);
                addDB(new CartEntity(name, Integer.parseInt(quan.getText().toString()), 5));
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant--;
                if (quant == 0) {
                    //  orange_linear.setVisibility(View.GONE);
                    //  orange_plus.setVisibility(View.VISIBLE);
                    DeleteDB(name);
                    addDB(new CartEntity(name, Integer.parseInt(quan.getText().toString()), 5));
                }
                quan.setText(quant + "");
                Toast.makeText(getContext(), "Added to Cart Successfully", Toast.LENGTH_LONG).show();
                DeleteDB(name);
                addDB(new CartEntity(name, Integer.parseInt(quan.getText().toString()), 5));
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public int getImage(String imageName) {

        int drawableResourceId = getContext().getResources().getIdentifier(imageName, "drawable", getContext().getPackageName());
        return drawableResourceId;
    }


    private static void addDB(final CartEntity entity) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    db.cartDao().insertOne(entity);
                    Log.e("roomDB", entity.getItem_name());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void DeleteDB(final String name) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    db.cartDao().deleteOne(name);
                    Log.e("roomDB", name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void LoadAllDB() {
        db.cartDao().getAll().observe(getViewLifecycleOwner(), new Observer<List<CartEntity>>() {
            @Override
            public void onChanged(List<CartEntity> entities) {
                entitiesList = entities;

                if (entitiesList.size() != 0) {
                    for (int i = 0; i < entitiesList.size(); i++) {
                        if (entitiesList.get(i).getItem_name().equals("strawberry")) {
                            staw_count = entitiesList.get(i).getQuantity();
                            quantity.setText(staw_count + "");
                        }
                        if (entitiesList.get(i).getItem_name().equals("lichi")) {
                            lichi_count = entitiesList.get(i).getQuantity();
                            quantity2.setText(lichi_count + "");
                        }
                        if (entitiesList.get(i).getItem_name().equals("orange")) {
                            orange_count = entitiesList.get(i).getQuantity();
                            quantity3.setText(orange_count + "");
                        }
                    }

                    if (lichi_count == 0) {
                        lichi_linear.setVisibility(View.GONE);
                        lichi_plus.setVisibility(View.VISIBLE);
                        DeleteDB("lichi");
                    } else {
                        lichi_linear.setVisibility(View.VISIBLE);
                        lichi_plus.setVisibility(View.GONE);
                    }
                    if (staw_count == 0) {
                        straw_linear.setVisibility(View.GONE);
                        strawberry_plus.setVisibility(View.VISIBLE);
                        DeleteDB("strawberry");
                    } else {
                        straw_linear.setVisibility(View.VISIBLE);
                        strawberry_plus.setVisibility(View.GONE);
                    }
                    if (orange_count == 0) {
                        orange_linear.setVisibility(View.GONE);
                        orange_plus.setVisibility(View.VISIBLE);
                        DeleteDB("orange");
                    } else {
                        orange_linear.setVisibility(View.VISIBLE);
                        orange_plus.setVisibility(View.GONE);
                    }
                }
            }
        });
    }



   /* public static void UpdateAdapter(Context context,int position, String name,String Image,int quantity){

        for(int i = 0; i< mMainItemsList.size(); i++){
            if(i == position){
                mMainItemsList.get(i).setImage(Image);
                mMainItemsList.get(i).setName(name);
                mMainItemsList.get(i).setQuantity(quantity);
                DeleteDB(mMainItemsList.get(position).getName());
                addDB(new CartEntity(mMainItemsList.get(position).getName(),quantity,5));

                Log.e("TESTING","2 "+ mMainItemsList.get(position).getName()+" "+ mMainItemsList.get(position).getQuantity());
                myAdapter.notifyDataSetChanged();
            }
        }

    */
}