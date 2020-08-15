package com.example.umbeo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.UsersApi;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.ProductModel;
import com.example.umbeo.response_data.ProductResponse;
import com.example.umbeo.response_data.UserGetProfileResponse;
import com.example.umbeo.room.AppDatabase;
import com.example.umbeo.room.AppExecutors;
import com.example.umbeo.room.ProductEntity;
import com.example.umbeo.room.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoyaltyPointsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoyaltyPointsFragment extends Fragment implements View.OnClickListener {




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoyaltyPointsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoyaltyPointsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoyaltyPointsFragment newInstance(String param1, String param2) {
        LoyaltyPointsFragment fragment = new LoyaltyPointsFragment();
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
    UserPreference preference;

    List<Boolean> results;
    ProgressBar progress;


    TextView my_points;
    RecyclerView item_recycler;
    List<ItemModel> mPersonalItems;

    TextView text1,text2,text3;
    CardView card_view1,card_view2,card_view3;
    ImageView circular1,circular2,circular3;
    ImageView gem1,gem2,gem3;
    GridLayout grid;
    List<UserEntity> entityList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        preference = new UserPreference(getContext());

        results = new ArrayList<>();

        if(preference.getTheme()==1){
            return inflater.inflate(R.layout.dark_loyalty, container, false);
        }

        // Inflate the layout for this fragment
      else   return inflater.inflate(R.layout.fragment_loyalty_points, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preference = new UserPreference(getContext());

        if(preference.getEmail()!=null){
            getProfile();
        }

        progress = view.findViewById(R.id.progress);
        grid = view.findViewById(R.id.grid);

        text1 = view.findViewById(R.id.text1);
        text2 = view.findViewById(R.id.text2);
        text3 = view.findViewById(R.id.text3);

        card_view1 = view.findViewById(R.id.card_view1);
        card_view2 = view.findViewById(R.id.card_view2);
        card_view3 = view.findViewById(R.id.card_view3);

        circular1 = view.findViewById(R.id.circular1);
        circular2 = view.findViewById(R.id.circular2);
        circular3 = view.findViewById(R.id.circular3);

        gem1 = view.findViewById(R.id.gem1);
        gem2 = view.findViewById(R.id.gem2);
        gem3 = view.findViewById(R.id.gem3);

        if(preference.getTheme()==1){
            text1.setTextColor(Color.WHITE);
            text2.setTextColor(Color.WHITE);
            text3.setTextColor(Color.WHITE);
        }

        my_points = view.findViewById(R.id.my_points);

        if(preference.getLoyaltyPoints()!=0){
            int loyaltyPoint = preference.getLoyaltyPoints();
            int dollar = preference.getLoyaltyPoints()/100;
            my_points.setText(loyaltyPoint+" = $ "+dollar);
        }




        item_recycler = view.findViewById(R.id.item_recycler);
        GridLayoutManager mGridLayoutManager2 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        item_recycler.setLayoutManager(mGridLayoutManager2);

            getTrendingProducts();

   /*     mPersonalItems = new ArrayList<>();

        mPersonalItems.add(new ItemModel("Colgate", "pic_2", 0));
        mPersonalItems.add(new ItemModel("Hair oil", "pic_3", 0));



        List<CategoryModel> categoryModelList = new ArrayList<>();
        categoryModelList.add(new CategoryModel("Top Awarded Products", mPersonalItems));

        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(categoryModelList, getContext());
        item_recycler.setAdapter(categoryListAdapter);

    */




    }

    private void getTrendingProducts() {

        RetrofitClient api_manager = new RetrofitClient();
        UsersApi retrofit_interface =api_manager.usersClient().create(UsersApi.class);

        Call<ProductResponse> call = retrofit_interface.fetchTrendingProducts(preference.getShopId());

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                try {
                    Log.e("TrendingProduct",response+"");
                    Log.e("TrendingProduct",response.code()+"");
                    Log.e("TrendingProduct",response.message()+"");
                    if(response.code()==200){
                        List<ProductEntity> productModels = response.body().getData().getProducts();
                        Log.e("TrendingProduct",productModels.get(0).getName()+"");

                        List<CategoryModel> categoryModelList = new ArrayList<>();
                        categoryModelList.add(new CategoryModel("","Top Awarded Products", productModels));

                        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(categoryModelList, getContext());
                        item_recycler.setAdapter(categoryListAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
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
                    results = response.body().getData().getAchievements();

                    final List<UserEntity> entityList = new ArrayList<>();

                    for(int i =0;i<results.size();i++){
                        entityList.add(new UserEntity(results.get(i)));
                    }

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            AppDatabase.getInstance(getContext()).preferenceDao().insertLoyaltyAll(entityList);
                            for(int i = 0;i<entityList.size();i++){
                                Log.e("loyalty","room in  -----"+entityList.get(i).getLoyalty());
                            }
                        }
                    });
                    Log.e("Achievements",results.toString());
                    setColor();

                    progress.setVisibility(View.GONE);
                    grid.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<UserGetProfileResponse> call, Throwable t) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        entityList =  AppDatabase.getInstance(getContext()).preferenceDao().loadLoyaltyAll();
                        for(int i = 0;i<entityList.size();i++){
                            Log.e("loyalty","room get -----"+entityList.get(i).getLoyalty());
                        }
                    }
                });
                results = new ArrayList<>();
                for(int i =0;i<entityList.size();i++){
                    results.add(entityList.get(i).getLoyalty());
                }
                Log.e("loyalty","room get -----"+results.toString());

                setColor();
                progress.setVisibility(View.GONE);
                grid.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setColor() {
            if(results.size()>0 && results.get(0) && results.get(1) && results.get(2)) {
                card_view1.setOnClickListener(this);
                card_view2.setOnClickListener(this);
                card_view3.setOnClickListener(this);
            }
           else if(results.size()>0 && results.get(0) && results.get(1)){
                card_view3.setCardBackgroundColor(ColorStateList.valueOf(Color.parseColor("#555555")));

                circular3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF202020")));

                gem3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#000000")));


                card_view1.setOnClickListener(this);
                card_view2.setOnClickListener(this);
                card_view3.setOnClickListener(this);
           }
           else if(results.size()>0 && results.get(0)){
                card_view2.setCardBackgroundColor(ColorStateList.valueOf(Color.parseColor("#555555")));
                card_view3.setCardBackgroundColor(ColorStateList.valueOf(Color.parseColor("#555555")));

                circular2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF202020")));
                circular3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF202020")));

                gem2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                gem3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#000000")));

                card_view1.setOnClickListener(this);
                card_view2.setOnClickListener(this);
                card_view3.setOnClickListener(this);
            }
            else {
                card_view1.setCardBackgroundColor(ColorStateList.valueOf(Color.parseColor("#555555")));
                card_view2.setCardBackgroundColor(ColorStateList.valueOf(Color.parseColor("#555555")));
                card_view3.setCardBackgroundColor(ColorStateList.valueOf(Color.parseColor("#555555")));

                circular1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF202020")));
                circular2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF202020")));
                circular3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF202020")));

                gem1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                gem2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                gem3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#000000")));

                card_view1.setOnClickListener(this);
                card_view2.setOnClickListener(this);
                card_view3.setOnClickListener(this);
            }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_view1:{
                dailog(text1,card_view1,"Early Starter","100",circular1,gem1,R.drawable.ic_crystal);
            }break;
            case R.id.card_view2:{
                dailog(text2,card_view2,"Sparkling Star","500",circular2,gem2,R.drawable.new_three_crystal);
            }break;
            case R.id.card_view3:{
                dailog(text3,card_view3,"Hidden Gem","1000",circular3,gem3,R.drawable.new_five_crystal);
            }break;
        }
    }


    private void dailog(TextView textview, CardView card, String name, String points, ImageView circular1, ImageView gem1, int ic_crystal){
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert li != null;
        View mView = li.inflate(R.layout.loyalty_dailog, null);
        CardView card_view = mView.findViewById(R.id.card_view);
        LinearLayout main_linear = mView.findViewById(R.id.main_linear);
        TextView text = mView.findViewById(R.id.text);
        TextView title = mView.findViewById(R.id.title);
        TextView point = mView.findViewById(R.id.points);
        ImageView circular = mView.findViewById(R.id.circular);
        ImageView gem = mView.findViewById(R.id.gem);
        title.setText(name+"");
        point.setText(points+" Crystals");
        card_view.setCardBackgroundColor(card.getCardBackgroundColor());
        circular.setImageTintList(circular1.getImageTintList());
        gem.setImageTintList(gem1.getImageTintList());
        gem.setImageResource(ic_crystal);

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.50);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.50);

        text.setText(textview.getText().toString());


        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        dialog.getWindow().setLayout(width, height);
        dialog.show();

        main_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }
}