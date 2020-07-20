package com.example.umbeo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

import static android.view.Gravity.END;
import static android.view.Gravity.START;

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
    TextView welcome, trending_txt, popular_txt, feature_txt;

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


    List<CartEntity> entities = new ArrayList<>();
    List<ProductModel> productModels = new ArrayList<>();

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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        preference = new UserPreference(getContext());
        if(preference.getTheme()==1){
            return inflater.inflate(R.layout.dark_dashboard, container, false);
        }
        else return inflater.inflate(R.layout.activity_dashboard, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        item_recycler = v.findViewById(R.id.item_recycler);
        category_list = v.findViewById(R.id.category_list);
        list_category = v.findViewById(R.id.list_category);
        list_category_fruit = v.findViewById(R.id.list_category_fruit);
        welcome = v.findViewById(R.id.welcome);
        scroll = v.findViewById(R.id.scroll);
        main_scroll = v.findViewById(R.id.main_scroll);
        log = (TextView) v.findViewById(R.id.login);


        if (db == null) {
            db = AppDatabase.getInstance(getContext());
        }

        LoadAllDB();

        getCategory();

        GridLayoutManager mGridLayoutManager2 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        list_category.setLayoutManager(mGridLayoutManager2);

        if (preference.getUserName() != null) {
            log.setText(preference.getUserName());
            log.setTextSize(20);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = START;
            params.setMargins(0,40,0,0);
            log.setLayoutParams(params);
        } else {
            log.setText("Log in / Signup");
            log.setGravity(END);
        }

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(log.getText().toString().contains("Log")) {
                    startActivity(new Intent(getActivity(), login.class));
                }
            }
        });


        see_more = v.findViewById(R.id.see_more);
        see_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CategoryActivity.class);
                i.putExtra("Cat", 1);
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

        trending_txt = v.findViewById(R.id.trending_txt);
        feature_txt = v.findViewById(R.id.feature_txt);
        popular_txt = v.findViewById(R.id.popular_txt);

        getFeaturedProducts();

        if(preference.getTheme()==1){
            Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
            Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
            DrawableCompat.setTint(wrappedDrawable, getContext().getColor(R.color.colorWhite));
            popular_txt.setBackground(wrappedDrawable);
            trending_txt.setBackground(wrappedDrawable);


            Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
            Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
            DrawableCompat.setTint(wrappedDrawable2, getContext().getColor(R.color.purple));
            feature_txt.setBackground(wrappedDrawable2);

            trending_txt.setTextColor(getContext().getColor(R.color.colorWhite));
            feature_txt.setTextColor(getContext().getColor(R.color.purple));
            popular_txt.setTextColor(getContext().getColor(R.color.colorWhite));


            trending_txt.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
                    Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                    DrawableCompat.setTint(wrappedDrawable, getContext().getColor(R.color.colorWhite));
                    popular_txt.setBackground(wrappedDrawable);
                    feature_txt.setBackground(wrappedDrawable);


                    Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
                    Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
                    DrawableCompat.setTint(wrappedDrawable2, getContext().getColor(R.color.purple));
                    trending_txt.setBackground(wrappedDrawable2);

                    trending_txt.setTextColor(getContext().getColor(R.color.purple));
                    feature_txt.setTextColor(getContext().getColor(R.color.colorWhite));
                    popular_txt.setTextColor(getContext().getColor(R.color.colorWhite));

                    getTrendingProducts();

                }
            });
            feature_txt.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
                    Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                    DrawableCompat.setTint(wrappedDrawable, getContext().getColor(R.color.colorWhite));
                    popular_txt.setBackground(wrappedDrawable);
                    trending_txt.setBackground(wrappedDrawable);


                    Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
                    Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
                    DrawableCompat.setTint(wrappedDrawable2, getContext().getColor(R.color.purple));
                    feature_txt.setBackground(wrappedDrawable2);

                    trending_txt.setTextColor(getContext().getColor(R.color.colorWhite));
                    feature_txt.setTextColor(getContext().getColor(R.color.purple));
                    popular_txt.setTextColor(getContext().getColor(R.color.colorWhite));

                    getFeaturedProducts();
                }
            });
            popular_txt.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
                    Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                    DrawableCompat.setTint(wrappedDrawable, getContext().getColor(R.color.colorWhite));
                    trending_txt.setBackground(wrappedDrawable);
                    feature_txt.setBackground(wrappedDrawable);


                    Drawable unwrappedDrawable2 = AppCompatResources.getDrawable(getContext(), R.drawable.bg_feature_card);
                    Drawable wrappedDrawable2 = DrawableCompat.wrap(unwrappedDrawable2);
                    DrawableCompat.setTint(wrappedDrawable2, getContext().getColor(R.color.purple));
                    popular_txt.setBackground(wrappedDrawable2);

                    trending_txt.setTextColor(getContext().getColor(R.color.colorWhite));
                    feature_txt.setTextColor(getContext().getColor(R.color.colorWhite));
                    popular_txt.setTextColor(getContext().getColor(R.color.purple));


                    if(preference.getEmail()!=null){
                        getRecommendedProducts();
                    }
                    else getTrendingProducts();
                }
            });
        }

        else {
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

                    getTrendingProducts();

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

                    getFeaturedProducts();
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


                    if(preference.getEmail()!=null){
                        getRecommendedProducts();
                    }
                    else getTrendingProducts();
                }
            });
        }
        mViewPager = v.findViewById(R.id.pager);
        int[] mResources = {
                R.drawable.bananas1,
                R.drawable.strawberries,
                R.drawable.orange,
                R.drawable.lichi,
        };

        CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(getContext(), mResources);

        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.startAutoScroll(5000);
    }

    private void getCategoryProduct(final String categoryName, final String categoryId) {

        final Utils.CustomDialog customDialog = new Utils.CustomDialog();
        customDialog.showProgress(getContext(),"Loading...","Please Wait");


        RetrofitClient api_manager = new RetrofitClient();
        Api retrofit_interface =api_manager.usersClient().create(Api.class);

        Call<ProductResponse> call = retrofit_interface.fetchAllProducts(preference.getShopId(),categoryId);

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                try {
                    Log.e("ProductResponse",response+"");
                    Log.e("ProductResponse",response.code()+"");
                    Log.e("ProductResponse",response.message()+"");
                    Log.e("ProductResponse",response.body().getStatus()+"");
                    if(response.code()==200){
                        productModels = new ArrayList<>();
                        //categoryModelList = new ArrayList<>();

                        productModels = response.body().getData().getProducts();

                        categoryModelList.add(new CategoryModel(categoryId,categoryName, productModels));

                        GridLayoutManager mGridLayoutManager2 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
                        list_category.setLayoutManager(mGridLayoutManager2);
                        categoryListAdapter = new CategoryListAdapter(categoryModelList, getContext());
                        list_category.setAdapter(categoryListAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    customDialog.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                customDialog.hideProgress();
            }
        });
    }

    private void getCategory() {
        final Utils.CustomDialog customDialog = new Utils.CustomDialog();
        customDialog.showProgress(getContext(),"Loading...","Please Wait");

        RetrofitClient api_manager = new RetrofitClient();
        Api retrofit_interface =api_manager.usersClient().create(Api.class);

        Call<CategoryResponse> call = retrofit_interface.fetchAllCategory();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                try {
                    Log.e("CategoryResponse",response+"");
                    Log.e("CategoryResponse",response.code()+"");
                    Log.e("CategoryResponse",response.message()+"");
                    if(response.code()==200){
                        categoryModel = new ArrayList<>();
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
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    customDialog.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                customDialog.hideProgress();
            }
        });

    }



    private void LoadAllDB() {
        db.cartDao().getAll().observe(DashBoardFragment.this, new Observer<List<CartEntity>>() {
            @Override
            public void onChanged(List<CartEntity> cartEntities) {
                entities = cartEntities;
            }
        });

    }

    private void getFeaturedProducts() {
        final Utils.CustomDialog customDialog = new Utils.CustomDialog();
        customDialog.showProgress(getContext(),"Loading...","Please Wait");

        RetrofitClient api_manager = new RetrofitClient();
        Api retrofit_interface =api_manager.usersClient().create(Api.class);

        Call<ProductResponse> call = retrofit_interface.fetchFeaturedProducts(preference.getShopId());

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                try {
                    Log.e("FeaturedProducts",response+"");
                    Log.e("FeaturedProducts",response.code()+"");
                    Log.e("FeaturedProducts",response.message()+"");
                    if(response.code()==200){
                        List<ProductModel> productModels = response.body().getData().getProducts();
                        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
                        item_recycler.setLayoutManager(mGridLayoutManager);

                        myAdapter = new ItemAdapter(productModels, getContext());
                        item_recycler.setAdapter(myAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    customDialog.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                customDialog.hideProgress();
            }
        });
    }

    private void getTrendingProducts() {
        final Utils.CustomDialog customDialog = new Utils.CustomDialog();
        customDialog.showProgress(getContext(),"Loading...","Please Wait");

        RetrofitClient api_manager = new RetrofitClient();
        Api retrofit_interface =api_manager.usersClient().create(Api.class);

        Call<ProductResponse> call = retrofit_interface.fetchTrendingProducts(preference.getShopId());

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                try {
                    Log.e("TrendingProduct",response+"");
                    Log.e("TrendingProduct",response.code()+"");
                    Log.e("TrendingProduct",response.message()+"");
                    if(response.code()==200){
                       List<ProductModel> productModels = response.body().getData().getProducts();
                        Log.e("TrendingProduct",productModels.get(0).getName()+"");

                  /*      for(int i = 0;i<productModels.size();i++) {
                            for (CartEntity e : entities) {
                                if (e.getName().equalsIgnoreCase(productModels.get(i).getName())) {
                                    ProductModel productModel = new ProductModel(e.getName(),e.getCategoryId()
                                            ,e.getSubCategoryId(),productModels.get(i).getPrice(),e.getDescription(),e.getQuantity(),productModels.get(i).getDiscount(),productModels.get(i).getImage());

                                    productModels.remove(i);
                                    productModels.add(productModel);
                                }
                            }
                        }

                   */

                        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
                        item_recycler.setLayoutManager(mGridLayoutManager);

                       ItemAdapter myAdapter = new ItemAdapter(productModels, getContext());
                        item_recycler.setAdapter(myAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    customDialog.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                customDialog.hideProgress();
            }
        });
    }

    private void getRecommendedProducts() {
        final Utils.CustomDialog customDialog = new Utils.CustomDialog();
        customDialog.showProgress(getContext(),"Loading...","Please Wait");

        RetrofitClient api_manager = new RetrofitClient();
        Api retrofit_interface =api_manager.usersClient().create(Api.class);

        Call<ProductResponse> call = retrofit_interface.fetchRecommendedProducts(preference.getShopId(),preference.getUserId());

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                try {
                    Log.e("RecommendedProducts",response+"");
                    Log.e("RecommendedProducts",response.code()+"");
                    Log.e("RecommendedProducts",response.message()+"");
                    if(response.code()==200){

                       List<ProductModel> productModels = response.body().getData().getProducts();

                        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
                        item_recycler.setLayoutManager(mGridLayoutManager);

                        ItemAdapter myAdapter = new ItemAdapter(productModels, getContext());
                        item_recycler.setAdapter(myAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    customDialog.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                customDialog.hideProgress();
            }
        });
    }

}