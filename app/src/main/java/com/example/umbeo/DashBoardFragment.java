package com.example.umbeo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.UsersApi;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.CategoryResponse;
import com.example.umbeo.response_data.GetOrders.Product;
import com.example.umbeo.response_data.ProductResponse;
import com.example.umbeo.response_data.UserGetProfileResponse;
import com.example.umbeo.response_data.shop.ShopResponse;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.CartEntity;
import com.example.umbeo.room.ProductEntity;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.angeldevil.autoscrollviewpager.AutoScrollViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import spencerstudios.com.bungeelib.Bungee;

import static android.view.Gravity.END;
import static android.view.Gravity.START;
import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashBoardFragment extends Fragment implements TextWatcher{

    /////LinearLayout trending, popular, feature;
    List<com.example.umbeo.room.CategoryModel> modelList = new ArrayList<>();
    List<com.example.umbeo.room.CategoryModel> shotCat = new ArrayList<>();

    LinearLayout lichi, strawberry;
    AutoScrollViewPager mViewPager;
    CardView cat1, cat2, cat3, cat4;
    ImageView orange_plus, lichi_plus;
    FrameLayout strawberry_plus;

    List<String> categoryList = new ArrayList<>();
    TextView log, fruit;
    TextView address;
    CardView fruits;
    TextView welcome, trending_txt, popular_txt, feature_txt,noData;
    List<com.example.umbeo.room.CategoryModel> categoryModels = new ArrayList<>();

    static AppDatabase db;
    static int staw_count = 0, lichi_count = 0, orange_count = 0, quant = 0;
    LinearLayout straw_linear, orange_linear, lichi_linear;
    ImageView add, remove, add2, remove2, add3, remove3;
    TextView quantity, quantity3, quantity2;
    ViewPager viewPager2;
    List<ItemModel> mMainItemsList, mFruitsItem, mPersonalItems;
    RecyclerView category_list, item_recycler, list_category, list_category_fruit;
    ItemAdapter myAdapter;

    static List<com.example.umbeo.room.CategoryModel> categoryModel = new ArrayList<>();
    private float total = 0;
    List<CategoryModel> categoryModelList = new ArrayList<>();
    List<CategoryModel> categoryModelArrayList = new ArrayList<>();
    CardView see_more;
    CategoryListAdapter categoryListAdapter, categoryListAdapter2;

    ProgressBar simpleProgressBar;

    List<CartEntity> entities = new ArrayList<>();
    List<ProductEntity> productModels = new ArrayList<>();
    List<ProductEntity> productEntityList = new ArrayList<>();

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
    HorizontalScrollView scroll;
    ScrollView main_scroll;
    private InputMethodManager imm;
    EditText search;
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

            //LoadAllDB();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        preference = new UserPreference(getContext());
        if (preference.getTheme() == 1) {
            return inflater.inflate(R.layout.dark_dashboard, container, false);
        } else return inflater.inflate(R.layout.activity_dashboard, container, false);
    }
    public static AppCompatAutoCompleteTextView autoComplete;
    List<String> itemList = new ArrayList<String>();

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        categoryModelList = new ArrayList<>();

        item_recycler = v.findViewById(R.id.item_recycler);
        category_list = v.findViewById(R.id.category_list);
        list_category = v.findViewById(R.id.list_category);
        welcome = v.findViewById(R.id.welcome);
        simpleProgressBar = v.findViewById(R.id.simpleProgressBar);
        noData = v.findViewById(R.id.noData);


        if (db == null) {
            db = AppDatabase.getInstance(getContext());
        }
        shopData();
        //getProducts(preference.getShopId());

        Loadall();


        showProgress();

        //LoadAllDB();

       // getCategory();

        //Loadall();


        log = (TextView) v.findViewById(R.id.login);
        autoComplete = v.findViewById(R.id.search);
        autoComplete.clearFocus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (preference.getUserName() != null) {
            log.setText(preference.getUserName());
            log.setTextSize(20);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = START;
            params.setMargins(0, 50, 0, 0);
            log.setLayoutParams(params);
        } else {
            log.setText("Log in / Signup");
            log.setGravity(END);
        }


        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>
                (getContext(),R.layout.item_search,itemList);
        autoComplete.setThreshold(1);//will start working from first character
        autoComplete.setAdapter(adapter1);
        autoComplete.addTextChangedListener(this);

        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("SEARCHLIST","select: "+autoComplete.getText().toString());
                try {
                    filter(autoComplete.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (log.getText().toString().contains("Log")) {
                    startActivity(new Intent(getActivity(), login.class));
                    Bungee.fade(getContext());
                }
            }
        });


        trending_txt = v.findViewById(R.id.trending_txt);
        feature_txt = v.findViewById(R.id.feature_txt);
        popular_txt = v.findViewById(R.id.popular_txt);

        popular_txt.setText("On Sale");
        trending_txt.setText("Trending");
        feature_txt.setText("Deal of the day");

        getTrendingProducts();

        if (preference.getTheme() == 1) {
            noData.setVisibility(View.GONE);

            feature_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            feature_txt.setBackgroundResource(R.drawable.bg_feature_card2);

            trending_txt.setBackgroundResource(R.drawable.bg_feature_card);
            trending_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            trending_txt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7F8E24")));
            popular_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            popular_txt.setBackgroundResource(R.drawable.bg_feature_card2);


            trending_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noData.setVisibility(View.GONE);

                    feature_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    feature_txt.setBackgroundResource(R.drawable.bg_feature_card2);

                    trending_txt.setBackgroundResource(R.drawable.bg_feature_card);
                    trending_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    trending_txt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7F8E24")));
                    popular_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    popular_txt.setBackgroundResource(R.drawable.bg_feature_card2);


                    getTrendingProducts();

                }
            });
            feature_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noData.setVisibility(View.GONE);

                    trending_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    trending_txt.setBackgroundResource(R.drawable.bg_feature_card2);

                    feature_txt.setBackgroundResource(R.drawable.bg_feature_card);
                    feature_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    feature_txt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7F8E24")));
                    popular_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    popular_txt.setBackgroundResource(R.drawable.bg_feature_card2);

                    getRecommendedProducts();
                }
            });
            popular_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noData.setVisibility(View.GONE);

                    trending_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    trending_txt.setBackgroundResource(R.drawable.bg_feature_card2);

                    popular_txt.setBackgroundResource(R.drawable.bg_feature_card);
                    popular_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    popular_txt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7F8E24")));
                    feature_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    feature_txt.setBackgroundResource(R.drawable.bg_feature_card2);

                    if (preference.getEmail() != null) {
                        getFeaturedProducts();
                    }// else getTrendingProducts();


                    getFeaturedProducts();
                }
            });
        } else {
            noData.setVisibility(View.GONE);

            feature_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#7F8E24")));
            feature_txt.setBackgroundResource(R.drawable.bg_feature_card2);

            trending_txt.setBackgroundResource(R.drawable.bg_feature_card);
            trending_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            trending_txt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7F8E24")));
            popular_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#7F8E24")));
            popular_txt.setBackgroundResource(R.drawable.bg_feature_card2);


            trending_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noData.setVisibility(View.GONE);

                    feature_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#7F8E24")));
                    feature_txt.setBackgroundResource(R.drawable.bg_feature_card2);

                    trending_txt.setBackgroundResource(R.drawable.bg_feature_card);
                    trending_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    trending_txt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7F8E24")));
                    popular_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#7F8E24")));
                    popular_txt.setBackgroundResource(R.drawable.bg_feature_card2);


                    getTrendingProducts();

                }
            });
            feature_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noData.setVisibility(View.GONE);

                    trending_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#7F8E24")));
                    trending_txt.setBackgroundResource(R.drawable.bg_feature_card2);

                    feature_txt.setBackgroundResource(R.drawable.bg_feature_card);
                    feature_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    feature_txt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7F8E24")));
                    popular_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#7F8E24")));
                    popular_txt.setBackgroundResource(R.drawable.bg_feature_card2);

                    getRecommendedProducts();
                }
            });
            popular_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noData.setVisibility(View.GONE);

                    trending_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#7F8E24")));
                    trending_txt.setBackgroundResource(R.drawable.bg_feature_card2);

                    popular_txt.setBackgroundResource(R.drawable.bg_feature_card);
                    popular_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    popular_txt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7F8E24")));
                    feature_txt.setTextColor(ColorStateList.valueOf(Color.parseColor("#7F8E24")));
                    feature_txt.setBackgroundResource(R.drawable.bg_feature_card2);

                    if (preference.getEmail() != null) {
                        getFeaturedProducts();
                    } //else getTrendingProducts();


                    getFeaturedProducts();
                }
            });
        }


        mViewPager = v.findViewById(R.id.pager);

// This is just an example. You can use whatever collection of images.

        if (BuildConfig.USER_TYPE.equalsIgnoreCase("fashion")) {
            int[] mResources1 = {
                    R.drawable.banner1,
                    R.drawable.banner2,
            };
            CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(getContext(), mResources1);

            mViewPager.setAdapter(mCustomPagerAdapter);
            mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
            mViewPager.setScrollFactor(4);
            mViewPager.startAutoScroll(5000);
        } else {
            int[] mResources = {
                    R.drawable.back_1,
                    R.drawable.back_2,
                     R.drawable.back_3
            };
            CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(getContext(), mResources);

            mViewPager.setAdapter(mCustomPagerAdapter);
            mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
            mViewPager.setScrollFactor(4);
            mViewPager.startAutoScroll(5000);
        }

    }

    private void setItemList() {

        if(categoryModel!=null) {

            try {
                itemList.clear();
                for (com.example.umbeo.room.CategoryModel c : categoryModel) {
                    itemList.add(c.getCategoryName());
                }
            } catch (Exception e) {
                e.printStackTrace();
                shopData();

            }
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < categoryModel.size(); i++) {
                        productModels = new ArrayList<>();
                        productModels = db.productDao().findById(categoryModel.get(i).getCategoryId(), true);
                        Log.e("SEARCHLIST", "2  " + productModels.toString());
                        productEntityList.addAll(productModels);
                        for (ProductEntity p : productModels) {
                            itemList.add(p.getName());
                        }
                        Log.e("SEARCHLIST", i + " " + itemList.toString());
                    }
                }
            });
            Log.e("SEARCHLIST", itemList.toString());
        }
    }

    private void hideDefaultKeyboard(View et) {
        getMethodManager().hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    private InputMethodManager getMethodManager() {
        if (this.imm == null) {
            this.imm = (InputMethodManager) getContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        }
        return this.imm;
    }

    private void getCategoryProduct(final String categoryName, final String categoryId) {

        if (productModels.size() == 0 || categoryModelList.size() == 0) {
            showProgress();
            RetrofitClient api_manager = new RetrofitClient();
            UsersApi retrofit_interface = api_manager.usersClient().create(UsersApi.class);
            Call<ProductResponse> call = retrofit_interface.fetchAllProducts(preference.getShopId(), categoryId);
            call.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                    try {
                        Log.e("ProductResponse", response + "");
                        Log.e("ProductResponse", response.code() + "");
                        Log.e("ProductResponse", response.message() + "");
                        Log.e("ProductResponse", response.body().getStatus() + "");
                        if (response.code() == 200) {
                            productModels = new ArrayList<>();

                            productModels = response.body().getData().getProducts();
                            categoryModelList.add(new CategoryModel(categoryId, categoryName, productModels));
                            Log.e("categoryData","--- "+categoryModelList.size());

                            GridLayoutManager mGridLayoutManager2 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
                            list_category.setLayoutManager(mGridLayoutManager2);
                            categoryListAdapter = new CategoryListAdapter(categoryModelList, getContext());
                            list_category.setAdapter(categoryListAdapter);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        //LoadAllDB();
                    } finally {
                        HideProgress();
                    }
                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                }
            });
        }

    }

  /*  private void getCategory(final List<String> categoryList) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
              shotCat =  db.productDao().loadAllCategory();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        MainCategoriesAdapter adapter = new MainCategoriesAdapter(shotCat, getContext());
        category_list.setLayoutManager(linearLayoutManager);
        category_list.setAdapter(adapter);

        if(shotCat.size()==0) {
            showProgress();
            RetrofitClient api_manager = new RetrofitClient();
            UsersApi retrofit_interface = api_manager.usersClient().create(UsersApi.class);

            Call<CategoryResponse> call = retrofit_interface.fetchAllCategory();
            call.enqueue(new Callback<CategoryResponse>() {
                @Override
                public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                    try {
                        Log.e("CategoryResponse", response + "");
                        Log.e("CategoryResponse", response.code() + "");
                        Log.e("CategoryResponse", response.message() + "");
                        if (response.code() == 200) {
                            categoryModel = new ArrayList<>();
                            categoryModel = response.body().getData().getCategories();

                            final List<com.example.umbeo.room.CategoryModel> shotCat = new ArrayList<>();

                            for (int k = 0; k < categoryList.size(); k++) {
                                for (int i = 0; i < categoryModel.size(); i++) {

                                    if (DashBoardFragment.this.categoryList.get(k).equalsIgnoreCase(categoryModel.get(i).getCategoryId())) {
                                        getCategoryProduct(categoryModel.get(i).getCategoryName(), categoryModel.get(i).getCategoryId());
                                        shotCat.add(categoryModel.get(i));
                                    }
                                }
                            }
                            List<String> strings = new ArrayList<>();
                            for (int k = 0; k < shotCat.size(); k++) {
                                strings.add(shotCat.get(k).getCategoryName());
                            }
                            preference.setShopCategoryName(strings);

                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    db.productDao().nukeCategory();
                                    db.productDao().insertAllCategory(shotCat);
                                }
                            });

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                            MainCategoriesAdapter adapter = new MainCategoriesAdapter(shotCat, getContext());
                            category_list.setLayoutManager(linearLayoutManager);
                            category_list.setAdapter(adapter);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        HideProgress();
                    }
                }

                @Override
                public void onFailure(Call<CategoryResponse> call, Throwable t) {
                    getCategory(categoryList);
                }
            });
        }
    }

   */

    private void Loadall() {
        modelList = new ArrayList<>();
        productModels = new ArrayList<>();
        categoryModelList = new ArrayList<>();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                        categoryModel = db.productDao().loadAllCategory();

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                        MainCategoriesAdapter adapter = new MainCategoriesAdapter(categoryModel, getContext());
                        category_list.setLayoutManager(linearLayoutManager);
                        category_list.setAdapter(adapter);


                                try {
                                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                        @Override
                                        public void run() {

                                            Log.e("categoryData", String.valueOf(categoryModel.size()));
                                            int i=categoryModel.size();
                                            for (com.example.umbeo.room.CategoryModel c:categoryModel) {
                                                Log.e("categoryData", c.getCategoryName().toString());
                                                getCategoryProduct(c.getCategoryName(), c.getCategoryId());
                                            }
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
        setItemList();
    }
    private void shopData(){
        RetrofitClient api_manager = new RetrofitClient();
        UsersApi retrofit_interface =api_manager.usersClient().create(UsersApi.class);

        Call<ShopResponse> call= retrofit_interface.getShopProfile(preference.getShopId());

        call.enqueue(new Callback<ShopResponse>() {
            @Override
            public void onResponse(Call<ShopResponse> call, final Response<ShopResponse> response) {
                try {
                    Log.e("shopResponse", response.body().getStatus() + "");
                    Log.e("shopResponse", response.code() + "");
                    Log.e("shopResponse", response.message() + "");
                    Log.e("shopResponse",response.body().getData().getCategories().toString());

                    preference.setShopTimeSlot(response.body().getData().getDeliverySlots());
                    preference.setShopDeliveryCharges(response.body().getData().getDeliveryCharges());
                    preference.setShopPh(response.body().getData().getPhone());
                    preference.setShopCategory(response.body().getData().getCategories());
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            db.productDao().nukeCategory();
                        }
                    });

                    DashBoardFragment.categoryModel = new ArrayList<>();
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            db.productDao().insertAllCategory(response.body().getCategories());
                            DashBoardFragment.categoryModel = db.productDao().loadAllCategory();
                            Log.e("SEARCHLIST","1.0  "+response.body().getCategories().toString()+"\n"+DashBoardFragment.categoryModel.toString());

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ShopResponse> call, Throwable t) {
                Log.e("shopResponse",t.getLocalizedMessage()+"");
            }
        });
    }

    private void getFeaturedProducts() {
        productModels = new ArrayList<>();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                productModels = db.productDao().findByFeature(true,true);
            }
        });
        if(productModels.size()==0){
            noData.setVisibility(VISIBLE);
            noData.setText("No products on sale");
        }
        else noData.setVisibility(View.GONE);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false);

        item_recycler.setLayoutManager(mGridLayoutManager);

        myAdapter = new ItemAdapter(productModels, getContext());
        item_recycler.setAdapter(myAdapter);

        if(productModels.size()==0) {
            showProgress();

            RetrofitClient api_manager = new RetrofitClient();
            UsersApi retrofit_interface = api_manager.usersClient().create(UsersApi.class);

            Call<ProductResponse> call = retrofit_interface.fetchFeaturedProducts(preference.getShopId());

            call.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                    try {
                        Log.e("FeaturedProducts", response + "");
                        Log.e("FeaturedProducts", response.code() + "");
                        Log.e("FeaturedProducts", response.message() + "");
                        if (response.code() == 200) {
                            List<ProductEntity> productModels = response.body().getData().getProducts();
                            if(productModels.size()==0){
                                noData.setVisibility(VISIBLE);
                                noData.setText("No products on sale");
                            }
                            else noData.setVisibility(View.GONE);
                            Log.e("FeaturedProducts", productModels.get(0).getName() + "");
                            GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false);

                            item_recycler.setLayoutManager(mGridLayoutManager);

                            myAdapter = new ItemAdapter(productModels, getContext());
                            item_recycler.setAdapter(myAdapter);


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        HideProgress();
                    }
                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                    getFeaturedProducts();
                }
            });
        }
    }

    private void getTrendingProducts() {

        productModels = new ArrayList<>();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                productModels = db.productDao().findByTrending(true,true);
            }
        });


        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false);

        item_recycler.setLayoutManager(mGridLayoutManager);

        myAdapter = new ItemAdapter(productModels, getContext());
        item_recycler.setAdapter(myAdapter);

        if(productModels.size()==0) {
            showProgress();

            RetrofitClient api_manager = new RetrofitClient();
            UsersApi retrofit_interface = api_manager.usersClient().create(UsersApi.class);

            Call<ProductResponse> call = retrofit_interface.fetchTrendingProducts(preference.getShopId());

            call.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                    try {
                        Log.e("TrendingProduct", response + "");
                        Log.e("TrendingProduct", response.code() + "");
                        Log.e("TrendingProduct", response.message() + "");
                        if (response.code() == 200) {
                            final List<ProductEntity> productModels = response.body().getData().getProducts();
                            Log.e("TrendingProduct", productModels.get(0).getName() + "");

                            GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false);
                            item_recycler.setLayoutManager(mGridLayoutManager);

                            ItemAdapter myAdapter = new ItemAdapter(productModels, getContext());
                            item_recycler.setAdapter(myAdapter);

                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < productModels.size(); i++) {
                                        db.productDao().UpdateTrending(productModels.get(i).getId(), true);
                                    }
                                }
                            });


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        HideProgress();
                    }
                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                    getTrendingProducts();

                }
            });
        }
    }

    private void getRecommendedProducts() {

        productModels = new ArrayList<>();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                productModels = db.productDao().findByRecommended(true,true);

            }
        });
        if(productModels.size()<=0){
            noData.setVisibility(VISIBLE);
            noData.setText("No deals of the day");
        }else noData.setVisibility(View.GONE);


        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false);

        item_recycler.setLayoutManager(mGridLayoutManager);

        myAdapter = new ItemAdapter(productModels, getContext());
        item_recycler.setAdapter(myAdapter);

        if(productModels.size()==0) {
            showProgress();

            RetrofitClient api_manager = new RetrofitClient();
            UsersApi retrofit_interface = api_manager.usersClient().create(UsersApi.class);

            Call<ProductResponse> call = retrofit_interface.fetchRecommendedProducts(preference.getShopId(), preference.getUserId());

            call.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                    try {
                        Log.e("RecommendedProducts", response + "");
                        Log.e("RecommendedProducts", response.code() + "");
                        Log.e("RecommendedProducts", response.message() + "");
                        if (response.code() == 200) {

                            final List<ProductEntity> productModels = response.body().getData().getProducts();

                            if(productModels.size()<=0){
                                noData.setVisibility(VISIBLE);
                                noData.setText("No deals of the day");
                            }else noData.setVisibility(View.GONE);

                            Log.e("RecommendedProducts", productModels.get(0).getName() + "");
                            GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false);

                            item_recycler.setLayoutManager(mGridLayoutManager);

                            ItemAdapter myAdapter = new ItemAdapter(productModels, getContext());
                            item_recycler.setAdapter(myAdapter);
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < productModels.size(); i++) {
                                        db.productDao().UpdateRecommended(productModels.get(i).getId(), true);
                                    }
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        HideProgress();
                    }
                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                    getRecommendedProducts();
                }
            });
        }
    }

    private void showProgress(){
        simpleProgressBar.setVisibility(VISIBLE);
    }

    private void HideProgress(){
        simpleProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        getProfile(preference.getToken());
        Log.e("signup",preference.getToken()+"      token");

        autoComplete.setText("");
        if (preference.getUserName() != null) {
            log.setText(preference.getUserName());
            log.setTextSize(20);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = START;
            params.setMargins(0,50,0,0);
            log.setLayoutParams(params);
        } else {
            log.setText("Log in / Signup");
            log.setGravity(END);
        }


    }

    public void getProfile(final String tokens){
        RetrofitClient api_manager = new RetrofitClient();
        UsersApi retrofit_interface =api_manager.usersClient().create(UsersApi.class);

        final String token = "Bearer "+tokens;
        Call<UserGetProfileResponse> call= retrofit_interface.getProfile(token);

        call.enqueue(new Callback<UserGetProfileResponse>() {

            @Override
            public void onResponse(Call<UserGetProfileResponse> call, Response<UserGetProfileResponse> response) {
                Log.e("signup",response.code()+"");
                Log.e("signup",response.message()+"");
                if(response.code()==200) {
                    preference.setUserName(response.body().getData().getName());
                    preference.setEmail(response.body().getData().getEmail());
                    preference.setLoyaltyPoints(response.body().getData().getLoyaltyPoints());
                    preference.setAddresses(response.body().getData().getDeliveryAddresses());
                    preference.setUserId(response.body().getData().getId());
                    preference.setProfilePic(response.body().getData().getProfile_pic());
                    preference.setToken(tokens);

                    if (preference.getUserName() != null) {
                        log.setText(preference.getUserName());
                        log.setTextSize(20);
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.gravity = START;
                        params.setMargins(0,50,0,0);
                        log.setLayoutParams(params);
                    } else {
                        log.setText("Log in / Signup");
                        log.setGravity(END);
                    }

                }
            }

            @Override
            public void onFailure(Call<UserGetProfileResponse> call, Throwable t) {
                    getProfile(tokens);
            }
        });
    }


    void filter(String text){
        List<CategoryModel> temp = new ArrayList<>();
        List<ProductEntity> product = new ArrayList<>();

        for(CategoryModel d: categoryModelList) {
            if (d.getCategoryName().toLowerCase().equalsIgnoreCase(text.toLowerCase())) {
                Intent intent = new Intent(getContext(),CategoryActivity.class);
                intent.putExtra("category_id",d.getCategoryId());
                intent.putExtra("category_name",d.getCategoryName());
                Gson gson = new Gson();
                String products = gson.toJson(d.getCategoryItems());
                intent.putExtra("category_prods",products);
                startActivity(intent);
            } else {
                product = new ArrayList<>();
                for (ProductEntity p : productEntityList) {
                    Log.e("SEARCHLIST", "5 " + productEntityList.toString());
                    if (p.getName().toLowerCase().equalsIgnoreCase(text.toLowerCase())) {
                        //temp.add(d);
                        product.add(p);
                        Log.e("SEARCHLIST", "511 " + p.getName());
                    }
                }
                if(product.size()>0){
                    try {
                        Log.e("SEARCH","prod:   "+product.toString());
                        Intent intent = new Intent(getContext(),CategoryActivity.class);
                        intent.putExtra("category_id","0");
                        intent.putExtra("category_name",text);
                        Gson gson = new Gson();
                        String products = gson.toJson(product);
                        intent.putExtra("category_prods",products);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        try {
            for(int i=0;i<=product.size()+1;i++){
                if(product.get(i).getName().equalsIgnoreCase(product.get(i+1).getName())){
                    product.remove(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       // updateList(temp,product,text);
    }

    public void updateList(List<CategoryModel> list,List<ProductEntity> product, String text){
        if(list.size()>0) {
            categoryModelList = list;
            Log.e("Testing", "4 " + categoryModelList.toString() + " " + list.toString());
            GridLayoutManager mGridLayoutManager2 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
            list_category.setLayoutManager(mGridLayoutManager2);
            categoryListAdapter = new CategoryListAdapter(categoryModelList, getContext());
            list_category.setAdapter(categoryListAdapter);
            categoryListAdapter.notifyDataSetChanged();


        }
        if(product.size()>0){
            Log.e("SEARCH","prod:   "+product.toString());
            Intent intent = new Intent(getContext(),CategoryActivity.class);
            intent.putExtra("category_id","0");
            intent.putExtra("category_name",text);
            Gson gson = new Gson();
            String products = gson.toJson(product);
            intent.putExtra("category_prods",products);
            startActivity(intent);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        // TODO Auto-generated method stub
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        // TODO Auto-generated method stub
    }

    @Override
    public void afterTextChanged(final Editable s) {
        if(s.toString().length()==0){
          ///  Loadall();
        }
    }

}